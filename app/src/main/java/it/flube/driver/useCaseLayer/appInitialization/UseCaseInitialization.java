/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.appInitialization;

import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class UseCaseInitialization implements
        Runnable,
        CloudAuthInterface.StartMonitoringResponse {

    private final static String TAG = "UseCaseInitialization";

    private final Response response;
    private final MobileDeviceInterface device;

    public UseCaseInitialization(MobileDeviceInterface device, Response response) {
        this.response = response;
        this.device = device;
    }

    public void run() {
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        // Step 1 --> clear the user
        Timber.tag(TAG).d("   ... 1 -> clear user");
        //device.getUser().clear();

        // Step 2 --> start monitoring for user authentication state changes
        Timber.tag(TAG).d("   ... 2 -> start monitoring for user auth state changes");
        device.getCloudAuth().startMonitoringAuthStateChanges(this);
    }

    public void cloudAuthStartMonitoringComplete(){
        Timber.tag(TAG).d("   ...cloudAuth start monitoring COMPLETE");
        response.useCaseInitializationComplete();
    }

    public interface Response {
        void useCaseInitializationComplete();
    }
}
