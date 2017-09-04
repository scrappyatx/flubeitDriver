/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class FirebaseScheduledBatchEventListener implements ValueEventListener {
    private static final String TAG = "FirebaseScheduledBatchEventListener";

    private CloudDatabaseInterface.BatchesUpdated update;

    public FirebaseScheduledBatchEventListener(CloudDatabaseInterface.BatchesUpdated update) {
        this.update = update;
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("firebase database batch data CHANGED!");

        try{
            GenericTypeIndicator<ArrayList<BatchCloudDB>> t = new GenericTypeIndicator<ArrayList<BatchCloudDB>>() {};

            ArrayList<BatchCloudDB> batchList = dataSnapshot.getValue(t);

            if (batchList == null) {
                Timber.tag(TAG).d("no batches in this list");
                update.cloudDatabaseNoScheduledBatches();
            } else {
                Timber.tag(TAG).d("assigned batch list has " + Integer.toString(batchList.size()) + " batches");
                update.cloudDatabaseReceivedScheduledBatches(batchList);
            }
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
            update.cloudDatabaseNoScheduledBatches();
        }
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("firebase database read error in assigned batches : " + databaseError.getCode() + " --> " + databaseError.getMessage());
    }

}

