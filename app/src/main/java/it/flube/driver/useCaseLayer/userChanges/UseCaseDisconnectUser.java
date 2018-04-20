/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.userChanges;

import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPersonalOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPublicOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;

/**
 * Created on 1/2/2018
 * Project : Driver
 */

public class UseCaseDisconnectUser implements
        Runnable,
        ActiveBatchForegroundServiceInterface.StopActiveBatchForegroundServiceResponse,
        LocationTelemetryInterface.LocationTrackingStopResponse,
        CloudDemoOfferInterface.StopMonitoringResponse,
        CloudPersonalOfferInterface.StopMonitoringResponse,
        CloudPublicOfferInterface.StopMonitoringResponse,
        CloudScheduledBatchInterface.StopMonitoringResponse,
        CloudActiveBatchInterface.StopMonitoringResponse {

    private final static String TAG = "UseCaseDisconnectUser";

    private final static Integer responseCount = 7;
    private final MobileDeviceInterface device;
    private final Response response;
    private final ResponseCounter responseCounter;

    public UseCaseDisconnectUser(MobileDeviceInterface device, Response response){
        this.device = device;
        this.response = response;
        this.responseCounter = new ResponseCounter(responseCount);
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        ///
        ///     Do these operations in parallel, continue when they have ALL finished
        ///
        /// 1. stop active batch foreground service
        Timber.tag(TAG).d("   1 --> stop active batch foreground service...");
        device.getActiveBatchForegroundServiceController().stopActiveBatchForegroundServiceRequest(this);

        /// 2. stop location tracking
        Timber.tag(TAG).d("   2 --> stop location tracking...");
        device.getLocationTelemetry().locationTrackingStopRequest(this);

        ///3. stop monitoring cloud demo offers
        Timber.tag(TAG).d("   3 --> stop demo offers monitoring...");
        device.getCloudDemoOffer().stopMonitoringRequest(this);

        ///4. stop monitoring cloud public offers
        Timber.tag(TAG).d("   4 --> stop public offers monitoring...");
        device.getCloudPublicOffer().stopMonitoringRequest(this);

        ///5. stop monitoring cloud personal offers
        Timber.tag(TAG).d("   5 --> stop personal offers monitoring...");
        device.getCloudPersonalOffer().stopMonitoringRequest(this);

        ///6. stop monitoring cloud scheduled batches
        Timber.tag(TAG).d("   6 --> stop scheduled batches monitoring...");
        device.getCloudScheduledBatch().stopMonitoringRequest(this);

        ///7. stop monitoring active batch
        Timber.tag(TAG).d("   7 --> stop active batch monitoring...");
        device.getCloudActiveBatch().stopMonitoringRequest(this);

    }

    public void activeBatchForegroundServiceStopped(){
        Timber.tag(TAG).d("      ...active batch foreground service stopped");
        checkIfFinished();
    }

    public void locationTrackingStopComplete(){
        Timber.tag(TAG).d("      ...location tracking stopped");
        checkIfFinished();
    }

    public void cloudDemoOffersStopMonitoringComplete(){
        Timber.tag(TAG).d("      ...cloudDemoOffersStopMonitoringComplete");
        checkIfFinished();
    }

    public void cloudPublicOffersStopMonitoringComplete(){
        Timber.tag(TAG).d("      ...cloudPublicOffersStopMonitoringComplete");
        checkIfFinished();
    }

    public void cloudPersonalOffersStopMonitoringComplete(){
        Timber.tag(TAG).d("      ...cloudPersonalOffersStopMonitoringComplete");
        checkIfFinished();
    }

    public void cloudScheduledBatchStopMonitoringComplete(){
        Timber.tag(TAG).d("      ...cloudScheduledBatchStopMonitoringComplete");
        checkIfFinished();
    }

    public void cloudActiveBatchStopMonitoringComplete(){
        Timber.tag(TAG).d("      ...cloudActiveBatchStopMonitoringComplete");
        checkIfFinished();
    }

    private void checkIfFinished(){
        Timber.tag(TAG).d("         ...response " + responseCounter.getCount());
        responseCounter.onResponse();
        if (responseCounter.isFinished()){
            /// 5. clear out user & active batch
            Timber.tag(TAG).d("         ...all responses finished, clearing user");
            device.getUser().clear();
            Timber.tag(TAG).d("         ...clearing active batch");
            device.getActiveBatch().clear();
            Timber.tag(TAG).d("         ...UseCase COMPLETE");
            response.userDisconnectedComplete();
        }
    }

    public interface Response {
        void userDisconnectedComplete();
    }

}


