/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.actions;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.driver.modelLayer.interfaces.CloudServerMonitoringInterface;
import it.flube.libbatchdata.entities.LatLonLocation;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.DRIVER_LOCATION_NODE;

/**
 * Created on 8/30/2018
 * Project : Driver
 */
public class BatchRemoved implements
        OnCompleteListener<Void> {
    private static final String TAG = "BatchRemoved";

    private String batchGuid;
    private CloudServerMonitoringInterface.BatchRemovedResponse response;

    public void batchRemovedRequest(DatabaseReference destRef, String batchGuid, CloudServerMonitoringInterface.BatchRemovedResponse response) {
        Timber.tag(TAG).d("batchRemovedRequest START...");

        this.batchGuid = batchGuid;
        this.response = response;

        Timber.tag(TAG).d("   destRef -> " + destRef.toString());
        Timber.tag(TAG).d("   batchGuid -> " + batchGuid);

        destRef.child(batchGuid).setValue(null).addOnCompleteListener(this);
    }


    ///
    /// OnCompleteListener
    ///
    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        Timber.tag(TAG).d("COMPLETE");
        response.cloudServerMonitoringBatchRemovedComplete(batchGuid);
    }
}
