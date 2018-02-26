/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.batchData;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseBatchDataSave implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseBatchDataSave";

    private static final String BATCH_NODE = "batch";
    private static final String BATCH_DETAIL_NODE = "batchDetail";
    private static final String ROUTE_STOPS = "routeStops";
    private static final String SERVICE_ORDERS = "serviceOrders";
    private static final String STEP_IDS = "stepIds";
    private static final String STEPS = "steps";
    private static final String MAP_PINGS = "mapPings";
    private static final String CHAT_HISTORY = "chatHistory";
    private static final String DRIVER_CHAT = "driverChat";
    private static final String CUSTOMER_CHAT = "customerChat";
    private static final String SERVICE_PROVIDER_CHAT = "serviceProviderChat";
    private static final String CHAT_MESSAGES = "chatMessages";
    private static final String FILE_ATTACHMENTS = "fileAttachments";

    private ResponseCounter responseCounter;
    private CloudDatabaseInterface.SaveBatchDataResponse response;

    public void saveDemoBatchDataRequest(DatabaseReference batchDataRef, BatchHolder batchHolder, CloudDatabaseInterface.SaveBatchDataResponse response) {
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        this.response = response;
        responseCounter = new ResponseCounter(12);

        DatabaseReference thisBatchRef = batchDataRef.child(batchHolder.getBatch().getGuid());

        thisBatchRef.child(BATCH_NODE).setValue(batchHolder.getBatch())
                .addOnCompleteListener(this);

        thisBatchRef.child(BATCH_DETAIL_NODE).setValue(batchHolder.getBatchDetail())
                .addOnCompleteListener(this);

        thisBatchRef.child(ROUTE_STOPS).setValue(batchHolder.getRouteStops())
                .addOnCompleteListener(this);

        thisBatchRef.child(SERVICE_ORDERS).setValue(batchHolder.getServiceOrders())
                .addOnCompleteListener(this);

        thisBatchRef.child(STEP_IDS).setValue(batchHolder.getStepIds())
                .addOnCompleteListener(this);


        thisBatchRef.child(STEPS).setValue(batchHolder.getSteps())
                .addOnCompleteListener(this);

        thisBatchRef.child(MAP_PINGS).setValue(batchHolder.getMapPings())
                .addOnCompleteListener(this);

        thisBatchRef.child(CHAT_HISTORY).child(DRIVER_CHAT).setValue(batchHolder.getDriverChatHistories())
                .addOnCompleteListener(this);

        thisBatchRef.child(CHAT_HISTORY).child(CUSTOMER_CHAT).setValue(batchHolder.getCustomerChatHistories())
                .addOnCompleteListener(this);

        thisBatchRef.child(CHAT_HISTORY).child(SERVICE_PROVIDER_CHAT).setValue(batchHolder.getServiceProviderChatHistories())
                .addOnCompleteListener(this);

        thisBatchRef.child(CHAT_MESSAGES).setValue(batchHolder.getChatMessages())
                .addOnCompleteListener(this);

        thisBatchRef.child(FILE_ATTACHMENTS).setValue(batchHolder.getFileAttachments())
                .addOnCompleteListener(this);

        Timber.tag(TAG).d("saving DEMO BATCH DATA --> batch Guid : " + batchHolder.getBatch().getGuid());
    }


    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("onComplete...");
        responseCounter.onResponse();

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

        if (responseCounter.isFinished()) {
            Timber.tag(TAG).d("...COMPLETE");
            response.cloudDatabaseBatchDataSaveComplete();
        } else {
            Timber.tag(TAG).d("      ...responseCounter = " + Integer.toString(responseCounter.getCount()));
        }
    }

}
