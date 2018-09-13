/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.userChanges;

import android.support.v4.util.Pools;

import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPersonalOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPublicOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudUserAndDeviceInfoStorageInterface;
import it.flube.driver.modelLayer.interfaces.CloudUserProfileInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 1/2/2018
 * Project : Driver
 */

public class UseCaseConnectUser implements
        Runnable,
        CloudUserProfileInterface.UserProfileResponse,
        CloudUserAndDeviceInfoStorageInterface.SaveResponse,
        CloudDemoOfferInterface.StartMonitoringResponse,
        CloudPersonalOfferInterface.StartMonitoringResponse,
        CloudPublicOfferInterface.StartMonitoringResponse,
        CloudScheduledBatchInterface.StartMonitoringResponse,
        CloudActiveBatchInterface.StartMonitoringResponse {

    private final static String TAG = "UseCaseConnectUser";

    private final MobileDeviceInterface device;
    private final String clientId;
    private final String email;
    private final String idToken;
    private final Response response;
    private Driver driver;

    public UseCaseConnectUser(MobileDeviceInterface device,
                              String clientId, String email, String idToken,
                              Response response){

        this.device = device;
        this.clientId = clientId;
        this.email = email;
        this.idToken = idToken;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        /// 1. try and get user profile for this clientId & email

        Timber.tag(TAG).d("   get user profile request...");
        device.getCloudUserProfile().getUserProfileRequest(clientId, email, this);
    }

    ///
    /// Response A -> we get the user profile data
    ///
    public void cloudGetUserProfileSuccess(Driver driver){
        /// 1A. connect the new user
        this.driver = driver;
        //device.getUser().setDriver(driver);
        //device.getUser().setIdToken(idToken);

        //2. connect to the cloud database
        Timber.tag(TAG).d("   ...got user profile, now save user and device info...");
        device.getCloudUserAndDeviceInfoStorage().saveUserAndDeviceInfoRequest(driver, device.getDeviceInfo(), this);
    }

    public void cloudUserAndDeviceInfoSaveComplete(){
        //3. start monitoring demo offers
        Timber.tag(TAG).d("   ...saved user and device info, now start monitoring demo offers...");
        device.getCloudDemoOffer().startMonitoringRequest(driver, device.getOfferLists(),this);
    }

    public void cloudDemoOffersStartMonitoringComplete(){
        //4. start monitoring personal offers
        Timber.tag(TAG).d("   ...monitoring demo offers complete, now start monitoring personal offers...");
        device.getCloudPersonalOffer().startMonitoringRequest(driver, device.getOfferLists(), this);
    }

    public void cloudPersonalOffersStartMonitoringComplete(){
        //5. start monitoring public offers
        Timber.tag(TAG).d("   ...monitoring personal offers complete, now start monitoring public offers...");
        device.getCloudPublicOffer().startMonitoringRequest(driver, device.getOfferLists(), this);
    }

    public void cloudPublicOffersStartMonitoringComplete(){
        //6.  start monitoring scheduled batches
        Timber.tag(TAG).d("   ...monitoring public offers complete, now start monitoring scheduled batches...");
        device.getCloudScheduledBatch().startMonitoringRequest(driver, device.getOfferLists(), this);
    }

    public void cloudScheduledBatchStartMonitoringComplete(){
        //7.  start monitoring for an active batch
        Timber.tag(TAG).d("   ...monitoring scheduled batches complete, now start monitoring active batch...");
        device.getCloudActiveBatch().startMonitoringRequest(driver, device.getOfferLists(), this);
    }

    public void cloudActiveBatchStartMonitoringComplete(){
        //7.  we done
        Timber.tag(TAG).d("   ...monitoring active batch complete, we are finished");
        response.userConnectSuccess(this.driver);
    }


    ///
    /// Response B -> user profile is not found
    ///
    public void cloudGetUserProfileNotFound(){
        /// 1B.  we are done
        Timber.tag(TAG).d("   ...user profile not found, UseCase COMPLETE");
        response.userConnectFailureNoProfile();
    }

    ///
    /// Response C -> user access is denied
    ///
    public void cloudGetUserProfileAccessDenied(){
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
