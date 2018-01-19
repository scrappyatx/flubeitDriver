/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.splashScreen;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.DeviceCheckForGooglePlayServices;

import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.appInitialization.UseCaseInitialization;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import timber.log.Timber;

/**
 * Created by Bryan on 4/30/2017.
 */

public class SplashScreenController implements
    UseCaseInitialization.Response {

    private final String TAG = "SplashScreenController";

    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;
    private DeviceCheckForGooglePlayServices deviceCheckForGooglePlayServices;
    private ActivityNavigator navigator;

    public SplashScreenController(Context applicationContext, AppCompatActivity activity) {
        this.navigator = navigator;
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
        deviceCheckForGooglePlayServices = new DeviceCheckForGooglePlayServices(applicationContext, activity);
        Timber.tag(TAG).d("created splash screen controller");
    }

    public void doDeviceCheck(DeviceCheckForGooglePlayServices.Response response) {
        deviceCheckForGooglePlayServices.doTheCheck(response);
    }

    public void doStartupSequence() {
        Timber.tag(TAG).d("doStartupSequence START");
        useCaseExecutor.execute(new UseCaseInitialization(device, this));
    }

    public void useCaseInitializationComplete(){
        Timber.tag(TAG).d("useCaseInitialization complete");
    }

    public void close() {
        Timber.tag(TAG).d("close received!");
        deviceCheckForGooglePlayServices.close();
        device = null;
        useCaseExecutor=null;
    }


}