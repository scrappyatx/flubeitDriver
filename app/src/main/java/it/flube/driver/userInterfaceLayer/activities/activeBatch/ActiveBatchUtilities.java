/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch;

import android.support.v7.app.AppCompatActivity;

import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetActiveBatchPhotoRequest;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetOrderStep;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_ORDER_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_STEP_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_TASK_TYPE_KEY;


/**
 * Created on 4/27/2018
 * Project : Driver
 */
public class ActiveBatchUtilities implements
    UseCaseGetOrderStep.Response {

    private static final String TAG = "ActiveBatchUtilities";

    private GetStepResponse response;

    public void getOrderStepRequest(AppCompatActivity activity, MobileDeviceInterface device, GetStepResponse response) {
        Timber.tag(TAG).d("getOrderStepRequest START...");
        this.response = response;

        if (activity.getIntent().hasExtra(ACTIVE_BATCH_GUID_KEY)) {
            if (activity.getIntent().hasExtra(ACTIVE_BATCH_ORDER_GUID_KEY)) {
                if (activity.getIntent().hasExtra(ACTIVE_BATCH_STEP_GUID_KEY)) {
                    if (activity.getIntent().hasExtra(ACTIVE_BATCH_TASK_TYPE_KEY)) {
                        //get active batch data
                        String batchGuid = activity.getIntent().getStringExtra(ACTIVE_BATCH_GUID_KEY);
                        String orderGuid = activity.getIntent().getStringExtra(ACTIVE_BATCH_ORDER_GUID_KEY);
                        String stepGuid = activity.getIntent().getStringExtra(ACTIVE_BATCH_STEP_GUID_KEY);
                        OrderStepInterface.TaskType taskType = OrderStepInterface.TaskType.valueOf(activity.getIntent().getStringExtra(ACTIVE_BATCH_TASK_TYPE_KEY));

                        Timber.tag(TAG).d("   ...batchGuid -> " + batchGuid);
                        Timber.tag(TAG).d("   ...orderGuid -> " + orderGuid);
                        Timber.tag(TAG).d("   ...stepGuid  -> " + stepGuid);
                        Timber.tag(TAG).d("   ...taskType  -> " + taskType.toString());

                        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetOrderStep(device, batchGuid, stepGuid, this));

                    } else {
                        Timber.tag(TAG).d("   ...activeBatchStepTypeKey NOT FOUND");
                        response.getStepFailure();
                    }
                } else {
                    Timber.tag(TAG).d("   ...activeBatchStepGuidKey NOT FOUND");
                    response.getStepFailure();
                }
            } else {
                Timber.tag(TAG).d("   ...activeBatchOrderGuidKey NOT FOUND");
                response.getStepFailure();
            }
        } else {
            Timber.tag(TAG).d("   ...activeBatchGuidKey NOT FOUND");
            response.getStepFailure();
        }
    }

    public void getOrderStepSuccess(OrderStepInterface orderStep){
        Timber.tag(TAG).d("getOrderStepSuccess");
        response.getStepSuccess(orderStep);
    }

    public void getOrderStepFailure(){
        Timber.tag(TAG).d("getOrderStepFailure");
        response.getStepFailure();
    }

    public interface GetStepResponse {
        void getStepSuccess(OrderStepInterface orderStep);

        void getStepFailure();
    }
}
