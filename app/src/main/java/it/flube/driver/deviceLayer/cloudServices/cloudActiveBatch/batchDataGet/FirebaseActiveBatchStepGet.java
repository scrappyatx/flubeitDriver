/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor.ActiveBatchNode;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.steps.FirebaseOrderStepGet;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGenericStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderUserTriggerStep;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_STEPS_NODE;

/**
 * Created on 5/6/2018
 * Project : Driver
 */
public class FirebaseActiveBatchStepGet implements
        ValueEventListener {

    private static final String TAG = "FirebaseActiveBatchStepGet";

    private CloudActiveBatchInterface.GetActiveBatchStepResponse response;

    public void getOrderStepRequest(DatabaseReference batchDataRef, String batchGuid, String stepGuid, CloudActiveBatchInterface.GetActiveBatchStepResponse response){
        this.response = response;
        Timber.tag(TAG).d("batchDataRef   = " + batchDataRef.toString());
        batchDataRef.child(batchGuid).child(BATCH_DATA_STEPS_NODE).child(stepGuid).addListenerForSingleValueEvent(this);
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");

        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...dataSnapshot exists!");
            try {
                //// get order step here
                OrderStepInterface genericStep = dataSnapshot.getValue(ServiceOrderGenericStep.class);

                Timber.tag(TAG).d("      stepId : ");
                Timber.tag(TAG).d("         guid                : " + genericStep.getGuid());
                Timber.tag(TAG).d("         serviceOrderGuid    : " + genericStep.getServiceOrderGuid());
                Timber.tag(TAG).d("         taskType    : " + genericStep.getTaskType().toString());
                Timber.tag(TAG).d("         sequence    : " + genericStep.getSequence().toString());

                OrderStepInterface orderStep;
                switch (genericStep.getTaskType()){
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
                        orderStep = dataSnapshot.getValue(ServiceOrderUserTriggerStep.class);
                        break;
                    case AUTHORIZE_PAYMENT:
                        orderStep = dataSnapshot.getValue(ServiceOrderAuthorizePaymentStep.class);
                        break;
                    case GIVE_ASSET:
                        orderStep = dataSnapshot.getValue(ServiceOrderGiveAssetStep.class);
                        break;
                    case RECEIVE_ASSET:
                        orderStep = dataSnapshot.getValue(ServiceOrderReceiveAssetStep.class);
                        break;
                    default:
                        orderStep = dataSnapshot.getValue(ServiceOrderNavigationStep.class);
                        //TODO put in real class
                        break;
                }
                response.cloudGetActiveBatchStepSuccess(orderStep);


            } catch (Exception e) {
                Timber.tag(TAG).w("      ...error while trying to get active batch node data");
                Timber.tag(TAG).e(e);
                response.cloudGetActiveBatchStepFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.cloudGetActiveBatchStepFailure();
        }

    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error = " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.cloudGetActiveBatchStepFailure();
    }

}
