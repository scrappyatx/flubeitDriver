/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.splashScreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.dataLayer.DeviceCheckForGooglePlayServices;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers.UserInterfaceEventHandler;
import timber.log.Timber;

public class SplashScreenActivity extends AppCompatActivity implements
        DeviceCheckForGooglePlayServices.Response {

    private static final String TAG = "SplashScreenActivity";
    private SplashScreenController controller;
    private ActivityNavigator navigator;
    private UserInterfaceEventHandler eventHandler;

    ///
    ///   This activity should only be run ONCE when app launches
    ///   It will do the device check for the presence of google play services
    ///   then follow up with the initialization use case
    ///
    ///   normal exit from this activity is for a cloudAuth event to be received
    ///   user will go to either login screen, or home screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Timber.tag(TAG).d("onCreate");
    }


    @Override
    public void onResume(){
        super.onResume();
        Timber.tag(TAG).d("onResume");

        navigator = new ActivityNavigator();
        eventHandler = new UserInterfaceEventHandler(this, navigator);
        controller = new SplashScreenController(getApplicationContext(), this);

        Timber.tag(TAG).d("about to check for google play services");
        controller.doDeviceCheck(this);

    }


    @Override
    public void onPause(){
        super.onPause();
        Timber.tag(TAG).d("onPause");
        eventHandler.close();
        controller.close();
    }


    public void deviceHasGooglePlayServices(){
        Timber.tag(TAG).d("...deviceCheck SUCCESS, now doing statup sequence");
        controller.doStartupSequence();
    }

    public void deviceMissingGooglePlayServices(){
        Timber.tag(TAG).d("...deviceCheck FAILURE, now finish app");
        finish();
    }

}
