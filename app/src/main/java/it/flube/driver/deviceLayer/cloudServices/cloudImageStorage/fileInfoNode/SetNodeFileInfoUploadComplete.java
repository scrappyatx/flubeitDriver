/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FILE_INFO_BYTES_TRANSFERRED_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FILE_INFO_CLOUD_DOWNLOAD_URL_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FILE_INFO_CONTENT_TYPE_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FILE_INFO_FILE_SIZE_BYTES_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FILE_INFO_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FILE_INFO_UPLOAD_SUCCESS_NODE;

/**
 * Created on 1/24/2019
 * Project : Driver
 */
public class SetNodeFileInfoUploadComplete implements
        OnCompleteListener<Void> {

    private static final String TAG = "SetNodeFileInfoUploadComplete";

    private Response response;

    public void updateNodeRequest(DatabaseReference uploadTaskNodeRef, FileToUploadInfo fileToUploadInfo, Response response) {
        this.response = response;

        Timber.tag(TAG).d("   ...uploadTaskNodeRef = " + uploadTaskNodeRef.toString());
        Timber.tag(TAG).d("   ...ownerGuid         = " + fileToUploadInfo.getOwnerGuid());

        Timber.tag(TAG).d("   ...cloudDownloadUrl  = " + fileToUploadInfo.getCloudDownloadUrl());
        Timber.tag(TAG).d("   ...uploadSuccess     = " + fileToUploadInfo.getUploadSuccess().toString());
        Timber.tag(TAG).d("   ...bytesTransferred  = " + fileToUploadInfo.getBytesTransferred());
        Timber.tag(TAG).d("   ...fileSizeBytes     = " + fileToUploadInfo.getFileSizeBytes());


        HashMap<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(RTD_FILE_INFO_CLOUD_DOWNLOAD_URL_NODE, fileToUploadInfo.getCloudDownloadUrl());
        childUpdates.put(RTD_FILE_INFO_UPLOAD_SUCCESS_NODE, fileToUploadInfo.getUploadSuccess());
        childUpdates.put(RTD_FILE_INFO_BYTES_TRANSFERRED_NODE, fileToUploadInfo.getBytesTransferred());
        childUpdates.put(RTD_FILE_INFO_FILE_SIZE_BYTES_NODE, fileToUploadInfo.getFileSizeBytes());
        childUpdates.put(RTD_FILE_INFO_CONTENT_TYPE_NODE, fileToUploadInfo.getContentType());

        uploadTaskNodeRef.child(RTD_FILE_INFO_NODE).child(fileToUploadInfo.getOwnerGuid()).updateChildren(childUpdates).addOnCompleteListener(this);

    }

    //// OnCompleteListener interface
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
        response.setNodeFileInfoUploadComplete();
    }

    public interface Response {
        void setNodeFileInfoUploadComplete();
    }
}