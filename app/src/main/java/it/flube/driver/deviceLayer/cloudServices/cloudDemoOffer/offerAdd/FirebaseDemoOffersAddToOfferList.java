/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offerAdd;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseDemoOffersAddToOfferList implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseDemoOffersAddToOfferList";

    Response response;

    public void addDemoOfferToOfferListRequest(DatabaseReference demoOffersRef, String batchGuid, Response response) {
        this.response = response;
        Timber.tag(TAG).d("demoOffersRef = " + demoOffersRef.toString());

        demoOffersRef.child(batchGuid).setValue(true).addOnCompleteListener(this);
        Timber.tag(TAG).d("adding to demo offer list -> batch Guid : " + batchGuid);
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("onComplete...");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("   ...SUCCESS");
        } else {
            Timber.tag(TAG).w("   ...FAILURE");
            try {
                throw task.getException();
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        response.demoOfferAddToOfferListComplete();
    }

    public interface Response {
        void demoOfferAddToOfferListComplete();
    }
}
