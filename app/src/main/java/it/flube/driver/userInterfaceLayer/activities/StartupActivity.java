/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.rollbar.android.Rollbar;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.controllers.StartupController;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.activityTransition.GotoLoginActivityEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.activityTransition.GotoMainActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class StartupActivity extends AppCompatActivity {
    private static final String TAG = "StartupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize rollbar
        if (!Rollbar.isInit()) {Rollbar.init(this,"6489dbbc16e943beaebf5c0028ee588a", BuildConfig.BUILD_TYPE);}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        Log.d(TAG, "StartupActivity CREATED");
    }


    // subscribe to EventBus
    @Override
    public void onStart() {
        Log.d(TAG, "onStart() START");
        super.onStart();
        EventBus.getDefault().register(this);

        //this is just to introduce a 5 second delay so user can actually see login screen
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 3 seconds
                StartupController mStartupController = new StartupController(getApplicationContext());
                mStartupController.startupSequence();
            }
        }, 3000);

        Log.d(TAG, "onStart() END");
    }

    //unsubscribe to EventBus
    @Override
    public void onStop() {
        Log.d(TAG, "onStop() START");
        EventBus.getDefault().unregister(this);
        super.onStop();
        Log.d(TAG, "onStop() END");
    }

    //get the GotoStartupActivityEvent
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoLoginActivityEvent event) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Log.d(TAG,"*** Sent intent to start LoginActivity");
    }

    //get the GotoMainActivityEvent
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoMainActivityEvent event) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.d(TAG,"*** Sent intent to start MainActivity");
    }

}
