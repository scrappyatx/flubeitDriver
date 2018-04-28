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
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.ReceiveAssetActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.userTriggerStep.UserTriggerActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeActivity;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_GUID_KEY;
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

    public void gotoActiveBatchStep(Context context){

        this.context = context;

        MobileDeviceInterface device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetActiveBatchCurrentStep(device, this));
    }

    /// use case response interface
    public void useCaseGetActiveBatchCurrentStepSuccess(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep){
        Timber.tag(TAG).d("useCaseGetActiveBatchCurrentStepSuccess...");
        Timber.tag(TAG).d("   taskType -> " + orderStep.getTaskType());
        Intent i;
        switch (orderStep.getTaskType()){
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
}
