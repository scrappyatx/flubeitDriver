/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.steps;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/17/2017
 * Project : Driver
 */

public class FirebaseOrderStepSave implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseOrderStepSave";
    private static final String BATCH_DATA_STEPS_NODE = "steps";

    private SaveOrderStepResponse response;

    public void saveOrderStepRequest(@NonNull DatabaseReference batchDataRef, @NonNull OrderStepInterface step, @NonNull SaveOrderStepResponse response){
        this.response = response;

        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        batchDataRef.child(step.getBatchGuid()).child(BATCH_DATA_STEPS_NODE).child(step.getGuid()).setValue(step)
                .addOnCompleteListener(this);
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("onComplete...");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("   ...SUCCESS");
        } else {
            try {
                throw task.getException();
            } catch (Exception e) {
                Timber.tag(TAG).w("   ...ERROR");
                Timber.tag(TAG).e(e);
            }
            Timber.tag(TAG).w("   ...FAILURE");
        }
        response.saveOrderStepComplete();
    }

    public interface SaveOrderStepResponse {
        void saveOrderStepComplete();
    }

}
