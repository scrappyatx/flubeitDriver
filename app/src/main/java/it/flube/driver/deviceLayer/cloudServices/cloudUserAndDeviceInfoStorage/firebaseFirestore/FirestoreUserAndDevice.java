/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudUserAndDeviceInfoStorage.firebaseFirestore;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.Date;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudUserAndDeviceInfoStorageInterface;
import timber.log.Timber;

/**
 * Created on 3/18/2018
 * Project : Driver
 */

public class FirestoreUserAndDevice implements
        OnCompleteListener<Void> {

    private static final String TAG = "FirebaseUser";

    private static final String MOBILE_DATA_COLLECTION = "mobileData";
    private static final String DRIVER_APP_DOCUMENT = "userWriteable";

    private static final String USERS_COLLECTION = "userHistory";
    private static final String DEVICES_USED_BY_USER_COLLECTION = "devicesOfThisUser";

    private static final String DEVICES_COLLECTION = "deviceHistory";
    private static final String USERS_OF_THIS_DEVICE_COLLECTION = "usersOfThisDevice";

    private static final String LAST_LOGIN_TIMESTAMP = "lastLogin";

    private CloudUserAndDeviceInfoStorageInterface.SaveResponse response;

    public void addUserData(FirebaseFirestore db, Driver driver, DeviceInfo deviceInfo, CloudUserAndDeviceInfoStorageInterface.SaveResponse response ){
        Timber.tag(TAG).d("addUserData START...");

        this.response = response;

        /// get a new write batch
        WriteBatch batch = db.batch();
        Timber.tag(TAG).d("   ...got batch");

        // set driver info
        DocumentReference driverDoc = db
                .collection(MOBILE_DATA_COLLECTION)
                .document(DRIVER_APP_DOCUMENT)
                .collection(USERS_COLLECTION)
                .document(driver.getClientId());
        batch.set(driverDoc, driver);
        Timber.tag(TAG).d("   ...set driver info");

        // set last driver login
        batch.update(driverDoc, LAST_LOGIN_TIMESTAMP, FieldValue.serverTimestamp());
        Timber.tag(TAG).d("   ...set driver last login");

        // set devices used by this driver
        DocumentReference devicesOfThisUserDoc = db
                .collection(MOBILE_DATA_COLLECTION)
                .document(DRIVER_APP_DOCUMENT)
                .collection(USERS_COLLECTION)
                .document(driver.getClientId())
                .collection(DEVICES_USED_BY_USER_COLLECTION)
                .document(deviceInfo.getDeviceGUID());

        batch.set(devicesOfThisUserDoc, deviceInfo);
        Timber.tag(TAG).d("   ...set devices used by this driver");

        //set device info
        DocumentReference deviceDoc = db
                .collection(MOBILE_DATA_COLLECTION)
                .document(DRIVER_APP_DOCUMENT)
                .collection(DEVICES_COLLECTION)
                .document(deviceInfo.getDeviceGUID());
        batch.set(deviceDoc, deviceInfo);
        Timber.tag(TAG).d("   ...set device info");

        //set most recent login for this device
        batch.update(deviceDoc, LAST_LOGIN_TIMESTAMP, FieldValue.serverTimestamp());
        Timber.tag(TAG).d("   ...set device last login");

        //set users of this device
        DocumentReference usersOfThisDeviceDoc = db
                .collection(MOBILE_DATA_COLLECTION)
                .document(DRIVER_APP_DOCUMENT)
                .collection(DEVICES_COLLECTION)
                .document(deviceInfo.getDeviceGUID())
                .collection(USERS_OF_THIS_DEVICE_COLLECTION)
                .document(driver.getClientId());

        batch.set(usersOfThisDeviceDoc, driver);
        Timber.tag(TAG).d("   ...set users of this device");

        //commit the batch
        batch.commit().addOnCompleteListener(this);
        Timber.tag(TAG).d("   ...committing batch");
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("...onComplete");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("   ...SUCCESS");
        } else {
            Timber.tag(TAG).w("   ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        response.cloudUserAndDeviceInfoSaveComplete();
    }
}
