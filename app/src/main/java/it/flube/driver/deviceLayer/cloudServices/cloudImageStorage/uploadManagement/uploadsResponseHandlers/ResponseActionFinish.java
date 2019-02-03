/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.GetNodeFileInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.filesToDelete.RemoveDeviceFileFromFilesToDelete;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.RemoveImageUploadTask;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 1/25/2019
 * Project : Driver
 */
public class ResponseActionFinish implements
        GetNodeFileInfo.Response,
        CloudActiveBatchInterface.CloudUploadResultResponse,
        DeviceImageStorageInterface.DeleteResponse,
        RemoveDeviceFileFromFilesToDelete.Response,
        RemoveImageUploadTask.Response{

    private static final String TAG = "ResponseActionFinish";

    private Response response;
    private DatabaseReference uploadTaskNodeRef;
    private String ownerGuid;
    private FileToUploadInfo fileToUploadInfo;
    private Driver driver;
    private CloudActiveBatchInterface cloudActiveBatch;
    private DeviceImageStorageInterface deviceImageStorage;
    private FirebaseDatabase driverDb;


    public void finishRequest(FirebaseDatabase driverDb, DatabaseReference uploadTaskNodeRef,
                              Driver driver, CloudActiveBatchInterface cloudActiveBatch, DeviceImageStorageInterface deviceImageStorage,
                              String ownerGuid, Response response){

        Timber.tag(TAG).d("finishRequest");
        this.response = response;
        this.uploadTaskNodeRef = uploadTaskNodeRef;
        this.ownerGuid = ownerGuid;
        this.driver = driver;
        this.cloudActiveBatch = cloudActiveBatch;
        this.deviceImageStorage = deviceImageStorage;
        this.driverDb = driverDb;


        Timber.tag(TAG).d("    uploadTaskNodeRef -> " + uploadTaskNodeRef.toString());
        Timber.tag(TAG).d("    ownerGuid         -> " + ownerGuid);

        //// so we need to do these things:
        //// 1. get the FileToUploadInfo node
        //// 2. update the active batch data with the file upload info
        //// 3. delete the device file
        //// 4. remove the file entry from the "filesToDelete" node
        //// 5. delete fileToUploadInfo node & all task nodes

        /// get the fileToUploadInfo
        new GetNodeFileInfo().getNodeFileInfo(uploadTaskNodeRef, ownerGuid, this );
    }

    //// interface from GetNodeFileInfo.Response
    public void getNodeFileInfoSuccess(FileToUploadInfo fileToUploadInfo){
        Timber.tag(TAG).d("getNodeFileInfoSuccess (%s)", ownerGuid);
        this.fileToUploadInfo = fileToUploadInfo;
        ///now we need to update the active batch
        cloudActiveBatch.updateCloudImageStorageUploadResult(driver, fileToUploadInfo, this);
    }

    public void getNodeFileInfoFailure(){
        Timber.tag(TAG).w("getNodeFileInfoFailure, this should never happen (%s)", ownerGuid);
        //// this shouldn't happen
        response.finishComplete(ownerGuid);
    }

    /// interface from CloudActiveBatch.CloudUploadResultResponse
    public void cloudActiveBatchCloudUploadComplete(){
        Timber.tag(TAG).d("cloudActiveBatchCloudUploadComplete (%s)", ownerGuid);
        /// now we need to delete the device file
        Timber.tag(TAG).d("   deleting device file -> %s", fileToUploadInfo.getDeviceAbsoluteFileName());
        deviceImageStorage.deleteImageRequest(fileToUploadInfo.getDeviceAbsoluteFileName(), this);
    }

    /// interface from DeviceImageStorage.DeleteResponse
    public void deviceImageStorageDeleteComplete(){
        Timber.tag(TAG).d("deviceImageStorageDeleteComplete (%s)", ownerGuid);
        /// now remove the entry from the files to delete node
        new RemoveDeviceFileFromFilesToDelete().removeDeviceFileFromFilesToDelete(driverDb, fileToUploadInfo, this);
    }

    /// interface from RemoveDeviceFileFromFilesToDelete
    public void removeDeviceFileFromFilesToDeleteComplete(){
        Timber.tag(TAG).d("removeDeviceFileFromFilesToDeleteComplete (%s)", ownerGuid);
        //// now remove task status node
        new RemoveImageUploadTask().removeUploadTask(uploadTaskNodeRef, fileToUploadInfo.getOwnerGuid(), this);
    }

    /// interface from RemoveImageUploadTask
    public void removeImageUploadTaskComplete(){
        Timber.tag(TAG).d("removeImageUploadTaskComplete (%s)", ownerGuid);
        /// and we're done
        response.finishComplete(ownerGuid);
    }

    public interface Response {
        void finishComplete(String ownerGuid);
    }

}
