package com.thebenjhoward.steamcode;

import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationListener extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();


    public int onStartCommand() {
        return START_STICKY;
    }

    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if(sbn.getPackageName().equals("com.valvesoftware.android.steam.community")) {
            String title = sbn.getNotification().extras.getString(Notification.EXTRA_TITLE);
            Log.d(TAG, "Got notification with title " + title);
            if(title.toLowerCase().equals("new steam login for er5000")) {
                String text = sbn.getNotification().extras.getString(Notification.EXTRA_TEXT);
                String code = text.substring(9, 14);
                Intent i = new Intent("com.thebenjhoward.steamcode.NOTIFICATION_LISTENER");
                i.putExtra("Code", code);
                sendBroadcast(i);
            }
        }
    }
}
