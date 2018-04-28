/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch;

import android.support.v7.app.AppCompatActivity;

import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
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
public class ActiveBatchUtilities {
    private static final String TAG = "ActiveBatchUtilities";

    public static void getActivityLaunchDataRequest(AppCompatActivity activity, Response response) {
        Timber.tag(TAG).d("getActivityLaunchDataRequest START...");

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

                        response.activityLaunchDataFound(batchGuid, orderGuid, stepGuid, taskType);

                    } else {
                        Timber.tag(TAG).d("   ...activeBatchStepTypeKey NOT FOUND");
                        response.activityLaunchDataNotFound();
                    }
                } else {
                    Timber.tag(TAG).d("   ...activeBatchStepGuidKey NOT FOUND");
                    response.activityLaunchDataNotFound();
                }
            } else {
                Timber.tag(TAG).d("   ...activeBatchOrderGuidKey NOT FOUND");
                response.activityLaunchDataNotFound();
            }
        } else {
            Timber.tag(TAG).d("   ...activeBatchGuidKey NOT FOUND");
            response.activityLaunchDataNotFound();
        }
    }

    public interface Response {
        void activityLaunchDataFound(String batchGuid, String serviceOrderGuid, String orderStepGuid, OrderStepInterface.TaskType taskType);

        void activityLaunchDataNotFound();
    }
}
