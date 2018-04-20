/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim;

import android.os.Handler;
import android.os.Looper;

import com.google.firebase.database.DatabaseReference;

import it.flube.libbatchdata.entities.claimOffer.ClaimOfferResponse;
import timber.log.Timber;

/**
 * Created on 3/20/2018
 * Project : Driver
 */

public class FirebaseClaimOfferResponseMonitor implements
    FirebaseClaimOfferResponseEventListener.Response {
    private static final String TAG = "FirebaseClaimOfferResponseMonitor";
    private static final Integer TIMEOUT_MSEC = 5000;    //5 seconds

    private DatabaseReference claimOfferResponseRef;
    private String batchGuid;

    private FirebaseClaimOfferResponseEventListener offerResponseListener;
    private Response response;
    private Boolean offerClaimed;

    public FirebaseClaimOfferResponseMonitor(DatabaseReference claimOfferResponseRef, String batchGuid, Response response){
        Timber.tag(TAG).d("FirebaseClaimOfferResponseMonitor created...");
        this.response = response;
        this.claimOfferResponseRef = claimOfferResponseRef;
        this.batchGuid = batchGuid;
        offerResponseListener = new FirebaseClaimOfferResponseEventListener(this);
        Timber.tag(TAG).d("   ...claimOfferResponseRef    = " + claimOfferResponseRef.toString());
        Timber.tag(TAG).d("   ...batchGuid                = " + batchGuid);
    }

    public void startListening(){

        offerClaimed = false;
        claimOfferResponseRef.child(batchGuid).addValueEventListener(offerResponseListener);

        Timber.tag(TAG).d("STARTED listening");
        //create a time delay to check if we have timed out without getting a claim response from the server
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable(){
                public void run () {
                    //actions to do after timeout
                    Timber.tag(TAG).d("   ...timeout expiry elapsed, see if offer was claimed");
                    if (!offerClaimed){
                        Timber.tag(TAG).d("      ...offer wasn't claimed, return timeout");
                        response.offerTimeout();
                    } else {
                        Timber.tag(TAG).d("      ...offer was claimed, do nothing");
                    }
                }
            }, TIMEOUT_MSEC);

    }


    /// callback from event listener
    public void offerClaimed(ClaimOfferResponse claimOfferResponse) {
        //stop listenening
        Timber.tag(TAG).d("offer claimed, removing offerResponseListener");
        offerClaimed = true;
        claimOfferResponseRef.child(batchGuid).removeEventListener(offerResponseListener);
        response.offerClaimed(claimOfferResponse);
    }


    public interface Response {
        public void offerClaimed(ClaimOfferResponse claimOfferResponse);

        public void offerTimeout();
    }

}
