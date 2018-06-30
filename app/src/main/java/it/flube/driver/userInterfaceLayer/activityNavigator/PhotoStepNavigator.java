/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderAbstractStep;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoDetail.PhotoDetailActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoList.PhotoActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoTake.PhotoTakeActivity;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_ORDER_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_STEP_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ACTIVE_BATCH_TASK_TYPE_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoStepConstants.BATCH_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoStepConstants.ORDER_STEP_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoStepConstants.PHOTO_REQUEST_GUID_KEY;

/**
 * Created on 2/24/2018
 * Project : Driver
 */

public class PhotoStepNavigator {
    private static final String TAG = "PhotoStepNavigator";

    public static void gotoActivityPhotoList(Context context, String batchGuid, String serviceOrderGuid, String orderStepGuid, OrderStepInterface.TaskType taskType){
        Intent i = new Intent(context, PhotoActivity.class);
        i.putExtra(ACTIVE_BATCH_GUID_KEY, batchGuid);
        i.putExtra(ACTIVE_BATCH_ORDER_GUID_KEY, serviceOrderGuid);
        i.putExtra(ACTIVE_BATCH_STEP_GUID_KEY, orderStepGuid);
        i.putExtra(ACTIVE_BATCH_TASK_TYPE_KEY, taskType.toString());
        context.startActivity(i);
        Timber.tag(TAG).d("starting PhotoActivity");
    }

    public static void gotoActivityPhotoDetail(Context context, String batchGuid, String orderStepGuid, String photoRequestGuid){
        Intent i = new Intent(context, PhotoDetailActivity.class);
        i.putExtra(BATCH_GUID_KEY, batchGuid);
        i.putExtra(ORDER_STEP_GUID_KEY, orderStepGuid);
        i.putExtra(PHOTO_REQUEST_GUID_KEY, photoRequestGuid);
        context.startActivity(i);
        Timber.tag(TAG).d("starting PhotoDetailActivity, photoRequestGuid -> " + photoRequestGuid);
    }

    public static void gotoActivityPhotoTake(Context context, String batchGuid, String orderStepGuid, String photoRequestGuid){
        Intent i = new Intent(context, PhotoTakeActivity.class);
        i.putExtra(BATCH_GUID_KEY, batchGuid);
        i.putExtra(ORDER_STEP_GUID_KEY, orderStepGuid);
        i.putExtra(PHOTO_REQUEST_GUID_KEY, photoRequestGuid);
        context.startActivity(i);
        Timber.tag(TAG).d("starting PhotoTakeActivity, photoRequestGuid -> " + photoRequestGuid);
    }


}
