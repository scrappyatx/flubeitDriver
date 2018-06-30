/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetActiveBatchCurrentStep;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.AuthorizePaymentActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep.GiveAssetActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.NavigationActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoList.PhotoActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.ReceiveAssetActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.userTriggerStep.UserTriggerActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeActivity;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;


import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_ACTION_TYPE_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_ACTOR_TYPE_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_NEW_BATCH_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_NEW_ORDER_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_ORDER_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_STEP_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_TASK_TYPE_KEY;

/**
 * Created on 3/23/2018
 * Project : Driver
 */

public class ActiveBatchNavigator implements
    UseCaseGetActiveBatchCurrentStep.Response {
    private static final String TAG = "ActiveBatchNavigator";

    private Context context;

    ///
    ///  This method used then the batchGuid, serviceOrderGuid, stepGuid, taskType are KNOWN
    ///
    public void gotoActiveBatchStep(Context context, ActiveBatchManageInterface.ActorType actorType, ActiveBatchManageInterface.ActionType actionType,
                                    Boolean batchStarted, Boolean orderStarted, String batchGuid, String serviceOrderGuid, String stepGuid, OrderStepInterface.TaskType taskType) {

        this.context = context;

        Timber.tag(TAG).d("gotoActiveBatchStep STARTED");
        Timber.tag(TAG).d("   actorType        -> " + actorType);
        Timber.tag(TAG).d("   actionType       -> " + actionType);
        Timber.tag(TAG).d("   batchStarted     -> " + batchStarted);
        Timber.tag(TAG).d("   orderStarted     -> " + orderStarted);
        Timber.tag(TAG).d("   batchGuid        -> " + batchGuid);
        Timber.tag(TAG).d("   serviceOrderGuid -> " + serviceOrderGuid);
        Timber.tag(TAG).d("   stepGuid         -> " + stepGuid);
        Timber.tag(TAG).d("   taskType         -> " + taskType);

        Intent i = getIntentForTaskType(taskType);
        i.putExtra(ACTIVE_BATCH_ACTOR_TYPE_KEY, actorType.toString());
        i.putExtra(ACTIVE_BATCH_ACTION_TYPE_KEY, actionType.toString());
        i.putExtra(ACTIVE_BATCH_NEW_BATCH_KEY, batchStarted);
        i.putExtra(ACTIVE_BATCH_NEW_ORDER_KEY, orderStarted);
        i.putExtra(ACTIVE_BATCH_GUID_KEY, batchGuid);
        i.putExtra(ACTIVE_BATCH_ORDER_GUID_KEY, serviceOrderGuid);
        i.putExtra(ACTIVE_BATCH_STEP_GUID_KEY, stepGuid);
        i.putExtra(ACTIVE_BATCH_TASK_TYPE_KEY, taskType.toString());

        Timber.tag(TAG).d("starting activity");
        context.startActivity(i);
    }

    ///
    /// This method used when batchGuid and all the rest are UNKNOWN
    ///

    public void gotoActiveBatchStep(Context context){

        this.context = context;

        MobileDeviceInterface device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetActiveBatchCurrentStep(device, this));
    }

    /// use case response interface
    public void useCaseGetActiveBatchCurrentStepSuccess(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep){
        Timber.tag(TAG).d("useCaseGetActiveBatchCurrentStepSuccess...");
        Timber.tag(TAG).d("   taskType -> " + orderStep.getTaskType());

        Intent i = getIntentForTaskType(orderStep.getTaskType());

        ///add additional header data manually
        i.putExtra(ACTIVE_BATCH_ACTOR_TYPE_KEY, ActiveBatchManageInterface.ActorType.MOBILE_USER.toString());
        i.putExtra(ACTIVE_BATCH_ACTION_TYPE_KEY, ActiveBatchManageInterface.ActionType.STEP_STARTED.toString());
        i.putExtra(ACTIVE_BATCH_NEW_BATCH_KEY, Boolean.FALSE);
        i.putExtra(ACTIVE_BATCH_NEW_ORDER_KEY, Boolean.FALSE);

        ///put current active batch step info into the intent
        i.putExtra(ACTIVE_BATCH_GUID_KEY, batchDetail.getBatchGuid());
        i.putExtra(ACTIVE_BATCH_ORDER_GUID_KEY, serviceOrder.getGuid());
        i.putExtra(ACTIVE_BATCH_STEP_GUID_KEY, orderStep.getGuid());
        i.putExtra(ACTIVE_BATCH_TASK_TYPE_KEY, orderStep.getTaskType().toString());
        context.startActivity(i);
        context = null;
    }

    public void useCaseGetActiveBatchCurrentStepFailure(){
        Timber.tag(TAG).d("useCaseGetActiveBatchCurrentStepFailure...");
        context.startActivity(new Intent(context, HomeActivity.class));
        context = null;
    }

    private Intent getIntentForTaskType(OrderStepInterface.TaskType taskType){
        Intent i;
        switch (taskType){
            case NAVIGATION:
                i = new Intent(context,NavigationActivity.class);
                break;
            case TAKE_PHOTOS:
                i = new Intent(context, PhotoActivity.class);
                break;
            case RECEIVE_ASSET:
                i = new Intent(context, ReceiveAssetActivity.class);
                break;
            case GIVE_ASSET:
                i = new Intent(context, GiveAssetActivity.class);
                break;
            case WAIT_FOR_USER_TRIGGER:
                i = new Intent(context, UserTriggerActivity.class);
                break;
            case AUTHORIZE_PAYMENT:
                i = new Intent(context, AuthorizePaymentActivity.class);
                break;
            case WAIT_FOR_EXTERNAL_TRIGGER:
                //TODO put in correct activity
                i = new Intent(context, HomeActivity.class);
                break;
            default:
                i = new Intent(context, HomeActivity.class);
                break;
        }

        return i;
    }
}
