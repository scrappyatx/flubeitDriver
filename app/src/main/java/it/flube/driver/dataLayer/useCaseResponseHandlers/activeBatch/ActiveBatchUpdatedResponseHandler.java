/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedEvent;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
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
        CloudDatabaseInterface.ActiveBatchUpdated,
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

    /// there is a step

    public void stepStarted(CloudDatabaseInterface.ActorType actorType, CloudDatabaseInterface.ActionType actionType,
                            BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step){

        Timber.tag(TAG).d("received batchStarted");
        Timber.tag(TAG).d("    actorType        --> " + actorType.toString());
        Timber.tag(TAG).d("    actionType       --> " + actionType.toString());
        Timber.tag(TAG).d("    batchGuid        --> " + batchDetail.getBatchGuid());
        Timber.tag(TAG).d("    serviceOrderGuid --> " + serviceOrder.getGuid());
        Timber.tag(TAG).d("    stepGuid         --> " + step.getGuid());


        device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseStepStartedRequest(device, actorType, actionType,
                batchDetail, serviceOrder, step, this));
    }

    /// there isn't a step
    public void batchFinished(CloudDatabaseInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).d("received batchFinished");
        Timber.tag(TAG).d("    actorType        --> " + actorType.toString());

        device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseBatchFinishedRequest(device, batchGuid, this));
    }

    public void batchRemoved(CloudDatabaseInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).d("received batchRemoved");
        Timber.tag(TAG).d("    actorType        --> " + actorType.toString());

        device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseBatchRemovedRequest(device, batchGuid, this));
    }

    public void noBatch(){
        Timber.tag(TAG).d("received noBatch");
    }

    public void batchRemovedComplete(){
        EventBus.getDefault().post(new ActiveBatchUpdatedEvent());
    }

    public void batchFinishedComplete(){
        EventBus.getDefault().post(new ActiveBatchUpdatedEvent());
    }

    public void startCurrentStepComplete(){
        EventBus.getDefault().post(new ActiveBatchUpdatedEvent());
    }

}
