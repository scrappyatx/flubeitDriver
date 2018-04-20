/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch;

import com.google.firebase.database.DatabaseReference;

import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.forfeitBatch.ForfeitBatchResponse;
import timber.log.Timber;

/**
 * Created on 3/30/2018
 * Project : Driver
 */
public class FirebaseProductionBatchForfeit implements
    BatchForfeitRequestWrite.Response,
    BatchForfeitResponseMonitor.Response {

    private static final String TAG = "FirebaseProductionBatchForfeit";


    private BatchForfeitResponseMonitor forfeitResponseMonitor;
    private String batchGuid;
    private Response response;

    public FirebaseProductionBatchForfeit(){

    }

    public void forfeitBatchRequest(DatabaseReference batchForfeitRequestRef, DatabaseReference batchForfeitResponseRef,
                                    String clientId, String batchGuid, BatchDetail.BatchType batchType, Response response){

        Timber.tag(TAG).d("forfeitBatchRequest START...");
        Timber.tag(TAG).d("   ...batchForfeitRequestRef  = " + batchForfeitRequestRef.toString());
        Timber.tag(TAG).d("   ...batchForfeitResponseRef = " + batchForfeitResponseRef.toString());
        Timber.tag(TAG).d("   ...clientId                = " + clientId);
        Timber.tag(TAG).d("   ...batchGuid               = " + batchGuid);
        Timber.tag(TAG).d("   ...batchType               = " + batchType.toString());

        this.batchGuid = batchGuid;
        this.response = response;


        //write the request
        new BatchForfeitRequestWrite().writeForfeitRequest(batchForfeitRequestRef, clientId, batchGuid, batchType, this);

        //start monitoring for a response
        forfeitResponseMonitor = new BatchForfeitResponseMonitor(batchForfeitResponseRef, batchGuid, this);
        forfeitResponseMonitor.startListening();

    }

    /// callback from BatchForfeitResponseMonitor
    public void batchForfeitResponseTimeout(){
        Timber.tag(TAG).d("   ...batchForfeitResponseTimeout");
                response.batchForfeitTimeout(batchGuid);
    }

    public void batchForfeitResponseReceived(ForfeitBatchResponse forfeitBatchResponse){
        Timber.tag(TAG).d("   ...batchForfeitResponseReceived");

        //see if batch was forfeit or not
        if (forfeitBatchResponse.getApproved()){
            //batch was forfeit
            Timber.tag(TAG).d("      ...batch was forfeit!");
            response.batchForfeitSuccess(batchGuid);
        } else {
            //batch was not forfeit
            Timber.tag(TAG).d("      ...batch was not forfeit");
            Timber.tag(TAG).d("      ...reason -> " + forfeitBatchResponse.getReason());
            response.batchForfeitDenied(batchGuid, forfeitBatchResponse.getReason());
        }
    }

    /// callback from FirebaseOfferClaimRequestWriteOffer
    public void writeForfeitRequestComplete(){
        Timber.tag(TAG).d("   ...writeForfeitRequestComplete");
    }

    public interface Response {
        void batchForfeitSuccess(String batchGuid);

        void batchForfeitTimeout(String batchGuid);

        void batchForfeitDenied(String batchGuid, String reason);
    }
}
