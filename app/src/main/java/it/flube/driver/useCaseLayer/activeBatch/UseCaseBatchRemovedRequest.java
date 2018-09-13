/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudServerMonitoringInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 11/8/2017
 * Project : Driver
 */

public class UseCaseBatchRemovedRequest implements
        Runnable,
        CloudActiveBatchInterface.GetBatchDetailResponse,
        LocationTelemetryInterface.LocationTrackingStopResponse,
        ActiveBatchForegroundServiceInterface.StopActiveBatchForegroundServiceResponse,
        CloudActiveBatchInterface.AcknowledgeRemovedBatchResponse,
        CloudServerMonitoringInterface.BatchRemovedResponse {

    private final static String TAG = "UseCaseBatchRemovedRequest";

        private MobileDeviceInterface device;
        private Driver driver;
        private String batchGuid;
        private BatchDetail batchDetail;
        private UseCaseBatchRemovedRequest.Response response;

    public UseCaseBatchRemovedRequest(MobileDeviceInterface device, String batchGuid, UseCaseBatchRemovedRequest.Response response){
            this.device = device;
            this.driver = device.getCloudAuth().getDriver();
            this.batchGuid = batchGuid;
            this.response = response;
        }

    public void run(){
        /// clear active batch
        device.getActiveBatch().clear();

        device.getCloudActiveBatch().getActiveBatchDetailRequest(driver, batchGuid, this);
    }

    public void cloudGetActiveBatchDetailFailure(){
        Timber.tag(TAG).w("cloudGetActiveBatchDetailFailure -> should never get here");
        response.batchRemovedComplete();
    }

    public void cloudGetActiveBatchDetailSuccess(BatchDetail batchDetail){
        Timber.tag(TAG).d("cloudGetActiveBatchDetailSuccess");
        this.batchDetail = batchDetail;

        //stop location tracking
        Timber.tag(TAG).d("   ...stop location tracking");
        device.getLocationTelemetry().locationTrackingStopRequest(this);
    }

    public void locationTrackingStopComplete(){
        //  stop the foreground service
        device.getActiveBatchForegroundServiceController().stopActiveBatchForegroundServiceRequest(this);
    }

    public void activeBatchForegroundServiceStopped(){
        //set the active batch server node to null
        device.getCloudServerMonitoring().batchRemovedRequest(driver, batchDetail, this );
    }

    public void cloudServerMonitoringBatchRemovedComplete(String batchGuid){
        //acknowledge the removed batch
        device.getCloudActiveBatch().acknowledgeRemovedBatchRequest(driver, batchGuid,this);
    }

    public void cloudActiveBatchRemovedBatchAckComplete(){
        response.batchRemovedComplete();
    }

    public interface Response {
        void batchRemovedComplete();
    }
}
