/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import timber.log.Timber;

/**
 * Created on 1/25/2019
 * Project : Driver
 */
public class FirebaseImageStorageUploadResult implements
    FirebaseFileUpload.Response {
    private static final String TAG = "FirebaseImageStorageUploadResult";

    private DatabaseReference batchDataRef;
    private FileToUploadInfo fileToUploadInfo;
    private CloudActiveBatchInterface.CloudUploadResultResponse response;

    public void processUploadResult(DatabaseReference batchDataRef, FileToUploadInfo fileToUploadInfo, CloudActiveBatchInterface.CloudUploadResultResponse response){
        Timber.tag(TAG).d("processUploadResult");
        Timber.tag(TAG).d("   ownerType -> " + fileToUploadInfo.getOwnerType().toString());

        this.response = response;
        this.batchDataRef = batchDataRef;
        this.fileToUploadInfo = fileToUploadInfo;

        ///first, save the fileUpload info into batchDetail
        new FirebaseFileUpload().setFileUploadRequest(batchDataRef, fileToUploadInfo, this);
    }

    public void setFileUploadComplete(){
        Timber.tag(TAG).d("setFileUploadComplete");
        /// now save to the appropriate owner object
        switch(fileToUploadInfo.getOwnerType()){
            case PHOTO_REQUEST:
                new FirebasePhotoRequestImageUploadResult().updatePhotoRequestImageUploadResult(batchDataRef, fileToUploadInfo, response);
                break;
            case SIGNATURE_REQUEST:
                new FirebaseSignatureRequestImageUploadResult().updateSignatureRequestImageUploadResult(batchDataRef, fileToUploadInfo, response);
                break;
            case RECEIPT_REQUEST:
                new FirebaseReceiptRequestImageUploadResult().updateReceiptRequestImageUploadResult(batchDataRef, fileToUploadInfo, response);
                break;
            default:
                response.cloudActiveBatchCloudUploadComplete();
                break;
        }
    }
}
