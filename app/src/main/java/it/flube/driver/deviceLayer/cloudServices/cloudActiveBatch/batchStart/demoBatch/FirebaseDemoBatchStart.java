/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.demoBatch;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate.FirebaseDriverProxyInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.FirebaseActiveBatchSetData;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import timber.log.Timber;

/**
 * Created on 6/9/2018
 * Project : Driver
 */
public class FirebaseDemoBatchStart implements
        FirebaseActiveBatchSetData.Response,
        CloudActiveBatchInterface.UpdateDriverProxyInfoResponse {

    private static final String TAG = "FirebaseDemoBatchStart";

    private static final String DRIVER_PROXY_DIAL_NUMBER = "demo";
    private static final String DRIVER_PROXY_DISPLAY_NUMBER = "demo";
    private String batchGuid;
    private DatabaseReference activeBatchRef;
    private ActiveBatchManageInterface.ActorType actorType;
    private CloudActiveBatchInterface.StartActiveBatchResponse response;

    public void startBatchRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef, Driver driver, String batchGuid, ActiveBatchManageInterface.ActorType actorType, CloudActiveBatchInterface.StartActiveBatchResponse response){
        this.response = response;
        this.batchGuid = batchGuid;
        this.activeBatchRef = activeBatchRef;
        this.actorType = actorType;

        /// set the data in the driver proxy node in batch data
        // save the proxy info in the batchDetail node in the batch data
        new FirebaseDriverProxyInfo().updateDriverProxyInfoRequest(batchDataRef, driver, batchGuid, DRIVER_PROXY_DIAL_NUMBER, DRIVER_PROXY_DISPLAY_NUMBER, this );

        Timber.tag(TAG).d("starting batch : batchGuid " + batchGuid);
    }

    /// callback from updateDriverProxyInfoRequest
    public void cloudActiveBatchUpdateDriverProxyInfoComplete(){
        /// set the data in the active batch node
        new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef,
                batchGuid, 1, 1,
                ActiveBatchManageInterface.ActionType.BATCH_STARTED, actorType, this);
    }

    public void setDataComplete(){
        response.cloudStartActiveBatchSuccess(batchGuid, DRIVER_PROXY_DIAL_NUMBER,DRIVER_PROXY_DISPLAY_NUMBER);
        Timber.tag(TAG).d("starting batch : COMPLETE");
    }
}
