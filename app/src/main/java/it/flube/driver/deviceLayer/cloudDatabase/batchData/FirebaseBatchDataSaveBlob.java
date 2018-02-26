/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.batchData;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 10/27/2017
 * Project : Driver
 */

public class FirebaseBatchDataSaveBlob implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseBatchDataSaveBlob";

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

    private CloudDatabaseInterface.SaveBatchDataResponse response;

    public void saveDemoBatchDataRequest(DatabaseReference batchDataRef, BatchHolder batchHolder, CloudDatabaseInterface.SaveBatchDataResponse response) {
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        this.response = response;

        DatabaseReference thisBatchRef = batchDataRef.child(batchHolder.getBatch().getGuid());
        thisBatchRef.setValue(getDataMap(batchHolder)).addOnCompleteListener(this);

        Timber.tag(TAG).d("saving DEMO BATCH DATA --> batch Guid : " + batchHolder.getBatch().getGuid());
    }

    private HashMap<String, Object> getDataMap(BatchHolder batchHolder){
        HashMap<String, Object> batchData = new HashMap<String, Object>();

        //build the holder for the data, in the format it will be stored in firebase database
        batchData.put(BATCH_NODE, batchHolder.getBatch());
        batchData.put(BATCH_DETAIL_NODE, batchHolder.getBatchDetail());
        batchData.put(ROUTE_STOPS, batchHolder.getRouteStops());
        batchData.put(SERVICE_ORDERS, batchHolder.getServiceOrders());
        //batchData.put(STEP_IDS, batchHolder.getStepIds());
        batchData.put(STEPS, batchHolder.getSteps());
        batchData.put(MAP_PINGS, batchHolder.getMapPings());

        batchData.put(DRIVER_CHAT, batchHolder.getDriverChatHistories());

        batchData.put(CHAT_MESSAGES, batchHolder.getChatMessages());
        batchData.put(FILE_ATTACHMENTS, batchHolder.getFileAttachments());

        HashMap<String, Object> chatHistories = new HashMap<String, Object>();
        chatHistories.put(DRIVER_CHAT, batchHolder.getDriverChatHistories());
        chatHistories.put(CUSTOMER_CHAT, batchHolder.getCustomerChatHistories());
        chatHistories.put(SERVICE_PROVIDER_CHAT, batchHolder.getServiceProviderChatHistories());

        batchData.put(CHAT_HISTORY, chatHistories);

        return batchData;
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
        Timber.tag(TAG).d("...COMPLETE");
        response.cloudDatabaseBatchDataSaveComplete();
    }

}
