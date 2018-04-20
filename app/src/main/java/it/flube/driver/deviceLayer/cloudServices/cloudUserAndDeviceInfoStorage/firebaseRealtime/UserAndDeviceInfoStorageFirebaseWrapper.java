/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudUserAndDeviceInfoStorage.firebaseRealtime;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudUserAndDeviceInfoStorageInterface;
import timber.log.Timber;

/**
 * Created on 3/13/2018
 * Project : Driver
 */

public class UserAndDeviceInfoStorageFirebaseWrapper implements
        CloudUserAndDeviceInfoStorageInterface {

    private static final String TAG = "UserAndDeviceInfoStorageFirebaseWrapper";

    private String userNode;
    private String deviceNode;

    public UserAndDeviceInfoStorageFirebaseWrapper(CloudConfigInterface cloudConfig){
        userNode = cloudConfig.getCloudDatabaseBaseNodeUserData();
        Timber.tag(TAG).d("userNode = " + userNode);

        deviceNode = cloudConfig.getCloudDatabaseBaseNodeDeviceData();
        Timber.tag(TAG).d("deviceNode = " + deviceNode);
    }


    ////
    ////    User and Device Requests
    ////
    ////

    public void saveUserAndDeviceInfoRequest(Driver driver, DeviceInfo deviceInfo, SaveResponse response){
        Timber.tag(TAG).d("do nothing");
    }


}
