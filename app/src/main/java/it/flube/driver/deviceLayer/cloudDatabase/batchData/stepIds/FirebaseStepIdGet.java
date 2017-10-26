/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.batchData.stepIds;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.modelLayer.entities.orderStep.StepId;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 10/11/2017
 * Project : Driver
 */

public class FirebaseStepIdGet implements ValueEventListener {
    private static final String TAG = "FirebaseStepIdGet";
    private static final String SERVICE_ORDER_GUID = "serviceOrderGuid";

    private static final String BATCH_DATA_STEPID_NODE = "stepIds";

    private Integer sequence;
    private StepIdResponse response;

    public void getStepIdRequest(DatabaseReference batchDataRef, String batchGuid, String serviceOrderGuid, Integer sequence, StepIdResponse response){
        this.response = response;
        this.sequence = sequence;
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        batchDataRef.child(batchGuid).child(BATCH_DATA_STEPID_NODE).orderByChild(SERVICE_ORDER_GUID).equalTo(serviceOrderGuid).addListenerForSingleValueEvent(this);
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");
        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...stepId data FOUND!");

            // check every one until we find the one that matches
            try {
                Boolean matchFound = false;
                for (DataSnapshot step : dataSnapshot.getChildren()) {
                    StepId stepId = step.getValue(StepId.class);

                    Timber.tag(TAG).d("      stepId : ");
                    Timber.tag(TAG).d("         guid                : " + stepId.getGuid());
                    Timber.tag(TAG).d("         batchDetailGuid     : " + stepId.getBatchDetailGuid());
                    Timber.tag(TAG).d("         batchGuid           : " + stepId.getBatchGuid());
                    Timber.tag(TAG).d("         serviceOrderGuid    : " + stepId.getServiceOrderGuid());
                    Timber.tag(TAG).d("         taskType    : " + stepId.getTaskType().toString());
                    Timber.tag(TAG).d("         sequence    : " + stepId.getSequence().toString());

                    if (stepId.getSequence().equals(sequence)) {
                        Timber.tag(TAG).d(" ^^^^^^ MATCH FOUND ^^^^^^");
                        matchFound = true;
                        response.stepIdSuccess(stepId);
                        break;
                    }
                }

                if (!matchFound) {
                    Timber.tag(TAG).d("      ...no match found, returning failure");
                    response.stepIdFailure();
                }

            } catch (Exception e) {
                Timber.tag(TAG).w("error while trying to get step id sequence " + Integer.toString(sequence));
                Timber.tag(TAG).e(e);
                response.stepIdFailure();
            }

        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("dataSnapshot does not exist");
            response.stepIdFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError){
        Timber.tag(TAG).e("firebase database read error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.stepIdFailure();
    }

    public interface StepIdResponse {
        void stepIdSuccess(StepId stepId);

        void stepIdFailure();
    }

}
