/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.offersMonitor;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.OffersInterface;
import timber.log.Timber;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class FirebasePublicOffersMonitor {
    private static final String TAG = "FirebasePublicOffersMonitor";

    private DatabaseReference publicOffersRef;
    private DatabaseReference batchDataRef;
    private OffersInterface offersLists;

    private FirebasePublicOffersEventListener publicOffersListener;
    private Boolean isListening;

    public FirebasePublicOffersMonitor(DatabaseReference publicOffersRef, DatabaseReference batchDataRef, OffersInterface offersLists){
        this.publicOffersRef = publicOffersRef;
        this.batchDataRef = batchDataRef;
        this.offersLists = offersLists;

        Timber.tag(TAG).d("publicOffersRef    = " + publicOffersRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        isListening = false;
    }

    public void startListening(){
        if (!isListening) {
            publicOffersListener = new FirebasePublicOffersEventListener(batchDataRef, new PublicOffersAvailableResponseHandler(offersLists));
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

