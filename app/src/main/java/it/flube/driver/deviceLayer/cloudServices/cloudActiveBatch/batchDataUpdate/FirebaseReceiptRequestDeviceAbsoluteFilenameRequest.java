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
import it.flube.libbatchdata.entities.ReceiptRequest;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_STEPS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.RECEIPT_REQUEST_CLOUD_OCR_RESULTS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.RECEIPT_REQUEST_DEVICE_ABSOLUTE_FILENAME_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.RECEIPT_REQUEST_DEVICE_OCR_RESULTS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.RECEIPT_REQUEST_HAS_DEVICE_FILE_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.RECEIPT_REQUEST_HAS_TEXT_MAP_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.RECEIPT_REQUEST_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.RECEIPT_REQUEST_STATUS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.RECEIPT_REQUEST_TEXT_MAP_NODE;

/**
 * Created on 9/10/2018
 * Project : Driver
 */
public class FirebaseReceiptRequestDeviceAbsoluteFilenameRequest
        implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseReceiptRequestDeviceAbsoluteFilenameRequest";

    private CloudActiveBatchInterface.ReceiptRequestDeviceAbsoluteFileNameResponse response;

    public void updateReceiptRequestDeviceFilenameRequest(DatabaseReference batchDataNode, ReceiptRequest receiptRequest, CloudActiveBatchInterface.ReceiptRequestDeviceAbsoluteFileNameResponse response){
        Timber.tag(TAG).d("batchDataNode = " + batchDataNode.toString());
        this.response = response;

        HashMap<String, Object> data = new HashMap<String, Object>();

        data.put(RECEIPT_REQUEST_HAS_DEVICE_FILE_NODE, receiptRequest.getHasDeviceFile());
        data.put(RECEIPT_REQUEST_DEVICE_ABSOLUTE_FILENAME_NODE, receiptRequest.getDeviceAbsoluteFileName());

        if (receiptRequest.getHasDeviceFile()){
            data.put(RECEIPT_REQUEST_STATUS_NODE, ReceiptRequest.ReceiptStatus.COMPLETED_SUCCESS);
        } else {
            data.put(RECEIPT_REQUEST_STATUS_NODE, ReceiptRequest.ReceiptStatus.COMPLETED_FAILED);
        }

        batchDataNode.child(receiptRequest.getBatchGuid()).child(BATCH_DATA_STEPS_NODE).child(receiptRequest.getStepGuid())
                .child(RECEIPT_REQUEST_NODE)
                .updateChildren(data).addOnCompleteListener(this);
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
        response.cloudActiveBatchUpdateReceiptRequestDeviceAbsoluteFilenameComplete();
    }

}
