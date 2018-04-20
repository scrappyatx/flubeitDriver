/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 2/20/2018
 * Project : Driver
 */

public class CloudImageStorageSaveImageResume implements
    OnProgressListener<UploadTask.TaskSnapshot>,
    OnPausedListener<UploadTask.TaskSnapshot>,
    OnSuccessListener<UploadTask.TaskSnapshot>,
    OnFailureListener {

        private static final String TAG = "CloudImageStorageSaveImageResume";

        private FirebaseStorage storage;
        private String sessionUriString;
        private String deviceStorageAbsoluteFileName;
        private String cloudStorageFileName;
        private CloudImageStorageInterface.SaveResumeResponse response;

    public CloudImageStorageSaveImageResume() {
        Timber.tag(TAG).d("created...");
    }

    public void saveImageResumeRequest(FirebaseStorage storage,
                                       String sessionUriString, String deviceStorageAbsoluteFileName, String cloudStorageFileName,
                                       CloudImageStorageInterface.SaveResumeResponse response){

        Timber.tag(TAG).d("saveImageResumeRequest START...");

        this.storage = storage;
        this.sessionUriString = sessionUriString;
        this.deviceStorageAbsoluteFileName = deviceStorageAbsoluteFileName;
        this.cloudStorageFileName = cloudStorageFileName;
        this.response = response;

        Timber.tag(TAG).d("   ...storage                       -> " + storage.toString());
        Timber.tag(TAG).d("   ...sessionUriString              -> " + sessionUriString);
        Timber.tag(TAG).d("   ...cloudStorageFileName          -> " + cloudStorageFileName);

        Uri file = Uri.fromFile(new File(deviceStorageAbsoluteFileName));
        Timber.tag(TAG).d("   ...file uri created");

        //create sessionUri from sessionUriString
        Uri sessionUri = Uri.parse(sessionUriString);
        Timber.tag(TAG).d("   ...session uri created");

        StorageReference storageRef = storage.getReference().child(cloudStorageFileName);
        Timber.tag(TAG).d("   ...storage reference created");

        UploadTask uploadTask = storageRef.putFile(file, new StorageMetadata.Builder().build(), sessionUri);
        Timber.tag(TAG).d("   ...upload task created");

        uploadTask.addOnFailureListener(this);
        uploadTask.addOnSuccessListener(this);
        uploadTask.addOnProgressListener(this);
        uploadTask.addOnPausedListener(this);
        Timber.tag(TAG).d("   ...listeners added");

        Timber.tag(TAG).d("...saveImageResumeRequest COMPLETE");
    }

    public void onProgress(UploadTask.TaskSnapshot taskSnapshot){
        Timber.tag(TAG).d("...onProgress");
        Timber.tag(TAG).d("   ...deviceStorageAbsoluteFileName -> " + deviceStorageAbsoluteFileName);

        try {
            String sessionUriString = taskSnapshot.getUploadSessionUri().toString();
            Double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

            Timber.tag(TAG).d("   ...progress = " + progress.toString());
            Timber.tag(TAG).d("   ...sessionUriString = " + sessionUriString);
        } catch (Exception e){
            Timber.tag(TAG).w("   ...ERROR updating progress");
        }
    }

    public void onPaused(UploadTask.TaskSnapshot taskSnapshot){
        Timber.tag(TAG).d("...onPaused");
        Timber.tag(TAG).d("   ...deviceStorageAbsoluteFileName -> " + deviceStorageAbsoluteFileName);

        try {
            String sessionUriString = taskSnapshot.getUploadSessionUri().toString();
            Double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

            Timber.tag(TAG).d("   ...progress = " + progress.toString());
            Timber.tag(TAG).d("   ...sessionUriString = " + sessionUriString);

            response.cloudImageStorageResumePaused(sessionUriString, cloudStorageFileName, progress);

        } catch (Exception e){
            Timber.tag(TAG).w("   ...ERROR updating progress");
            response.cloudImageStorageResumeFailure();
        }
    }

    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
        Timber.tag(TAG).d("...onSuccess");
        Timber.tag(TAG).d("   ...deviceStorageAbsoluteFileName -> " + deviceStorageAbsoluteFileName);

        try {
            String downloadUrl = taskSnapshot.getDownloadUrl().toString();
            Timber.tag(TAG).d("   ...downloadUrl -> " + downloadUrl);
            response.cloudImageStorageResumeSuccess(downloadUrl);
        } catch (Exception e) {
            Timber.tag(TAG).w(" ...ERROR getting downloadUrl");
            Timber.tag(TAG).e(e);
            response.cloudImageStorageResumeFailure();
        }
    }

    public void onFailure(@NonNull Exception exception){
        Timber.tag(TAG).d("...onFailure");
        Timber.tag(TAG).d("   ...deviceStorageAbsoluteFileName -> " + deviceStorageAbsoluteFileName);
        Timber.tag(TAG).e(exception);
        response.cloudImageStorageResumeFailure();
    }
}
