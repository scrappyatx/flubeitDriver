/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.PaymentAuthorization;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_STEPS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.PAYMENT_AUTHORIZATION_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.PAYMENT_AUTHORIZATION_SERVICE_PROVIDER_TRANSACTION_ID_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.PAYMENT_AUTHORIZATION_SERVICE_PROVIDER_TRANSACTION_ID_SOURCE_TYPE_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.PAYMENT_AUTHORIZATION_SERVICE_PROVIDER_TRANSACTION_ID_STATUS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.PAYMENT_VERIFICATION_STATUS_NODE;

/**
 * Created on 2/21/2019
 * Project : Driver
 */
public class FirebaseServiceProviderTransactionIdUpdate implements OnCompleteListener<Void> {
    private static final String TAG="FirebaseServiceProviderTransactionIdUpdate";

    private CloudActiveBatchInterface.ServiceProviderTransactionIdUpdateResponse response;

    public void updateServiceProviderTransactionIdRequest(DatabaseReference batchDataNode, ServiceOrderAuthorizePaymentStep orderStep, CloudActiveBatchInterface.ServiceProviderTransactionIdUpdateResponse response){
        Timber.tag(TAG).d("updateServiceProviderTransactionIdRequest START...");
        this.response = response;

        Timber.tag(TAG).d("   batchDataNode    = " + batchDataNode.toString());
        Timber.tag(TAG).d("   batchGuid        = " + orderStep.getBatchGuid());
        Timber.tag(TAG).d("   serviceOrderGuid = " + orderStep.getServiceOrderGuid());
        Timber.tag(TAG).d("   stepGuid         = " + orderStep.getGuid());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(PAYMENT_AUTHORIZATION_SERVICE_PROVIDER_TRANSACTION_ID_NODE, orderStep.getServiceProviderTransactionId());
        data.put(PAYMENT_AUTHORIZATION_SERVICE_PROVIDER_TRANSACTION_ID_SOURCE_TYPE_NODE, orderStep.getTransactionIdSourceType().toString());
        data.put(PAYMENT_AUTHORIZATION_SERVICE_PROVIDER_TRANSACTION_ID_STATUS_NODE, orderStep.getTransactionIdStatus().toString());

        batchDataNode.child(orderStep.getBatchGuid()).child(BATCH_DATA_STEPS_NODE).child(orderStep.getGuid()).updateChildren(data).addOnCompleteListener(this);
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
        response.cloudActiveBatchUpdateServiceProviderTransactionIdComplete();
        close();
    }

    private void close(){
        Timber.tag(TAG).d("close");
        response = null;
    }
}
