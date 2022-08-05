package jp.anmt.silent.tapsilent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ExeActivity extends AppCompatActivity {
    private static final String TAG = "ExeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exe);

        if (Param.D) Log.d(TAG, "ExeActivity()");

        executeChangeVolume();

        finish();
    }

    private boolean executeChangeVolume() {
        boolean ret = true;
        boolean isSilent = PrefUtils.readPrefBool(this, PrefUtils.KEY_SETTING_IS_SILENT);
        boolean noAlarm = PrefUtils.readPrefBool(this, PrefUtils.KEY_SETTING_NO_ALARM);
        boolean noMusic = PrefUtils.readPrefBool(this, PrefUtils.KEY_SETTING_NO_MUSIC);

        // AudioManagerを取得する
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if (am == null) {
            if (Param.D) Log.d(TAG, "executeChangeVolume() AudioManager is null!");
            return false;
        }
        if (nm == null) {
            if (Param.D) Log.d(TAG, "executeChangeVolume() NotificationManager is null!");
            return false;
        }

        if (Param.D) Log.d(TAG, "executeChangeVolume()" + " silent:" + isSilent + " noAlarm:" + noAlarm + " noMusic:" + noMusic);

        if (!isSilent) {
            // 消音にする

            int[] volArray = new int[Param.STREAM_TYPE_MAX];

            // 現在の設定値取得と強制ゼロ設定
            for (int i = 0; i < Param.STREAM_TYPE_MAX; i++) {
                switch (i) {
                    case Param.STREAM_TYPE_ALARM:
                        if (noAlarm) continue;
                        break;
                    case Param.STREAM_TYPE_MUSIC:
                        if (noMusic) continue;
                        break;
                    default:
                        break;
                }

                // 音量取得
                volArray[i] = am.getStreamVolume(Param.AM_STREAM_TYPE[i]);

                if (Param.D) Log.d(TAG, "executeChangeVolume() set silent. id:" + i + " stream:" + Param.AM_STREAM_TYPE[i] + " before:" + volArray[i]);
            }

            // 対象ストリームを0に設定
            // getStreamVolume直後にひとつずつsetStreamVolumeするとnotify,ring,system音量がゼロになるため
            // 一度全部getStreamVolumeしてからまとめてsetStreamVolumeする
            for (int i = 0; i < Param.STREAM_TYPE_MAX; i++) {
                switch (i) {
                    case Param.STREAM_TYPE_ALARM:
                        if (noAlarm) continue;
                        break;
                    case Param.STREAM_TYPE_MUSIC:
                        if (noMusic) continue;
                        break;
                    default:
                        break;
                }

                // 対象ストリームの音量をゼロに変更
                // 0以下はスキップ
                if (volArray[i] > 0) {
                    am.setStreamVolume(Param.AM_STREAM_TYPE[i], 0, 0);
                }
            }

            // もともとの設定値を記憶
            PrefUtils.writePrefsVol(this, volArray, noAlarm, noMusic);

            // 消音中状態に設定
            PrefUtils.writePrefBool(this, PrefUtils.KEY_SETTING_IS_SILENT, true);

        } else {
            // 元に戻す

            int[] volArray = PrefUtils.readPrefsVol(this);

            for (int i = 0; i < Param.STREAM_TYPE_MAX; i++) {
                switch (i) {
                    case Param.STREAM_TYPE_ALARM:
                        if (noAlarm) continue;
                        break;
                    case Param.STREAM_TYPE_MUSIC:
                        if (noMusic) continue;
                        break;
                    default:
                        break;
                }

                // 対象ストリームにもとの音量を設定
                // 0以下はスキップ
                if (volArray[i] > 0) {
                    am.setStreamVolume(Param.AM_STREAM_TYPE[i], volArray[i], 0);
                }

                if (Param.D) Log.d(TAG, "executeChangeVolume() reverse volume. id:" + i + " stream:" + Param.AM_STREAM_TYPE[i] + " set:" + volArray[i]);
            }

            // 消音中状態を解除
            PrefUtils.writePrefBool(this, PrefUtils.KEY_SETTING_IS_SILENT, false);
        }

        // トースト表示
        showChangeVolumeToast(!isSilent);

        // ウィジェット画像の変更
        updateWidgetImage(!isSilent);

        return ret;
    }

    // ウィジェット画像の更新
    private void updateWidgetImage(boolean isSilent) {
        int resId = 0;
        if (isSilent) {
            resId = R.drawable.widget_vol_off;
        } else {
            resId = R.drawable.widget_vol_on;
        }

        // 画像リソースをViewにセット
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.silent_widget);
        views.setImageViewResource(R.id.widget_image_main, resId);

        Log.d(TAG, "updateWidgetImage() views:" + views);

        // ウィジェットの更新
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, SilentWidget.class));
        for (int appWidgetId : appWidgetIds) {
            Log.d(TAG, "updateWidgetImage() updateAppWidget id:" + appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    // トースト出力
    private void showChangeVolumeToast(boolean isSilent) {
        int resId = 0;
        if (isSilent) {
            resId = R.string.toast_volume_zero;
        } else {
            resId = R.string.toast_volume_restored;
        }

        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

}