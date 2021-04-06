package com.thebenjhoward.steamcode;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import androidx.annotation.Nullable;

public class TextMessageService extends Service {

    private String TAG = this.getClass().getSimpleName();
    private NotificationBroadcastReceiver notificationBroadcastReceiver;
    private SmsManager smsManager;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationBroadcastReceiver= new NotificationBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.thebenjhoward.steamcode.NOTIFICATION_LISTENER");

        registerReceiver(notificationBroadcastReceiver, intentFilter);
        smsManager = SmsManager.getDefault();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notificationBroadcastReceiver);
    }

    public int onStartCommand(Intent intent) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void sendSms(String phonenumber, String message) {
        smsManager.sendTextMessage(phonenumber, null, message, null, null);
    }

    public class NotificationBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "Received: " + intent.getStringExtra("Code"));
            SharedPreferences prefs = getSharedPreferences("phoneNumber", Context.MODE_PRIVATE);
            String num = prefs.getString("num", "");
            if(!num.equals(""))
                TextMessageService.this.sendSms(num, intent.getStringExtra("Code"));
        }

    }
}
