/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_STEPS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.SIGNATURE_REQUEST_CLOUD_STORAGE_DOWNLOAD_URL;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.SIGNATURE_REQUEST_CLOUD_STORAGE_FILENAME_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.SIGNATURE_REQUEST_HAS_CLOUD_FILE_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.SIGNATURE_REQUEST_NODE;

/**
 * Created on 1/25/2019
 * Project : Driver
 */
public class FirebaseSignatureRequestImageUploadResult implements
        OnCompleteListener<Void> {

    public static final String TAG = "FirebaseSignatureRequestImageUploadResult";

    private CloudActiveBatchInterface.CloudUploadResultResponse response;

    public void updateSignatureRequestImageUploadResult(DatabaseReference batchDataNode, FileToUploadInfo fileToUploadInfo,
                                                      CloudActiveBatchInterface.CloudUploadResultResponse response) {

        Timber.tag(TAG).d("updateSignatureRequestImageUploadResult START...");
        this.response = response;

        Timber.tag(TAG).d("   batchDataNode        = " + batchDataNode.toString());
        Timber.tag(TAG).d("   batchGuid            = " + fileToUploadInfo.getBatchGuid());
        Timber.tag(TAG).d("   serviceOrderGuid     = " + fileToUploadInfo.getServiceOrderGuid());
        Timber.tag(TAG).d("   stepGuid             = " + fileToUploadInfo.getOrderStepGuid());
        Timber.tag(TAG).d("   signatureRequestGuid = " + fileToUploadInfo.getOwnerGuid());
        Timber.tag(TAG).d("   hasCloudFile         = " + fileToUploadInfo.getUploadSuccess());
        Timber.tag(TAG).d("   cloudFileName        = " + fileToUploadInfo.getCloudFileName());
        Timber.tag(TAG).d("   cloudDownloadUrl     = " + fileToUploadInfo.getCloudDownloadUrl());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(SIGNATURE_REQUEST_HAS_CLOUD_FILE_NODE, fileToUploadInfo.getUploadSuccess());
        data.put(SIGNATURE_REQUEST_CLOUD_STORAGE_FILENAME_NODE, fileToUploadInfo.getCloudFileName());
        data.put(SIGNATURE_REQUEST_CLOUD_STORAGE_DOWNLOAD_URL, fileToUploadInfo.getCloudDownloadUrl());

        batchDataNode.child(fileToUploadInfo.getBatchGuid()).child(BATCH_DATA_STEPS_NODE).child(fileToUploadInfo.getOrderStepGuid()).child(SIGNATURE_REQUEST_NODE).updateChildren(data).addOnCompleteListener(this);

    }


    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        Timber.tag(TAG).d("COMPLETE");
        response.cloudActiveBatchCloudUploadComplete();
    }
}