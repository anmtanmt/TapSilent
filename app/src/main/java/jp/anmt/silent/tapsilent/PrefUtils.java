package jp.anmt.silent.tapsilent;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Created by numata on 2017/05/04.
 */

public class PrefUtils {
    // 設定
    public static final String KEY_SETTING_IS_SILENT = "key_is_silent"; //消音中か否か
    public static final String KEY_SETTING_NO_ALARM = "key_no_alarm"; //アラーム音量も含めるか否か
    public static final String KEY_SETTING_NO_MUSIC = "key_no_music"; //音楽再生音量も含めるか否か

    // 音量
    public static final String KEY_VOL_RING = "key_volume_ring"; //着信音量
    public static final String KEY_VOL_MUSIC = "key_volume_music"; //音楽再生音量
    public static final String KEY_VOL_NOTIFY = "key_volume_notify"; //通知音量
    public static final String KEY_VOL_ALARM = "key_volume_alarm"; //アラーム音量
    public static final String KEY_VOL_DTMF = "key_volume_dtmf"; //DTMF音量
    public static final String KEY_VOL_SYSTEM = "key_volume_system"; //システム音量
    public static final String KEY_VOL_ACCESSIBILITY = "key_volume_accessibility"; //システム音量

    public static final String[] PREFS_KEY_VOL = {
            KEY_VOL_RING,
            KEY_VOL_MUSIC,
            KEY_VOL_NOTIFY,
            KEY_VOL_ALARM,
            KEY_VOL_DTMF,
            KEY_VOL_SYSTEM,
            KEY_VOL_ACCESSIBILITY
    };

    // int型の値の書き込み
    public static void writePrefInt(Context ctx, String key, int param) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, param);
        editor.commit();
    }

    // int型の値の読み込み
    public static int readPrefInt(Context ctx, String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);

        return pref.getInt(key, -1);
    }

    // String型の値の書き込み
    public static void writePrefStr(Context ctx, String key, String param) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);

        Editor editor = pref.edit();
        editor.putString(key, param);
        editor.commit();
    }

    // String型の値の読み込み
    public static String readPrefStr(Context ctx, String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);

        return pref.getString(key, null);
    }

    // boolean型の値の書き込み
    public static void writePrefBool(Context ctx, String key, boolean param) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);

        Editor editor = pref.edit();
        editor.putBoolean(key, param);
        editor.commit();
    }

    // boolean型の値の読み込み
    public static boolean readPrefBool(Context ctx, String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);

        return pref.getBoolean(key, false);
    }

    // 削除
    public static void removePref(Context ctx, String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);

        Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    // 音量設定値をまとめて設定
    public static void writePrefsVol(Context ctx, int[]params, boolean noAlarm, boolean noMusic) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);

        SharedPreferences.Editor editor = pref.edit();
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
            editor.putInt(PREFS_KEY_VOL[i], params[i]);
        }
        editor.commit();
    }

    // 音量設定値をまとめて取得
    public static int[] readPrefsVol(Context ctx) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);

        int[] volArray = new int[Param.STREAM_TYPE_MAX];
        for (int i = 0; i < Param.STREAM_TYPE_MAX; i++) {
            volArray[i] = pref.getInt(PREFS_KEY_VOL[i], -1);
        }
        return volArray;
    }

}
