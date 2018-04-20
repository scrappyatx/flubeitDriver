/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.stepFinish;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor.FirebaseActiveBatchNodeListener;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 11/8/2017
 * Project : Driver
 */

public class FirebaseActiveBatchGet  {

    private static final String TAG = "FirebaseActiveBatchGet";

    public void getActiveBatchRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef, CloudActiveBatchInterface.ActiveBatchUpdated response){
        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        activeBatchRef.addListenerForSingleValueEvent(new FirebaseActiveBatchNodeListener(batchDataRef, response));
    }

}
