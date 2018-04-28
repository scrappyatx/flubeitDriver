/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.steps;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGenericStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderUserTriggerStep;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/11/2017
 * Project : Driver
 */

public class FirebaseOrderStepGet implements ValueEventListener {
    private static final String TAG = "FirebaseOrderStepGet";
    private static final String BATCH_DATA_STEPS_NODE = "steps";
    private static final String SERVICE_ORDER_GUID = "serviceOrderGuid";

    private Integer sequence;
    private GetOrderStepResponse response;

    public void getOrderStep(DatabaseReference batchDataRef,
                             String batchGuid, String serviceOrderGuid, Integer sequence,
                             GetOrderStepResponse response){

        this.sequence = sequence;
        this.response = response;
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        //batchDataRef.child(batchGuid).child(BATCH_DATA_STEPS_NODE).child(stepGuid).addListenerForSingleValueEvent(this);
        batchDataRef.child(batchGuid).child(BATCH_DATA_STEPS_NODE).orderByChild(SERVICE_ORDER_GUID).equalTo(serviceOrderGuid).addListenerForSingleValueEvent(this);
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");
        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...step data FOUND!");
            try {
                //loop through all steps for this service order until we find the one that matches sequence
                Boolean matchFound = false;
                for (DataSnapshot stepData : dataSnapshot.getChildren()) {
                    OrderStepInterface genericStep = stepData.getValue(ServiceOrderGenericStep.class);

                    Timber.tag(TAG).d("      stepId : ");
                    Timber.tag(TAG).d("         guid                : " + genericStep.getGuid());
                    Timber.tag(TAG).d("         serviceOrderGuid    : " + genericStep.getServiceOrderGuid());
                    Timber.tag(TAG).d("         taskType    : " + genericStep.getTaskType().toString());
                    Timber.tag(TAG).d("         sequence    : " + genericStep.getSequence().toString());

                    if (genericStep.getSequence().equals(sequence)) {
                        Timber.tag(TAG).d(" ^^^^^^ MATCH FOUND ^^^^^^");
                        matchFound = true;

                        OrderStepInterface orderStep;
                        switch (genericStep.getTaskType()){
                            case NAVIGATION:
                                orderStep = stepData.getValue(ServiceOrderNavigationStep.class);
                                break;
                            case TAKE_PHOTOS:
                                orderStep = stepData.getValue(ServiceOrderPhotoStep.class);
                                break;
                            case WAIT_FOR_EXTERNAL_TRIGGER:
                                orderStep = stepData.getValue(ServiceOrderNavigationStep.class);
                                //TODO put in real class
                                break;
                            case WAIT_FOR_USER_TRIGGER:
                                orderStep = stepData.getValue(ServiceOrderUserTriggerStep.class);
                                break;
                            case AUTHORIZE_PAYMENT:
                                orderStep = stepData.getValue(ServiceOrderAuthorizePaymentStep.class);
                                break;
                            case GIVE_ASSET:
                                orderStep = stepData.getValue(ServiceOrderGiveAssetStep.class);
                                break;
                            case RECEIVE_ASSET:
                                orderStep = stepData.getValue(ServiceOrderReceiveAssetStep.class);
                                break;
                            default:
                                orderStep = stepData.getValue(ServiceOrderNavigationStep.class);
                                //TODO put in real class
                                break;
                        }

                        Timber.tag(TAG).d("      finalStep : ");
                        Timber.tag(TAG).d("         guid                : " + orderStep.getGuid());
                        Timber.tag(TAG).d("         batchDetailGuid     : " + orderStep.getBatchDetailGuid());
                        Timber.tag(TAG).d("         batchGuid           : " + orderStep.getBatchGuid());
                        Timber.tag(TAG).d("         serviceOrderGuid    : " + orderStep.getServiceOrderGuid());
                        Timber.tag(TAG).d("         taskType            : " + orderStep.getTaskType().toString());
                        Timber.tag(TAG).d("         title               : " + orderStep.getTitle());
                        Timber.tag(TAG).d("         getClass()          : " + orderStep.getClass());
                        Timber.tag(TAG).d("       got step data!");
                        response.getOrderStepSuccess(orderStep);

                        break;
                    }
                }

                if (!matchFound) {
                    Timber.tag(TAG).d("      ...no match found, returning failure");
                    response.getOrderStepFailure();
                }


            } catch (Exception e) {
                Timber.tag(TAG).w("   ...error while trying to get step data!");
                Timber.tag(TAG).e(e);
                response.getOrderStepFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
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
