/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.offersMonitor;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.OffersInterface;
import timber.log.Timber;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class FirebasePersonalOffersMonitor {
    private static final String TAG = "FirebasePersonalOffersMonitor";

    private DatabaseReference personalOffersRef;
    private DatabaseReference batchDataRef;
    private OffersInterface offersLists;

    private FirebasePersonalOffersEventListener personalOffersListener;
    private Boolean isListening;

    public FirebasePersonalOffersMonitor(DatabaseReference personalOffersRef, DatabaseReference batchDataRef, OffersInterface offersLists){
        this.personalOffersRef = personalOffersRef;
        this.batchDataRef = batchDataRef;
        this.offersLists = offersLists;

        Timber.tag(TAG).d("personalOffersRef    = " + personalOffersRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        isListening = false;
    }

    public void startListening(){
        if (!isListening) {
            personalOffersListener = new FirebasePersonalOffersEventListener(batchDataRef, new PersonalOffersAvailableResponseHandler(offersLists));
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
