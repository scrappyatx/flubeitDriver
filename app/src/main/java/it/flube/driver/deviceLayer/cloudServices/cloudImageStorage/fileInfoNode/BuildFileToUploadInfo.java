/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.interfaces.FileUploadInterface;

/**
 * Created on 1/23/2019
 * Project : Driver
 */
public class BuildFileToUploadInfo {
    public static final String TAG = "BuildFileToUploadInfo";

    public static FileToUploadInfo getFileToUploadInfo(Driver driver, DeviceInfo deviceInfo, SignatureRequest signatureRequest){
        FileToUploadInfo info = new FileToUploadInfo();

        /// owner info
        info.setClientId(driver.getClientId());
        info.setBatchGuid(signatureRequest.getBatchGuid());
        info.setServiceOrderGuid(signatureRequest.getServiceOrderGuid());
        info.setOrderStepGuid(signatureRequest.getStepGuid());
        info.setOwnerGuid(signatureRequest.getGuid());
        info.setOwnerType(FileUploadInterface.OwnerType.SIGNATURE_REQUEST);

        // device file info
        info.setDeviceGuid(deviceInfo.getDeviceGUID());
        info.setDeviceAbsoluteFileName(signatureRequest.getDeviceAbsoluteFileName());

        // initialize upload info
        info.setAttempts(0);
        info.setCloudFileName(null);
        info.setCloudDownloadUrl(null);
        info.setProgress(null);
        info.setSessionUriString(null);
        info.setUploadSuccess(false);
        info.setBytesTransferred(0l);
        info.setFileSizeBytes(0l);
        info.setContentType(null);

        // initialize deletionKey
        info.setDeletionKey(BuilderUtilities.generateGuid());

        return info;
    }

    public static FileToUploadInfo getFileToUploadInfo(Driver driver, DeviceInfo deviceInfo, PhotoRequest photoRequest){
        FileToUploadInfo info = new FileToUploadInfo();

        /// owner info
        info.setClientId(driver.getClientId());
        info.setBatchGuid(photoRequest.getBatchGuid());
        info.setServiceOrderGuid(photoRequest.getServiceOrderGuid());
        info.setOrderStepGuid(photoRequest.getStepGuid());
        info.setOwnerGuid(photoRequest.getGuid());
        info.setOwnerType(FileUploadInterface.OwnerType.PHOTO_REQUEST);

        // device file info
        info.setDeviceGuid(deviceInfo.getDeviceGUID());
        info.setDeviceAbsoluteFileName(photoRequest.getDeviceAbsoluteFileName());

        // initialize upload info
        info.setAttempts(0);
        info.setCloudFileName(null);
        info.setCloudDownloadUrl(null);
        info.setProgress(null);
        info.setSessionUriString(null);
        info.setUploadSuccess(false);
        info.setBytesTransferred(0l);
        info.setFileSizeBytes(0l);
        info.setContentType(null);

        // initialize deletionKey
        info.setDeletionKey(BuilderUtilities.generateGuid());

        return info;
    }

    public static FileToUploadInfo getFileToUploadInfo(Driver driver, DeviceInfo deviceInfo, ReceiptRequest receiptRequest){
        FileToUploadInfo info = new FileToUploadInfo();

        /// owner info
        info.setClientId(driver.getClientId());
        info.setBatchGuid(receiptRequest.getBatchGuid());
        info.setServiceOrderGuid(receiptRequest.getServiceOrderGuid());
        info.setOrderStepGuid(receiptRequest.getStepGuid());
        info.setOwnerGuid(receiptRequest.getGuid());
        info.setOwnerType(FileUploadInterface.OwnerType.RECEIPT_REQUEST);

        // device file info
        info.setDeviceGuid(deviceInfo.getDeviceGUID());
        info.setDeviceAbsoluteFileName(receiptRequest.getDeviceAbsoluteFileName());

        // initialize upload info
        info.setAttempts(0);
        info.setCloudFileName(null);
        info.setCloudDownloadUrl(null);
        info.setProgress(null);
        info.setSessionUriString(null);
        info.setUploadSuccess(false);
        info.setBytesTransferred(0l);
        info.setFileSizeBytes(0l);
        info.setContentType(null);

        // initialize deletionKey
        info.setDeletionKey(BuilderUtilities.generateGuid());

        return info;
    }

}
