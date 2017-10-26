/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.offers.publicOffers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers.DemoOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.publicOffers.PublicOffersAvailableResponseHandler;
import it.flube.driver.deviceLayer.cloudDatabase.offers.demoOffers.FirebaseDemoOffersEventListener;
import it.flube.driver.modelLayer.entities.Driver;
import timber.log.Timber;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class FirebasePublicOffersMonitor {
    private static final String TAG = "FirebasePublicOffersMonitor";

    private DatabaseReference publicOffersRef;
    private DatabaseReference batchDataRef;

    private FirebasePublicOffersEventListener publicOffersListener;
    private Boolean isListening;

    public FirebasePublicOffersMonitor(DatabaseReference publicOffersRef, DatabaseReference batchDataRef){
        this.publicOffersRef = publicOffersRef;
        this.batchDataRef = batchDataRef;

        Timber.tag(TAG).d("publicOffersRef    = " + publicOffersRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        isListening = false;
    }

    public void startListening(){
        if (!isListening) {
            publicOffersListener = new FirebasePublicOffersEventListener(batchDataRef, new PublicOffersAvailableResponseHandler());
            publicOffersRef.addValueEventListener(publicOffersListener);
            isListening = true;
            Timber.tag(TAG).d("STARTED listening");
        } else {
            Timber.tag(TAG).d("...called startListening when already listening...");
        }
    }

    public void stopListening(){
        if (isListening) {
            publicOffersRef.removeEventListener(publicOffersListener);
            isListening = false;
            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("...called stopListening when not listening...");
        }
    }
}

