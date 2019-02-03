/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.filesToUpload;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.libbatchdata.interfaces.FileUploadInterface;
import timber.log.Timber;

/**
 * Created on 2/22/2018
 * Project : Driver
 */

public class CloudImageStorageFilenameGet {
    private static final String TAG = "CloudImageStorageFilenameGet";

    private static final String PHOTO_FILE_NAME = "photo.jpg";
    private static final String RECEIPT_FILE_NAME = "receipt.jpg";
    private static final String SIGNATURE_FILE_NAME = "signature.jpg";
    private static final String UNKNOWN_FILE_NAME = "unknown.jpg";

    public CloudImageStorageFilenameGet(){
        Timber.tag(TAG).d("created...");
    }

    public void getFileNameRequest(StorageReference storageRef,
                                   String batchGuid, String serviceOrderGuid, String orderStepGuid, String ownerGuid, FileUploadInterface.OwnerType ownerType,
                                   GetFileNameResponse response){

        Timber.tag(TAG).d("getFileNameRequest START...");
        Timber.tag(TAG).d("   ...storageRef                    -> " + storageRef.toString());
        Timber.tag(TAG).d("   ...batchGuid                     -> " + batchGuid);
        Timber.tag(TAG).d("   ...serviceOrderGuid              -> " + serviceOrderGuid);
        Timber.tag(TAG).d("   ...orderStepGuid                 -> " + orderStepGuid);
        Timber.tag(TAG).d("   ...ownerGuid                     -> " + ownerGuid);
        Timber.tag(TAG).d("   ...ownerType                     -> " + ownerType.toString());

        /// determine name of file, based on owner type
        String cloudFileName;
        switch (ownerType){
            case PHOTO_REQUEST:
                cloudFileName = PHOTO_FILE_NAME;
                break;
            case SIGNATURE_REQUEST:
                cloudFileName = SIGNATURE_FILE_NAME;
                break;
            case RECEIPT_REQUEST:
                cloudFileName = RECEIPT_FILE_NAME;
                break;
            default:
                cloudFileName = UNKNOWN_FILE_NAME;
                break;
        }
        Timber.tag(TAG).d("     ...cloudFileName               -> " + cloudFileName);

        //determine cloud storage file name
        // format is /mobileAppDriver/imageUploads/<batchGuid>/<serviceOrderGuid>/<ownerGuid>/<file_name>
        StorageReference fileRef = storageRef.child(batchGuid).child(serviceOrderGuid).child(orderStepGuid).child(ownerGuid).child(cloudFileName);
        String cloudStorageFileName = fileRef.getPath();

        Timber.tag(TAG).d("   ...cloudStorageFileName          -> " + cloudStorageFileName);
        response.fileNameGetComplete(cloudStorageFileName);

        Timber.tag(TAG).d("...getFileNameRequest COMPLETE");
    }

    public interface GetFileNameResponse {
        void fileNameGetComplete(String cloudStorageFileName);
    }
}
