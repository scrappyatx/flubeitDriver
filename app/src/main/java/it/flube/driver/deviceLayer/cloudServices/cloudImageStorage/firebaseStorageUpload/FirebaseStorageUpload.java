/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.firebaseStorageUpload;

import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import timber.log.Timber;

/**
 * Created on 1/21/2019
 * Project : Driver
 */
public class FirebaseStorageUpload  {
    private static final String TAG = "FirebaseStorageUpload";

    private static final int MAX_ATTEMPTS = 5;

    public void startUploadRequest(DatabaseReference uploadTaskNodeRef, FileToUploadInfo fileToUploadInfo, Response response){
        Timber.tag(TAG).d("startUploadRequest");
        Timber.tag(TAG).d("   uploadTaskNodeRef -> " + uploadTaskNodeRef.toString());
        Timber.tag(TAG).d("   upload source     -> " + fileToUploadInfo.getDeviceAbsoluteFileName());
        Timber.tag(TAG).d("   upload dest       -> " + fileToUploadInfo.getCloudFileName());
        Timber.tag(TAG).d("   attempts          -> " + Integer.toString(fileToUploadInfo.getAttempts()));

        if (fileToUploadInfo.getAttempts() <= MAX_ATTEMPTS) {
            Timber.tag(TAG).d("...haven't exceed max attempts");
            try {

                StorageReference storageRef = FirebaseStorage.getInstance().getReference(fileToUploadInfo.getCloudFileName());
                Uri file = Uri.fromFile(new File(fileToUploadInfo.getDeviceAbsoluteFileName()));
                Timber.tag(TAG).d("   ...file Uri -> %s", file.toString());

                if (fileToUploadInfo.getSessionUriString() == null) {
                    // this is a new upload
                    Timber.tag(TAG).d("...new upload");
                    storageRef.putFile(file).addOnSuccessListener(new FirebaseStorageOnSuccessListener(uploadTaskNodeRef, fileToUploadInfo))
                            .addOnFailureListener(new FirebaseStorageOnFailureListener(uploadTaskNodeRef, fileToUploadInfo))
                            .addOnPausedListener(new FirebaseStorageOnPausedListener(uploadTaskNodeRef, fileToUploadInfo))
                            .addOnProgressListener(new FirebaseStorageOnProgressListener(uploadTaskNodeRef, fileToUploadInfo))
                            .addOnCanceledListener(new FirebaseStorageOnCanceledListener(uploadTaskNodeRef,fileToUploadInfo));

                } else {
                    // this is a continuing upload
                    Timber.tag(TAG).d("...resuming upload");
                    Timber.tag(TAG).d("...sessionUri = " + fileToUploadInfo.getSessionUriString());

                    storageRef.putFile(file, new StorageMetadata.Builder().build(), Uri.parse(fileToUploadInfo.getSessionUriString()))
                            .addOnSuccessListener(new FirebaseStorageOnSuccessListener(uploadTaskNodeRef, fileToUploadInfo))
                            .addOnFailureListener(new FirebaseStorageOnFailureListener(uploadTaskNodeRef, fileToUploadInfo))
                            .addOnPausedListener(new FirebaseStorageOnPausedListener(uploadTaskNodeRef, fileToUploadInfo))
                            .addOnProgressListener(new FirebaseStorageOnProgressListener(uploadTaskNodeRef, fileToUploadInfo))
                            .addOnCanceledListener(new FirebaseStorageOnCanceledListener(uploadTaskNodeRef,fileToUploadInfo));
                }

                Timber.tag(TAG).d("   ...started the upload!");
                response.startUploadSuccess();

            } catch (Exception e) {
                Timber.tag(TAG).d("   ...could not start upload, e = " + e.getLocalizedMessage());
                Timber.tag(TAG).e(e);
                response.startUploadFailure();
            }
        } else {
            Timber.tag(TAG).d("...exceeded max attempts");
            response.startUploadExceedMaxAttempts();
        }
    }

    public interface Response {
        void startUploadSuccess();

        void startUploadFailure();

        void startUploadExceedMaxAttempts();
    }


}
