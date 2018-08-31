/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.demoBatch.FirebaseDemoBatchStart;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.productionBatch.FirebaseProductionBatchStart;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchDataGet.FirebaseScheduledBatchDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.demoBatch.FirebaseDemoBatchForfeit;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch.FirebaseProductionBatchForfeit;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseActiveBatchStart implements
        FirebaseProductionBatchStart.Response,
        CloudActiveBatchInterface.GetBatchDetailResponse {

    private static final String TAG = "FirebaseActiveBatchStart";

    private DatabaseReference activeBatchRef;
    private DatabaseReference batchDataRef;
    private DatabaseReference batchStartRequestRef;
    private DatabaseReference batchStartResponseRef;
    private Driver driver;
    private String batchGuid;
    private CloudActiveBatchInterface.StartActiveBatchResponse response;

    public void startBatchRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef,
                                  DatabaseReference batchStartRequestRef, DatabaseReference batchStartResponseRef,
                                  Driver driver, String batchGuid, ActiveBatchManageInterface.ActorType actorType, CloudActiveBatchInterface.StartActiveBatchResponse response){

        this.activeBatchRef = activeBatchRef;
        this.driver = driver;
        this.batchDataRef = batchDataRef;
        this.batchStartRequestRef = batchStartRequestRef;
        this.batchStartResponseRef = batchStartResponseRef;

        this.batchGuid = batchGuid;
        this.response = response;

        Timber.tag(TAG).d("activeBatchRef          = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef            = " + batchDataRef.toString());
        Timber.tag(TAG).d("batchStartRequestRef    = " + batchStartRequestRef.toString());
        Timber.tag(TAG).d("batchStartResponseRef   = " + batchStartResponseRef.toString());
        Timber.tag(TAG).d("driver clientId         = " + driver.getClientId());
        Timber.tag(TAG).d("driver displayName      = " + driver.getNameSettings().getDisplayName());
        Timber.tag(TAG).d("driver dialNumber       = " + driver.getPhoneSettings().getDialNumber());
        Timber.tag(TAG).d("batchGuid               = " + batchGuid);
        Timber.tag(TAG).d("actorType               = " + actorType);

        // 1. determine batchType
        // 2. if batchType =
        //          PRODUCTION,
        //          PRODUCTION_TEST,
        //          MOBILE_DEMO
        new FirebaseActiveBatchDetailGet().getBatchDetailRequest(batchDataRef, batchGuid, this);

    }

    /// GOT SCHEDULED BATCH DETAIL
    public void cloudGetActiveBatchDetailSuccess(BatchDetail batchDetail){
        Timber.tag(TAG).d("   ...cloudGetScheduledeBatchDetailSuccess");
        Timber.tag(TAG).d("      ...batchType = " + batchDetail.getBatchType().toString());
        switch (batchDetail.getBatchType()) {
            case MOBILE_DEMO:
                new FirebaseDemoBatchStart().startBatchRequest(activeBatchRef, batchDataRef, driver, batchGuid, ActiveBatchManageInterface.ActorType.MOBILE_USER, response);
                break;
            case PRODUCTION:
                new FirebaseProductionBatchStart().startBatchRequest(activeBatchRef, batchDataRef, batchStartRequestRef, batchStartResponseRef, driver, batchDetail,this);
                break;
            case PRODUCTION_TEST:
                new FirebaseProductionBatchStart().startBatchRequest(activeBatchRef, batchDataRef, batchStartRequestRef, batchStartResponseRef, driver, batchDetail, this);
                break;
            default:
                Timber.tag(TAG).w("      ...no batchType, should never get here");
                response.cloudStartActiveBatchFailure(batchGuid);
                break;
        }
    }

    //// COULDN'T GET SCHEDULED BATCH DETAIL
    public void cloudGetActiveBatchDetailFailure(){
        Timber.tag(TAG).d("   ...cloudGetScheduledBatchDetailFailure");
        response.cloudStartActiveBatchFailure(batchGuid);
    }

    //// PRODUCTION BATCH START RESULT
    public void batchStartSuccess(String batchGuid, String driverProxyDialNumber, String driverProxyDisplayNumber){
        Timber.tag(TAG).d("   ...production batchStartSuccess");
        response.cloudStartActiveBatchSuccess(batchGuid, driverProxyDialNumber, driverProxyDisplayNumber);
    }

    public void batchStartTimeout(String batchGuid){
        Timber.tag(TAG).d("   ...production batchStartTimeout");
        response.cloudStartActiveBatchTimeout(batchGuid);
    }

    public void batchStartDenied(String batchGuid, String reason){
        Timber.tag(TAG).d("   ...production batchStartDenied");
        response.cloudStartActiveBatchDenied(batchGuid, reason);
    }

}
