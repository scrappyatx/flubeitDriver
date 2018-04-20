/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offerClaim;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseDemoOffersRemove implements
        OnCompleteListener<Void> {

    private static final String TAG = "FirebaseDemoOffersRemove";

    private Response response;

    public void removeDemoOfferFromOfferListRequest(DatabaseReference demoOffersRef, String batchGuid, Response response) {
        this.response = response;
        Timber.tag(TAG).d("demoOffersRef = " + demoOffersRef.toString());
        demoOffersRef.child(batchGuid).setValue(null).addOnCompleteListener(this);
        Timber.tag(TAG).d("removing batch guid from demo offer list --> batchGuid : " + batchGuid);
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("onComplete...");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("   ...SUCCESS");
        } else {
            Timber.tag(TAG).w("   ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        response.demoOfferRemoveComplete();
    }

    public interface Response {
        void demoOfferRemoveComplete();
    }
}
