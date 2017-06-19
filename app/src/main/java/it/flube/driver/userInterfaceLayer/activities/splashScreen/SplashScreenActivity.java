/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.splashScreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.rollbar.android.Rollbar;

import io.fabric.sdk.android.Fabric;
import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.controllers.SplashScreenController;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeActiveBatchActivityEventDELETE;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeNoActiveBatchActivityEventDELETE;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoLoginActivityEvent;
import timber.log.Timber;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SplashScreenController mController = new SplashScreenController(this);
        mController.startupSequence();

        Timber.tag(TAG).d("onCreate");
    }

}
