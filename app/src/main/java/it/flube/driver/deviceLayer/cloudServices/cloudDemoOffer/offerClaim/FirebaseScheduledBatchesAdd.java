/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offerClaim;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseScheduledBatchesAdd implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseScheduledBatchesAdd";

    private Response response;

    public void addDemoBatchToScheduledBatchListRequest(DatabaseReference scheduledBatchesRef, String batchGuid,
                                                        Response response) {
        this.response = response;
        Timber.tag(TAG).d("scheduledBatchesRef = " + scheduledBatchesRef.toString());

        scheduledBatchesRef.child(batchGuid).setValue(true).addOnCompleteListener(this);
        Timber.tag(TAG).d("adding batch guid to scheduled batch list --> batch Guid : " + batchGuid);
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
        response.scheduledBatchesAddComplete();
    }

    public interface Response {
        void scheduledBatchesAddComplete();
    }
}
