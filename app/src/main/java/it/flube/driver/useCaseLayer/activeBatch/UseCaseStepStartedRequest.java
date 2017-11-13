/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.services.ActiveBatchForegroundServiceController;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class UseCaseStepStartedRequest implements
        Runnable {

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
        /// set the active batch data with the current info
        device.getActiveBatch().setActiveBatch(batchDetail, serviceOrder, step);

        // repopulate the service order, route list & step list
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetServiceOrderList(device, batchDetail.getBatchGuid()));
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetRouteStopList(device, batchDetail.getBatchGuid()));
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetOrderStepList(device, batchDetail.getBatchGuid(), serviceOrder.getGuid()));

        String notificationTitle;
        String notificationSubTitle;

        switch (actionType){
            case BATCH_STARTED:
                notificationTitle = step.getTitle() + " (step " + step.getSequence().toString() + " of " + serviceOrder.getTotalSteps().toString() + ")" ;
                notificationSubTitle = "Batch just started";

                device.getRealtimeActiveBatchMessages().attach(batchDetail.getBatchGuid());
                device.getRealtimeActiveBatchMessages().sendCurrentlyDoing(step.getTitle());

                device.getActiveBatchForegroundServiceController().startActiveBatchForegroundService(notificationTitle, notificationSubTitle, step.getTaskType());

                break;

            case ORDER_STARTED:
                notificationTitle = step.getTitle() + " (step " + step.getSequence().toString() + " of " + serviceOrder.getTotalSteps().toString() + ")" ;
                notificationSubTitle = "New order just started";

                device.getRealtimeActiveBatchMessages().attach(batchDetail.getBatchGuid());
                device.getRealtimeActiveBatchMessages().sendCurrentlyDoing(step.getTitle());

                device.getActiveBatchForegroundServiceController().updateActiveBatchForegroundService(notificationTitle, notificationSubTitle, step.getTaskType());
                break;

            case STEP_STARTED:
                notificationTitle = step.getTitle() + " (step " + step.getSequence().toString() + " of " + serviceOrder.getTotalSteps().toString() + ")" ;
                notificationSubTitle = "Batch in progress";

                device.getRealtimeActiveBatchMessages().attach(batchDetail.getBatchGuid());
                device.getRealtimeActiveBatchMessages().sendCurrentlyDoing(step.getTitle());

                device.getActiveBatchForegroundServiceController().updateActiveBatchForegroundService(notificationTitle, notificationSubTitle, step.getTaskType());
                break;
        }


        // update the server node info for the current step
        if (device.getLocationTelemetry().hasLastGoodPosition()){
            LatLonLocation driverPosition = device.getLocationTelemetry().getLastGoodPosition();
            device.getCloudDatabase().updateActiveBatchServerNodeStatus(batchDetail, serviceOrder, step, driverPosition);
        } else {
            device.getCloudDatabase().updateActiveBatchServerNodeStatus(batchDetail, serviceOrder, step);
        }

        response.startCurrentStepComplete();
    }

    public interface Response {
        void startCurrentStepComplete();
    }
}
