/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.home.HomeActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimActivity;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.home.HomeConstants.HOME_ACTOR_TYPE_KEY;
import static it.flube.driver.userInterfaceLayer.activities.home.HomeConstants.HOME_BATCH_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.home.HomeConstants.HOME_SHOW_BATCH_FINISHED_KEY;
import static it.flube.driver.userInterfaceLayer.activities.home.HomeConstants.HOME_SHOW_BATCH_REMOVED_KEY;


/**
 * Created on 6/21/2018
 * Project : Driver
 */
public class HomeNavigator {
    public static final String TAG = "HomeNavigator";

    public void gotoActivityHome(Context context){
        context.startActivity(new Intent(context, HomeActivity.class));
        Timber.tag(TAG).d("starting activity HomeActivity.class");
    }

    public void gotoActivityHomeAndShowBatchFinishedMessage(Context context, ActiveBatchManageInterface.ActorType actorType, String batchGuid) {

        Intent i = new Intent(context, HomeActivity.class);
        i.putExtra(HOME_SHOW_BATCH_FINISHED_KEY, Boolean.TRUE);
        i.putExtra(HOME_ACTOR_TYPE_KEY, actorType.toString());
        i.putExtra(HOME_BATCH_GUID_KEY, batchGuid);
        context.startActivity(i);

        Timber.tag(TAG).d("starting activity HomeActivity.class...");
        Timber.tag(TAG).d("   ...actorType = " + actorType.toString());
        Timber.tag(TAG).d("   ...batchGuid = " + batchGuid);

        context.startActivity(i);
    }

    public void gotoActivityHomeAndShowBatchRemovedMessage(Context context,  ActiveBatchManageInterface.ActorType actorType, String batchGuid){

        Intent i = new Intent(context, HomeActivity.class);
        i.putExtra(HOME_SHOW_BATCH_REMOVED_KEY, Boolean.TRUE);
        i.putExtra(HOME_ACTOR_TYPE_KEY, actorType.toString());
        i.putExtra(HOME_BATCH_GUID_KEY, batchGuid);
        context.startActivity(i);

        Timber.tag(TAG).d("starting activity HomeActivity.class...");
        Timber.tag(TAG).d("   ...actorType = " + actorType.toString());
        Timber.tag(TAG).d("   ...batchGuid = " + batchGuid);

        context.startActivity(i);
    }

}
