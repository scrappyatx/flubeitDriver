/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.graphics.Bitmap;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.entities.batch.BatchDetail;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public interface CloudImageStorageInterface {

    ///
    ///    Start monitoring for files to upload
    ///
    void startMonitoringForFilesToUploadRequest(Driver driver, DeviceInfo deviceInfo, String batchGuid,
                                                CloudActiveBatchInterface cloudActiveBatch, DeviceImageStorageInterface deviceImageStorage,
                                                StartMonitoringForFilesToUploadResponse response);

    interface StartMonitoringForFilesToUploadResponse {
        void cloudImageStorageStartMonitoringForFilesToUploadComplete();
    }

    ///     Stop monitoring for files to upload
    void stopMonitoringForFilesToUploadRequest(StopMonitoringForFilesToUploadResponse response);

    interface StopMonitoringForFilesToUploadResponse {
        void cloudImageStorageStopMonitoringForFilesToUploadComplete();
    }


    /// add a photo request to the upload list
    void addPhotoRequestToUploadList(Driver driver, DeviceInfo deviceInfo, PhotoRequest photoRequest,
                                     AddImageToUploadListResponse response);

    /// add a list of photo requests to the upload list
    void addPhotoRequestListToUploadList(Driver driver, DeviceInfo deviceInfo, ArrayList<PhotoRequest> photoList,
                                         AddPhotoRequestListToUploadListResponse response);

    /// add a signature request to the upload list
    void addSignatureRequestToUploadList(Driver driver, DeviceInfo deviceInfo, SignatureRequest signatureRequest,
                                         AddImageToUploadListResponse response);

    /// add a receipt request to the upload list
    void addReceiptRequestToUploadList(Driver driver, DeviceInfo deviceInfo, ReceiptRequest receiptRequest,
                                       AddImageToUploadListResponse response);

    interface AddImageToUploadListResponse {
        void cloudImageStorageAddImageToUploadListComplete(String ownerGuid);
    }

    interface AddPhotoRequestListToUploadListResponse {
        void cloudImageStorageAddPhotoRequestListToUploadListComplete();
    }


    ////
    ////    Wait for all pending uploads to finish
    ////
    void waitForAllPendingUploadsToFinish(Driver driver, DeviceInfo deviceInfo, String batchGuid, WaitForAllPendingUploadsToFinishResponse response);

    interface WaitForAllPendingUploadsToFinishResponse {
        void cloudImageStorageAllUploadsInProgress(Integer uploadsPending);
        void cloudImageStorageAllUploadsComplete();
    }

    ////
    ////   Wait for all pending deletes to finish
    ////
    void waitForAllPendingDeviceFilesToBeDeletedRequest(Driver driver, DeviceInfo deviceInfo, DeviceImageStorageInterface deviceImageStorage, WaitForAllPendingDeviceFileDeletesToFinishResponse response);

    interface WaitForAllPendingDeviceFileDeletesToFinishResponse {
        void cloudImageStorageDeviceFileDeletesInProgress(Integer deletesPending);
        void cloudImageStorageAllDeletesComplete();
    }









}
