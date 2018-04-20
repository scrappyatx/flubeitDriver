/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offersMonitor;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.OffersInterface;
import timber.log.Timber;

/**
 * Created on 9/10/2017
 * Project : Driver
 */

public class FirebaseDemoOffersMonitor {
    private static final String TAG = "FirebaseDemoOffersMonitor";

    private DatabaseReference demoOffersRef;
    private DatabaseReference batchDataRef;
    private OffersInterface offersLists;

    private FirebaseDemoOffersEventListener demoOffersListener;
    private Boolean isListening;

    public FirebaseDemoOffersMonitor(DatabaseReference demoOffersRef, DatabaseReference batchDataRef, OffersInterface offersLists){
        this.demoOffersRef = demoOffersRef;
        this.batchDataRef = batchDataRef;
        this.offersLists = offersLists;

        Timber.tag(TAG).d("demoOffersRef    = " + demoOffersRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        isListening = false;
    }

    public void startListening(){
        if (!isListening) {
            demoOffersListener = new FirebaseDemoOffersEventListener(batchDataRef, new DemoOffersAvailableResponseHandler(offersLists));
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
