/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.batchDetail.FirebaseBatchDetailSetFileUpload;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.libbatchdata.builders.FileUploadBuilder;
import it.flube.libbatchdata.entities.FileUpload;
import timber.log.Timber;

/**
 * Created on 1/29/2019
 * Project : Driver
 */
public class FirebaseFileUpload implements
    FirebaseBatchDetailSetFileUpload.Response {
    private static final String TAG = "FirebaseFileUpload";

    private Response response;

    public void setFileUploadRequest(DatabaseReference batchDataRef, FileToUploadInfo fileToUploadInfo, Response response){
        Timber.tag(TAG).d("createFileUploadRequest");
        this.response = response;

        /// log the info
        Timber.tag(TAG).d("    batchGuid -> %s", fileToUploadInfo.getBatchGuid());
        Timber.tag(TAG).d("    serviceOrderGuid        -> %s", fileToUploadInfo.getServiceOrderGuid());
        Timber.tag(TAG).d("    stepGuid                -> %s",fileToUploadInfo.getOrderStepGuid());
        Timber.tag(TAG).d("    ownerGuid               -> %s", fileToUploadInfo.getOwnerGuid());
        Timber.tag(TAG).d("    ownerType               -> %s", fileToUploadInfo.getOwnerType().toString());
        Timber.tag(TAG).d("    cloudStorageFileName    -> %s", fileToUploadInfo.getCloudFileName());
        Timber.tag(TAG).d("    cloudStorageDownloadUrl -> %s", fileToUploadInfo.getCloudDownloadUrl());
        Timber.tag(TAG).d("    fileSizeBytes           -> %s", fileToUploadInfo.getFileSizeBytes());
        Timber.tag(TAG).d("    bytesTransferred        -> %s", fileToUploadInfo.getBytesTransferred());
        Timber.tag(TAG).d("    contentType             -> %s", fileToUploadInfo.getContentType());

        ///first, build the fileUpload object
        FileUpload fileUpload = new FileUploadBuilder.Builder()
                .batchGuid(fileToUploadInfo.getBatchGuid())
                .serviceOrderGuid(fileToUploadInfo.getServiceOrderGuid())
                .stepGuid(fileToUploadInfo.getOrderStepGuid())
                .ownerGuid(fileToUploadInfo.getOwnerGuid())
                .ownerType(fileToUploadInfo.getOwnerType())
                .cloudStorageFileName(fileToUploadInfo.getCloudFileName())
                .cloudStorageDownloadUrl(fileToUploadInfo.getCloudDownloadUrl())
                .fileSizeBytes(fileToUploadInfo.getFileSizeBytes())
                .bytesTransferred(fileToUploadInfo.getBytesTransferred())
                .contentType(fileToUploadInfo.getContentType())
                .build();

        //now add to batchDetail
        new FirebaseBatchDetailSetFileUpload().fileUploadSetRequest(batchDataRef,fileUpload, this);
    }

    public void fileUploadSetComplete(){
        Timber.tag(TAG).d("fileUploadSetComplete");
        response.setFileUploadComplete();
    }

    public interface Response {
        void setFileUploadComplete();
    }

}
