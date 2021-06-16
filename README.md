# react-native-endless-background-service-without-notification

## Getting started

`$ npm install react-native-endless-background-service-without-notification --save`

### Mostly automatic installation

`$ react-native link react-native-endless-background-service-without-notification`

## Usage

As Android doesn't allow background services to run without showing any interface or notification to user. And if we want to do our work in background but don't want to show notification then this package come into action, Instead of creating a forever notification , this package starts our task in background at a particular time interval again and again. The service will start with a notification , do it's work and will close the notification after work without user noticing it.
We are actually doing our work in batches and start the next batch after a particular time so that user doesn't notice the notification.

# index.js
Add the below code in your index.js file.

```javascript
import  EndlessService from "react-native-endless-background-service-without-notification";
const BackgroundTask = async () => {

      // Do your task here.

      // Be sure to call stopService at the end.
      EndlessService.stopService();

    });

  };
AppRegistry.registerHeadlessTask('EBSWN', () => BackgroundTask);
AppRegistry.registerComponent(appName, () => App);
```



# AndroidManifest.xml

In your AndroidManifest.xml file add the below code in <application> tag -
```javascript

        <service
            android:name="com.ebswn.EBSWNService"
            android:enabled="true"
            android:exported="false" >
        </service>

        <service
            android:name="com.ebswn.EBSWNEventService">
        </service>

        <receiver
            android:name="com.ebswn.BootReceiver"
            android:enabled="true"
            android:permission="android.permission.RECEIVE_BOOT_COMPLETED">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </receiver>
```

Additionally add below permissions in AndroidManifest.xml - 
```javascript
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
```


# Start Service
To start our service just execute the below code. Below function takes integer argument which is timeInSeconds (the time after which services starts again even if the app is closed).
place this code anywhere where you want to start your task.
```javascript
EndlessService.startService(60 * 60 * 2); // starts service after every 2 hours.
```