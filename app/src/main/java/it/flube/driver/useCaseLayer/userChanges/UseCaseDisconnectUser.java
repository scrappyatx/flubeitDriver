/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.userChanges;

import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
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
        CloudDatabaseInterface.DisconnectResponse,
        RealtimeMessagingInterface.Connection.DisconnectResponse,
        LocationTelemetryInterface.LocationTrackingStopResponse {

    private final static String TAG = "UseCaseDisconnectUser";

    private final static Integer responseCount = 4;
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

        /// 2. disconnect from cloud database
        Timber.tag(TAG).d("   2 --> disconnect from cloud database...");
        device.getCloudDatabase().disconnectDriverRequest(this);

        /// 3. disconnect from realtime messaging server
        Timber.tag(TAG).d("   3 --> disconnect from realtime messaging server...");
        device.getRealtimeConnection().messageServerDisconnectRequest(this);

        /// 4. stop location tracking
        Timber.tag(TAG).d("   4 --> stop location tracking...");
        device.getLocationTelemetry().locationTrackingStopRequest(this);

    }

    public void activeBatchForegroundServiceStopped(){
        Timber.tag(TAG).d("      ...active batch foreground service stopped");
        checkIfFinished();
    }

    public void cloudDatabaseDisconnectDriverComplete(){
        Timber.tag(TAG).d("      ...cloud database disconnect driver complete");
        checkIfFinished();
    }

    public void messageServerDisconnectComplete() {
        Timber.tag(TAG).d("      ...realtime messaging server disconnect complete");
        checkIfFinished();
    }

    public void locationTrackingStopComplete(){
        Timber.tag(TAG).d("      ...location tracking stopped");
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


