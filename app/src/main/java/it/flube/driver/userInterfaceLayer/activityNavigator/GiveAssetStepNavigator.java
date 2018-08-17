/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep.getSignature.GiveAssetGetSignatureActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep.itemDetail.GiveAssetItemActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.getSignature.ReceiveAssetGetSignatureActivity;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ASSET_TRANSFER_ASSET_GUID_KEY;

/**
 * Created on 8/16/2018
 * Project : Driver
 */
public class GiveAssetStepNavigator {
    private static final String TAG = "GiveAssetStepNavigator";

    public static void gotoActivityGiveAssetGetSignature(Context context){
        Intent i = new Intent(context, GiveAssetGetSignatureActivity.class);
        //i.putExtra(ACTIVE_BATCH_GUID_KEY, batchGuid);
        //i.putExtra(ACTIVE_BATCH_ORDER_GUID_KEY, serviceOrderGuid);
        //i.putExtra(ACTIVE_BATCH_STEP_GUID_KEY, orderStepGuid);
        //i.putExtra(ACTIVE_BATCH_TASK_TYPE_KEY, taskType.toString());
        context.startActivity(i);
        Timber.tag(TAG).d("starting GiveAssetGetSignatureActivity");
    }

    public static void gotoActivityGiveAssetItem(Context context, String assetGuid){
        Intent i = new Intent(context, GiveAssetItemActivity.class);
        i.putExtra(ASSET_TRANSFER_ASSET_GUID_KEY, assetGuid);
        context.startActivity(i);
        Timber.tag(TAG).d("starting GiveAssetItemActivity");
    }

    public static void gotoActivityGiveAssetItemList(Context context){
    ///    Intent i = new Intent(context, GiveAssetItemListActivity.class);
    ///    context.startActivity(i);
           Timber.tag(TAG).d("starting GiveAssetItemListActivity");
    }
}
