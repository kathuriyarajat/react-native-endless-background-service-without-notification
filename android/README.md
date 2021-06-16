README
======

Usage -
Add the below code in your index.js file.


import  EndlessService from "react-native-endless-background-service-without-notification";
const BackgroundTask = async () => {

      // Do your task here.

      // Be sure to call stopService at the end.
      EndlessService.stopService();

    });

  };
AppRegistry.registerHeadlessTask('EBSWN', () => BackgroundTask);
AppRegistry.registerComponent(appName, () => App);





In your AndroidManifest.xml file add the below code in <application> tag -

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

