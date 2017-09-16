/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class FirebaseActiveBatch {
    private static final String TAG = "FirebaseActiveBatch";

    public FirebaseActiveBatch(){

    }

    public void saveActiveBatchRequest(DatabaseReference reference, Driver driver, Batch batch, CloudDatabaseInterface.SaveActiveBatchResponse response) {
        reference.child(batch.getGUID()).setValue(batch).addOnCompleteListener(new SaveActiveBatchCompleteListener(response));
        Timber.tag(TAG).d("saving ACTIVE BATCH ---> driver Id -> " + driver.getClientId() + " batchGUID --> " + batch.getGUID());
    }

    private class SaveActiveBatchCompleteListener implements OnCompleteListener<Void> {
        private CloudDatabaseInterface.SaveActiveBatchResponse response;

        public SaveActiveBatchCompleteListener(CloudDatabaseInterface.SaveActiveBatchResponse response){
            this.response = response;
        }

        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("saveActiveBatchRequest --> SUCCESS");
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("saveActiveBatchRequest --> FAILURE");
            }
            response.cloudDatabaseActiveBatchSaveComplete();
        }
    }

    public void loadActiveBatchRequest(DatabaseReference reference, Driver driver, CloudDatabaseInterface.LoadActiveBatchResponse response) {

        reference.addListenerForSingleValueEvent(new FirebaseActiveBatchEventListener(response));
        Timber.tag(TAG).d("loading ACTIVE BATCH ---> driver Id -> " + driver.getClientId());
    }

    public class FirebaseActiveBatchEventListener implements ValueEventListener {

        private CloudDatabaseInterface.LoadActiveBatchResponse response;

        public FirebaseActiveBatchEventListener(CloudDatabaseInterface.LoadActiveBatchResponse response) {
            this.response = response;
        }

        public void onDataChange(DataSnapshot dataSnapshot) {
            Timber.tag(TAG).d("firebase database active batch data CHANGED!");

            try{
                GenericTypeIndicator<Batch> t = new GenericTypeIndicator<Batch>() {};
                Batch activeBatch = dataSnapshot.getValue(t);

                if (activeBatch == null) {
                    Timber.tag(TAG).d("no active batch");
                    response.cloudDatabaseNoActiveBatchAvailable();
                } else {
                    Timber.tag(TAG).d("active batch GUID --> " + activeBatch.getGUID());
                    response.cloudDatabaseActiveBatchLoaded(activeBatch);
                }
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
                response.cloudDatabaseNoActiveBatchAvailable();
            }
        }

        public void onCancelled(DatabaseError databaseError) {
            Timber.tag(TAG).e("firebase database read error in active batch : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        }

    }

}

