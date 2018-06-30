/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.productionBatch;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.Date;
import java.util.HashMap;

import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch.BatchForfeitRequestWrite;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 6/9/2018
 * Project : Driver
 */
public class BatchStartRequestWrite implements
        OnCompleteListener<Void> {

    private static final String TAG = "BatchStartRequestWrite";

    private static final String BATCH_NODE = "batchGuid";
    private static final String BATCH_TYPE_NODE = "batchType";
    private static final String CLIENT_NODE = "clientId";
    private static final String TIMESTAMP_NODE = "timestamp";
    private static final String START_TIME_NODE = "expectedStartTime";
    private static final String EARLIEST_START_MINUTES_PRIOR_NODE = "earliestStartMinutesPrior";
    private static final String LATEST_START_MINUTES_AFTER_NODE = "latestStartMinutesAfter";
    private static final String REQUEST_GUID_NODE = "requestGuid";

    private Response response;

    public void writeStartRequest(DatabaseReference batchStartRequestRef, String clientId, BatchDetail batchDetail, String requestGuid,
                                    Response response){

        Timber.tag(TAG).d("writeStartRequest START...");
        this.response = response;

        Timber.tag(TAG).d("   batchStartRequestRef      = " + batchStartRequestRef.toString());
        Timber.tag(TAG).d("   clientId                  = " + clientId);
        Timber.tag(TAG).d("   batchGuid                 = " + batchDetail.getBatchGuid());
        Timber.tag(TAG).d("   batchType                 = " + batchDetail.getBatchType().toString());
        Timber.tag(TAG).d("   expectedStartTime         = " + batchDetail.getExpectedStartTime().toString());
        Timber.tag(TAG).d("   earliestStartMinutesPrior = " + batchDetail.getEarliestStartMinutesPrior().toString());
        Timber.tag(TAG).d("   latestStartMinutesAfter   = " + batchDetail.getLatestStartMinutesAfter().toString());
        Timber.tag(TAG).d("   requestGuid               = " + requestGuid);

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(BATCH_NODE, batchDetail.getBatchGuid());
        data.put(BATCH_TYPE_NODE, batchDetail.getBatchType().toString());
        data.put(CLIENT_NODE, clientId);
        data.put(START_TIME_NODE, batchDetail.getExpectedStartTime());
        data.put(EARLIEST_START_MINUTES_PRIOR_NODE, batchDetail.getEarliestStartMinutesPrior());
        data.put(LATEST_START_MINUTES_AFTER_NODE, batchDetail.getLatestStartMinutesAfter());
        data.put(TIMESTAMP_NODE, ServerValue.TIMESTAMP);
        data.put(REQUEST_GUID_NODE, requestGuid);
        Timber.tag(TAG).d("   ...building hash map");

        batchStartRequestRef.child(batchDetail.getBatchGuid()).setValue(data).addOnCompleteListener(this);
        Timber.tag(TAG).d("   ...setting value");
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
        response.writeStartRequestComplete();
    }

    public interface Response {
        void writeStartRequestComplete();
    }
}
