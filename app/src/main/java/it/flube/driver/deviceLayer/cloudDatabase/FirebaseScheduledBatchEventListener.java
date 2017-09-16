/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class FirebaseScheduledBatchEventListener implements ValueEventListener {
    private static final String TAG = "FirebaseScheduledBatchEventListener";

    private CloudDatabaseInterface.ScheduledBatchesUpdated update;

    public FirebaseScheduledBatchEventListener(CloudDatabaseInterface.ScheduledBatchesUpdated update) {
        this.update = update;
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("scheduled batch data CHANGED!");

        ArrayList<BatchCloudDB> batchList = new ArrayList<BatchCloudDB>();

        if (dataSnapshot.exists()) {
            if (dataSnapshot.getChildrenCount() > 0) {
                //dataSnapshot EXISTS & HAS CHILDREN
                // each child node should be an OFFER
                for (DataSnapshot batchSnapshot : dataSnapshot.getChildren()) {
                    try {
                        BatchCloudDB batch = batchSnapshot.getValue(BatchCloudDB.class);
                        batchList.add(batch);
                    } catch (Exception e) {
                        Timber.tag(TAG).e(e);
                    }
                    Timber.tag(TAG).d("scheduled batch list has " + Integer.toString(batchList.size()) + " batches --> ");
                }

            } else {
                //dataSnapshot has no children
                Timber.tag(TAG).d("no scheduled batches in this list");
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("dataSnapshot does not exist");
        }

        update.cloudDatabaseScheduledBatchesUpdated(batchList);
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("firebase database read error in scheduled batches : " + databaseError.getCode() + " --> " + databaseError.getMessage());
    }

}

