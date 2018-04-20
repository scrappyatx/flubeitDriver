/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.serviceOrders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 10/11/2017
 * Project : Driver
 */

public class FirebaseServiceOrderGet implements ValueEventListener  {
    private static final String TAG = "FirebaseServiceOrderGet";
    private static final String BATCH_DATA_SERVICE_ORDER_NODE = "serviceOrders";
    private static final String SEQUENCE = "sequence";

    private GetServiceOrderResponse response;
    private Integer sequence;

    public void getServiceOrderRequest(DatabaseReference batchDataRef, String batchGuid, Integer sequence, GetServiceOrderResponse response){
        this.sequence = sequence;
        this.response = response;

        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        batchDataRef.child(batchGuid).child(BATCH_DATA_SERVICE_ORDER_NODE)
                .orderByChild(SEQUENCE).equalTo(sequence).addListenerForSingleValueEvent(this);
    }


    public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("service order data FOUND!");
            if (dataSnapshot.getChildrenCount() == 1) {
                Timber.tag(TAG).d("one child found, as expected");
                try {
                    ServiceOrder serviceOrder = new ServiceOrder();
                    for (DataSnapshot order : dataSnapshot.getChildren()) {
                        serviceOrder = order.getValue(ServiceOrder.class);

                        Timber.tag(TAG).d("service order :");
                        Timber.tag(TAG).d("   guid                : " + serviceOrder.getGuid());
                        Timber.tag(TAG).d("   batchDetailGuid     : " + serviceOrder.getBatchDetailGuid());
                        Timber.tag(TAG).d("   batchGuid           : " + serviceOrder.getBatchGuid());
                        Timber.tag(TAG).d("   description         : " + serviceOrder.getDescription());

                        Timber.tag(TAG).d("got service order sequence " + Integer.toString(sequence) + " for batch guid : " + serviceOrder.getBatchGuid());
                    }
                    response.getServiceOrderSuccess(serviceOrder);

                } catch (Exception e) {
                    Timber.tag(TAG).w("error while trying to get service order sequence " + Integer.toString(sequence));
                    Timber.tag(TAG).e(e);
                    response.getServiceOrderFailure();
                }
            } else {
                // too many children with sequence of 1, something is very very wrong
                Timber.tag(TAG).w("Too many children with same sequence, expected 1, got " + dataSnapshot.getChildrenCount());
                response.getServiceOrderFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("dataSnapshot does not exist");
            response.getServiceOrderFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("firebase database read error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.getServiceOrderFailure();
    }

    public interface GetServiceOrderResponse {
        void getServiceOrderSuccess(ServiceOrder serviceOrder);
        void getServiceOrderFailure();
    }


}
