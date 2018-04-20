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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 2/20/2018
 * Project : Driver
 */

public class CloudImageStorageSaveImageStart implements
        OnProgressListener<UploadTask.TaskSnapshot>,
        OnPausedListener<UploadTask.TaskSnapshot>,
        OnSuccessListener<UploadTask.TaskSnapshot>,
        OnFailureListener {

    private static final String TAG = "CloudImageStorageSaveImageStart";

    private String deviceStorageAbsoluteFileName;
    private String cloudStorageFileName;
    private CloudImageStorageInterface.SaveStartResponse response;

    public CloudImageStorageSaveImageStart() {
        Timber.tag(TAG).d("created...");
    }

    public void saveImageStartRequest(FirebaseStorage storage, String deviceStorageAbsoluteFileName, String cloudStorageFileName,
                                      CloudImageStorageInterface.SaveStartResponse response){

        Timber.tag(TAG).d("saveImageStartRequest START...");

        this.cloudStorageFileName = cloudStorageFileName;
        this.deviceStorageAbsoluteFileName = deviceStorageAbsoluteFileName;
        this.response = response;

        Timber.tag(TAG).d("   ...storage                       -> " + storage.toString());
        Timber.tag(TAG).d("   ...deviceStorageAbsoluteFileName -> " + deviceStorageAbsoluteFileName);
        Timber.tag(TAG).d("   ...cloudStorageFileName          -> " + cloudStorageFileName);

        //create storage ref
        StorageReference storageRef = storage.getReference().child(cloudStorageFileName);
        Timber.tag(TAG).d("   ...got cloud storage reference");

        //create file instance based on device storage file name
        Uri file = Uri.fromFile(new File(deviceStorageAbsoluteFileName));
        Timber.tag(TAG).d("   ...got device image file");

        //create upload task and add listeners
        UploadTask uploadTask = storageRef.putFile(file);
        Timber.tag(TAG).d("   ...created upload task");

        uploadTask.addOnFailureListener(this);
        uploadTask.addOnSuccessListener(this);
        uploadTask.addOnProgressListener(this);
        uploadTask.addOnPausedListener(this);
        Timber.tag(TAG).d("   ...added listeners to upload task");

        Timber.tag(TAG).d("...saveImageStartRequest COMPLETE");
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

            response.cloudImageStorageSavePaused(sessionUriString, cloudStorageFileName, progress);
        } catch (Exception e){
            Timber.tag(TAG).w("   ...ERROR updating progress");
            response.cloudImageStorageSaveFailure();
        }
    }

    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
        Timber.tag(TAG).d("...onSuccess");
        Timber.tag(TAG).d("   ...deviceStorageAbsoluteFileName -> " + deviceStorageAbsoluteFileName);

        try {
            String downloadUrl = taskSnapshot.getDownloadUrl().toString();
            Timber.tag(TAG).d("   ...downloadUrl -> " + downloadUrl);
            response.cloudImageStorageSaveSuccess(downloadUrl);
        } catch (Exception e) {
            Timber.tag(TAG).w(" ...ERROR getting downloadUrl");
            Timber.tag(TAG).e(e);
            response.cloudImageStorageSaveFailure();
        }
    }

    public void onFailure(@NonNull Exception exception){
        Timber.tag(TAG).d("...onFailure");
        Timber.tag(TAG).d("   ...deviceStorageAbsoluteFileName -> " + deviceStorageAbsoluteFileName);
        Timber.tag(TAG).e(exception);
        response.cloudImageStorageSaveFailure();
    }

}
