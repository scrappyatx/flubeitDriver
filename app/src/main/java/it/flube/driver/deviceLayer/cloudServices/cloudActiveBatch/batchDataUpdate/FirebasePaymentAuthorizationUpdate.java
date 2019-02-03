/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.PaymentAuthorization;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_ASSET_LIST_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_STEPS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.PAYMENT_AUTHORIZATION_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.PAYMENT_VERIFICATION_STATUS_NODE;

/**
 * Created on 9/10/2018
 * Project : Driver
 */
public class FirebasePaymentAuthorizationUpdate implements OnCompleteListener<Void> {
    private static final String TAG="FirebasePaymentAuthorizationUpdate";

    private CloudActiveBatchInterface.PaymentAuthorizationUpdateResponse response;

    public void updatePaymentAuthorizationRequest(DatabaseReference batchDataNode, PaymentAuthorization paymentAuthorization,
                                                  CloudActiveBatchInterface.PaymentAuthorizationUpdateResponse response){

        Timber.tag(TAG).d("updatePaymentAuthorizationRequest START...");

        this.response = response;
        Timber.tag(TAG).d("   batchDataNode    = " + batchDataNode.toString());
        Timber.tag(TAG).d("   batchGuid        = " + paymentAuthorization.getBatchGuid());
        Timber.tag(TAG).d("   serviceOrderGuid = " + paymentAuthorization.getServiceOrderGuid());
        Timber.tag(TAG).d("   stepGuid         = " + paymentAuthorization.getStepGuid());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(PAYMENT_VERIFICATION_STATUS_NODE, paymentAuthorization.getPaymentVerificationStatus().toString());

        batchDataNode.child(paymentAuthorization.getBatchGuid()).child(BATCH_DATA_STEPS_NODE).child(paymentAuthorization.getStepGuid()).child(PAYMENT_AUTHORIZATION_NODE).updateChildren(data).addOnCompleteListener(this);

        this.response = response;
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        Timber.tag(TAG).d("COMPLETE");
        response.cloudActiveBatchUpdatePaymentAuthorizationComplete();
        close();
    }

    private void close(){
        Timber.tag(TAG).d("close");
        response = null;
    }
}
