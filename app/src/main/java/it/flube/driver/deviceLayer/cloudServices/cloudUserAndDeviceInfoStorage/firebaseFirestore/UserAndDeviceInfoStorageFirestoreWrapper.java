/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudUserAndDeviceInfoStorage.firebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestore;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudUserAndDeviceInfoStorageInterface;
import timber.log.Timber;

/**
 * Created on 3/18/2018
 * Project : Driver
 */

public class UserAndDeviceInfoStorageFirestoreWrapper implements
        CloudUserAndDeviceInfoStorageInterface {


    private static final String TAG = "UserAndDeviceInfoStorageFirebaseWrapper";

    public UserAndDeviceInfoStorageFirestoreWrapper(){
        Timber.tag(TAG).d("created");
    }

    public void saveUserAndDeviceInfoRequest(Driver driver, DeviceInfo deviceInfo, SaveResponse response){
        Timber.tag(TAG).d("saveUserAndDeviceInfoRequest START...");
        new FirestoreUserAndDevice().addUserData(FirebaseFirestore.getInstance(), driver, deviceInfo, response);
    }
}
