/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.productionBatch;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.UUID;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate.FirebaseDriverProxyInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.FirebaseActiveBatchSetData;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch.BatchForfeitRequestWrite;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch.BatchForfeitResponseMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch.FirebaseProductionBatchForfeit;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.forfeitBatch.ForfeitBatchResponse;
import it.flube.libbatchdata.entities.startBatch.StartBatchResponse;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import timber.log.Timber;

/**
 * Created on 6/9/2018
 * Project : Driver
 */
public class FirebaseProductionBatchStart implements
        FirebaseActiveBatchSetData.Response,
        BatchStartRequestWrite.Response,
        BatchStartResponseMonitor.Response,
        CloudActiveBatchInterface.UpdateDriverProxyInfoResponse {

    private static final String TAG = "FirebaseProductionBatchStart";

    private DatabaseReference activeBatchRef;
    private DatabaseReference batchDataRef;
    private Driver driver;
    private BatchStartResponseMonitor startResponseMonitor;
    private String batchGuid;
    private Response response;

    String driverProxyDialNumber;
    String driverProxyDisplayNumber;

    public FirebaseProductionBatchStart(){

    }

    public void startBatchRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef, DatabaseReference batchStartRequestRef, DatabaseReference batchStartResponseRef,
                                  Driver driver, BatchDetail batchDetail, Response response){

        Timber.tag(TAG).d("startBatchRequest START...");
        Timber.tag(TAG).d("   ...activeBatchRef          = " + activeBatchRef.toString());
        Timber.tag(TAG).d("   ...batchDataRef            = " + batchDataRef.toString());
        Timber.tag(TAG).d("   ...batchStartRequestRef    = " + batchStartRequestRef.toString());
        Timber.tag(TAG).d("   ...batchStartResponseRef   = " + batchStartResponseRef.toString());
        Timber.tag(TAG).d("   ...driver clientId         = " + driver.getClientId());
        Timber.tag(TAG).d("   ...driver displayName      = " + driver.getNameSettings().getDisplayName());
        Timber.tag(TAG).d("   ...driver dialNumber       = " + driver.getPhoneSettings().getDialNumber());
        Timber.tag(TAG).d("   ...batchGuid               = " + batchDetail.getBatchGuid());
        Timber.tag(TAG).d("   ...batchType               = " + batchDetail.getBatchType().toString());
        Timber.tag(TAG).d("   ...expectedStartTime       = " + batchDetail.getExpectedStartTime().toString());

        this.activeBatchRef = activeBatchRef;
        this.batchDataRef = batchDataRef;
        this.batchGuid = batchDetail.getBatchGuid();
        this.driver = driver;
        this.response = response;

        //get a guid for this request
        String requestGuid = UUID.randomUUID().toString();
        Timber.tag(TAG).d("   ...requestGuid             = " + requestGuid);

        //write the request
        new BatchStartRequestWrite().writeStartRequest(batchStartRequestRef, driver, batchDetail, requestGuid, this);

        //start monitoring for a response
        startResponseMonitor = new BatchStartResponseMonitor(batchStartResponseRef, batchGuid, requestGuid, this);
        startResponseMonitor.startListening();

    }

    /// callback from BatchStartResponseMonitor
    public void batchStartResponseTimeout(){
        Timber.tag(TAG).d("   ...batchStartResponseTimeout");
        response.batchStartTimeout(batchGuid);
    }

    public void batchStartResponseReceived(StartBatchResponse startBatchResponse){
        Timber.tag(TAG).d("   ...batchForfeitResponseReceived");

        //see if batch was forfeit or not
        if (startBatchResponse.getApproved()){
            //batch was started
            Timber.tag(TAG).d("      ...batch was started!");

            this.driverProxyDialNumber = startBatchResponse.getDriverProxyDialNumber();
            this.driverProxyDisplayNumber = startBatchResponse.getDriverProxyDisplayNumber();

            // save the proxy info in the batchDetail node in the batch data
            new FirebaseDriverProxyInfo().updateDriverProxyInfoRequest(batchDataRef, driver, batchGuid, driverProxyDialNumber, driverProxyDisplayNumber, this );

        } else {
            //batch was not started
            Timber.tag(TAG).d("      ...batch was not started");
            Timber.tag(TAG).d("      ...reason -> " + startBatchResponse.getReason());
            response.batchStartDenied(batchGuid, startBatchResponse.getReason());
        }
    }

    /// callback from FirebaseStartRequestWrite
    public void writeStartRequestComplete(){
        Timber.tag(TAG).d("   ...writeStartRequestComplete");
    }

    /// callback from updateDriverProxyInfoRequest
    public void cloudActiveBatchUpdateDriverProxyInfoComplete(){
        /// now set the data in the active batch node
        new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef,
                batchGuid, 1, 1,
                ActiveBatchManageInterface.ActionType.BATCH_STARTED, ActiveBatchManageInterface.ActorType.MOBILE_USER, this);
    }


    //// callback from ActiveBatchSetData
    public void setDataComplete(){
        response.batchStartSuccess(batchGuid, driverProxyDialNumber, driverProxyDisplayNumber);
        Timber.tag(TAG).d("starting batch : COMPLETE");
    }


    public interface Response {
        void batchStartSuccess(String batchGuid, String driverProxyDialNumber, String driverProxyDisplayNumber);

        void batchStartTimeout(String batchGuid);

        void batchStartDenied(String batchGuid, String reason);
    }
}
