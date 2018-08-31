/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 11/8/2017
 * Project : Driver
 */

public class UseCaseNoBatchRequest implements
        Runnable,
        LocationTelemetryInterface.LocationTrackingStopResponse,
        ActiveBatchForegroundServiceInterface.StopActiveBatchForegroundServiceResponse {

    private final MobileDeviceInterface device;
    private final Response response;

    public UseCaseNoBatchRequest(MobileDeviceInterface device, Response response){
        this.device = device;
        this.response = response;
    }

    public void run(){
        /// clear active batch
        device.getActiveBatch().clear();

        // stop location tracking
        device.getLocationTelemetry().locationTrackingStopRequest(this);


    }

    public void locationTrackingStopComplete(){
        //  stop the foreground service
        device.getActiveBatchForegroundServiceController().stopActiveBatchForegroundServiceRequest(this);
    }

    public void activeBatchForegroundServiceStopped(){
        response.noBatchComplete();
    }

    public interface Response {
        void noBatchComplete();
    }


}
