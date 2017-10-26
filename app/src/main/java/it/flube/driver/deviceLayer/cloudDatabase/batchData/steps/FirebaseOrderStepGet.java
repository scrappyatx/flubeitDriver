/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.batchData.steps;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.driver.modelLayer.entities.orderStep.StepId;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/11/2017
 * Project : Driver
 */

public class FirebaseOrderStepGet implements ValueEventListener {
    private static final String TAG = "FirebaseOrderStepGet";
    private static final String BATCH_DATA_STEPS_NODE = "steps";

    private OrderStepInterface.TaskType taskType;
    private GetOrderStepResponse response;

    public void getOrderStep(DatabaseReference batchDataRef,
                             String batchGuid, String stepGuid, OrderStepInterface.TaskType taskType,
                             GetOrderStepResponse response){

        this.taskType = taskType;
        this.response = response;
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        batchDataRef.child(batchGuid).child(BATCH_DATA_STEPS_NODE).child(stepGuid).addListenerForSingleValueEvent(this);
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");
        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...step data FOUND!");
            try {
                OrderStepInterface orderStep;
                switch (taskType){
                    case NAVIGATION:
                        orderStep = dataSnapshot.getValue(ServiceOrderNavigationStep.class);
                        break;
                    case TAKE_PHOTOS:
                        orderStep = dataSnapshot.getValue(ServiceOrderPhotoStep.class);
                        break;
                    case WAIT_FOR_EXTERNAL_TRIGGER:
                        orderStep = dataSnapshot.getValue(ServiceOrderNavigationStep.class);
                        //TODO put in real class
                        break;
                    case WAIT_FOR_USER_TRIGGER:
                        orderStep = dataSnapshot.getValue(ServiceOrderNavigationStep.class);
                        //TODO put in real class
                        break;
                    case AUTHORIZE_PAYMENT:
                        orderStep = dataSnapshot.getValue(ServiceOrderNavigationStep.class);
                        //TODO put in real class
                        break;
                    case GIVE_ASSET:
                        orderStep = dataSnapshot.getValue(ServiceOrderNavigationStep.class);
                        //TODO put in real class
                        break;
                    case RECEIVE_ASSET:
                        orderStep = dataSnapshot.getValue(ServiceOrderNavigationStep.class);
                        //TODO put in real class
                        break;
                    case WAIT_FOR_SERVICE_ON_ASSET:
                        orderStep = dataSnapshot.getValue(ServiceOrderNavigationStep.class);
                        //TODO put in real class
                        break;
                    default:
                        orderStep = dataSnapshot.getValue(ServiceOrderNavigationStep.class);
                        //TODO put in real class
                        break;
                }

                Timber.tag(TAG).d("      orderStep : ");
                Timber.tag(TAG).d("         guid                : " + orderStep.getGuid());
                Timber.tag(TAG).d("         batchDetailGuid     : " + orderStep.getBatchDetailGuid());
                Timber.tag(TAG).d("         batchGuid           : " + orderStep.getBatchGuid());
                Timber.tag(TAG).d("         serviceOrderGuid    : " + orderStep.getServiceOrderGuid());
                Timber.tag(TAG).d("         taskType            : " + orderStep.getTaskType().toString());
                Timber.tag(TAG).d("         title               : " + orderStep.getTitle());
                Timber.tag(TAG).d("         getClass()          : " + orderStep.getClass());
                Timber.tag(TAG).d("       got step data!");
                response.getOrderStepSuccess(orderStep);
            } catch (Exception e) {
                Timber.tag(TAG).w("   ...error while trying to get step data!");
                Timber.tag(TAG).e(e);
                response.getOrderStepFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.getOrderStepFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError){
        Timber.tag(TAG).e("onCancelled -> error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.getOrderStepFailure();
    }

    public interface GetOrderStepResponse {
        void getOrderStepSuccess(OrderStepInterface orderStep);

        void getOrderStepFailure();
    }
}
