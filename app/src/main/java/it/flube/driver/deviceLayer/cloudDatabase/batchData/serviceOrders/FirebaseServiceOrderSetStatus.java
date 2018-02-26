/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class FirebaseServiceOrderSetStatus implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseServiceOrderSetStatus";
    private static final String BATCH_DATA_SERVICE_ORDER_NODE = "serviceOrders";
    private static final String STATUS_PROPERTY = "status";
    private static final String START_TIME_PROPERTY = "startTime/actualTime";
    private static final String FINISH_TIME_PROPERTY = "finishTime/actualTime";


    private Response response;

    public void setServiceOrderStatusRequest(DatabaseReference batchDataRef, ServiceOrder serviceOrder, ServiceOrder.ServiceOrderStatus status,
                                             Response response){
        this.response = response;

        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(STATUS_PROPERTY, status);
        Timber.tag(TAG).d("...added key -> " + STATUS_PROPERTY + " value -> " + status.toString());

        switch (status){
            case ACTIVE:
                Timber.tag(TAG).d("...added key -> " + START_TIME_PROPERTY);
                //data.put(START_TIME_PROPERTY, ServerValue.TIMESTAMP);
                break;
            case PAUSED:
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

        batchDataRef.child(serviceOrder.getBatchGuid()).child(BATCH_DATA_SERVICE_ORDER_NODE).child(serviceOrder.getGuid()).updateChildren(data)
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
        response.setServiceOrderStatusComplete();
    }

    public interface Response {
        void setServiceOrderStatusComplete();
    }


}
