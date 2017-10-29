/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.scheduledBatches;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.flube.driver.deviceLayer.cloudDatabase.batchData.batch.FirebaseBatchSummaryGet;
import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class FirebaseScheduledBatchEventListener implements
        ValueEventListener,
        FirebaseBatchSummaryGet.GetBatchSummaryResponse {

    private static final String TAG = "FirebaseScheduledBatchEventListener";

    private CloudDatabaseInterface.ScheduledBatchesUpdated update;
    private DatabaseReference batchDataRef;
    private ArrayList<Batch> batchList;
    private ResponseCounter responseCounter;

    public FirebaseScheduledBatchEventListener(DatabaseReference batchDataRef, CloudDatabaseInterface.ScheduledBatchesUpdated update) {
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
                FirebaseBatchSummaryGet batchSummaryGet = new FirebaseBatchSummaryGet();

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
                update.cloudDatabaseScheduledBatchesUpdated(batchList);
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("dataSnapshot does not exist");
            update.cloudDatabaseScheduledBatchesUpdated(batchList);
        }
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("firebase database read error in scheduled batches : " + databaseError.getCode() + " --> " + databaseError.getMessage());
    }

    public void getBatchSummarySuccess(Batch batch) {
        responseCounter.onResponse();
        batchList.add(batch);
        Timber.tag(TAG).d("got batch data (batch node) for batch guid : " + batch.getGuid());
        checkIfAllResponseReceived();
    }

    public void getBatchSummaryFailure(){
        responseCounter.onResponse();
        Timber.tag(TAG).w("error while trying to get batch data (batch node)");
        checkIfAllResponseReceived();
    }

    private void checkIfAllResponseReceived(){
        if (responseCounter.isFinished()){
            Timber.tag(TAG).d("response counter is finished");
            Timber.tag(TAG).d("batchList has " + batchList.size() + " batches");
            update.cloudDatabaseScheduledBatchesUpdated(batchList);
        } else {
            Timber.tag(TAG).d("response counter = " + responseCounter.getCount());
        }
    }

}

