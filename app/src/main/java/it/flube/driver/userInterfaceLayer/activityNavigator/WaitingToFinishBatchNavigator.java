/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.NavigationActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.waitingToFinishBatch.WaitingToFinishBatchActivity;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;

import static it.flube.driver.userInterfaceLayer.activities.activeBatch.waitingToFinishBatch.WaitingToFinishBatchConstants.ACTOR_TYPE_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.waitingToFinishBatch.WaitingToFinishBatchConstants.BATCH_GUID_KEY;

/**
 * Created on 6/22/2018
 * Project : Driver
 */
public class WaitingToFinishBatchNavigator {
    private static final String TAG = "WaitingToFinishBatchNavigator";

    public void gotoWaitingToFinishBatch(Context context, ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        Intent i = new Intent(context,WaitingToFinishBatchActivity.class);
        i.putExtra(BATCH_GUID_KEY, batchGuid);
        i.putExtra(ACTOR_TYPE_KEY, actorType.toString());
        context.startActivity(i);
    }
}
