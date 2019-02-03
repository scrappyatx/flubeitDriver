/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.productionBatch;

import android.os.Handler;
import android.os.Looper;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch.BatchForfeitResponseEventListener;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch.BatchForfeitResponseMonitor;
import it.flube.libbatchdata.entities.forfeitBatch.ForfeitBatchResponse;
import it.flube.libbatchdata.entities.startBatch.StartBatchResponse;
import timber.log.Timber;

/**
 * Created on 6/9/2018
 * Project : Driver
 */
public class BatchStartResponseMonitor implements
        BatchStartResponseEventListener.Response {

    private static final String TAG="BatchStartResponseMonitor";
    private static final Integer TIMEOUT_MSEC = 10000;    //10 seconds

    private BatchStartResponseEventListener startResponseListener;
    private DatabaseReference startBatchResponseRef;
    private String batchGuid;
    private String requestGuid;

    private Boolean responseReceived;
    private Response response;

    public BatchStartResponseMonitor(DatabaseReference startBatchResponseRef, String batchGuid, String requestGuid, Response response){
        Timber.tag(TAG).d("BatchStartResponseMonitor created...");
        this.response = response;
        this.startBatchResponseRef = startBatchResponseRef;
        this.batchGuid = batchGuid;
        this.requestGuid = requestGuid;

        startResponseListener = new BatchStartResponseEventListener(this);

        Timber.tag(TAG).d("   ...startBatchResponseRef    = " + startBatchResponseRef.toString());
        Timber.tag(TAG).d("   ...batchGuid                = " + batchGuid);
        Timber.tag(TAG).d("   ...requestGuid              = " + requestGuid);

    }

    public void startListening(){

        responseReceived = false;
        startBatchResponseRef.child(batchGuid).child(requestGuid).addValueEventListener(startResponseListener);


        //create a time delay to check if we have timed out without getting a claim response from the server
        Timber.tag(TAG).d("...STARTED listeninng");
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable(){
            public void run () {
                //actions to do after timeout
                Timber.tag(TAG).d("   ...timeout expired, see if we got a response...");
                if (!responseReceived) {
                    Timber.tag(TAG).d("      ...we didn't get a response, respond with TIMEOUT");
                    response.batchStartResponseTimeout();
                } else {
                    Timber.tag(TAG).d("      ...we got a response, do nothing");
                }
            }
        }, TIMEOUT_MSEC);
    }


    public void startBatchResponseReceived(StartBatchResponse startBatchResponse){
        //stop listenening
        responseReceived = true;
        startBatchResponseRef.child(batchGuid).child(requestGuid).removeEventListener(startResponseListener);
        response.batchStartResponseReceived(startBatchResponse);
    }

    public interface Response {
        void batchStartResponseReceived(StartBatchResponse startBatchResponse);

        void batchStartResponseTimeout();
    }
}
