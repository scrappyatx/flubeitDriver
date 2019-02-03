/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch;

/**
 * Created on 4/29/2018
 * Project : Driver
 */
public class ActiveBatchFirebaseConstants {

    public static final String BATCH_DATA_STEPS_NODE = "steps";

    public static final String PHOTO_REQUEST_LIST_NODE = "photoRequestList";
    public static final String PHOTO_REQUEST_HAS_DEVICE_FILE_NODE  = "hasDeviceFile";
    public static final String PHOTO_REQUEST_DEVICE_ABSOLUTE_FILENAME_NODE = "deviceAbsoluteFileName";
    public static final String PHOTO_REQUEST_HAS_LABEL_MAP_NODE = "hasLabelMap";
    public static final String PHOTO_REQUEST_LABEL_MAP_NODE = "labelMap";
    public static final String PHOTO_REQUEST_HAS_CLOUD_FILE_NODE = "hasCloudFile";
    public static final String PHOTO_REQUEST_CLOUD_STORAGE_FILENAME_NODE = "cloudStorageFileName";
    public static final String PHOTO_REQUEST_CLOUD_STORAGE_DOWNLOAD_URL = "cloudStorageDownloadUrl";


    public static final String SIGNATURE_REQUEST_NODE = "signatureRequest";
    public static final String SIGNATURE_REQUEST_STATUS_NODE = "signatureStatus";
    public static final String SIGNATURE_REQUEST_HAS_DEVICE_FILE_NODE = "hasDeviceFile";
    public static final String SIGNATURE_REQUEST_DEVICE_ABSOLUTE_FILENAME_NODE = "deviceAbsoluteFileName";
    public static final String SIGNATURE_REQUEST_ATTEMPT_COUNT_NODE = "attemptCount";
    public static final String SIGNATURE_REQUEST_HAS_CLOUD_FILE_NODE = "hasCloudFile";
    public static final String SIGNATURE_REQUEST_CLOUD_STORAGE_FILENAME_NODE = "cloudStorageFileName";
    public static final String SIGNATURE_REQUEST_CLOUD_STORAGE_DOWNLOAD_URL = "cloudStorageDownloadUrl";

    public static final String RECEIPT_REQUEST_NODE = "receiptRequest";
    public static final String RECEIPT_REQUEST_STATUS_NODE = "receiptStatus";
    public static final String RECEIPT_REQUEST_ATTEMPT_COUNT_NODE = "attemptCount";
    public static final String RECEIPT_REQUEST_HAS_DEVICE_FILE_NODE = "hasDeviceFile";
    public static final String RECEIPT_REQUEST_DEVICE_ABSOLUTE_FILENAME_NODE = "deviceAbsoluteFileName";
    public static final String RECEIPT_REQUEST_HAS_TEXT_MAP_NODE = "hasTextMap";
    public static final String RECEIPT_REQUEST_TEXT_MAP_NODE = "textMap";
    public static final String RECEIPT_REQUEST_HAS_CLOUD_FILE_NODE = "hasCloudFile";
    public static final String RECEIPT_REQUEST_CLOUD_STORAGE_FILENAME_NODE = "cloudStorageFileName";
    public static final String RECEIPT_REQUEST_CLOUD_STORAGE_DOWNLOAD_URL = "cloudStorageDownloadUrl";

    public static final String SERVICE_ORDER_GUID = "serviceOrderGuid";

    public static final String BATCH_START_REQUEST_NODE = "mobileData/userWriteable/batchStartRequest";
    public static final String BATCH_START_RESPONSE_NODE = "mobileData/userReadable/batchStartResponse";


    public static final String BATCH_DATA_SERVICE_ORDER_NODE = "serviceOrders";
    public static final String BATCH_DATA_CONTACT_PERSONS_BY_SERVICE_ORDER_NODE = "contactPersonsByServiceOrder";

    public static final String BATCH_DATA_ASSET_LIST_NODE = "assetList";

    public static final String ASSET_TRANSFER_STATUS_NODE = "transferStatus";

    public static final String BATCH_DETAIL_NODE = "batchDetail";
    public static final String DRIVER_INFO_NODE = "driverInfo";

    public static final String PAYMENT_AUTHORIZATION_NODE = "paymentAuthorization";
    public static final String PAYMENT_VERIFICATION_STATUS_NODE = "paymentVerificationStatus";

}
