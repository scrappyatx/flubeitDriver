/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.batchData.steps;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail.FireBatchDetailSetStatus;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class FirebaseOrderStepSetWorkStage implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseOrderStepSetWorkStage";
    private static final String BATCH_DATA_STEPS_NODE = "steps";
    private static final String WORK_STAGE_PROPERTY = "workStage";
    private static final String START_TIME_PROPERTY = "startTime/actualTime";
    private static final String FINISH_TIME_PROPERTY = "finishTime/actualTime";

    private CloudDatabaseInterface.OrderStepWorkStageUpdated response;

    public void setOrderStepSetWorkStageRequest(DatabaseReference batchDataRef, OrderStepInterface step, OrderStepInterface.WorkStage workStage,
                                                CloudDatabaseInterface.OrderStepWorkStageUpdated response){
        this.response = response;

        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(WORK_STAGE_PROPERTY, workStage);
        Timber.tag(TAG).d("...added key -> " + WORK_STAGE_PROPERTY + " value -> " + workStage.toString());

        switch (workStage){
            case ACTIVE:
                Timber.tag(TAG).d("...added key -> " + START_TIME_PROPERTY);
                //data.put(START_TIME_PROPERTY, ServerValue.TIMESTAMP);
                break;
            case COMPLETED:
                Timber.tag(TAG).d("...added key -> " + FINISH_TIME_PROPERTY);
                //data.put(FINISH_TIME_PROPERTY, ServerValue.TIMESTAMP);
                break;
            case NOT_STARTED:
                break;
            default:
                break;
        }

        batchDataRef.child(step.getBatchGuid()).child(BATCH_DATA_STEPS_NODE).child(step.getGuid()).updateChildren(data)
                .addOnCompleteListener(this);

    }


    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("onComplete...");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("   ...SUCCESS");
        } else {
            Timber.tag(TAG).w("   ...FAILURE");
            try {
                throw task.getException();
            } catch (Exception e) {
                Timber.tag(TAG).d("   ...ERROR");
                Timber.tag(TAG).e(e);
            }
        }
        response.cloudDatabaseOrderStepWorkStageSetComplete();
    }

}
