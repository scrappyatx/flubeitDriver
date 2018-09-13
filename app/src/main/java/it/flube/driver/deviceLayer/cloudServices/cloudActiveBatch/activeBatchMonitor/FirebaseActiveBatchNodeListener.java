/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 11/5/2017
 * Project : Driver
 */

public class FirebaseActiveBatchNodeListener implements
        ValueEventListener {

    private final static String TAG = "FirebaseActiveBatchNodeListener";

    private CloudActiveBatchInterface.ActiveBatchUpdated response;
    private DatabaseReference batchDataRef;

    private ActiveBatchNode nodeData;

    public FirebaseActiveBatchNodeListener(DatabaseReference batchDataRef, CloudActiveBatchInterface.ActiveBatchUpdated response){
        this.response = response;
        this.batchDataRef = batchDataRef;
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");

        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...dataSnapshot exists!");
            try {
                nodeData = dataSnapshot.getValue(ActiveBatchNode.class);
                logNodeData(nodeData);

                if (nodeData.hasAllBatchData()){
                    Timber.tag(TAG).d("      ...nodeData has all batch data");
                    new ActiveBatchNodeWithBatchData().processNode(batchDataRef, nodeData, response);

                } else {
                    Timber.tag(TAG).d("      ...nodeData does not have all batch data");
                    new ActiveBatchNodeWithoutBatchData().processNode(nodeData, response);
                }
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
                Timber.tag(TAG).w("   ...ERROR : response -> noBatch");
                response.dataMismatchOnNode();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist : response -> noBatch");
            response.dataMismatchOnNode();
        }
    }

    private void logNodeData(ActiveBatchNode nodeData){
        if (nodeData.hasAllBatchData()) {
            Timber.tag(TAG).d("batch  ---> " + nodeData.getBatchGuid());
            Timber.tag(TAG).d("serviceOrderSequence --> " + nodeData.getServiceOrderSequence());
            Timber.tag(TAG).d("stepSequence --> " + nodeData.getStepSequence());
        } else {
            Timber.tag(TAG).d("batch  ---> NO BATCH NODE");
            Timber.tag(TAG).d("serviceOrderSequence --> NO SERVICE ORDER SEQUENCE NODE");
            Timber.tag(TAG).d("stepSequence --> NO STEP SEQUENCE NODE");
        }
        Timber.tag(TAG).d("actionType --> " + nodeData.getActionType().toString());
        Timber.tag(TAG).d("actorType --> " + nodeData.getActorType().toString());
    }

    public void onCancelled(DatabaseError databaseError){
        Timber.tag(TAG).e("onCancelled - > firebase database read error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.dataMismatchOnNode();
    }


}
