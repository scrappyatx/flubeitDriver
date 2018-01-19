/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.userChanges;

import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;

/**
 * Created on 12/3/2017
 * Project : Driver
 */

public class UseCaseThingsToDoWhenUserChangesToNoUser implements
        Runnable,
        UseCaseDisconnectUser.Response {

    private final static String TAG = "UseCaseThingsToDoWhenUserChangesToNoUser";

    private final MobileDeviceInterface device;
    private final UseCaseThingsToDoWhenUserChangesToNoUser.Response response;

    public UseCaseThingsToDoWhenUserChangesToNoUser(MobileDeviceInterface device, UseCaseThingsToDoWhenUserChangesToNoUser.Response response){
        this.device = device;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        Timber.tag(TAG).d("   disconnecting current user...");
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseDisconnectUser(device, this));
    }

    public void userDisconnectedComplete(){
        Timber.tag(TAG).d("   ...disconnecting current user complete, UseCase COMPLETE");
        response.userChangedToNoUserComplete();
    }

    public interface Response {
        void userChangedToNoUserComplete();
    }

}
