/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage;

import android.os.Handler;
import android.os.Looper;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;


import java.util.ArrayList;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.CloudImageStorageMasterUploadMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.filesToUpload.AddDeviceImageToUploadList;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.filesToUpload.AddPhotoRequestListToUploadList;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.FirebaseStorageWaitForAllUploadsToComplete;
import it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseDbInitialization;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_UPLOAD_TASKS_NOT_STARTED_LISTENER_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_UPLOAD_TASKS_PATH;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.STOR_MOBILE_UPLOAD_PATH;

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

    //// listeners for device files we need to upload
    private CloudImageStorageMasterUploadMonitor uploadMonitor;


    public CloudImageStorageFirebaseWrapper(TargetEnvironmentConstants.TargetEnvironment targetEnvironment, CloudConfigInterface cloudConfig){
        Timber.tag(TAG).d("created");
        driverDb = FirebaseDbInitialization.getFirebaseDriverDb(targetEnvironment);

        baseNodeBatchData = cloudConfig.getCloudDatabaseBaseNodeBatchData();
        Timber.tag(TAG).d("   baseNodeBatchData = " + baseNodeBatchData);

        baseNodeActiveBatch = cloudConfig.getCloudDatabaseBaseNodeActiveBatch();
        Timber.tag(TAG).d("   baseNodeActiveBatch = " + baseNodeActiveBatch);

        uploadMonitor = new CloudImageStorageMasterUploadMonitor();
    }

    private void getNodes(Driver driver, DeviceInfo deviceInfo){
        batchDataNode = baseNodeBatchData + "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getBatchDataNode();
        Timber.tag(TAG).d("   ...batchDataNode = " + batchDataNode);

        activeBatchNode = baseNodeActiveBatch+ "/" + driver.getClientId() + "/" + driver.getCloudDatabaseSettings().getActiveBatchNode();
        Timber.tag(TAG).d("   ...activeBatchNode = " + activeBatchNode);

    }

    ////
    ////  Start monitoring for files to upload
    ////
    public void startMonitoringForFilesToUploadRequest(Driver driver, DeviceInfo deviceInfo, String batchGuid,
                                                CloudActiveBatchInterface cloudActiveBatch, DeviceImageStorageInterface deviceImageStorage,
                                                StartMonitoringForFilesToUploadResponse response){
        Timber.tag(TAG).d("startMonitoringForFilesToUploadRequest");

        /// node to listen to is based on driver, deviceInfo, & batch Guid
        String uploadTasksNotStartedListeningNode = String.format(RTD_UPLOAD_TASKS_NOT_STARTED_LISTENER_NODE, driver.getClientId(), deviceInfo.getDeviceGUID(), batchGuid);
        String uploadTasksNode = String.format(RTD_UPLOAD_TASKS_PATH, driver.getClientId(), deviceInfo.getDeviceGUID(), batchGuid);
        Timber.tag(TAG).d("   uploadTasksNotStartedListeningNode -> " + uploadTasksNotStartedListeningNode);
        Timber.tag(TAG).d("   uploadTasksNode                    -> " + uploadTasksNode);

        uploadMonitor.startListening(FirebaseDatabase.getInstance(driverDb), driver, driver.getClientId(), deviceInfo.getDeviceGUID(), batchGuid, cloudActiveBatch, deviceImageStorage);
        response.cloudImageStorageStartMonitoringForFilesToUploadComplete();
    }

    ///
    ///  Stop monitoring for files to upload
    ///
    public void stopMonitoringForFilesToUploadRequest(StopMonitoringForFilesToUploadResponse response){
        Timber.tag(TAG).d("stopMonitoringForFilesToUploadRequest");
        uploadMonitor.stopListening();
        response.cloudImageStorageStopMonitoringForFilesToUploadComplete();
    }


    ////
    ////    Add file to upload list
    ////
    /// add a photo request to the upload list
    public void addPhotoRequestToUploadList(Driver driver, DeviceInfo deviceInfo, PhotoRequest photoRequest,
                                            AddImageToUploadListResponse response){
        Timber.tag(TAG).d("addPhotoRequestToUploadList");

        new AddDeviceImageToUploadList().addPhotoRequestToList(FirebaseDatabase.getInstance(driverDb),FirebaseStorage.getInstance().getReference(STOR_MOBILE_UPLOAD_PATH),
                driver, deviceInfo, photoRequest, response);

    }

    public void addPhotoRequestListToUploadList(Driver driver, DeviceInfo deviceInfo, ArrayList<PhotoRequest> photoList,
                                         AddPhotoRequestListToUploadListResponse response){
        Timber.tag(TAG).d("addPhotoRequestListToUploadList");
        new AddPhotoRequestListToUploadList().addPhotoRequestListToUploadList(FirebaseDatabase.getInstance(driverDb),FirebaseStorage.getInstance().getReference(STOR_MOBILE_UPLOAD_PATH),
                driver, deviceInfo, photoList, response);
    }

    /// add a signature request to the upload list
    public void addSignatureRequestToUploadList(Driver driver, DeviceInfo deviceInfo, SignatureRequest signatureRequest,
                                         AddImageToUploadListResponse response){
        Timber.tag(TAG).d("addSignatureRequestToUploadList");
        new AddDeviceImageToUploadList().addSignatureRequestToList(FirebaseDatabase.getInstance(driverDb),FirebaseStorage.getInstance().getReference(STOR_MOBILE_UPLOAD_PATH),
                driver, deviceInfo, signatureRequest, response);

    }

    /// add a receipt request to the upload list
    public void addReceiptRequestToUploadList(Driver driver, DeviceInfo deviceInfo, ReceiptRequest receiptRequest,
                                       AddImageToUploadListResponse response){
        Timber.tag(TAG).d("addReceiptRequestToUploadList");
        new AddDeviceImageToUploadList().addReceiptRequestToList(FirebaseDatabase.getInstance(driverDb),FirebaseStorage.getInstance().getReference(STOR_MOBILE_UPLOAD_PATH),
                driver, deviceInfo, receiptRequest, response);
    }

    ////
    ////  Wait for all pending uploads to complete
    ///
    public void waitForAllPendingUploadsToFinish(Driver driver, DeviceInfo deviceInfo, String batchGuid, WaitForAllPendingUploadsToFinishResponse response){
        Timber.tag(TAG).d("waitForAllImageFilesForActiveBatchToFinishUploading START...");
        new FirebaseStorageWaitForAllUploadsToComplete().waitForAllUploadsToComplete(response);
        //getNodes(driver, deviceInfo);
        // for right now, just wait 10 seconds and return

        //response.cloudImageStorageAllUploadsInProgress(4);

        //// this is just dummy code to wait ten seconds and return an empty list - there are no files to delete
        //Integer timeoutMsec = 2000;
        //Handler handler = new Handler(Looper.getMainLooper());
        //handler.postDelayed(new Runnable(){
        //    public void run () {
                //actions to do after timeout
        //        Timber.tag(TAG).d("   ...timeout expired");
        //        response.cloudImageStorageAllUploadsComplete();
        //    }
        //}, timeoutMsec);
    }

    public void waitForAllPendingDeviceFilesToBeDeletedRequest(Driver driver, DeviceInfo deviceInfo, DeviceImageStorageInterface deviceImageStorage, WaitForAllPendingDeviceFileDeletesToFinishResponse response){
        //TODO need to implement this feature. it really should be a corner case, only needs to be done if driver switches devices AND take has images on 2 or more devices during an active batch
        response.cloudImageStorageDeviceFileDeletesInProgress(2);
        response.cloudImageStorageAllDeletesComplete();
    }


}
