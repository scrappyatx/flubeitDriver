/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 4/1/2018
 * Project : Driver
 */
public class FirebaseActiveBatchServiceOrderListGet implements ValueEventListener {
    private static final String TAG = "FirebaseActiveBatchServiceOrderListGet";
    private static final String BATCH_DATA_SERVICE_ORDER_NODE = "serviceOrders";

    private CloudActiveBatchInterface.GetServiceOrderListResponse response;

    public void getServiceOrderListRequest(DatabaseReference batchDataRef, String batchGuid, CloudActiveBatchInterface.GetServiceOrderListResponse response){
        this.response = response;

        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        Timber.tag(TAG).d("getting service order list for batchGuid : " + batchGuid);

        batchDataRef.child(batchGuid).child(BATCH_DATA_SERVICE_ORDER_NODE)
                .addListenerForSingleValueEvent(this);

    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("...onDataChange");


        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...service order data FOUND!");
            Map<Integer, ServiceOrder> orderMap = new TreeMap<Integer, ServiceOrder>();
            ArrayList<ServiceOrder> orderList = new ArrayList<ServiceOrder>();

            try {
                ServiceOrder serviceOrder;
                for (DataSnapshot order : dataSnapshot.getChildren()) {
                    serviceOrder = order.getValue(ServiceOrder.class);

                    Timber.tag(TAG).d("      service order :");
                    Timber.tag(TAG).d("         guid                : " + serviceOrder.getGuid());
                    Timber.tag(TAG).d("         batchDetailGuid     : " + serviceOrder.getBatchDetailGuid());
                    Timber.tag(TAG).d("         batchGuid           : " + serviceOrder.getBatchGuid());
                    Timber.tag(TAG).d("         title               : " + serviceOrder.getTitle());
                    Timber.tag(TAG).d("         description         : " + serviceOrder.getDescription());
                    Timber.tag(TAG).d("         sequence            : " + serviceOrder.getSequence());

                    //orderList.add(serviceOrder);
                    orderMap.put(serviceOrder.getSequence(), serviceOrder);
                }
                //put results into arraylist, should be sorted by sequence ascending in the treemap
                for (Map.Entry<Integer, ServiceOrder> entry : orderMap.entrySet()){
                    orderList.add(entry.getValue());
                    Timber.tag(TAG).d(" sequence : " + entry.getValue().getSequence() + " service order guid : " + entry.getValue().getGuid());
                }

                response.cloudGetActiveBatchServiceOrderListSuccess(orderList);

            } catch (Exception e) {
                Timber.tag(TAG).w("   ...ERROR");
                Timber.tag(TAG).e(e);
                response.cloudGetActiveBatchServiceOrderListFailure();
            }

        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
            response.cloudGetActiveBatchServiceOrderListFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).w("...onCancelled -->  error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.cloudGetActiveBatchServiceOrderListFailure();
    }
}
