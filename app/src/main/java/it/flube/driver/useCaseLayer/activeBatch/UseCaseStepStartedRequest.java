/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import timber.log.Timber;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class UseCaseStepStartedRequest implements
        Runnable,
        CloudActiveBatchInterface.GetServiceOrderListResponse,
        CloudActiveBatchInterface.GetRouteStopListResponse,
        CloudActiveBatchInterface.GetOrderStepListResponse,
        LocationTelemetryInterface.LocationTrackingStartResponse,
        ActiveBatchForegroundServiceInterface.StartActiveBatchForegroundServiceResponse,
        ActiveBatchForegroundServiceInterface.UpdateActiveBatchForegroundServiceResponse {

    private final static String TAG = "UseCaseStepStartedRequest";

    private final MobileDeviceInterface device;
    private final CloudActiveBatchInterface cloudActiveBatch;
    private final Driver driver;
    private final ActiveBatchManageInterface.ActorType actorType;
    private final ActiveBatchManageInterface.ActionType actionType;

    private String batchGuid;
    private BatchDetail batchDetail;
    private ServiceOrder serviceOrder;
    private OrderStepInterface step;

    private Response response;

    public UseCaseStepStartedRequest(MobileDeviceInterface device,
                                     ActiveBatchManageInterface.ActorType actorType, ActiveBatchManageInterface.ActionType actionType,
                                     BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step,
                                     Response response){
        this.device = device;
        this.driver = device.getUser().getDriver();
        this.cloudActiveBatch = device.getCloudActiveBatch();
        this.actorType = actorType;
        this.actionType = actionType;
        this.batchGuid = batchDetail.getBatchGuid();
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
        device.getCloudActiveBatch().getActiveBatchServiceOrderListRequest(driver, batchGuid, this);
        device.getCloudActiveBatch().getActiveBatchRouteStopListRequest(driver, batchGuid,this);
        device.getCloudActiveBatch().getActiveBatchOrderStepListRequest(driver, batchGuid, serviceOrder.getGuid(), this);

        Timber.tag(TAG).d("   start location tracking...");
        device.getLocationTelemetry().locationTrackingStartRequest(this);

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
            device.getCloudActiveBatch().updateActiveBatchServerNodeStatus(driver, batchDetail, serviceOrder, step, driverPosition);
        } else {
            Timber.tag(TAG).d("   updating cloud database active batch node WITHOUT position data...");
            device.getCloudActiveBatch().updateActiveBatchServerNodeStatus(driver, batchDetail, serviceOrder, step);
        }

        Timber.tag(TAG).d("   UseCase COMPLETE");
        response.startCurrentStepComplete();
    }

    public void cloudGetActiveBatchRouteStopListSuccess(ArrayList<RouteStop> stopList){
        Timber.tag(TAG).d("   ...cloudGetActiveBatchRouteStopListSuccess");
        device.getActiveBatch().setRouteStopList(stopList);
    }

    public void cloudGetActiveBatchRouteStopListFailure(){
        Timber.tag(TAG).d("   ...cloudGetActiveBatchRouteStopListFailure");
        device.getActiveBatch().setRouteStopList();
    }

    public void cloudGetActiveBatchServiceOrderListSuccess(ArrayList<ServiceOrder> orderList){
        Timber.tag(TAG).d("   ...cloudGetActiveBatchServiceOrderListSuccess");
        device.getActiveBatch().setServiceOrderList(orderList);
    }

    public void cloudGetActiveBatchServiceOrderListFailure(){
        Timber.tag(TAG).d("   ...cloudGetActiveBatchServiceOrderListFailure");
        device.getActiveBatch().setServiceOrderList();
    }

    public void cloudGetActiveBatchOrderStepListSuccess(ArrayList<OrderStepInterface> stepList){
        Timber.tag(TAG).d("   ...cloudGetActiveBatchOrderStepListSuccess");
        device.getActiveBatch().setOrderStepList(stepList);
    }

    public void cloudGetActiveBatchOrderStepListFailure(){
        Timber.tag(TAG).d("   ...cloudGetActiveBatchOrderStepListFailure");
        device.getActiveBatch().setOrderStepList();
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
