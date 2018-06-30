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
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGenericStep;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_STEPS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.PHOTO_REQUEST_LIST_NODE;

/**
 * Created on 4/29/2018
 * Project : Driver
 */
public class FirebaseActiveBatchPhotoRequestGet implements ValueEventListener {
    private static final String TAG = "FirebaseActiveBatchPhotoRequestGet";

    private CloudActiveBatchInterface.GetActiveBatchPhotoRequestResponse response;

    public void getActiveBatchPhotoRequest(DatabaseReference batchDataRef, String batchGuid, String orderStepGuid, String photoRequestGuid,
                                           CloudActiveBatchInterface.GetActiveBatchPhotoRequestResponse response){

        this.response = response;
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        Timber.tag(TAG).d("getting photoRequest : photoRequestGuid -> " + photoRequestGuid + " batchGuid -> " + batchGuid + " orderStep guid : " + orderStepGuid);

        batchDataRef.child(batchGuid).child(BATCH_DATA_STEPS_NODE).child(orderStepGuid).child(PHOTO_REQUEST_LIST_NODE).child(photoRequestGuid)
                .addListenerForSingleValueEvent(this);
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");
        if (dataSnapshot.exists()) {

            Timber.tag(TAG).d("   ...dataSnapshot Exists!");

            try {
                PhotoRequest photoRequest = dataSnapshot.getValue(PhotoRequest.class);
                Timber.tag(TAG).d("      ...got photoRequest for photoRequest guid : " + photoRequest.getGuid());
                response.cloudGetActiveBatchPhotoRequestSuccess(photoRequest);
            } catch (Exception e) {
                Timber.tag(TAG).w("   ...error while trying to get photo request!");
                Timber.tag(TAG).e(e);
                response.cloudGetActiveBatchPhotoRequestFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.cloudGetActiveBatchPhotoRequestFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError){
        Timber.tag(TAG).e("onCancelled -> error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.cloudGetActiveBatchPhotoRequestFailure();
    }
}
