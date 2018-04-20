/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch;

import android.os.Handler;
import android.os.Looper;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim.FirebaseClaimOfferResponseEventListener;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.libbatchdata.entities.forfeitBatch.ForfeitBatchResponse;
import timber.log.Timber;

/**
 * Created on 3/31/2018
 * Project : Driver
 */
public class BatchForfeitResponseMonitor implements
    BatchForfeitResponseEventListener.Response {

    private static final String TAG="BatchForfeitResponseMonitor";
    private static final Integer TIMEOUT_MSEC = 5000;    //5 seconds

    private BatchForfeitResponseEventListener forfeitResponseListener;
    private DatabaseReference forfeitBatchResponseRef;
    private String batchGuid;

    private Boolean responseReceived;
    private Response response;

    public BatchForfeitResponseMonitor(DatabaseReference forfeitBatchResponseRef, String batchGuid, Response response){
        Timber.tag(TAG).d("BatchForfeitResponseMonitor created...");
        this.response = response;
        this.forfeitBatchResponseRef = forfeitBatchResponseRef;
        this.batchGuid = batchGuid;
        forfeitResponseListener = new BatchForfeitResponseEventListener(this);

        Timber.tag(TAG).d("   ...forfeitBatchResponseRef    = " + forfeitBatchResponseRef.toString());
        Timber.tag(TAG).d("   ...batchGuid                = " + batchGuid);
    }

    public void startListening(){

        responseReceived = false;
        forfeitBatchResponseRef.child(batchGuid).addValueEventListener(forfeitResponseListener);


        //create a time delay to check if we have timed out without getting a claim response from the server
        Timber.tag(TAG).d("...STARTED listeninng");
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable(){
            public void run () {
                //actions to do after timeout
                Timber.tag(TAG).d("   ...timeout expired, see if we got a response...");
                if (!responseReceived) {
                    Timber.tag(TAG).d("      ...we didn't get a response, respond with TIMEOUT");
                    response.batchForfeitResponseTimeout();
                } else {
                    Timber.tag(TAG).d("      ...we got a response, do nothing");
                }
            }
        }, TIMEOUT_MSEC);
    }


    public void forfeitBatchResponseReceived(ForfeitBatchResponse forfeitBatchResponse){
        //stop listenening
        responseReceived = true;
        forfeitBatchResponseRef.child(batchGuid).removeEventListener(forfeitResponseListener);
        response.batchForfeitResponseReceived(forfeitBatchResponse);
    }

    public interface Response {
        void batchForfeitResponseReceived(ForfeitBatchResponse forfeitBatchResponse);

        void batchForfeitResponseTimeout();
    }
}
