/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 10/17/2017
 * Project : Driver
 */

public class FirebaseServiceOrderSave implements OnCompleteListener<Void>  {
    private static final String TAG = "FirebaseServiceOrderSave";
    private static final String BATCH_DATA_SERVICE_ORDER_NODE = "serviceOrders";

    private SaveServiceOrderResponse response;

    public void saveServiceOrderRequest(DatabaseReference batchDataRef, ServiceOrder serviceOrder, SaveServiceOrderResponse response){
        this.response = response;

        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        batchDataRef.child(serviceOrder.getBatchGuid()).child(BATCH_DATA_SERVICE_ORDER_NODE).child(serviceOrder.getGuid()).setValue(serviceOrder)
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
                Timber.tag(TAG).d("   ...ERROR");
                Timber.tag(TAG).e(e);
            }
            Timber.tag(TAG).w("   ...FAILURE");
        }
        response.saveServiceOrderComplete();
    }

    public interface SaveServiceOrderResponse {
        void saveServiceOrderComplete();
    }
}
