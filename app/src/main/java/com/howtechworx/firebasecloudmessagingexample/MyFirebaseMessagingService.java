package com.howtechworx.firebasecloudmessagingexample;

import android.app.LauncherActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService{
    public static final String EXTRA_PUSH = "push";
    public static final String EXTRA_SCREEN = "screen";
    public static final java.lang.String EXTRA_URL = "url";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // super.onMessageReceived(remoteMessage);


        if (remoteMessage.getData().size() > 0) {
            Bundle data = new Bundle();
            for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
                data.putString(entry.getKey(), entry.getValue());
            }
                Intent defaultAction = new Intent(this.getApplicationContext(), LauncherActivity.class)
                        .setAction(Intent.ACTION_DEFAULT)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtras(data);

                String title = data.getString("title");
                String body = data.getString("body");
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.getApplicationContext())
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle(title)
                            .setContentText(body)
                            .setAutoCancel(true)
                            .setContentIntent(PendingIntent.getActivity(
                                    this.getApplicationContext(),
                                    0,
                                    defaultAction,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            ));

                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    if (mNotificationManager != null) {
                        mNotificationManager.notify(123, mBuilder.build());
                    }
                }
    }
}
