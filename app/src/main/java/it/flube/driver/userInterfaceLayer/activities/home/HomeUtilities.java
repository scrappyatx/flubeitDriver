/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.home;

import android.support.v7.app.AppCompatActivity;

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
public class HomeUtilities {
    private static final String TAG = "HomeUtilities";

    public void getActivityLaunchInfo(AppCompatActivity activity,GetActivityLaunchInfoResponse response){
        if (activity.getIntent().hasExtra(HOME_SHOW_BATCH_FINISHED_KEY)) {
            /// we have BATCH_FINISHED

            //get actorType & batchGuid
            if (activity.getIntent().hasExtra(HOME_ACTOR_TYPE_KEY)){
                if (activity.getIntent().hasExtra(HOME_BATCH_GUID_KEY)) {

                    ActiveBatchManageInterface.ActorType actorType = ActiveBatchManageInterface.ActorType.valueOf(activity.getIntent().getStringExtra(HOME_ACTOR_TYPE_KEY));
                    String batchGuid = activity.getIntent().getStringExtra(HOME_BATCH_GUID_KEY);

                    //clear extras, only want to display this once
                    activity.getIntent().removeExtra(HOME_SHOW_BATCH_FINISHED_KEY);
                    activity.getIntent().removeExtra(HOME_ACTOR_TYPE_KEY);
                    activity.getIntent().removeExtra(HOME_BATCH_GUID_KEY);

                    switch (actorType){
                        case MOBILE_USER:
                        case NOT_SPECIFIED:
                            response.showBatchFinishedByUser(batchGuid);
                            break;
                        case SERVER_ADMIN:
                            response.showBatchFinishedByAdmin(batchGuid);
                            break;
                        default:
                            response.doNothing();
                            break;
                    }

                } else {
                    Timber.tag(TAG).d("   missing key -> " + HOME_BATCH_GUID_KEY);
                    response.doNothing();
                }
            } else {
                Timber.tag(TAG).d("   missing key -> " + HOME_ACTOR_TYPE_KEY);
                response.doNothing();
            }


        } else if (activity.getIntent().hasExtra(HOME_SHOW_BATCH_REMOVED_KEY)) {
            // we have BATCH_REMOVED

            //get actorType & batchGuid
            if (activity.getIntent().hasExtra(HOME_ACTOR_TYPE_KEY)){
                if (activity.getIntent().hasExtra(HOME_BATCH_GUID_KEY)) {

                    ActiveBatchManageInterface.ActorType actorType = ActiveBatchManageInterface.ActorType.valueOf(activity.getIntent().getStringExtra(HOME_ACTOR_TYPE_KEY));
                    String batchGuid = activity.getIntent().getStringExtra(HOME_BATCH_GUID_KEY);

                    //clear extras, only want to display this once
                    activity.getIntent().removeExtra(HOME_SHOW_BATCH_REMOVED_KEY);
                    activity.getIntent().removeExtra(HOME_ACTOR_TYPE_KEY);
                    activity.getIntent().removeExtra(HOME_BATCH_GUID_KEY);

                    switch (actorType){
                        case MOBILE_USER:
                        case NOT_SPECIFIED:
                            response.showBatchRemovedByUser(batchGuid);
                            break;
                        case SERVER_ADMIN:
                            response.showBatchRemovedByAdmin(batchGuid);
                            break;
                        default:
                            response.doNothing();
                            break;
                    }

                } else {
                    Timber.tag(TAG).d("   missing key -> " + HOME_BATCH_GUID_KEY);
                    response.doNothing();
                }
            } else {
                Timber.tag(TAG).d("   missing key -> " + HOME_ACTOR_TYPE_KEY);
                response.doNothing();
            }
        } else {
            response.doNothing();
        }
    }

    public interface GetActivityLaunchInfoResponse {
        void showBatchRemovedByAdmin(String batchGuid);

        void showBatchRemovedByUser(String batchGuid);

        void showBatchFinishedByAdmin(String batchGuid);

        void showBatchFinishedByUser(String batchGuid);

        void doNothing();
    }
}
