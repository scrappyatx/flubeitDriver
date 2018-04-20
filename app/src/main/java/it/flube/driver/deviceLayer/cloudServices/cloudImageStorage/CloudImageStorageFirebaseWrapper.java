/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.AddPhotoUploadTaskNotStarted;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.MovePhotoUploadTaskToFailed;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.MovePhotoUploadTaskToFinished;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.MovePhotoUploadTaskToInProgress;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 9/16/2017
 * Project : Driver
 */

public class CloudImageStorageFirebaseWrapper implements
        CloudImageStorageInterface {

    private static final String TAG = "CloudImageStorageFirebaseWrapper";

    private Driver driver;
    private String batchDataNode;


    public CloudImageStorageFirebaseWrapper(){
        Timber.tag(TAG).d("created");
    }

    ///
    ///     Initialize for a given user
    ///
    public void initialize(CloudConfigInterface cloudConfig, Driver driver){
        Timber.tag(TAG).d("initialize...");

        batchDataNode = cloudConfig.getCloudDatabaseBaseNodeBatchData() + "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getBatchDataNode();
        Timber.tag(TAG).d("   ...batchDataNode = " + batchDataNode);

        this.driver = driver;
        Timber.tag(TAG).d("   ...driver clientId = " + driver.getClientId());
    }

    ///
    ///  These methods use firebase STORAGE
    ///
    public void getCloudStorageFileName(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid, GetFileNameResponse response){
        Timber.tag(TAG).d("getCloudStorageFileName...");
        new CloudImageStorageFilenameGet().getFileNameRequest(FirebaseStorage.getInstance(), batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, response);
    }

    public void saveImageStartRequest(String deviceStorageAbsoluteFileName, String cloudStorageFileName, SaveStartResponse response){

        Timber.tag(TAG).d("saveImageStartRequest...");
        new CloudImageStorageSaveImageStart().saveImageStartRequest(FirebaseStorage.getInstance(), deviceStorageAbsoluteFileName, cloudStorageFileName, response);
    }

    public void saveImageResumeRequest(String sessionUriString, String deviceStorageAbsoluteFileName, String cloudStorageFileName, SaveResumeResponse response){

        Timber.tag(TAG).d("saveImageResumeRequest...");
        new CloudImageStorageSaveImageResume().saveImageResumeRequest(FirebaseStorage.getInstance(), sessionUriString, deviceStorageAbsoluteFileName, cloudStorageFileName, response);
    }

    ///
    ///    These methods use firebase Realtime Database
    ///

    public void addPhotoUploadTaskToNotStartedRequest(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                                      String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                                      AddPhotoUploadTaskResponse response){

        Timber.tag(TAG).d("addPhotoUploadTaskToNotStartedRequest");
        new AddPhotoUploadTaskNotStarted().addPhotoUploadTaskToNotStartedRequest(FirebaseDatabase.getInstance().getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName, response);



    }

    public void movePhotoUploadTaskToInProgress(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                                String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                                String sessionUriString, Double progress,
                                                MovePhotoUploadTaskInProgressResponse response){

        Timber.tag(TAG).d("movePhotoUploadTaskToInProgress");
        new MovePhotoUploadTaskToInProgress().movePhotoUploadTaskToInProgress(FirebaseDatabase.getInstance().getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName,
                sessionUriString, progress, response);

    }

    public void movePhotoUploadTaskToFinished(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                              String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                              MovePhotoUploadTaskFinishedResponse response){

        Timber.tag(TAG).d("movePhotoUploadTaskToFinished");
        new MovePhotoUploadTaskToFinished().movePhotoUploadTaskToFinished(FirebaseDatabase.getInstance().getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName, response);

    }

    public void movePhotoUploadTaskToFailed(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                            String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                            MovePhotoUploadTaskFailedResponse response){

        Timber.tag(TAG).d("movePhotoUploadTaskToFailed");
        new MovePhotoUploadTaskToFailed().movePhotoUploadTaskToFailed(FirebaseDatabase.getInstance().getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName, response);

    }


}
