/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.ActiveBatchInterface;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.NavigationActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeActivity;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 3/23/2018
 * Project : Driver
 */

public class ActiveBatchNavigator {
    private static final String TAG = "ScheduledBatchNavigator";

    public static void gotoActiveBatchStep(Context context){

        ActiveBatchInterface activeBatch = AndroidDevice.getInstance().getActiveBatch();

        if (activeBatch.hasActiveBatch()){
            OrderStepInterface.TaskType taskType = activeBatch.getTaskType();

            Timber.tag(TAG).d("gotoActiveBatchStep...");
            switch (taskType){
                case NAVIGATION:
                    Timber.tag(TAG).d("  ...starting NavigationActivity.class");
                    context.startActivity(new Intent(context, NavigationActivity.class));
                    break;
                case TAKE_PHOTOS:
                    Timber.tag(TAG).d("  ...starting PhotoActivity.class");
                    context.startActivity(new Intent(context, PhotoActivity.class));
                    break;
            }

        } else {
            Timber.tag(TAG).d("can't go to ActiveBatchStep, no active batch");
            context.startActivity(new Intent(context, HomeActivity.class));
        }

    }



}
