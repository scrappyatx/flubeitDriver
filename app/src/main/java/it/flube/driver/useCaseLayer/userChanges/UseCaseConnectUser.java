/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.userChanges;

import android.support.v4.util.Pools;

import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 1/2/2018
 * Project : Driver
 */

public class UseCaseConnectUser implements
        Runnable,
        CloudDatabaseInterface.UserProfileResponse,
        CloudDatabaseInterface.ConnectResponse,
        CloudDatabaseInterface.StartMonitoringResponse {

    private final static String TAG = "UseCaseConnectUser";

    private final MobileDeviceInterface device;
    private final String clientId;
    private final String email;
    private final String idToken;
    private final Response response;
    private final ResponseCounter connectUserResponseCounter;
    private Driver driver;

    public UseCaseConnectUser(MobileDeviceInterface device,
                              String clientId, String email, String idToken,
                              Response response){

        this.device = device;
        this.clientId = clientId;
        this.email = email;
        this.idToken = idToken;
        this.response = response;
        this.connectUserResponseCounter = new ResponseCounter(3);

    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        /// 1. try and get user profile for this clientId & email

        Timber.tag(TAG).d("   get user profile request...");
        device.getCloudDatabase().getUserProfileRequest(clientId, email, this);
    }

    ///
    /// Response A -> we get the user profile data
    ///
    public void cloudDatabaseGetUserProfileSuccess(Driver driver){
        /// 1A. connect the new user
        this.driver = driver;
        device.getUser().setDriver(driver);
        device.getUser().setIdToken(idToken);

        //2. connect to the cloud database
        Timber.tag(TAG).d("   ...got user profile, now connectDriverRequest...");
        device.getCloudDatabase().connectDriverRequest(device.getAppRemoteConfig(), driver, this);
    }

    public void cloudDatabaseConnectDriverComplete(){
        //3. start monitoring offers & active batch
        Timber.tag(TAG).d("   ...connectDriverRequest complete, now start monitoring cloud database...");
        device.getCloudDatabase().startMonitoringRequest(this);
    }

    public void cloudDatabaseStartMonitoringComplete(){
        //4. we are done
        Timber.tag(TAG).d("   ...cloud database monitoring started, UseCase COMPLETE");
        response.userConnectSuccess(this.driver);
    }

    ///
    /// Response B -> user profile is not found
    ///
    public void cloudDatabaseGetUserProfileNotFound(){
        /// 1B.  we are done
        Timber.tag(TAG).d("   ...user profile not found, UseCase COMPLETE");
        response.userConnectFailureNoProfile();
    }

    ///
    /// Response C -> user access is denied
    ///
    public void cloudDatabaseGetUserProfileAccessDenied(){
        /// 1C. we are done
        Timber.tag(TAG).d("   ...user profile access denied, UseCase COMPLETE");
        response.userConnectFailureAccessDenied();
    }


    public interface Response {
        void userConnectSuccess(Driver driver);

        void userConnectFailureNoProfile();

        void userConnectFailureAccessDenied();
    }

}
