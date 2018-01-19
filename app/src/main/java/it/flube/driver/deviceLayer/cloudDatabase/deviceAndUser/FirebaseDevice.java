/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.deviceAndUser;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class FirebaseDevice {
    private static final String TAG = "FirebaseDevice";

    private DatabaseReference deviceRef;

    public FirebaseDevice(FirebaseDatabase database, String deviceNode){
        deviceRef = database.getReference(deviceNode);
    }

    public void saveDeviceInfoRequest(Driver driver, DeviceInfo deviceInfo, CloudDatabaseInterface.SaveDeviceInfoResponse response) {
        deviceRef.child(deviceInfo.getDeviceGUID()).setValue(deviceInfo).addOnCompleteListener(new FirebaseDevice.SaveDeviceInfoCompleteListener(response));
        Timber.tag(TAG).d("saving DEVICE INFO object --> device GUID : " + deviceInfo.getDeviceGUID());
    }

    private class SaveDeviceInfoCompleteListener implements OnCompleteListener<Void> {

        private CloudDatabaseInterface.SaveDeviceInfoResponse response;

        public SaveDeviceInfoCompleteListener(CloudDatabaseInterface.SaveDeviceInfoResponse response){
            this.response = response;
        }
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("saveDeviceInfoRequest --> SUCCESS");
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("saveDeviceInfoRequest --> FAILURE");
            }
            response.cloudDatabaseDeviceInfoSaveComplete();
        }
    }

}
