package com.ebswn;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;

import com.facebook.react.bridge.ReactContextBaseJavaModule;

import com.facebook.react.bridge.ReactMethod;

import android.util.Log;
import android.widget.Toast;

import javax.annotation.Nonnull;
import static android.content.Context.ALARM_SERVICE;
import android.content.SharedPreferences;
import android.content.Context;

public class EBSWNModule extends ReactContextBaseJavaModule {

   public static final String REACT_CLASS = "EndlessService";

   private static ReactApplicationContext reactContext;

   public EBSWNModule(@Nonnull ReactApplicationContext reactContext) {

       super(reactContext);

       this.reactContext = reactContext;

   }

   @Nonnull

   @Override

   public String getName() {

       return REACT_CLASS;

   }

   @ReactMethod

   public void startService(int timeInSeconds) {

//       System.out.println("In Start Service");
       SharedPreferences sharedPreferences = this.reactContext.getSharedPreferences("EBSWN",Context.MODE_PRIVATE);
       SharedPreferences.Editor myEdit = sharedPreferences.edit();
       myEdit.putInt("timeInSeconds", timeInSeconds);
       myEdit.commit();

       this.reactContext.startService(new Intent(this.reactContext, EBSWNService.class));
       Intent newIntent = new Intent(this.reactContext, BootReceiver.class);
       PendingIntent pendingIntent = PendingIntent.getBroadcast(
               this.reactContext.getApplicationContext(), 234324243, newIntent, 0);
       AlarmManager alarmManager = (AlarmManager) this.reactContext.getSystemService(ALARM_SERVICE);
       alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
               + (1 * 1000 * timeInSeconds), pendingIntent);

//       System.out.println("Start Service completed");


   }

   @ReactMethod

   public void stopService() {

       this.reactContext.stopService(new Intent(this.reactContext, EBSWNService.class));

   }

}