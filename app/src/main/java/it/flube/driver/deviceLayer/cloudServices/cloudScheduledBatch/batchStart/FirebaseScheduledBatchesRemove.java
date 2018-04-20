/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchStart;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseScheduledBatchesRemove implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseScheduledBatchesRemove";

    private Response response;

    public void removeDemoBatchFromScheduledBatchListRequest(DatabaseReference scheduledBatchesRef, String batchGuid,
                                                             Response response) {

        this.response = response;
        Timber.tag(TAG).d("scheduledBatchesRef = " + scheduledBatchesRef.toString());
        scheduledBatchesRef.child(batchGuid).setValue(null).addOnCompleteListener(this);
        Timber.tag(TAG).d("removing batch guid from scheduled batches list --> batchGuid : " + batchGuid);
    }


    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("onComplete...");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("   ...SUCCESS");
        } else {
            Timber.tag(TAG).d("   ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        response.removeBatchFromScheduledBatchListComplete();
    }

    public interface Response {
        void removeBatchFromScheduledBatchListComplete();
    }
}
