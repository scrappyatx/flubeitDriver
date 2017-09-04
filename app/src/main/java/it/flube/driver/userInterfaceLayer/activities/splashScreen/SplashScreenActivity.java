/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.splashScreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.DeviceCheck;
import it.flube.driver.dataLayer.useCaseResponseHandlers.signInAndSignOut.SignInFromDeviceStorageResponseHandler;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.activities.PermissionsCheckActivity;
import timber.log.Timber;

public class SplashScreenActivity extends AppCompatActivity implements DeviceCheck.Response {

    private static final String TAG = "SplashScreenActivity";
    private SplashScreenController controller;
    private ActivityNavigator navigator;

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

        EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        controller = new SplashScreenController(getApplicationContext(), this);

        Timber.tag(TAG).d("about to check for google play services");
        controller.doDeviceCheck(this);

    }


    @Override
    public void onPause(){
        super.onPause();
        Timber.tag(TAG).d("onPermissionPause");

        EventBus.getDefault().unregister(this);
        controller.close();
    }


    public void deviceCheckSuccess(){
        controller.doStartupSequence();
    }

    public void deviceCheckFailure(){
        finish();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SignInFromDeviceStorageResponseHandler.UseCaseSignInFromDeviceStorageSuccessEvent event) {
        EventBus.getDefault().removeStickyEvent(SignInFromDeviceStorageResponseHandler.UseCaseSignInFromDeviceStorageSuccessEvent.class);
        navigator.gotoActivityHome(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(SignInFromDeviceStorageResponseHandler.UseCaseSignInFromDeviceStorageFailureEvent event) {
        EventBus.getDefault().removeStickyEvent(SignInFromDeviceStorageResponseHandler.UseCaseSignInFromDeviceStorageFailureEvent.class);
        navigator.gotoActivityLogin(this);
    }

}
