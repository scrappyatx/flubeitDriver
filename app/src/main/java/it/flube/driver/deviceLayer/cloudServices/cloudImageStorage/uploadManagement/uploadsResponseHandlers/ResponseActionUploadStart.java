/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.GetNodeFileInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.SetNodeFileInfoAttempts;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.filesToDelete.AddDeviceFileToFilesToDelete;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.firebaseStorageUpload.FirebaseStorageUpload;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToInProgress;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToPermanentlyFailed;
import timber.log.Timber;

/**
 * Created on 1/25/2019
 * Project : Driver
 */
public class ResponseActionUploadStart implements
        GetNodeFileInfo.Response,
        SetNodeFileInfoAttempts.Response,
        FirebaseStorageUpload.Response,
        AddDeviceFileToFilesToDelete.Response,
        MoveImageUploadTaskToInProgress.Response,
        MoveImageUploadTaskToPermanentlyFailed.Response {

    private static final String TAG = "ResponseActionUploadStart";

    private FirebaseDatabase driverDb;
    private Response response;
    private DatabaseReference uploadTaskNodeRef;
    private String ownerGuid;
    private FileToUploadInfo fileToUploadInfo;


    public void startUploadRequest(FirebaseDatabase driverDb, DatabaseReference uploadTaskNodeRef, String ownerGuid, Response response){
        Timber.tag(TAG).d("startUpload");
        this.driverDb = driverDb;
        this.response = response;
        this.uploadTaskNodeRef = uploadTaskNodeRef;
        this.ownerGuid = ownerGuid;


        Timber.tag(TAG).d("    uploadTaskNodeRef -> " + uploadTaskNodeRef.toString());
        Timber.tag(TAG).d("    ownerGuid         -> " + ownerGuid);

        /// steps
        /// 1. get the fileToUploadInfo
        /// 2. increment the attempt counter
        /// 3. add file to list of files to delete (failsafe for if user changes device)
        /// 3. start an upload task
        /// 4. move guid node to appropriate state

        /// get the fileToUploadInfo
        new GetNodeFileInfo().getNodeFileInfo(uploadTaskNodeRef, ownerGuid, this );
    }

    ///
    ///  Step 1 - get response from GetNodeFileInfo
    ///
    /// response interface for getNodeFileInfo
    public void getNodeFileInfoSuccess(FileToUploadInfo fileToUploadInfo){
        Timber.tag(TAG).d("getNodeFileInfoSuccess(%s)", ownerGuid);

        /// increment the attempt count
        fileToUploadInfo.setAttempts(fileToUploadInfo.getAttempts() + 1);
        this.fileToUploadInfo = fileToUploadInfo;

        // save the update to attempts
        new SetNodeFileInfoAttempts().updateNodeRequest(uploadTaskNodeRef, fileToUploadInfo, this);
    }

    public void getNodeFileInfoFailure(){
        Timber.tag(TAG).w("getNodeFileInfoFailure(%s)", ownerGuid);
        //this should never happen - there is no info for this ownerGuid
        // move this guid to permanently failed
        new MoveImageUploadTaskToPermanentlyFailed().moveImageUploadTaskToInPermanentlyFailed(uploadTaskNodeRef, ownerGuid, this);
    }

    ///
    ///  Step 2 - get response from updating the attempt count
    ///
    /// response interface for updating attempt count
    public void setNodeFileInfoAttemptsComplete(){
        Timber.tag(TAG).d("setNodeFileInfoAttemptsComplete (%s)", ownerGuid);
        /// now start a task to upload this file
        new AddDeviceFileToFilesToDelete().addDeviceFileToFilesToDelete(driverDb, fileToUploadInfo, this);
    }

    ////
    //// Step 3 - add file to list of files to delete (this is a failsafe for if user changes device in middle of order
    ////

    public void addDeviceFileToFilesToDeleteComplete(){
        Timber.tag(TAG).d("addDeviceFileToFilesToDeleteComplete (%s)", ownerGuid);
        new FirebaseStorageUpload().startUploadRequest(uploadTaskNodeRef, fileToUploadInfo, this);
    }

    ///
    ///  Step 4 - get response from starting the upload task
    ///     success -> move owner guid to "in progress" node
    ///     failure -> move owner guid to "permanently failed" node
    ///     exceed attempt count -> move owner guid to "permanently failed" node
    ///

    /// response interface for starting upload
    public void startUploadSuccess(){
        Timber.tag(TAG).d("startUploadSuccess(%s)", ownerGuid);
        /// now set node in progress
        new MoveImageUploadTaskToInProgress().moveImageUploadTaskToInProgress(uploadTaskNodeRef, ownerGuid, this);
    }


    public void startUploadFailure(){
        Timber.tag(TAG).d("startUploadFailure(%s)", ownerGuid);
        /// there was some failure in creating the upload task, so it is permanently failed
        new MoveImageUploadTaskToPermanentlyFailed().moveImageUploadTaskToInPermanentlyFailed(uploadTaskNodeRef, ownerGuid, this);
    }

    public void startUploadExceedMaxAttempts(){
        Timber.tag(TAG).d("startUploadExceedMaxAttempts(%s)", ownerGuid);
        /// we exceeded max attempts, so it is permanently failed
        new MoveImageUploadTaskToPermanentlyFailed().moveImageUploadTaskToInPermanentlyFailed(uploadTaskNodeRef, ownerGuid, this);
    }

    ///
    /// Step 5A -> response moving to "in progress"
    ///


    //// response interface for moving to in progress
    public void moveImageUploadTaskToInProgressComplete(){
        Timber.tag(TAG).d("moveImageUploadTaskToInProgressComplete (%s)", ownerGuid);
        response.startUploadComplete(ownerGuid);
    }

    ////
    ///     Step 5B -> response to moving to "permanently failed
    ///

    /// response interface for moving to permanently failed
    public void moveImageUploadTaskToPermanentlyFailedComplete(){
        Timber.tag(TAG).d("moveImageUploadtaskToPermanentlyFailed (%s)", ownerGuid);
        response.startUploadComplete(ownerGuid);
    }


    public interface Response {
        void startUploadComplete(String ownerGuid);
    }

}
