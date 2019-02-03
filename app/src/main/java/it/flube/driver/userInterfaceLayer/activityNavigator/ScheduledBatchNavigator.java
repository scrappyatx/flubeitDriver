/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.offers.publicOffers.PublicOffersActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch.BatchManageActivity;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.scheduledBatchList.ScheduledBatchesActivity;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_RESULT_KEY;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.CLAIM_OFFER_SUCCESS_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.BATCH_DEINED_REASON_KEY;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.BATCH_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_DENIED_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_FAILURE_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_RESULT_KEY;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_SUCCESS_VALUE;
import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.FORFEIT_BATCH_TIMEOUT_VALUE;


/**
 * Created on 3/23/2018
 * Project : Driver
 */

public class ScheduledBatchNavigator {
    private static final String TAG = "ScheduledBatchNavigator";

    ///
    ///  SCHEDULED BATCHES
    ///
    public static void gotoActivityScheduledBatches(Context context) {
        context.startActivity(new Intent(context, ScheduledBatchesActivity.class));
        Timber.tag(TAG).d("starting activity ScheduledBatchesActivity.class");
    }

    public static void gotoActivityScheduledBatchesAndShowBatchForfeitSuccessAlert(Context context){
        Intent i = new Intent(context, ScheduledBatchesActivity.class);
        i.putExtra(FORFEIT_BATCH_RESULT_KEY, FORFEIT_BATCH_SUCCESS_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity ScheduledBatchesActivity.class AND show batch forfeit success");
    }

    public static void gotoActivityScheduledBatchesAndShowBatchForfeitFailureAlert(Context context){
        Intent i = new Intent(context, ScheduledBatchesActivity.class);
        i.putExtra(FORFEIT_BATCH_RESULT_KEY, FORFEIT_BATCH_FAILURE_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity ScheduledBatchesActivity.class AND show batch forfeit failure");
    }

    public static void gotoActivityScheduledBatchesAndShowBatchForfeitTimeoutAlert(Context context){
        Intent i = new Intent(context, ScheduledBatchesActivity.class);
        i.putExtra(FORFEIT_BATCH_RESULT_KEY, FORFEIT_BATCH_TIMEOUT_VALUE);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity ScheduledBatchesActivity.class AND show batch forfeit timeout");
    }

    public static void gotoActivityScheduledBatchesAndShowBatchForfeitDeniedAlert(Context context, String reason){
        Intent i = new Intent(context, ScheduledBatchesActivity.class);
        i.putExtra(FORFEIT_BATCH_RESULT_KEY, FORFEIT_BATCH_DENIED_VALUE);
        i.putExtra(BATCH_DEINED_REASON_KEY, reason);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity ScheduledBatchesActivity.class AND show batch forfeit denied");
    }

    ///
    ///     BATCH MANAGE
    ///
    public static void gotoActivityBatchManage(Context context, String batchGuid) {
        Intent i = new Intent(context, BatchManageActivity.class);
        i.putExtra(BATCH_GUID_KEY, batchGuid);
        context.startActivity(i);
        Timber.tag(TAG).d("starting activity BatchManageActivity.class");
        Timber.tag(TAG).d("    ...batchGuid = " + batchGuid);
    }



}
