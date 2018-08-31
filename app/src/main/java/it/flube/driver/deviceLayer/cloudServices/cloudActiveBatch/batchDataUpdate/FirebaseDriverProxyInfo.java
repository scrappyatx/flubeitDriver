/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.DriverInfo;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DETAIL_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.DRIVER_INFO_NODE;

/**
 * Created on 8/28/2018
 * Project : Driver
 */
public class FirebaseDriverProxyInfo implements
        OnCompleteListener<Void> {

    private static final String TAG = "FirebaseDriverProxyInfo";

    private CloudActiveBatchInterface.UpdateDriverProxyInfoResponse response;

    public void updateDriverProxyInfoRequest(DatabaseReference batchDataRef, Driver driver, String batchGuid, String driverProxyDialNumber, String driverProxyDisplayNumber, CloudActiveBatchInterface.UpdateDriverProxyInfoResponse response){
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        this.response = response;

        //put data into a driver info object
        DriverInfo driverInfo = new DriverInfo();
        driverInfo.setClientId(driver.getClientId());
        driverInfo.setDisplayName(driver.getNameSettings().getFirstName());
        driverInfo.setProxyDialNumber(driverProxyDialNumber);
        driverInfo.setProxyDisplayNumber(driverProxyDisplayNumber);

        //put it into a hashmap
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(DRIVER_INFO_NODE, driverInfo);

        //update the data in the batchDetail node
        batchDataRef.child(batchGuid).child(BATCH_DETAIL_NODE).updateChildren(data).addOnCompleteListener(this);
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        Timber.tag(TAG).d("COMPLETE");
        response.cloudActiveBatchUpdateDriverProxyInfoComplete();
    }


}
