/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.filesToUpload;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.BuildFileToUploadInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.SetNodeFileInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToNotStarted;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.entities.SignatureRequest;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_UPLOAD_TASKS_PATH;

/**
 * Created on 5/26/2018
 * Project : Driver
 */
public class AddDeviceImageToUploadList implements
    CloudImageStorageFilenameGet.GetFileNameResponse,
    SetNodeFileInfo.Response,
        MoveImageUploadTaskToNotStarted.Response {

    public static final String TAG = "AddDeviceImageToUploadList";

    private DatabaseReference uploadTaskNodeRef;
    private FileToUploadInfo fileToUploadInfo;
    private CloudImageStorageInterface.AddImageToUploadListResponse response;



    //// different add method for each object that could have a device file to upload
    //// TODO should really abstract out the "upload file" properties in each of these objects and put in their own object

    public void addPhotoRequestToList(FirebaseDatabase driverDb, StorageReference storageRef,
                                      Driver driver, DeviceInfo deviceInfo, PhotoRequest photoRequest,  CloudImageStorageInterface.AddImageToUploadListResponse response){

        this.response = response;
        fileToUploadInfo = BuildFileToUploadInfo.getFileToUploadInfo(driver, deviceInfo, photoRequest);
        addToList(driverDb, storageRef, driver, deviceInfo, fileToUploadInfo);
    }

    public void addSignatureRequestToList(FirebaseDatabase driverDb, StorageReference storageRef,
                                          Driver driver, DeviceInfo deviceInfo, SignatureRequest signatureRequest, CloudImageStorageInterface.AddImageToUploadListResponse response){

        this.response = response;
        fileToUploadInfo = BuildFileToUploadInfo.getFileToUploadInfo(driver, deviceInfo, signatureRequest);
        addToList(driverDb, storageRef, driver, deviceInfo, fileToUploadInfo);
    }

    public void addReceiptRequestToList(FirebaseDatabase driverDb, StorageReference storageRef,
                                        Driver driver, DeviceInfo deviceInfo, ReceiptRequest receiptRequest, CloudImageStorageInterface.AddImageToUploadListResponse response){

        this.response = response;
        fileToUploadInfo = BuildFileToUploadInfo.getFileToUploadInfo(driver, deviceInfo, receiptRequest);
        addToList(driverDb, storageRef, driver, deviceInfo, fileToUploadInfo);
    }

    /// generic method to do all the heavy lifting

    private void addToList(FirebaseDatabase driverDb, StorageReference storageRef, Driver driver, DeviceInfo deviceInfo, FileToUploadInfo fileToUploadInfo) {

        String uploadTasksNode = String.format(RTD_UPLOAD_TASKS_PATH, driver.getClientId(), deviceInfo.getDeviceGUID(), fileToUploadInfo.getBatchGuid());
        uploadTaskNodeRef = driverDb.getReference(uploadTasksNode);

        Timber.tag(TAG).d("addToList START...");
        Timber.tag(TAG).d("   uploadTaskNodeRef             -> " + uploadTaskNodeRef.toString());
        Timber.tag(TAG).d("   storageRef                    -> " + storageRef.toString());


        Timber.tag(TAG).d("   getting cloud image storage filename...");
        new CloudImageStorageFilenameGet().getFileNameRequest(storageRef, fileToUploadInfo.getBatchGuid(), fileToUploadInfo.getServiceOrderGuid(),
                fileToUploadInfo.getOrderStepGuid(), fileToUploadInfo.getOwnerGuid(), fileToUploadInfo.getOwnerType(), this);
    }

    public void fileNameGetComplete(String cloudStorageFileName){
        Timber.tag(TAG).d("   fileNameGetComplete, cloudStorageFileName -> " + cloudStorageFileName);
        fileToUploadInfo.setCloudFileName(cloudStorageFileName);

        // save the fileToUploadInfo object
        new SetNodeFileInfo().addNodeRequest(uploadTaskNodeRef, fileToUploadInfo, this);
    }

    public void setNodeFileInfoComplete(){
        Timber.tag(TAG).d("      setNodeFileInfoComplete");
        /// now add owner guid to "not started"
        new MoveImageUploadTaskToNotStarted().moveImageUploadTaskToNotStarted(uploadTaskNodeRef, fileToUploadInfo.getOwnerGuid(), this );
    }

    public void moveImageUploadTaskToNotStartedComplete(){
        Timber.tag(TAG).d("      moveImageUploadTaskToNotStartedComplete");
        response.cloudImageStorageAddImageToUploadListComplete(fileToUploadInfo.getOwnerGuid());
        close();
    }

    private void close(){
        Timber.tag(TAG).d("close");
        response = null;
        uploadTaskNodeRef = null;
        fileToUploadInfo = null;

    }

}
