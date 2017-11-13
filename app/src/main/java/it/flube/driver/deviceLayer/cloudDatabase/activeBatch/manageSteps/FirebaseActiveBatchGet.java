/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.manageSteps;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor2.FirebaseActiveBatchNodeListener;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 11/8/2017
 * Project : Driver
 */

public class FirebaseActiveBatchGet  {

    private static final String TAG = "FirebaseActiveBatchGet";

    public void getActiveBatchRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef, CloudDatabaseInterface.ActiveBatchUpdated response){
        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        activeBatchRef.addListenerForSingleValueEvent(new FirebaseActiveBatchNodeListener(batchDataRef, response));
    }

}
