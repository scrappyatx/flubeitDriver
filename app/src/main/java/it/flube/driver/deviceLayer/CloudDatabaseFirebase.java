/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.ScheduledBatchesAvailableResponseHandler;
import it.flube.driver.modelLayer.entities.BatchCloudDB;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 7/5/2017
 * Project : Driver
 */

public class CloudDatabaseFirebase implements CloudDatabaseInterface {
    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static volatile CloudDatabaseFirebase mInstance = new CloudDatabaseFirebase();
    }

    private CloudDatabaseFirebase() {
        setupFirebaseDatabaseForOfflinePersistence();
    }

    public static CloudDatabaseFirebase getInstance() {
        return CloudDatabaseFirebase.Loader.mInstance;
    }

    private final String TAG = "CloudDatabaseFirebase";

    private FirebaseDatabase database;
    private DatabaseReference offersRef;
    private DatabaseReference scheduledBatchesRef;

    private CloudDatabaseInterface.SaveResponse response;

    private void setupFirebaseDatabaseForOfflinePersistence(){
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        Timber.tag(TAG).d("FirebaseDatabase --> setPersistenceEnabled TRUE for OFFLINE persistence");
    }

    public void saveUserRequest(Driver driver, CloudDatabaseInterface.SaveResponse response) {
        this.response = response;
        DatabaseReference usersRef = database.getReference("users");
        usersRef.child(driver.getClientId()).setValue(driver).addOnCompleteListener(new SaveUserCompleteListener());
        Timber.tag(TAG).d("saving DRIVER object --> clientId : " + driver.getClientId() + " name : " + driver.getDisplayName());
    }

    private class SaveUserCompleteListener implements OnCompleteListener<Void> {

        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("saveUserRequest --> SUCCESS");
                response.cloudDatabaseUserSaveComplete();
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("saveUserRequest --> FAILURE");
                response.cloudDatabaseUserSaveComplete();
            }
        }
    }

    public void listenForCurrentOffers() {
        DatabaseReference offersRef = database.getReference("userReadable/offers");
        offersRef.addValueEventListener(new OffersEventListener(new OffersAvailableResponseHandler()));
    }

    private class OffersEventListener implements ValueEventListener {
        private CloudDatabaseInterface.OffersUpdated update;

        public OffersEventListener(CloudDatabaseInterface.OffersUpdated update) {
           this.update = update;
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Timber.tag(TAG).d("firebase database offer data CHANGED!");
            try {
                GenericTypeIndicator<ArrayList<Offer>> t = new GenericTypeIndicator<ArrayList<Offer>>() {};
                ArrayList<Offer> offerList = dataSnapshot.getValue(t);

                if (offerList == null) {
                    Timber.tag(TAG).d("no offers in this list");
                    update.cloudDatabaseNoAvailableOffers();
                } else {
                    Timber.tag(TAG).d("offer list has " + Integer.toString(offerList.size()) + " offers");
                    update.cloudDatabaseAvailableOffersUpdated(offerList);
                }
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }

        public void onCancelled(DatabaseError databaseError) {
            Timber.tag(TAG).e("firebase database read error in offers : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        }

    }


    public void listenForScheduledBatches(Driver driver) {
        DatabaseReference batchRef = database.getReference("userReadable/users/" + driver.getClientId() + "/assignedBatches");
        batchRef.addValueEventListener(new BatchEventListener(new ScheduledBatchesAvailableResponseHandler()));
    }

    private class BatchEventListener implements ValueEventListener {
        private CloudDatabaseInterface.BatchesUpdated update;

        public BatchEventListener(CloudDatabaseInterface.BatchesUpdated update) {
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
            }
        }

        public void onCancelled(DatabaseError databaseError) {
            Timber.tag(TAG).e("firebase database read error in assigned batches : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        }

    }


}
