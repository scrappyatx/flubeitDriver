/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
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
    ActiveBatchForegroundServiceInterface.StopActiveBatchForegroundServiceResponse,
    CloudActiveBatchInterface.AcknowledgeFinishedBatchResponse {

    private final static String TAG = "UseCaseBatchFinishedRequest";

    private final MobileDeviceInterface device;
    private final Driver driver;
    private final String batchGuid;
    private final Response response;

    public UseCaseBatchFinishedRequest(MobileDeviceInterface device, String batchGuid, Response response){
        this.device = device;
        this.driver = device.getUser().getDriver();
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

        //set the active batch server node to complete (null)
        Timber.tag(TAG).d("   ...set the active batch cloud database server node to complete");
        device.getCloudActiveBatch().updateActiveBatchServerNodeStatus(driver, batchGuid);

        //set a node on the completed server node
        device.getCloudActiveBatch().updateBatchCompletedServerNode(driver, batchGuid);

        //acknowledge that batch is finished
        device.getCloudActiveBatch().acknowledgeFinishedBatchRequest(driver, batchGuid,this);

    }


    public void locationTrackingStopComplete(){
        //do nothing
        Timber.tag(TAG).d("   ...location tracking stopped");
    }
    public void activeBatchForegroundServiceStopped(){
        //do nothing
        Timber.tag(TAG).d("   ...active batch foreground service stopped");
    }

    public void cloudActiveBatchFinishedBatchAckComplete(){
        Timber.tag(TAG).d("   ...cloud database finished Batch acknowledgement COMPLETE");
        response.batchFinishedComplete();
    }

    public interface Response {
        void batchFinishedComplete();
    }
}
