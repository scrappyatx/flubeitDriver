/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.scheduledBatchList;

import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch.BatchManageAlerts;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.BATCH_DEINED_REASON_KEY;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_DENIED_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_FAILURE_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_RESULT_KEY;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_SUCCESS_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_TIMEOUT_VALUE;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class ScheduledBatchesController implements
        BatchManageAlerts.ForfeitBatchAlertHidden {

    private final String TAG = "SchedBatchesController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public ScheduledBatchesController() {
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
        Timber.tag(TAG).d("controller CREATED");
    }

    public void checkIfForfeitAlertNeedsToBeShown(AppCompatActivity activity){
        /// get the batch data for the batchGuid that was used to launch this activity
        if (activity.getIntent().hasExtra(FORFEIT_BATCH_RESULT_KEY)){
            //get the result
            String resultKey = activity.getIntent().getStringExtra(FORFEIT_BATCH_RESULT_KEY);
            Timber.tag(TAG).d(FORFEIT_BATCH_RESULT_KEY + " --> " + resultKey);
            switch (resultKey){
                case FORFEIT_BATCH_SUCCESS_VALUE:
                    Timber.tag(TAG).d("forfeit batch SUCCESS!");
                    new BatchManageAlerts().showForfeitBatchSuccessAlert(activity, this);
                    break;
                case FORFEIT_BATCH_FAILURE_VALUE:
                    Timber.tag(TAG).d("claim offer FAILURE!");
                    new BatchManageAlerts().showForfeitBatchAlertFailure(activity, this);
                    break;
                case FORFEIT_BATCH_TIMEOUT_VALUE:
                    Timber.tag(TAG).d("claim offer TIMEOUT");
                    new BatchManageAlerts().showForfeitBatchAlertTimeout(activity, this);
                    break;
                case FORFEIT_BATCH_DENIED_VALUE:
                    Timber.tag(TAG).d("claim offer DENIED");
                    if (activity.getIntent().hasExtra(BATCH_DEINED_REASON_KEY)){
                        Timber.tag(TAG).d("...reason -> " + activity.getIntent().getStringExtra(BATCH_DEINED_REASON_KEY));
                        new BatchManageAlerts().showForfeitBatchAlertDeniedWithReason(activity, activity.getIntent().getStringExtra(BATCH_DEINED_REASON_KEY),this);
                        activity.getIntent().removeExtra(BATCH_DEINED_REASON_KEY);
                    } else {
                        Timber.tag(TAG).d("...no reason provided");
                        new BatchManageAlerts().showForfeitBatchAlertDeniedWithoutReason(activity, this);
                    }
                    break;
                default:
                    Timber.tag(TAG).w("unknown resultKey, this should never happen");
                    break;
            }
            // now remove the key, only want to show an alert once
            activity.getIntent().removeExtra(FORFEIT_BATCH_RESULT_KEY);
        } else {
            Timber.tag(TAG).d("activity started with no claim_offer_result");
        }
    }

    public void close() {
        useCaseExecutor = null;
        device = null;
        Timber.tag(TAG).d("controller CLOSED");
    }

    //// interface for batch forfeit alert
    public void forfeitBatchAlertHidden() {
        Timber.tag(TAG).d("forfeit batch alert hidden");
    }


}
