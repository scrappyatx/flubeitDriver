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
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsFinishedMonitor.FirebaseUploadsFinishedMonitor;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 1/23/2019
 * Project : Driver
 */
public class FirebaseUploadsFinishedResponseHandler implements
    FirebaseUploadsFinishedMonitor.Update,
    ResponseActionFinish.Response {
    private static final String TAG = "FirebaseUploadsFinishedResponseHandler";

    private CloudActiveBatchInterface cloudActiveBatch;
    private DeviceImageStorageInterface deviceImageStorage;
    private FirebaseDatabase driverDb;
    private DatabaseReference uploadTaskNodeRef;
    private Driver driver;

    public FirebaseUploadsFinishedResponseHandler(FirebaseDatabase driverDb, DatabaseReference uploadTaskNodeRef, Driver driver, CloudActiveBatchInterface cloudActiveBatch, DeviceImageStorageInterface deviceImageStorage){
        Timber.tag(TAG).d("FirebaseUploadsFinishedResponseHandler");
        this.driverDb = driverDb;
        this.driver = driver;
        this.uploadTaskNodeRef = uploadTaskNodeRef;
        this.cloudActiveBatch = cloudActiveBatch;
        this.deviceImageStorage = deviceImageStorage;

        Timber.tag(TAG).d("   uploadTaskNodeRef    = " + uploadTaskNodeRef.toString());
    }

    //// interface from FirebaseUploadsFinishedMonitor.Update
    public void finishedUploadImageFile(String ownerGuid){
        Timber.tag(TAG).d("finishedUploadImageFile, ownerGuid -> %s", ownerGuid);

        //// so we need to do these things:
        //// 1. get the FileToUploadInfo node
        //// 2. update the active batch data with the file upload info
        //// 3. delete the device file
        //// 4. remove the file entry from the "filesToDelete" node
        //// 5. delete fileToUploadInfo node & all task nodes
       new ResponseActionFinish().finishRequest(driverDb, uploadTaskNodeRef, driver, cloudActiveBatch, deviceImageStorage, ownerGuid, this);
    }

    public void finishComplete(String ownerGuid){
        Timber.tag(TAG).d("finishComplete (%s)", ownerGuid);
    }

}
