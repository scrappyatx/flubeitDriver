/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudServerMonitoringInterface;
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
    CloudActiveBatchInterface.GetBatchDetailResponse,
    LocationTelemetryInterface.LocationTrackingStopResponse,
    ActiveBatchForegroundServiceInterface.StopActiveBatchForegroundServiceResponse,
    CloudServerMonitoringInterface.BatchFinishedResponse,
    CloudActiveBatchInterface.AcknowledgeFinishedBatchResponse {

    private final static String TAG = "UseCaseBatchFinishedRequest";

    private final MobileDeviceInterface device;
    private final Driver driver;
    private final String batchGuid;

    private final Response response;

    private BatchDetail batchDetail;

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

        // get the batchDetail
        device.getCloudActiveBatch().getActiveBatchDetailRequest(driver, batchGuid, this);

    }

    public void cloudGetActiveBatchDetailFailure(){
        Timber.tag(TAG).w("cloudGetActiveBatchDetailFailure -> should never get here");
        response.batchFinishedComplete();
    }

    public void cloudGetActiveBatchDetailSuccess(BatchDetail batchDetail){
        Timber.tag(TAG).d("cloudGetActiveBatchDetailSuccess");
        this.batchDetail = batchDetail;

        //stop location tracking
        Timber.tag(TAG).d("   ...stop location tracking");
        device.getLocationTelemetry().locationTrackingStopRequest(this);

    }

    public void locationTrackingStopComplete(){
        Timber.tag(TAG).d("   ...location tracking stopped");
        //  stop the foreground service
        device.getActiveBatchForegroundServiceController().stopActiveBatchForegroundServiceRequest(this);
    }
    public void activeBatchForegroundServiceStopped(){
        Timber.tag(TAG).d("   ...active batch foreground service stopped");
        //remove batch from server monitoring
        Timber.tag(TAG).d("   ...set the active batch cloud database server node to complete");
        device.getCloudServerMonitoring().batchFinishedRequest(driver, batchDetail, this);
    }

    public void cloudServerMonitoringBatchFinishedComplete(String batchGuid){
        Timber.tag(TAG).d("cloudServerMonitoringBatchFinishedComplete");
        //acknowledge that batch is finished
        device.getCloudActiveBatch().acknowledgeFinishedBatchRequest(driver, batchGuid,this);
    }

    public void cloudActiveBatchFinishedBatchAckComplete(){
        Timber.tag(TAG).d("   ...cloud database finished Batch acknowledgement COMPLETE");
        response.batchFinishedComplete();
    }

    public interface Response {
        void batchFinishedComplete();
    }
}
