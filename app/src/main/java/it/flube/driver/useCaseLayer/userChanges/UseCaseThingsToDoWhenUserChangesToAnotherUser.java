/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.userChanges;

import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;

/**
 * Created on 12/3/2017
 * Project : Driver
 */

public class UseCaseThingsToDoWhenUserChangesToAnotherUser implements
        Runnable,
        UseCaseDisconnectUser.Response,
        UseCaseConnectUser.Response {

    private final static String TAG = "UseCaseThingsToDoWhenUserChangesToAnotherUser";

    private final MobileDeviceInterface device;
    private final String clientId;
    private final String email;
    private final String idToken;
    private final UseCaseThingsToDoWhenUserChangesToAnotherUser.Response response;

    public UseCaseThingsToDoWhenUserChangesToAnotherUser(MobileDeviceInterface device,
                                                         String clientId, String email, String idToken,
                                                         UseCaseThingsToDoWhenUserChangesToAnotherUser.Response response){
        this.device = device;
        this.clientId = clientId;
        this.email = email;
        this.idToken = idToken;
        this.response = response;
    }


    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        // 1. disconnect current user
        Timber.tag(TAG).d("   disconnecting current user...");
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseDisconnectUser(device, this));
    }

    public void userDisconnectedComplete(){
        // 2. connect new user
        Timber.tag(TAG).d("   ...disconnecting current user complete, connecting new user...");
       device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseConnectUser(device, clientId, email, idToken, this));
    }

    public void userConnectSuccess(Driver driver){
        Timber.tag(TAG).d("   ...new user connect success! UseCase COMPLETE");
        response.userChangedToNewUserSuccess(driver);
    }

    public void userConnectFailureNoProfile(){
        Timber.tag(TAG).d("   ...new user connect failure, no profile! UseCase COMPLETE");
        response.userChangedToNewUserNoProfile();
    }

    public void userConnectFailureAccessDenied(){
        Timber.tag(TAG).d("   ...new user connect failure, access denied! UseCase COMPLETE");
        response.userChangedToNewUserAccessDenied();
    }

    public interface Response {
        void userChangedToNewUserSuccess(Driver driver);

        void userChangedToNewUserNoProfile();

        void userChangedToNewUserAccessDenied();
    }
}
