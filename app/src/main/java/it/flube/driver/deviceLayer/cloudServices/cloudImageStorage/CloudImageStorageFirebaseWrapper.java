/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage;

import android.os.Handler;
import android.os.Looper;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadList.AddDeviceImageToUploadList;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadList.AddPhotoUploadTaskNotStarted;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.MovePhotoUploadTaskToFailed;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.MovePhotoUploadTaskToFinished;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.MovePhotoUploadTaskToInProgress;
import it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseDbInitialization;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.MOBILE_UPLOAD_PATH;

/**
 * Created on 9/16/2017
 * Project : Driver
 */

public class CloudImageStorageFirebaseWrapper implements
        CloudImageStorageInterface {

    private static final String TAG = "CloudImageStorageFirebaseWrapper";
    
    private final String driverDb;
    private final String baseNodeBatchData;
    private final String baseNodeActiveBatch;

    private String activeBatchNode;
    private String batchDataNode;


    public CloudImageStorageFirebaseWrapper(TargetEnvironmentConstants.TargetEnvironment targetEnvironment, CloudConfigInterface cloudConfig){
        Timber.tag(TAG).d("created");
        driverDb = FirebaseDbInitialization.getFirebaseDriverDb(targetEnvironment);

        baseNodeBatchData = cloudConfig.getCloudDatabaseBaseNodeBatchData();
        Timber.tag(TAG).d("   baseNodeBatchData = " + baseNodeBatchData);

        baseNodeActiveBatch = cloudConfig.getCloudDatabaseBaseNodeActiveBatch();
        Timber.tag(TAG).d("   baseNodeActiveBatch = " + baseNodeActiveBatch);
    }

    private void getNodes(Driver driver){
        batchDataNode = baseNodeBatchData + "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getBatchDataNode();
        Timber.tag(TAG).d("   ...batchDataNode = " + batchDataNode);

        activeBatchNode = baseNodeActiveBatch+ "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getActiveBatchNode();
        Timber.tag(TAG).d("   ...activeBatchNode = " + activeBatchNode);
    }


    public void addDeviceImageToActiveBatchUploadList(Driver driver, DeviceInfo deviceInfo, String deviceStorageAbsoluteFileName, String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid, AddDeviceImageResponse response){
        Timber.tag(TAG).d("addDeviceImageToActiveBatchUploadList START...");

        getNodes(driver);
        new AddDeviceImageToUploadList().addToList(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode), FirebaseStorage.getInstance().getReference(MOBILE_UPLOAD_PATH),
                                                    batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid,
                                                    deviceStorageAbsoluteFileName, deviceInfo.getDeviceGUID(), response);
    }

    public void startOrResumeUploadingImagesForActiveBatch(Driver driver, DeviceInfo deviceInfo, String batchGuid, StartOrResumeResponse response){
        Timber.tag(TAG).d("startOrResumeUploadingImagesForActiveBatch START...");

        getNodes(driver);
        // for right now, do nothing just return complete...will fill in later
        response.cloudImageStorageStartOrResumeComplete();
    }

    public void waitForAllImageFilesForActiveBatchToFinishUploading(Driver driver, DeviceInfo deviceInfo, String batchGuid, WaitForUploadToFinishResponse response){
        Timber.tag(TAG).d("waitForAllImageFilesForActiveBatchToFinishUploading START...");

        getNodes(driver);
        // for right now, just wait 10 seconds and return
        Integer timeoutMsec = 10000;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable(){
            public void run () {
                //actions to do after timeout
                Timber.tag(TAG).d("   ...timeout expired");
                response.cloudImageStorageAllUploadsComplete();
            }
        }, timeoutMsec);
    }

    ///
    ///  These methods use firebase STORAGE
    ///

    public void saveImageStartRequest(String deviceStorageAbsoluteFileName, String cloudStorageFileName, CloudImageStorageSaveImageStart.SaveImageResponse response){

        Timber.tag(TAG).d("saveImageStartRequest...");
        new CloudImageStorageSaveImageStart().saveImageStartRequest(FirebaseStorage.getInstance(), deviceStorageAbsoluteFileName, cloudStorageFileName, response);
    }

    public void saveImageResumeRequest(String sessionUriString, String deviceStorageAbsoluteFileName, String cloudStorageFileName, CloudImageStorageSaveImageResume.SaveImageResumeResponse response){

        Timber.tag(TAG).d("saveImageResumeRequest...");
        new CloudImageStorageSaveImageResume().saveImageResumeRequest(FirebaseStorage.getInstance(), sessionUriString, deviceStorageAbsoluteFileName, cloudStorageFileName, response);
    }

    ///
    ///    These methods use firebase Realtime Database
    ///

    public void addPhotoUploadTaskToNotStartedRequest(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                                      String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                                      AddPhotoUploadTaskNotStarted.AddPhotoUploadTaskNotStartedResponse response){

        Timber.tag(TAG).d("addPhotoUploadTaskToNotStartedRequest");
        new AddPhotoUploadTaskNotStarted().addPhotoUploadTaskToNotStartedRequest(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName, response);



    }

    public void movePhotoUploadTaskToInProgress(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                                String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                                String sessionUriString, Double progress,
                                                MovePhotoUploadTaskToInProgress.MovePhotoUploadTaskToInProgressResponse response){

        Timber.tag(TAG).d("movePhotoUploadTaskToInProgress");
        new MovePhotoUploadTaskToInProgress().movePhotoUploadTaskToInProgress(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName,
                sessionUriString, progress, response);

    }

    public void movePhotoUploadTaskToFinished(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                              String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                              MovePhotoUploadTaskToFinished.MovePhotoUploadTaskFinishedResponse response){

        Timber.tag(TAG).d("movePhotoUploadTaskToFinished");
        new MovePhotoUploadTaskToFinished().movePhotoUploadTaskToFinished(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName, response);

    }

    public void movePhotoUploadTaskToFailed(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                            String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                            MovePhotoUploadTaskToFailed.MovePhotoUploadTaskToFailedResponse response){

        Timber.tag(TAG).d("movePhotoUploadTaskToFailed");
        new MovePhotoUploadTaskToFailed().movePhotoUploadTaskToFailed(FirebaseDatabase.getInstance(driverDb).getReference(batchDataNode),
                batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName, response);

    }


}
