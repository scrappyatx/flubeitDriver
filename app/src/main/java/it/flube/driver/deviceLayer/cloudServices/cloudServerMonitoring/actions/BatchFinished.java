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
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudServerMonitoring.ServerMonitoringFirebaseConstants.DRIVER_LOCATION_NODE;

/**
 * Created on 8/30/2018
 * Project : Driver
 */
public class BatchFinished implements
        OnCompleteListener<Void>,
        CloudServerMonitoringInterface.BatchRemovedResponse  {
    private static final String TAG = "BatchFinished";

    private String batchGuid;
    private String clientId;
    private BatchDetail.BatchType batchType;
    private DatabaseReference addRef;
    private CloudServerMonitoringInterface.BatchFinishedResponse response;

    public void batchFinishedRequest(DatabaseReference removeRef, DatabaseReference addRef, String clientId, BatchDetail batchDetail, CloudServerMonitoringInterface.BatchFinishedResponse response) {
        Timber.tag(TAG).d("batchRemovedRequest START...");

        this.batchGuid = batchDetail.getBatchGuid();
        this.clientId = clientId;
        this.batchType = batchDetail.getBatchType();
        this.addRef = addRef;
        this.response = response;

        Timber.tag(TAG).d("   removeRef -> " + removeRef.toString());
        Timber.tag(TAG).d("   addRef    -> " + addRef.toString());
        Timber.tag(TAG).d("   batchGuid -> " + batchGuid);

        /// remove the node from the remove ref
        new BatchRemoved().batchRemovedRequest(removeRef, batchGuid, this);
    }

    ////
    //// BatchRemovedResponse interface
    ////
    public void cloudServerMonitoringBatchRemovedComplete(String batchGuid){
        Timber.tag(TAG).d("cloudServerMonitoringBatchRemovedComplete");
        /// now write the data to the addRef
        addRef.child(batchGuid).updateChildren(NodeBuilder.getCompletedBatchNode(clientId, batchType)).addOnCompleteListener(this);
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
        response.cloudServerMonitoringBatchFinishedComplete(batchGuid);
    }
}

