/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;

/**
 * Created on 11/8/2017
 * Project : Driver
 */

public class UseCaseBatchFinishedRequest implements
    Runnable,
    LocationTelemetryInterface.LocationTrackingStopResponse,
    RealtimeMessagingInterface.ActiveBatchChannel.ActiveBatchChannelDisconnectResponse,
    ActiveBatchForegroundServiceInterface.StopActiveBatchForegroundServiceResponse,
    CloudDatabaseInterface.GetBatchDetailResponse,
    CloudDatabaseInterface.AcknowledgeFinishedBatchResponse {

    private final static String TAG = "UseCaseBatchFinishedRequest";

    private MobileDeviceInterface device;
    private String batchGuid;
    private Response response;

    public UseCaseBatchFinishedRequest(MobileDeviceInterface device, String batchGuid, Response response){
        this.device = device;
        this.batchGuid = batchGuid;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        /// clear the active batch
        Timber.tag(TAG).d("   ...clearing active batch");
        device.getActiveBatch().clear();

        //stop location tracking
        Timber.tag(TAG).d("   ...stop location tracking");
        device.getLocationTelemetry().locationTrackingStopRequest(this);

        //  stop the foreground service
        Timber.tag(TAG).d("   ...stop the active batch foreground service");
        device.getActiveBatchForegroundServiceController().stopActiveBatchForegroundServiceRequest(this);

        // stop the active batch channel
        Timber.tag(TAG).d("   ...disconnect from active batch channel");
        device.getRealtimeActiveBatchMessages().disconnectRequest(this);

        //set the active batch server node to complete (null)
        Timber.tag(TAG).d("   ...set the active batch cloud database server node to complete");
        device.getCloudDatabase().updateActiveBatchServerNodeStatus(batchGuid);
        //TODO put a response interface here

        //get the batch detail
        Timber.tag(TAG).d("   ...get the detail for this batch");
        device.getCloudDatabase().getBatchDetailRequest(batchGuid, this);

    }

    public void activeBatchChannelDisconnectComplete(){
        //do nothing
        Timber.tag(TAG).d("   ...active batch channel disconnect complete");
    }

    public void locationTrackingStopComplete(){
        //do nothing
        Timber.tag(TAG).d("   ...location tracking stopped");
    }
    public void activeBatchForegroundServiceStopped(){
        //do nothing
        Timber.tag(TAG).d("   ...active batch foreground service stopped");
    }

    public void cloudDatabaseGetBatchDetailSuccess(BatchDetail batchDetail){
        //add this batch to the batch completed server node & acknowledge the finished batch
        Timber.tag(TAG).d("   ...cloud database getBatchDetail success");
        device.getCloudDatabase().updateBatchCompletedServerNode(batchDetail);
        device.getCloudDatabase().acknowledgeFinishedBatchRequest(this);
    }

    public void cloudDatabaseGetBatchDetailFailure(){
        Timber.tag(TAG).w("   ...cloud database getBatchDetail failure");
        //acknowledge the finished batch
        device.getCloudDatabase().acknowledgeFinishedBatchRequest(this);
    }

    public void cloudDatabaseFinishedBatchAckComplete(){
        Timber.tag(TAG).d("   ...cloud database finished Batch acknowledgement COMPLETE");
        response.batchFinishedComplete();
    }

    public interface Response {
        void batchFinishedComplete();
    }
}
