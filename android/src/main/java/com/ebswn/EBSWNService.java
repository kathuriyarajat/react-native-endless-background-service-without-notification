package com.ebswn;

import android.app.Notification;

import android.app.PendingIntent;

import android.app.Service;

import android.content.Context;

import android.content.Intent;

import android.graphics.Color;
import android.os.Handler;

import android.os.IBinder;

//import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;

import android.app.NotificationChannel;

import android.os.Build;

import com.facebook.react.HeadlessJsTaskService;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class EBSWNService extends Service {

    private static final int SERVICE_NOTIFICATION_ID = 100010;

    private static final String CHANNEL_ID = "EBSWN";

    private Handler handler = new Handler();

    private Runnable runnableCode = new Runnable() {

        @Override

        public void run() {

            Context context = getApplicationContext();

            Intent myIntent = new Intent(context, EBSWNEventService.class);

            context.startService(myIntent);

            HeadlessJsTaskService.acquireWakeLockNow(context);

            handler.postDelayed(this, 30000000); // 500 Min

        }

    };

    @Override

    public IBinder onBind(Intent intent) {

        return null;

    }

    @Override

    public void onCreate() {

        super.onCreate();

    }

    @Override

    public void onDestroy() {

//        Intent broadcastIntent = new Intent("com.EBSWN.mBill.serviceRestarted");
//        sendBroadcast(broadcastIntent);
        super.onDestroy();

        this.handler.removeCallbacks(this.runnableCode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true);
        }
    }

    @Override

    public int onStartCommand(Intent intent, int flags, int startId) {

        this.handler.post(this.runnableCode);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(CHANNEL_ID, "mBill Service");
        } else {

        }

        // The following code will turn it into a Foreground background process (Status bar notification)

        Intent notificationIntent = new Intent(this, EBSWNEventService.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.btn_plus)
                .setContentIntent(contentIntent)

                .setOngoing(false)


                .build();

            startForeground(SERVICE_NOTIFICATION_ID, notification);

        // startService(intent);

        return START_NOT_STICKY;


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId,String channelName){
        NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        return channelId;
    }

}