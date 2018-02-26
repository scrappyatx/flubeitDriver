/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.deviceLayer.cloudDatabase.batchData.steps.FirebaseOrderStepGet;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/11/2017
 * Project : Driver
 */

public class FirebaseActiveBatchCurrentStepListener implements
        ValueEventListener,
        FirebaseOrderStepGet.GetOrderStepResponse {

    private static final String TAG = "FirebaseActiveBatchCurrentStepListener";

    private DatabaseReference batchDataRef;
    private String batchGuid;
    private String serviceOrderGuid;
    private CurrentStepResponse response;

    public FirebaseActiveBatchCurrentStepListener(DatabaseReference batchDataRef, String batchGuid, String serviceOrderGuid, CurrentStepResponse response){
        this.batchGuid = batchGuid;
        this.serviceOrderGuid = serviceOrderGuid;
        this.batchDataRef = batchDataRef;
        this.response = response;

        Timber.tag(TAG).d("batchDataRef   = " + batchDataRef.toString());
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");

        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...dataSnapshot exists!");
            try {
                Integer stepIdSequence = dataSnapshot.getValue(Integer.class);
                Timber.tag(TAG).d("      ...looking for stepId sequence " + stepIdSequence + " for serviceOrder Guid : " + serviceOrderGuid);
                new FirebaseOrderStepGet().getOrderStep(batchDataRef, batchGuid, serviceOrderGuid, stepIdSequence, this);
            } catch (Exception e) {
                Timber.tag(TAG).d("      ...ERROR");
                Timber.tag(TAG).e(e);
                response.currentStepFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
            response.currentStepFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError){
        Timber.tag(TAG).e("onCancelled --> error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.currentStepFailure();
    }

    public void getOrderStepSuccess(OrderStepInterface orderStep) {
        Timber.tag(TAG).d("             ...got the step : step guid = " + orderStep.getGuid());
        response.currentStepSuccess(orderStep);
    }

    public void getOrderStepFailure() {
        Timber.tag(TAG).w("             ...couldn't find the step!");
        response.currentStepFailure();
    }

    interface CurrentStepResponse {
        void currentStepSuccess(OrderStepInterface orderStep);

        void currentStepFailure();
    }
}
