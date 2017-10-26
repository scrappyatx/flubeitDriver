/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.offers.personalOffers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers.DemoOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.personalOffers.PersonalOffersAvailableResponseHandler;
import it.flube.driver.deviceLayer.cloudDatabase.offers.demoOffers.FirebaseDemoOffersEventListener;
import it.flube.driver.modelLayer.entities.Driver;
import timber.log.Timber;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class FirebasePersonalOffersMonitor {
    private static final String TAG = "FirebasePersonalOffersMonitor";

    private DatabaseReference personalOffersRef;
    private DatabaseReference batchDataRef;

    private FirebasePersonalOffersEventListener personalOffersListener;
    private Boolean isListening;

    public FirebasePersonalOffersMonitor(DatabaseReference personalOffersRef, DatabaseReference batchDataRef){
        this.personalOffersRef = personalOffersRef;
        this.batchDataRef = batchDataRef;

        Timber.tag(TAG).d("personalOffersRef    = " + personalOffersRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        isListening = false;
    }

    public void startListening(){
        if (!isListening) {
            personalOffersListener = new FirebasePersonalOffersEventListener(batchDataRef, new PersonalOffersAvailableResponseHandler());
            personalOffersRef.addValueEventListener(personalOffersListener);
            isListening = true;
            Timber.tag(TAG).d("STARTED listening");
        } else {
            Timber.tag(TAG).d("...called startListening when already listening...");
        }
    }

    public void stopListening(){
        if (isListening) {
            personalOffersRef.removeEventListener(personalOffersListener);
            isListening = false;

            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("...called stopListening when not listening...");
        }
    }
}
