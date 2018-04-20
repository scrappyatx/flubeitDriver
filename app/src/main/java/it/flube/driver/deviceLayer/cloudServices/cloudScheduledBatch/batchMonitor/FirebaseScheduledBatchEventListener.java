/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchMonitor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchDataGet.FirebaseScheduledBatchSummaryGet;
import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.libbatchdata.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class FirebaseScheduledBatchEventListener implements
        ValueEventListener,
        CloudScheduledBatchInterface.GetBatchSummaryResponse {

    private static final String TAG = "FirebaseScheduledBatchEventListener";

    private CloudScheduledBatchInterface.ScheduledBatchesUpdated update;
    private DatabaseReference batchDataRef;
    private ArrayList<Batch> batchList;
    private ResponseCounter responseCounter;

    public FirebaseScheduledBatchEventListener(DatabaseReference batchDataRef, CloudScheduledBatchInterface.ScheduledBatchesUpdated update) {
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        this.batchDataRef = batchDataRef;
        this.update = update;
        batchList = new ArrayList<Batch>();
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");
        batchList.clear();

        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...dataSnapshot exists");

            Long batchCount = dataSnapshot.getChildrenCount();
            Timber.tag(TAG).d("   ...there are " + batchCount + " batch guids at this node");

            if (batchCount > 0) {
                responseCounter = new ResponseCounter(batchCount);
                FirebaseScheduledBatchSummaryGet batchSummaryGet = new FirebaseScheduledBatchSummaryGet();

                for (DataSnapshot batchSnapshot : dataSnapshot.getChildren()) {
                    try {

                        String batchGuid = batchSnapshot.getKey();
                        Timber.tag(TAG).d("...looking for batch data for guid : " + batchGuid);
                        batchSummaryGet.getBatchSummary(batchDataRef, batchGuid, this);

                    } catch (Exception e) {
                        Timber.tag(TAG).e(e);
                    }
                    Timber.tag(TAG).d("scheduled batch list has " + Integer.toString(batchList.size()) + " batches --> ");
                }

            } else {
                //dataSnapshot has no children
                Timber.tag(TAG).d("no scheduled batches in this list");
                update.cloudScheduledBatchesUpdated(batchList);
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("dataSnapshot does not exist");
            update.cloudScheduledBatchesUpdated(batchList);
        }
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("firebase database read error in scheduled batches : " + databaseError.getCode() + " --> " + databaseError.getMessage());
    }

    public void cloudGetScheduledBatchSummarySuccess(Batch batch) {
        responseCounter.onResponse();
        batchList.add(batch);
        Timber.tag(TAG).d("got batch data (batch node) for batch guid : " + batch.getGuid());
        checkIfAllResponseReceived();
    }

    public void cloudGetScheduledBatchSummaryFailure(){
        responseCounter.onResponse();
        Timber.tag(TAG).w("error while trying to get batch data (batch node)");
        checkIfAllResponseReceived();
    }

    private void checkIfAllResponseReceived(){
        if (responseCounter.isFinished()){
            Timber.tag(TAG).d("response counter is finished");
            Timber.tag(TAG).d("batchList has " + batchList.size() + " batches");
            update.cloudScheduledBatchesUpdated(batchList);
        } else {
            Timber.tag(TAG).d("response counter = " + responseCounter.getCount());
        }
    }

}

