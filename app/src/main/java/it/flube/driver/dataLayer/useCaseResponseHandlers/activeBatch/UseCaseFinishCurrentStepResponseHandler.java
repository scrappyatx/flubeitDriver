/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.userInterfaceEvents.activeBatch.ActiveBatchCompletedBatchEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.activeBatch.ActiveBatchCompletedServiceOrderEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.activeBatch.ActiveBatchCompletedStepEvent;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishCurrentStepRequest;
import timber.log.Timber;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class UseCaseFinishCurrentStepResponseHandler implements
        UseCaseFinishCurrentStepRequest.Response {

    private static final String TAG = "UseCaseFinishCurrentStepResponseHandler";

    public void finishCurrentStepWithStepComplete(){
        Timber.tag(TAG).d("finished a step!");
        EventBus.getDefault().postSticky(new ActiveBatchCompletedStepEvent());
    }

    public void finishCurrentStepWithOrderComplete(){
        Timber.tag(TAG).d("finished an order!");
        EventBus.getDefault().postSticky(new ActiveBatchCompletedServiceOrderEvent());
    }

    public void finishCurrentStepWithBatchComplete(){
        Timber.tag(TAG).d("finished a batch!");
        EventBus.getDefault().postSticky(new ActiveBatchCompletedBatchEvent());
    }

}
