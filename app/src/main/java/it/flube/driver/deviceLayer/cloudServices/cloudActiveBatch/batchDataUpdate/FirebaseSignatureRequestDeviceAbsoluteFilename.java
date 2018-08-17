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
import it.flube.libbatchdata.entities.SignatureRequest;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_STEPS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.SIGNATURE_REQUEST_ATTEMPT_COUNT_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.SIGNATURE_REQUEST_DEVICE_ABSOLUTE_FILENAME_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.SIGNATURE_REQUEST_HAS_DEVICE_FILE_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.SIGNATURE_REQUEST_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.SIGNATURE_REQUEST_STATUS_NODE;

/**
 * Created on 8/5/2018
 * Project : Driver
 */
public class FirebaseSignatureRequestDeviceAbsoluteFilename implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseSignatureRequestDeviceAbsoluteFilename";

    private CloudActiveBatchInterface.SignatureRequestDeviceAbsoluteFileNameResponse response;

    public void updateSignatureRequestDeviceAbsoluteFilenameRequest(DatabaseReference batchDataNode, SignatureRequest signatureRequest, String absoluteFileName, Boolean hasFile,
                                                                    CloudActiveBatchInterface.SignatureRequestDeviceAbsoluteFileNameResponse response){

        Timber.tag(TAG).d("batchDataNode = " + batchDataNode.toString());
        this.response = response;

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(SIGNATURE_REQUEST_HAS_DEVICE_FILE_NODE, hasFile);
        data.put(SIGNATURE_REQUEST_DEVICE_ABSOLUTE_FILENAME_NODE, absoluteFileName);
        data.put(SIGNATURE_REQUEST_ATTEMPT_COUNT_NODE, signatureRequest.getAttemptCount()+1);

        if (hasFile) {
            data.put(SIGNATURE_REQUEST_STATUS_NODE, SignatureRequest.SignatureStatus.COMPLETED_SUCCESS);
        } else {
            data.put(SIGNATURE_REQUEST_STATUS_NODE, SignatureRequest.SignatureStatus.COMPLETED_FAILED);
        }

        batchDataNode.child(signatureRequest.getBatchGuid()).child(BATCH_DATA_STEPS_NODE).child(signatureRequest.getStepGuid())
                .child(SIGNATURE_REQUEST_NODE)
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
        response.cloudActiveBatchUpdateSignatureRequestDeviceAbsoluteFilenameComplete();
    }

}
