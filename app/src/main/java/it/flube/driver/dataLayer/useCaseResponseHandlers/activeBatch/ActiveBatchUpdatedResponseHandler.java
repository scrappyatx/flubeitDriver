/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedEvent;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetOrderStepList;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetRouteStopList;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetServiceOrderList;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseStartCurrentStepRequest;
import timber.log.Timber;

/**
 * Created on 10/12/2017
 * Project : Driver
 */

public class ActiveBatchUpdatedResponseHandler implements
        CloudDatabaseInterface.ActiveBatchUpdated,
        UseCaseStartCurrentStepRequest.Response {

    private static final String TAG = "ActiveBatchUpdatedResponseHandler";

    ///
    ///     This response handler gets called whenever the cloud database sees a change on the ActiveBatch nodes
    ///
    ///     These changes could be the result of the mobile device user completing a step and moving on to the next,
    ///     or the back-end server could make a change to the active batch
    ///
    ///     The device should save any info that the user is working on in the current step, THEN change state to this new step
    ///
    ///

    private MobileDeviceInterface device;

    /// There is a step
    public void cloudDatabaseActiveBatchUpdated(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep) {
        Timber.tag(TAG).d("got an active batch!");

        device = AndroidDevice.getInstance();

        device.getActiveBatch().setActiveBatch(batchDetail, serviceOrder, orderStep);
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetServiceOrderList(device, batchDetail.getBatchGuid()));
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetRouteStopList(device, batchDetail.getBatchGuid()));
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetOrderStepList(device, batchDetail.getBatchGuid(), serviceOrder.getGuid()));
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseStartCurrentStepRequest(device,this));

    }

    public void startCurrentStepComplete(){
        EventBus.getDefault().post(new ActiveBatchUpdatedEvent());
    }

    // There isn't a step

    public void cloudDatabaseNoActiveBatch(){
        Timber.tag(TAG).d("no active batch!");

        AndroidDevice.getInstance().getActiveBatch().setActiveBatch();

        EventBus.getDefault().post(new ActiveBatchUpdatedEvent());
    }
}
