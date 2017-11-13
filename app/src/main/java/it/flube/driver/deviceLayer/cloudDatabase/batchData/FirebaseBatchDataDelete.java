/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.batchData;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseBatchDataDelete implements OnCompleteListener<Void>  {
    private static final String TAG = "FirebaseBatchDataDelete";

    private CloudDatabaseInterface.DeleteBatchDataResponse response;

    public void deleteDemoBatchDataRequest(DatabaseReference batchDataRef, String batchGuid, CloudDatabaseInterface.DeleteBatchDataResponse response) {
        this.response = response;
        Timber.tag(TAG).d("batchDatRef = " + batchDataRef);

        DatabaseReference thisBatchRef = batchDataRef.child(batchGuid);
        thisBatchRef.setValue(null).addOnCompleteListener(this);

        Timber.tag(TAG).d("deleting BATCH DATA --> batch Guid : " + batchGuid);
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("onComplete...");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("   ...SUCCESS");
        } else {
            Timber.tag(TAG).w("   ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }

        }
        response.cloudDatabaseBatchDataDeleteComplete();
    }

}
