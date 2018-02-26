/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderGet;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 10/11/2017
 * Project : Driver
 */

public class FirebaseActiveBatchCurrentServiceOrderListener implements
        ValueEventListener,
        FirebaseServiceOrderGet.GetServiceOrderResponse {

    private static final String TAG = "FirebaseActiveBatchCurrentServiceOrderListener";
    private static final String BATCH_DATA_SERVICE_ORDER_NODE = "serviceOrders";

    private DatabaseReference batchDataRef;
    private String batchGuid;
    private CurrentServiceOrderResponse response;

    public FirebaseActiveBatchCurrentServiceOrderListener(DatabaseReference batchDataRef, String batchGuid, CurrentServiceOrderResponse response){
        this.batchGuid = batchGuid;
        this.batchDataRef = batchDataRef;
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        this.response = response;
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");

        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...dataSnapshot exists!");
            try {
                Integer serviceOrderSequence = dataSnapshot.getValue(Integer.class);
                Timber.tag(TAG).d("      ...looking for service order sequence : " + serviceOrderSequence);
                new FirebaseServiceOrderGet().getServiceOrderRequest(batchDataRef, batchGuid, serviceOrderSequence, this);
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
                response.currentServiceOrderFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
            response.currentServiceOrderFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError){
        Timber.tag(TAG).e("onCancelled -> firebase database read error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.currentServiceOrderFailure();
    }

    public void getServiceOrderSuccess(ServiceOrder serviceOrder) {
        Timber.tag(TAG).d("      ...got service order!");
        response.currentServiceOrderSuccess(serviceOrder);
    }

    public void getServiceOrderFailure() {
        Timber.tag(TAG).w("      ...did NOT get service order");
        response.currentServiceOrderFailure();
    }

    interface CurrentServiceOrderResponse {
        void currentServiceOrderSuccess(ServiceOrder serviceOrder);

        void currentServiceOrderFailure();
    }
}
