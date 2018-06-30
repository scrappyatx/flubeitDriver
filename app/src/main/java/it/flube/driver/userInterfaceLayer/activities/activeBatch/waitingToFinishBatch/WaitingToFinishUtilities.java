/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.waitingToFinishBatch;

import android.support.v7.app.AppCompatActivity;

import it.flube.driver.userInterfaceLayer.activities.home.HomeUtilities;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.activeBatch.waitingToFinishBatch.WaitingToFinishBatchConstants.BATCH_GUID_KEY;


/**
 * Created on 6/22/2018
 * Project : Driver
 */
public class WaitingToFinishUtilities {
    private static final String TAG = "WaitingToFinishUtilities";

    public void getActivityLaunchInfo(AppCompatActivity activity, Response response){
        Timber.tag(TAG).d("getActivityLaunchInfo");
        if (activity.getIntent().hasExtra(BATCH_GUID_KEY)) {
            /// we have batch Guid
            Timber.tag(TAG).d("   we have batch guid key");
            String batchGuid = activity.getIntent().getStringExtra(BATCH_GUID_KEY);
            response.getActivityLaunchDataSuccess(batchGuid);
        } else {
            Timber.tag(TAG).w("   missing key -> " + BATCH_GUID_KEY);
            response.getActivityLaunchDataFailure();
        }
    }

    public interface Response {
        void getActivityLaunchDataSuccess(String batchGuid);

        void getActivityLaunchDataFailure();

    }

}
