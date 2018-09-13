/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.CloudServerMonitoringInterface;
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
        CloudImageStorageInterface.StartOrResumeResponse,
        LocationTelemetryInterface.LocationTrackingStartResponse,
        ActiveBatchForegroundServiceInterface.StartActiveBatchForegroundServiceResponse,
        ActiveBatchForegroundServiceInterface.UpdateActiveBatchForegroundServiceResponse,
        CloudServerMonitoringInterface.BatchStartedResponse,
        CloudServerMonitoringInterface.OrderStartedResponse,
        CloudServerMonitoringInterface.StepStartedResponse,
        CloudServerMonitoringInterface.LocationUpdateResponse {

    private final static String TAG = "UseCaseStepStartedRequest";

    private final MobileDeviceInterface device;
    private final CloudActiveBatchInterface cloudActiveBatch;
    private final Driver driver;
    private final DeviceInfo deviceInfo;
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
        this.driver = device.getCloudAuth().getDriver();
        this.deviceInfo = device.getDeviceInfo();
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

        // start location tracking
        Timber.tag(TAG).d("   start location tracking...");
        device.getLocationTelemetry().locationTrackingStartRequest(this);

        // start or resume any file upload tasks
        device.getCloudImageStorage().startOrResumeUploadingImagesForActiveBatch(driver, deviceInfo, batchDetail.getBatchGuid(), this);

        String notificationTitle;
        String notificationSubTitle;

        switch (actionType){
            case BATCH_STARTED:
                notificationTitle = step.getTitle() + " (step " + step.getSequence().toString() + " of " + serviceOrder.getTotalSteps().toString() + ")" ;
                notificationSubTitle = "Batch just started";
                Timber.tag(TAG).d("   batch just started, starting foreground service...");
                device.getActiveBatchForegroundServiceController().startActiveBatchForegroundServiceRequest(driver.getClientId(), batchDetail.getBatchGuid(), serviceOrder.getGuid(), step.getGuid(),
                        notificationTitle, notificationSubTitle, step.getTaskType(), this);
                device.getCloudServerMonitoring().batchStartedRequest(driver, batchDetail, serviceOrder, step, this);
                break;

            case ORDER_STARTED:
                notificationTitle = step.getTitle() + " (step " + step.getSequence().toString() + " of " + serviceOrder.getTotalSteps().toString() + ")" ;
                notificationSubTitle = "New order just started";
                Timber.tag(TAG).d("   new order just started, updating foreground service...");
                device.getActiveBatchForegroundServiceController().updateActiveBatchForegroundServiceRequest(driver.getClientId(), batchDetail.getBatchGuid(), serviceOrder.getGuid(), step.getGuid(),
                        notificationTitle, notificationSubTitle, step.getTaskType(),this);
                device.getCloudServerMonitoring().orderStartedRequest(driver, serviceOrder, step, this);
                break;

            case STEP_STARTED:
                notificationTitle = step.getTitle() + " (step " + step.getSequence().toString() + " of " + serviceOrder.getTotalSteps().toString() + ")" ;
                notificationSubTitle = "Batch in progress";
                Timber.tag(TAG).d("   batch in progress, updating foreground service...");
                device.getActiveBatchForegroundServiceController().updateActiveBatchForegroundServiceRequest(driver.getClientId(), batchDetail.getBatchGuid(), serviceOrder.getGuid(), step.getGuid(),
                        notificationTitle, notificationSubTitle, step.getTaskType(), this);
                device.getCloudServerMonitoring().stepStartedRequest(driver, step, this);
                break;
        }


        // update the server node info for the current step
        if (device.getLocationTelemetry().hasLastGoodPosition()){
            Timber.tag(TAG).d("   updating cloud database active batch node with position data...");
            LatLonLocation driverPosition = device.getLocationTelemetry().getLastGoodPosition();
            device.getCloudServerMonitoring().locationUpdateRequest(batchDetail.getBatchGuid(), driverPosition, this);
        } else {
            Timber.tag(TAG).d("   no driver position data...");
        }

        //TODO remove this legacy stuff - we want to deprecate use of the "activebatch" global, and do direct reads from firebase instead
        /// set the active batch data with the current info
        Timber.tag(TAG).d("   set the active batch with current batchDetal, serviceOrder, and step info");
        device.getActiveBatch().setActiveBatch(batchDetail, serviceOrder, step);

        // repopulate the service order, route list & step list
        Timber.tag(TAG).d("   repopulate the service order, route list & step list");
        device.getCloudActiveBatch().getActiveBatchServiceOrderListRequest(driver, batchGuid, this);
        device.getCloudActiveBatch().getActiveBatchRouteStopListRequest(driver, batchGuid,this);
        device.getCloudActiveBatch().getActiveBatchOrderStepListRequest(driver, batchGuid, serviceOrder.getGuid(), this);




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

    public void cloudImageStorageStartOrResumeComplete(){
        Timber.tag(TAG).d("   ...cloudImageStorageStartOrResumeComplete");
    }

    public void activeBatchForegroundServiceStarted(){
        Timber.tag(TAG).d("   ...activeBatchForegroundServiceStarted");
    }

    public void activeBatchForegroundServiceUpdated(){
        Timber.tag(TAG).d("   ...activeBatchForegroundServiceUpdated");
    }

    public void cloudServerMonitoringBatchStartedComplete(String batchGuid){
        Timber.tag(TAG).d("   ...cloudServerMonitoringBatchStartedComplete");
    }

    public void cloudServerMonitoringOrderStartedComplete(String batchGuid){
        Timber.tag(TAG).d("   ...cloudServerMonitoringOrderStartedComplete");
    }

    public void cloudServerMonitoringStepStartedComplete(String batchGuid){
        Timber.tag(TAG).d("   ...cloudServerMonitoringStepStartedComplete");
    }

    public void cloudServerMonitoringLocationUpdateComplete(String batchGuid){
        Timber.tag(TAG).d("   ...cloudServerMonitoringLocationUpdateComplete");
    }

    public interface Response {
        void startCurrentStepComplete();
    }
}
