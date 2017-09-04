/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class FirebaseDevice {
    private static final String TAG = "FirebaseDevice";

    public void saveDeviceInfoRequest(DatabaseReference reference, Driver driver, DeviceInfo deviceInfo, CloudDatabaseInterface.SaveDeviceInfoResponse response) {

        reference.child(deviceInfo.getDeviceGUID()).setValue(deviceInfo).addOnCompleteListener(new FirebaseDevice.SaveDeviceInfoCompleteListener(response));
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
