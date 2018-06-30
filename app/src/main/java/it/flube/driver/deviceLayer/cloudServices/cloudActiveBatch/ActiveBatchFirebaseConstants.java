/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch;

/**
 * Created on 4/29/2018
 * Project : Driver
 */
public class ActiveBatchFirebaseConstants {

    public static final String ACTIVE_BATCH_SERVER_NOTIFICATION_NODE = "mobileData/userWriteable/activeBatches";
    public static final String COMPLETED_BATCH_SERVER_NOTIFICATION_NODE = "mobileData/userWriteable/completedBatches";

    public static final String BATCH_DATA_STEPS_NODE = "steps";
    public static final String PHOTO_REQUEST_LIST_NODE = "photoRequestList";
    public static final String PHOTO_REQUEST_HAS_DEVICE_FILE_NODE  = "hasDeviceFile";
    public static final String PHOTO_REQUEST_DEVICE_ABSOLUTE_FILENAME_NODE = "deviceAbsoluteFileName";

    public static final String SERVICE_ORDER_GUID = "serviceOrderGuid";

    public static final String BATCH_START_REQUEST_NODE = "mobileData/userWriteable/batchStartRequest";
    public static final String BATCH_START_RESPONSE_NODE = "mobileData/userReadable/batchStartResponse";


}
