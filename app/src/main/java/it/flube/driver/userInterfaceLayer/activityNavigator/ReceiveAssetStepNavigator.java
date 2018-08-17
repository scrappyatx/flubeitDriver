/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.getSignature.ReceiveAssetGetSignatureActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.itemDetail.ReceiveAssetItemActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.itemList.ReceiveAssetItemListActivity;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ASSET_TRANSFER_ASSET_GUID_KEY;

/**
 * Created on 8/3/2018
 * Project : Driver
 */
public class ReceiveAssetStepNavigator {
    private static final String TAG = "ReceiveAssetStepNavigator";

    public static void gotoActivityReceiveAssetGetSignature(Context context){
        Intent i = new Intent(context, ReceiveAssetGetSignatureActivity.class);
        //i.putExtra(ACTIVE_BATCH_GUID_KEY, batchGuid);
        //i.putExtra(ACTIVE_BATCH_ORDER_GUID_KEY, serviceOrderGuid);
        //i.putExtra(ACTIVE_BATCH_STEP_GUID_KEY, orderStepGuid);
        //i.putExtra(ACTIVE_BATCH_TASK_TYPE_KEY, taskType.toString());
        context.startActivity(i);
        Timber.tag(TAG).d("starting ReceiveAssetGetSignatureActivity");
    }

    public static void gotoActivityReceiveAssetItem(Context context, String assetGuid){
        Intent i = new Intent(context, ReceiveAssetItemActivity.class);
        i.putExtra(ASSET_TRANSFER_ASSET_GUID_KEY, assetGuid);
        context.startActivity(i);
        Timber.tag(TAG).d("starting ReceiveAssetItemActivity");
    }

    public static void gotoActivityReceiveAssetItemList(Context context){
        Intent i = new Intent(context, ReceiveAssetItemListActivity.class);
        context.startActivity(i);
        Timber.tag(TAG).d("starting ReceiveAssetItemListActivity");
    }
}
