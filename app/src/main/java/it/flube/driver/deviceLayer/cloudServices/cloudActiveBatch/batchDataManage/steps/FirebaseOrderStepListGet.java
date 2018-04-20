/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.steps;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import it.flube.libbatchdata.entities.orderStep.ServiceOrderGenericStep;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/24/2017
 * Project : Driver
 */

public class FirebaseOrderStepListGet implements ValueEventListener {
    private static final String TAG = "FirebaseOrderStepListGet";
    private static final String BATCH_DATA_STEPS_NODE = "steps";
    private static final String SERVICE_ORDER_GUID = "serviceOrderGuid";

    private GetOrderStepListResponse response;

    public void getOrderStepList(DatabaseReference batchDataRef,
                             String batchGuid, String serviceOrderGuid, GetOrderStepListResponse response){

        this.response = response;
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        Timber.tag(TAG).d("getting step list for batchGuid : " + batchGuid + " service order guid : " + serviceOrderGuid);

        batchDataRef.child(batchGuid).child(BATCH_DATA_STEPS_NODE)
                .orderByChild(SERVICE_ORDER_GUID).equalTo(serviceOrderGuid)
                .addListenerForSingleValueEvent(this);
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");
        if (dataSnapshot.exists()) {

            Timber.tag(TAG).d("   ...step data FOUND!");
            Map<Integer, OrderStepInterface> stepMap = new TreeMap<Integer, OrderStepInterface>();
            ArrayList<OrderStepInterface> orderStepList = new ArrayList<OrderStepInterface>();

            try {
                OrderStepInterface orderStep;
                for (DataSnapshot step : dataSnapshot.getChildren()) {
                    orderStep = step.getValue(ServiceOrderGenericStep.class);

                    Timber.tag(TAG).d("      orderStep : ");
                    Timber.tag(TAG).d("         guid                : " + orderStep.getGuid());
                    Timber.tag(TAG).d("         batchDetailGuid     : " + orderStep.getBatchDetailGuid());
                    Timber.tag(TAG).d("         batchGuid           : " + orderStep.getBatchGuid());
                    Timber.tag(TAG).d("         serviceOrderGuid    : " + orderStep.getServiceOrderGuid());
                    Timber.tag(TAG).d("         taskType            : " + orderStep.getTaskType().toString());
                    Timber.tag(TAG).d("         title               : " + orderStep.getTitle());
                    Timber.tag(TAG).d("         getClass()          : " + orderStep.getClass());

                    //TODO after reading into a generic step, figure out the tasktype, then read into the appropriate class
                    //TODO this can get rid of the stepIds

                    //orderStepList.add(orderStep.getSequence(), orderStep);
                    stepMap.put(orderStep.getSequence(), orderStep);
                }
                //put results into arraylist, should be sorted by sequence ascending in the treemap
                for (Map.Entry<Integer, OrderStepInterface> entry : stepMap.entrySet()){
                    orderStepList.add(entry.getValue());
                    Timber.tag(TAG).d(" sequence : " + entry.getValue().getSequence() + " stepGuid : " + entry.getValue().getGuid());
                }
                response.getOrderStepListSuccess(orderStepList);

            } catch (Exception e) {
                Timber.tag(TAG).w("   ...error while trying to get step data!");
                Timber.tag(TAG).e(e);
                response.getOrderStepListFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.getOrderStepListFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError){
        Timber.tag(TAG).e("onCancelled -> error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.getOrderStepListFailure();
    }

    public interface GetOrderStepListResponse {
        void getOrderStepListSuccess(ArrayList<OrderStepInterface> orderStepList);

        void getOrderStepListFailure();
    }

}