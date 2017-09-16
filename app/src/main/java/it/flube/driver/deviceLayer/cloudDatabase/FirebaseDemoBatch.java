/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class FirebaseDemoBatch {
    private static final String TAG = "FirebaseDemoBatch";

    public void saveDemoBatchRequest(DatabaseReference reference,Offer offer, CloudDatabaseInterface.SaveDemoBatchResponse response) {
        reference.child(offer.getGUID()).setValue(offer).addOnCompleteListener(new FirebaseDemoBatch.SaveDemoBatchCompleteListener(response));

        Timber.tag(TAG).d("saving DEMO BATCH object --> batch GUID : " + offer.getGUID());


    }

    private class SaveDemoBatchCompleteListener implements OnCompleteListener<Void> {

        private CloudDatabaseInterface.SaveDemoBatchResponse response;

        public SaveDemoBatchCompleteListener(CloudDatabaseInterface.SaveDemoBatchResponse response){
            this.response = response;
        }
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("saveDemoBatchRequest --> SUCCESS");
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("saveDemoBatchRequest --> FAILURE");
            }
            response.cloudDatabaseDemoBatchSaveComplete();
        }
    }


    public void deleteDemoBatchRequest(DatabaseReference reference, Offer offer, CloudDatabaseInterface.DeleteDemoBatchResponse response) {
        reference.child(offer.getGUID()).setValue(null).addOnCompleteListener(new FirebaseDemoBatch.DeleteDemoBatchCompleteListener(response));

        Timber.tag(TAG).d("deleting DEMO BATCH object --> batch GUID : " + offer.getGUID());

    }

    private class DeleteDemoBatchCompleteListener implements OnCompleteListener<Void> {

        private CloudDatabaseInterface.DeleteDemoBatchResponse response;

        public DeleteDemoBatchCompleteListener(CloudDatabaseInterface.DeleteDemoBatchResponse response){
            this.response = response;
        }
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("deleteDemoBatchRequest --> SUCCESS");
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("deleteDemoBatchRequest --> FAILURE");
            }
            response.cloudDatabaseDemoBatchDeleteComplete();
        }
    }




}
