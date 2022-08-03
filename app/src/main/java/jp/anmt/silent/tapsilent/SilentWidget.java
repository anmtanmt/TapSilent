package jp.anmt.silent.tapsilent;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class SilentWidget extends AppWidgetProvider {
    private static final String TAG = "SilentWidget";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

     //   CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.silent_widget);
     //   views.setTextViewText(R.id.appwidget_text, widgetText);

        // 起動アクティビティ・サービスの設定
        Intent intent = null;
        PendingIntent pendingIntent = null;

        // 音量変更実行
//        intent = new Intent(context, ExeService.class);
//        pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        intent = new Intent(context, ExeActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_image_main, pendingIntent);

        // 設定用アクティビティ
        intent = new Intent(context, SetActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_image_setting, pendingIntent);
        views.setOnClickPendingIntent(R.id.widget_image_space, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            if (Param.D) Log.d(TAG, "onUpdate() id:" + appWidgetId);

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        if (Param.D) Log.d(TAG, "onEnabled()");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        if (Param.D) Log.d(TAG, "onDisabled()");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (Param.D) Log.d(TAG, "onReceive()");

    }

}

