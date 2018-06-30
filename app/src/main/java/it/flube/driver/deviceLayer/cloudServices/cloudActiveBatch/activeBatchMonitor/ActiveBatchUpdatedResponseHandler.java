/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedBatchFinishedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedBatchRemovedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedBatchWaitingToFinishEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedStepStartedEvent;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseBatchFinishedRequest;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseBatchRemovedRequest;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseStepStartedRequest;
import timber.log.Timber;

/**
 * Created on 10/12/2017
 * Project : Driver
 */

public class ActiveBatchUpdatedResponseHandler implements
        CloudActiveBatchInterface.ActiveBatchUpdated,
        UseCaseStepStartedRequest.Response,
        UseCaseBatchFinishedRequest.Response,
        UseCaseBatchRemovedRequest.Response {

    private static final String TAG = "ActiveBatchUpdatedResponseHandler";

    ///
    ///     This response handler gets called whenever the cloud database sees a change on the ActiveBatch nodes
    ///
    ///     These changes could be the result of the mobile device user completing a step and moving on to the next,
    ///     or the back-end server could make a change to the active batch
    ///
    ///
    ///

    private MobileDeviceInterface device;

    private ActiveBatchManageInterface.ActorType actorType;
    private ActiveBatchManageInterface.ActionType actionType;
    private Boolean batchStarted;
    private Boolean orderStarted;
    private String batchGuid;
    private String serviceOrderGuid;
    private String stepGuid;
    private OrderStepInterface.TaskType taskType;

    ///
    ///  STEP STARTED
    ///
    public void stepStarted(ActiveBatchManageInterface.ActorType actorType, ActiveBatchManageInterface.ActionType actionType,
                            Boolean batchStarted, Boolean orderStarted, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step){

        Timber.tag(TAG).d("received batchStarted");
        Timber.tag(TAG).d("    actorType        --> " + actorType.toString());
        Timber.tag(TAG).d("    actionType       --> " + actionType.toString());
        Timber.tag(TAG).d("    batchStarted     --> " + batchStarted);
        Timber.tag(TAG).d("    orderStarted     --> " + orderStarted);
        Timber.tag(TAG).d("    batchGuid        --> " + batchDetail.getBatchGuid());
        Timber.tag(TAG).d("    serviceOrderGuid --> " + serviceOrder.getGuid());
        Timber.tag(TAG).d("    stepGuid         --> " + step.getGuid());
        Timber.tag(TAG).d("    taskType         --> " + step.getTaskType());

        this.actorType = actorType;
        this.actionType = actionType;
        this.batchStarted = batchStarted;
        this.orderStarted = orderStarted;
        this.batchGuid = batchDetail.getBatchGuid();
        this.serviceOrderGuid = serviceOrder.getGuid();
        this.stepGuid = step.getGuid();
        this.taskType = step.getTaskType();


        device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseStepStartedRequest(device, actorType, actionType,
                batchDetail, serviceOrder, step, this));
    }

    public void startCurrentStepComplete(){
        EventBus.getDefault().postSticky(new ActiveBatchUpdatedStepStartedEvent(actorType, actionType, batchStarted, orderStarted, batchGuid, serviceOrderGuid, stepGuid, taskType));
    }


    ///
    /// BATCH WAITING TO FINISH
    ///
    public void batchWaitingToFinish(ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).d("received batchWaitingToFinish");
        Timber.tag(TAG).d("    actorType        --> " + actorType.toString());

        EventBus.getDefault().postSticky(new ActiveBatchUpdatedBatchWaitingToFinishEvent(actorType, batchGuid));
    }


    ///
    /// BATCH FINISHED
    ///
    public void batchFinished(ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).d("received batchFinished");
        Timber.tag(TAG).d("    actorType        --> " + actorType.toString());

        device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseBatchFinishedRequest(device, batchGuid, this));
    }

    public void batchFinishedComplete(){
        EventBus.getDefault().postSticky(new ActiveBatchUpdatedBatchFinishedEvent(actorType, batchGuid));
    }

    ///
    ///  BATCH REMOVED
    ///

    public void batchRemoved(ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).d("received batchRemoved");
        Timber.tag(TAG).d("    actorType        --> " + actorType.toString());

        this.actorType = actorType;
        this.batchGuid = batchGuid;

        device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseBatchRemovedRequest(device, batchGuid, this));
    }

    public void batchRemovedComplete(){
        EventBus.getDefault().postSticky(new ActiveBatchUpdatedBatchRemovedEvent(actorType, batchGuid));
    }


    ///
    /// NO BATCH
    ///
    public void noBatch(){
        Timber.tag(TAG).d("received noBatch");
    }






}
