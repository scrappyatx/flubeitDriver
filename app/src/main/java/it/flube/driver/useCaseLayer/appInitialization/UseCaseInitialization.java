/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.appInitialization;

import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.AppRemoteConfigInterface;
import it.flube.driver.modelLayer.interfaces.AppLoggingInterface;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class UseCaseInitialization implements
        Runnable,
        AppRemoteConfigInterface.Response,
        AppLoggingInterface.Response,
        CloudAuthInterface.StartMonitoringResponse {

    private final static String TAG = "UseCaseInitialization";

    private final Response response;
    private final MobileDeviceInterface device;
    private ResponseCounter responseCounter;

    public UseCaseInitialization(MobileDeviceInterface device, Response response) {
        this.response = response;
        this.device = device;
    }

    public void run() {
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        responseCounter = new ResponseCounter(2);

        // Step 1 --> clear the user
        Timber.tag(TAG).d("   ... 1 -> clear user");
        device.getUser().clear();

        // Step 2 --> initialize app config
        Timber.tag(TAG).d("   ... 2 -> initialize app config");
        device.getAppRemoteConfig().getUpdatedValuesFromRemoteServerRequest(this);

        // Step 3 --> initialize remote logging logging
        Timber.tag(TAG).d("   ... 3 -> initialize remote logging");
        device.getAppLogging().initializeRemoteLoggingAndCrashReportingRequest(device.getAppRemoteConfig(), this);

        // Step 4 --> start monitoring for user authentication state changes
        Timber.tag(TAG).d("   ... 4 -> start monitoring for user auth state changes");
        device.getCloudAuth().startMonitoringAuthStateChanges(this);
    }

    public void getUpdatedValuesFromRemoteServerComplete(){
        //responseCounter.onResponse();
        Timber.tag(TAG).d("   ...got updated values from remote server");
    }

    public void initializeRemoteLoggingAndCrashReportingComplete(){
        Timber.tag(TAG).d("   ...initialize remote logging COMPLETE");
        checkIfFinished();
    }

    public void cloudAuthStartMonitoringComplete(){
        Timber.tag(TAG).d("   ...cloudAuth start monitoring COMPLETE");
        checkIfFinished();
    }

    private void checkIfFinished(){
        Timber.tag(TAG).d("      ...response " + responseCounter.getCount());
        responseCounter.onResponse();
        if (responseCounter.isFinished()){
            Timber.tag(TAG).d("      ...UseCase COMPLETE");
            response.useCaseInitializationComplete();
        }
    }

    public interface Response {
        void useCaseInitializationComplete();
    }
}
