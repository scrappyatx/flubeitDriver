/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchDataGet.FirebaseScheduledBatchDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchDataGet.FirebaseScheduledBatchSummaryGet;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.demoBatch.FirebaseDemoBatchForfeit;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch.FirebaseProductionBatchForfeit;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 3/30/2018
 * Project : Driver
 */
public class FirebaseBatchForfeit implements
        FirebaseDemoBatchForfeit.Response,
        FirebaseProductionBatchForfeit.Response,
        CloudScheduledBatchInterface.GetBatchDetailResponse {

    private static final String TAG = "FirebaseBatchForfeit";

    private DatabaseReference scheduledBatchesRef;
    private DatabaseReference batchDataRef;
    private DatabaseReference batchForfeitRequestRef;
    private DatabaseReference batchForfeitResponseRef;
    private String clientId;
    private String batchGuid;
    private CloudScheduledBatchInterface.BatchForfeitResponse response;

    public FirebaseBatchForfeit(){

    }

    public void forfeitBatchRequest(DatabaseReference scheduledBatchesRef, DatabaseReference batchDataRef,
                                    DatabaseReference batchForfeitRequestRef, DatabaseReference batchForfeitResponseRef,
                                    String clientId, String batchGuid, CloudScheduledBatchInterface.BatchForfeitResponse response){

        this.scheduledBatchesRef = scheduledBatchesRef;
        this.clientId = clientId;
        this.batchDataRef = batchDataRef;
        this.batchForfeitRequestRef = batchForfeitRequestRef;
        this.batchForfeitResponseRef = batchForfeitResponseRef;

        this.batchGuid = batchGuid;
        this.response = response;

        Timber.tag(TAG).d("batchDataRef            = " + batchDataRef.toString());
        Timber.tag(TAG).d("scheduledBatchesRef     = " + scheduledBatchesRef.toString());
        Timber.tag(TAG).d("batchForfeitRequestRef  = " + batchForfeitRequestRef.toString());
        Timber.tag(TAG).d("batchForfeitResponseRef = " + batchForfeitResponseRef.toString());
        Timber.tag(TAG).d("clientId                = " + clientId);
        Timber.tag(TAG).d("batchGuid               = " + batchGuid);

        // 1. determine batchType
        // 2. if batchType =
        //          PRODUCTION,
        //          PRODUCTION_TEST,
        //          MOBILE_DEMO
        new FirebaseScheduledBatchDetailGet().getBatchDetailRequest(batchDataRef, batchGuid, this);

    }

    /// GOT SCHEDULED BATCH DETAIL
    public void cloudGetScheduledBatchDetailSuccess(BatchDetail batchDetail){
        Timber.tag(TAG).d("   ...cloudGetScheduledeBatchDetailSuccess");
        Timber.tag(TAG).d("      ...batchType = " + batchDetail.getBatchType().toString());
        switch (batchDetail.getBatchType()) {
            case MOBILE_DEMO:
                new FirebaseDemoBatchForfeit().demoBatchForfeitRequest(scheduledBatchesRef,batchDataRef, batchGuid, this);
                break;
            case PRODUCTION:
                new FirebaseProductionBatchForfeit().forfeitBatchRequest(batchForfeitRequestRef,batchForfeitResponseRef,clientId, batchGuid, batchDetail.getBatchType(), this);
                break;
            case PRODUCTION_TEST:
                new FirebaseProductionBatchForfeit().forfeitBatchRequest(batchForfeitRequestRef,batchForfeitResponseRef,clientId, batchGuid, batchDetail.getBatchType(), this);
                break;
            default:
                Timber.tag(TAG).w("      ...no batchType, should never get here");
                response.cloudScheduledBatchForfeitFailure(batchGuid);
                break;
        }
    }

    //// COULDN'T GET SCHEDULED BATCH DETAIL
    public void cloudGetScheduledBatchDetailFailure(){
        Timber.tag(TAG).d("   ...cloudGetScheduledBatchDetailFailure");
        response.cloudScheduledBatchForfeitFailure(batchGuid);
    }

    /// DEMO BATCH FORFEIT COMPLETE
    public void demoBatchForfeitComplete(String batchGuid){
        Timber.tag(TAG).d("   ...demoBatchForfeitComplete");
        response.cloudScheduledBatchForfeitSuccess(batchGuid);
    }

    //// PRODUCTION BATCH FORFEIT RESULT
    public void batchForfeitSuccess(String batchGuid){
        Timber.tag(TAG).d("   ...production batchForfeitSuccess");
        response.cloudScheduledBatchForfeitSuccess(batchGuid);
    }

    public void batchForfeitTimeout(String batchGuid){
        Timber.tag(TAG).d("   ...production batchForfeitTimeout");
        response.cloudScheduledBatchForfeitTimeout(batchGuid);
    }

    public void batchForfeitDenied(String batchGuid, String reason){
        Timber.tag(TAG).d("   ...production batchForfeitDenied");
        response.cloudScheduledBatchForfeitDenied(batchGuid, reason);
    }



}
