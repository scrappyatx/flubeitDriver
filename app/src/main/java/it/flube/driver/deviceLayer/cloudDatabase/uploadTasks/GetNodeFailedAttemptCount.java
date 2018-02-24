/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.uploadTasks;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor2.ActiveBatchNode;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor2.ActiveBatchNodeWithBatchData;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor2.ActiveBatchNodeWithoutBatchData;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor2.FirebaseActiveBatchNodeListener;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.ATTEMPT_COUNT_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.UPLOAD_TASKS_FAILED_NODE;

/**
 * Created on 2/23/2018
 * Project : Driver
 */

public class GetNodeFailedAttemptCount implements
        ValueEventListener {

    public static final String TAG = "GetNodeFailedAttemptCount";

    private Response response;

    public GetNodeFailedAttemptCount(){
        Timber.tag(TAG).d("created...");
    }

    public void getAttemptCountRequest(DatabaseReference batchDataNodeRef,
                                       String batchGuid, String photoRequestGuid,
                                       Response response){

        this.response = response;
        batchDataNodeRef.child(batchGuid).child(UPLOAD_TASKS_FAILED_NODE).child(photoRequestGuid).child(ATTEMPT_COUNT_NODE).addListenerForSingleValueEvent(this);
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");
        Integer attemptCount = 0;

        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...dataSnapshot exists!");
            try {
                attemptCount = (Integer) dataSnapshot.getValue();
                Timber.tag(TAG).d("attemptCount = " + attemptCount);
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
                Timber.tag(TAG).w("   ...ERROR : response -> attemptCount = 0");
                attemptCount = 0;
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist : response -> attemptCount = 0");
            attemptCount = 0;
        }
        response.getAttemptCountResponse(attemptCount);
    }

    public void onCancelled(DatabaseError databaseError){
        Timber.tag(TAG).e("onCancelled - > firebase database read error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.getAttemptCountResponse(0);
    }

    interface Response {
        void getAttemptCountResponse(Integer attemptCount);
    }
}
