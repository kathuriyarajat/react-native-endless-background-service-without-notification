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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.DateFormat;

public class BootReceiver extends BroadcastReceiver {

    @Override

    public void onReceive(Context context, Intent i) {

//        System.out.println("In Boot Receiver");

        SharedPreferences sh = context.getSharedPreferences("EBSWN", Context.MODE_APPEND);
        int timeInSeconds = sh.getInt("timeInSeconds", 0);
        int hour = sh.getInt("hour", -1);
        int minute = sh.getInt("minute", -1);
//        System.out.println(""+timeInSeconds);

        if(hour != -1){
            try{
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String currentDate = df.format(c);
                currentDate+=" ";
                if(hour<10){
                    currentDate+="0";
                }
                currentDate+=hour;
                currentDate+=":";
                if(minute<10){
                    currentDate+="0";
                }
                currentDate+=minute;
                currentDate+=":00";
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                Date date = (Date)formatter.parse(currentDate);
                long serviceStartTime=date.getTime();
                if(serviceStartTime < System.currentTimeMillis()){
                    serviceStartTime+= 1000*60*60*24;
                }
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
                alarmManager.set(AlarmManager.RTC_WAKEUP, serviceStartTime, pendingIntent);

            }
            catch (Exception e){

            }

        }
        else{

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

}