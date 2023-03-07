package com.ds.bot;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class ChatBotService extends Service {
    private static final String NOTIFICATION_ID = "notification";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String user = intent.getStringExtra("user");
            String generate = intent.getStringExtra("generate");

            if (generate == "yes") {
                String[] botMsg = new String[3];
                botMsg[0] = "Hello "+user+"!";
                botMsg[1] = "How are you?";
                botMsg[2] = "GoodBye "+user+"!";

                Intent inten = new Intent();
                inten.setAction("BroadCastMsg");
                inten.putExtra("messages",botMsg);
                sendBroadcast(inten);

                for (String msg : botMsg) {
                    sendNotification(getApplicationContext(), msg);
                }
            } else {
                String msg = "ChatBot Stopped: 51";
                sendNotification(getApplicationContext(), msg);
                stopSelf();
            }
        }
        return START_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    NOTIFICATION_ID,
                    "Notification Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }

    }

    private void sendNotification(Context context, String msg) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_ID)
                .setSmallIcon(R.drawable.msgicon)
                .setContentTitle(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        notificationManager.notify(new Random().nextInt(100), builder.build());
    }
}
