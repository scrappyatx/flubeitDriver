/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 9/10/2017
 * Project : Driver
 */

public class FirebaseDemoOffers {
    private static final String TAG = "FirebaseDemoOffers";

    public void saveDemoOfferRequest(DatabaseReference reference, Offer offer, CloudDatabaseInterface.SaveDemoOfferResponse response) {
        reference.child(offer.getGUID()).setValue(offer).addOnCompleteListener(new SaveDemoOfferCompleteListener(response));

        Timber.tag(TAG).d("saving DEMO OFFER object --> device GUID : " + offer.getGUID());
    }

    private class SaveDemoOfferCompleteListener implements OnCompleteListener<Void> {

        private CloudDatabaseInterface.SaveDemoOfferResponse response;

        public SaveDemoOfferCompleteListener(CloudDatabaseInterface.SaveDemoOfferResponse response){
            this.response = response;
        }
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("saveDemoOfferRequest --> SUCCESS");
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("saveDemoOfferRequest --> FAILURE");
            }
            response.cloudDatabaseDemoOfferSaveComplete();
        }
    }

    public void deleteAllDemoOffersRequest(DatabaseReference reference, CloudDatabaseInterface.DeleteAllDemoOfferResponse response) {
        reference.setValue(null).addOnCompleteListener(new DeleteAllDemoOffersCompleteListener(response));
    }

    private class DeleteAllDemoOffersCompleteListener implements OnCompleteListener<Void> {

        private CloudDatabaseInterface.DeleteAllDemoOfferResponse response;

        public DeleteAllDemoOffersCompleteListener(CloudDatabaseInterface.DeleteAllDemoOfferResponse response) {
            this.response = response;
        }

        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("deleteAllDemoOffersRequest --> SUCCESS");
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("deleteAllDemoOffersRequest --> FAILURE");
            }
            response.cloudDatabaseDemoOfferDeleteAllComplete();
        }

    }

    public void deleteDemoOfferRequest(DatabaseReference reference, Offer offer, CloudDatabaseInterface.DeleteDemoOfferResponse response) {
        reference.child(offer.getGUID()).setValue(null).addOnCompleteListener(new DeleteDemoOfferCompleteListener(response));

        Timber.tag(TAG).d("deleting DEMO OFFER object --> device GUID : " + offer.getGUID());
    }

    private class DeleteDemoOfferCompleteListener implements OnCompleteListener<Void> {
        private CloudDatabaseInterface.DeleteDemoOfferResponse response;

        public DeleteDemoOfferCompleteListener(CloudDatabaseInterface.DeleteDemoOfferResponse response) {
            this.response = response;
        }

        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("deleteDemoOfferRequest --> SUCCESS");
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("deleteDemoOfferRequest --> FAILURE");
            }
            response.cloudDatabaseDemoOfferDeleteComplete();
        }
    }
}
