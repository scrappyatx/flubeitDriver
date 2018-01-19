/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.dataLayer.useCaseResponseHandlers.deviceLocation.LocationTrackingPositionChangedHandler;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class UseCaseStepStartedRequest implements
        Runnable,
        LocationTelemetryInterface.LocationTrackingStartResponse,
        RealtimeMessagingInterface.ActiveBatchChannel.ActiveBatchChannelConnectResponse,
        ActiveBatchForegroundServiceInterface.StartActiveBatchForegroundServiceResponse,
        ActiveBatchForegroundServiceInterface.UpdateActiveBatchForegroundServiceResponse {

    private final static String TAG = "UseCaseStepStartedRequest";

    private MobileDeviceInterface device;

    private CloudDatabaseInterface.ActorType actorType;
    private CloudDatabaseInterface.ActionType actionType;

    private BatchDetail batchDetail;
    private ServiceOrder serviceOrder;
    private OrderStepInterface step;

    private Response response;

    public UseCaseStepStartedRequest(MobileDeviceInterface device,
                                     CloudDatabaseInterface.ActorType actorType, CloudDatabaseInterface.ActionType actionType,
                                     BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step,
                                     Response response){
        this.device = device;

        this.actorType = actorType;
        this.actionType = actionType;
        this.batchDetail = batchDetail;
        this.serviceOrder = serviceOrder;
        this.step = step;

        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        /// set the active batch data with the current info
        Timber.tag(TAG).d("   set the active batch with current batchDetal, serviceOrder, and step info");
        device.getActiveBatch().setActiveBatch(batchDetail, serviceOrder, step);

        // repopulate the service order, route list & step list
        Timber.tag(TAG).d("   repopulate the service order, route list & step list");
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetServiceOrderList(device, batchDetail.getBatchGuid()));
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetRouteStopList(device, batchDetail.getBatchGuid()));
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetOrderStepList(device, batchDetail.getBatchGuid(), serviceOrder.getGuid()));


        Timber.tag(TAG).d("   start location tracking...");
        device.getLocationTelemetry().locationTrackingStartRequest(this);

        Timber.tag(TAG).d("   connect to realtime messaging server...");
        device.getRealtimeActiveBatchMessages().connectRequest(batchDetail.getBatchGuid(), this);

        String notificationTitle;
        String notificationSubTitle;

        switch (actionType){
            case BATCH_STARTED:
                notificationTitle = step.getTitle() + " (step " + step.getSequence().toString() + " of " + serviceOrder.getTotalSteps().toString() + ")" ;
                notificationSubTitle = "Batch just started";
                Timber.tag(TAG).d("   batch just started, starting foreground service...");
                device.getActiveBatchForegroundServiceController().startActiveBatchForegroundServiceRequest(notificationTitle, notificationSubTitle, step.getTaskType(), this);

                break;

            case ORDER_STARTED:
                notificationTitle = step.getTitle() + " (step " + step.getSequence().toString() + " of " + serviceOrder.getTotalSteps().toString() + ")" ;
                notificationSubTitle = "New order just started";
                Timber.tag(TAG).d("   new order just started, updating foreground service...");
                device.getActiveBatchForegroundServiceController().updateActiveBatchForegroundServiceRequest(notificationTitle, notificationSubTitle, step.getTaskType(),this);
                break;

            case STEP_STARTED:
                notificationTitle = step.getTitle() + " (step " + step.getSequence().toString() + " of " + serviceOrder.getTotalSteps().toString() + ")" ;
                notificationSubTitle = "Batch in progress";
                Timber.tag(TAG).d("   batch in progress, updating foreground service...");
                device.getActiveBatchForegroundServiceController().updateActiveBatchForegroundServiceRequest(notificationTitle, notificationSubTitle, step.getTaskType(), this);
                break;
        }


        // update the server node info for the current step
        if (device.getLocationTelemetry().hasLastGoodPosition()){
            Timber.tag(TAG).d("   updating cloud database active batch node with position data...");
            LatLonLocation driverPosition = device.getLocationTelemetry().getLastGoodPosition();
            device.getCloudDatabase().updateActiveBatchServerNodeStatus(batchDetail, serviceOrder, step, driverPosition);
        } else {
            Timber.tag(TAG).d("   updating cloud database active batch node WITHOUT position data...");
            device.getCloudDatabase().updateActiveBatchServerNodeStatus(batchDetail, serviceOrder, step);
        }

        Timber.tag(TAG).d("   UseCase COMPLETE");
        response.startCurrentStepComplete();
    }

    public void activeBatchChannelConnectComplete(){
        Timber.tag(TAG).d("   ...realtime messaging server connecting, sending current step title");
        device.getRealtimeActiveBatchMessages().sendCurrentlyDoing(step.getTitle());
    }

    public void locationTrackingStartSuccess(){
        Timber.tag(TAG).d("   ...location tracking started SUCCESS");
    }

    public void locationTrackingStartFailure(){
        Timber.tag(TAG).d("   ...location tracking started FAILURE");
    }

    public void activeBatchForegroundServiceStarted(){
        Timber.tag(TAG).d("   ...active batch foreground service started");
    }

    public void activeBatchForegroundServiceUpdated(){
        Timber.tag(TAG).d("   ...active batch foreground service updated");
    }

    public interface Response {
        void startCurrentStepComplete();
    }
}
