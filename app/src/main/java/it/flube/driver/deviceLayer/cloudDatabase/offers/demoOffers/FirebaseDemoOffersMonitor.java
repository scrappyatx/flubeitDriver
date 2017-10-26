/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.offers.demoOffers;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers.DemoOffersAvailableResponseHandler;
import timber.log.Timber;

/**
 * Created on 9/10/2017
 * Project : Driver
 */

public class FirebaseDemoOffersMonitor {
    private static final String TAG = "FirebaseDemoOffersMonitor";

    private DatabaseReference demoOffersRef;
    private DatabaseReference batchDataRef;

    private FirebaseDemoOffersEventListener demoOffersListener;
    private Boolean isListening;

    public FirebaseDemoOffersMonitor(DatabaseReference demoOffersRef, DatabaseReference batchDataRef){
        this.demoOffersRef = demoOffersRef;
        this.batchDataRef = batchDataRef;

        Timber.tag(TAG).d("demoOffersRef    = " + demoOffersRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        isListening = false;
    }

    public void startListening(){
        if (!isListening) {
            demoOffersListener = new FirebaseDemoOffersEventListener(batchDataRef, new DemoOffersAvailableResponseHandler());
            demoOffersRef.addValueEventListener(demoOffersListener);
            isListening = true;
            Timber.tag(TAG).d("STARTED listening");
        } else {
            Timber.tag(TAG).d("...called startListening when already listening...");
        }
    }

    public void stopListening(){
        if (isListening) {
            demoOffersRef.removeEventListener(demoOffersListener);
            isListening = false;

            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("...called stopListening when not listening...");
        }
    }

}
