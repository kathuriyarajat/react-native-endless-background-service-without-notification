package com.ebswn;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import static android.content.Context.ALARM_SERVICE;
import android.content.SharedPreferences;
import android.content.Context;

public class BootReceiver extends BroadcastReceiver {

    @Override

    public void onReceive(Context context, Intent i) {

//        System.out.println("In Boot Receiver");

        SharedPreferences sh = context.getSharedPreferences("EBSWN", Context.MODE_APPEND);
        int timeInSeconds = sh.getInt("timeInSeconds", 0);
//        System.out.println(""+timeInSeconds);


        Intent intent=new Intent(context, EBSWNService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }

        Intent newIntent = new Intent(context, BootReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 234324243, newIntent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (1 * 1000 * timeInSeconds), pendingIntent);


    }

}