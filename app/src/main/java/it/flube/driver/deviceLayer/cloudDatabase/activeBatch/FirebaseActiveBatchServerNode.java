/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 10/29/2017
 * Project : Driver
 */

public class FirebaseActiveBatchServerNode implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseActiveBatchServerNode";
    private static final String CLIENT_ID_PROPERTY = "clientId";
    private static final String START_TIME_PROPERTY = "batchStarted";
    private static final String ACTIVE_BATCH_SERVER_NOTIFICATION_NODE = "userWriteable/activeBatches";

    public void activeBatchStartRequest(DatabaseReference activeBatchRef, String batchGuid, Driver driver){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(CLIENT_ID_PROPERTY, driver.getClientId());
        data.put(START_TIME_PROPERTY, ServerValue.TIMESTAMP);

        Timber.tag(TAG).d("setting active batch data...");
        Timber.tag(TAG).d("   clientId    --> " + driver.getClientId());
        Timber.tag(TAG).d("   start timestamp");

        activeBatchRef.child(ACTIVE_BATCH_SERVER_NOTIFICATION_NODE).child(batchGuid).setValue(data).addOnCompleteListener(this);
    }

    public void activeBatchFinishRequest(DatabaseReference activeBatchRef, String batchGuid){
        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        activeBatchRef.child(ACTIVE_BATCH_SERVER_NOTIFICATION_NODE).child(batchGuid).setValue(null).addOnCompleteListener(this);
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        Timber.tag(TAG).d("COMPLETE");
    }
}
