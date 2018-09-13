/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.receiptDetail.ReceiptDetailActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.receiptPhotoTake.ReceiptPhotoTakeActivity;
import timber.log.Timber;

/**
 * Created on 9/8/2018
 * Project : Driver
 */
public class AuthorizePaymentStepNavigator {
    public static final String TAG="AuthorizePaymentStepNavigator";

    public static void gotoActivityReceiptDetail(Context context){
        Intent i = new Intent(context, ReceiptDetailActivity.class);
        context.startActivity(i);
        Timber.tag(TAG).d("starting ReceiptDetailActivity");
    }

    public static void gotoActivityReceiptPhotoTakeActivity(Context context) {
        Intent i = new Intent(context, ReceiptPhotoTakeActivity.class);
        context.startActivity(i);
        Timber.tag(TAG).d("starting ReceiptPhotoTakeActivity");
    }

}
