/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.firebaseStorageUpload;


import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.SetNodeFileInfoUploadComplete;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToFinished;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToPermanentlyFailed;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToSuccess;
import timber.log.Timber;

/**
 * Created on 1/21/2019
 * Project : Driver
 */
public class FirebaseStorageOnSuccessListener implements
        OnSuccessListener<UploadTask.TaskSnapshot>,
        OnCompleteListener<Uri>,
        SetNodeFileInfoUploadComplete.Response,
        MoveImageUploadTaskToPermanentlyFailed.Response,
        MoveImageUploadTaskToSuccess.Response {

    public static final String TAG = "FirebaseStorageOnSuccessListener";

    private DatabaseReference uploadTaskNodeRef;
    private FileToUploadInfo fileToUploadInfo;

    public FirebaseStorageOnSuccessListener(DatabaseReference uploadTaskNodeRef, FileToUploadInfo fileToUploadInfo){
        this.fileToUploadInfo = fileToUploadInfo;
        this.uploadTaskNodeRef = uploadTaskNodeRef;
    }

    ///  when we have a successful upload, do these things:
    ///   1. get download url
    ///   2. update FileInfoToUpload node with upload info (uploadSuccess flag and cloudDownloadUrl)
    ///   3. move the ownerGuid to the "finished" node
    ///   4. if anything goes wrong, move the ownerGuid to the "permanentlyFailed" node
    ///
    ///
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot){
        Timber.tag(TAG).d("onSuccess -> ownerGuid (%s)", fileToUploadInfo.getOwnerGuid());
        try {

            fileToUploadInfo.setBytesTransferred(taskSnapshot.getBytesTransferred());
            Timber.tag(TAG).d("   bytes transferred -> " +taskSnapshot.getBytesTransferred());

            if (taskSnapshot.getMetadata() != null) {
                fileToUploadInfo.setFileSizeBytes(taskSnapshot.getMetadata().getSizeBytes());
                fileToUploadInfo.setContentType(taskSnapshot.getMetadata().getContentType());

                Timber.tag(TAG).d("   file size bytes = " + taskSnapshot.getMetadata().getSizeBytes());
                Timber.tag(TAG).d("   contentType     = " + taskSnapshot.getMetadata().getContentType());

                if (taskSnapshot.getMetadata().getReference() != null) {
                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(this);
                } else {
                    Timber.tag(TAG).w("taskSnapshot.getMetadata().getReferene() is NULL, downloadUrl");
                    new MoveImageUploadTaskToPermanentlyFailed().moveImageUploadTaskToInPermanentlyFailed(uploadTaskNodeRef, fileToUploadInfo.getOwnerGuid(), this);
                }
            } else {
                Timber.tag(TAG).w("taskSnapshot.getMetadata() is NULL, can't get size, contentType or downloadUrl");
                new MoveImageUploadTaskToPermanentlyFailed().moveImageUploadTaskToInPermanentlyFailed(uploadTaskNodeRef, fileToUploadInfo.getOwnerGuid(), this);
            }
        } catch (Exception e){
            Timber.tag(TAG).w("...couldn't get metaData of taskSnapshot -> " + e.getLocalizedMessage());
            Timber.tag(TAG).e(e);
            new MoveImageUploadTaskToPermanentlyFailed().moveImageUploadTaskToInPermanentlyFailed(uploadTaskNodeRef, fileToUploadInfo.getOwnerGuid(), this);
        }
    }

    public void onComplete(@NonNull Task<Uri> task){
        Timber.tag(TAG).d("onComplete");

        if (task.isSuccessful()){
            Timber.tag(TAG).d("   ...getDownloadUrl was successful!");
            try {
                Timber.tag(TAG).d("downloadUrl -> " + task.getResult().toString());

                fileToUploadInfo.setCloudDownloadUrl(task.getResult().toString());
                fileToUploadInfo.setUploadSuccess(true);
                new SetNodeFileInfoUploadComplete().updateNodeRequest(uploadTaskNodeRef, fileToUploadInfo, this);

            } catch (Exception e){
                Timber.tag(TAG).w("...getDownloadUrl was not successful -> " + e.getLocalizedMessage());
                Timber.tag(TAG).e(e);
                new MoveImageUploadTaskToPermanentlyFailed().moveImageUploadTaskToInPermanentlyFailed(uploadTaskNodeRef, fileToUploadInfo.getOwnerGuid(), this);
            }
        } else {
            Timber.tag(TAG).w("...couldn't get cloudDownloadUrl");
            try {
                throw task.getException();
            } catch (Exception e) {
                Timber.tag(TAG).d("exception -> " + e.getLocalizedMessage());
            }

            fileToUploadInfo.setCloudDownloadUrl(null);
            fileToUploadInfo.setUploadSuccess(true);
            new SetNodeFileInfoUploadComplete().updateNodeRequest(uploadTaskNodeRef, fileToUploadInfo, this);
        }
    }

    //// interface from SetNodeFileInfoUploadComplete
    public void setNodeFileInfoUploadComplete(){
        Timber.tag(TAG).d("setNodeFileInfoUploadComplete, now moving node guid to success");
        /// after we've updated the file, move node guid to SUCCESS
        new MoveImageUploadTaskToSuccess().moveImageUploadTaskToSuccess(uploadTaskNodeRef, fileToUploadInfo.getOwnerGuid(), this);
    }

    //// we will either move the node guid to PERMANENTLY FAILED or SUCCESS
    public void moveImageUploadTaskToPermanentlyFailedComplete(){
        Timber.tag(TAG).d("moveImageUploadTaskToPermanentlyFailedComplete");
    }

    public void moveImageUploadTaskToSuccessComplete(){
        Timber.tag(TAG).d("moveImageUploadTaskToSuccessComplete");
    }
}
