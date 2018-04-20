/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;

/**
 * Created on 11/8/2017
 * Project : Driver
 */

public class UseCaseBatchRemovedRequest implements
        Runnable,
        LocationTelemetryInterface.LocationTrackingStopResponse,
        ActiveBatchForegroundServiceInterface.StopActiveBatchForegroundServiceResponse,
        CloudActiveBatchInterface.AcknowledgeRemovedBatchResponse {

        private MobileDeviceInterface device;
        private Driver driver;
        private String batchGuid;
        private UseCaseBatchRemovedRequest.Response response;

    public UseCaseBatchRemovedRequest(MobileDeviceInterface device, String batchGuid, UseCaseBatchRemovedRequest.Response response){
            this.device = device;
            this.driver = device.getUser().getDriver();
            this.batchGuid = batchGuid;
            this.response = response;
        }

    public void run(){
        /// clear active batch
        device.getActiveBatch().clear();

        // stop location tracking
        device.getLocationTelemetry().locationTrackingStopRequest(this);

        //  stop the foreground service
        device.getActiveBatchForegroundServiceController().stopActiveBatchForegroundServiceRequest(this);

        // stop the active batch channel
        //device.getRealtimeActiveBatchMessages().disconnectRequest(this);

        //set the active batch server node to null
        device.getCloudActiveBatch().updateActiveBatchServerNodeStatus(driver, batchGuid);
        //TODO put a response interface here

        //acknowledge the removed batch
        device.getCloudActiveBatch().acknowledgeRemovedBatchRequest(driver, batchGuid,this);

    }

    //public void activeBatchChannelDisconnectComplete(){
        //do nothing
    //}

    public void locationTrackingStopComplete(){
        //do nothing
    }

    public void activeBatchForegroundServiceStopped(){
        //do nothing
    }

    public void cloudActiveBatchRemovedBatchAckComplete(){
        response.batchRemovedComplete();
    }

    public interface Response {
        void batchRemovedComplete();
    }
}
