/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks;

/**
 * Created on 2/23/2018
 * Project : Driver
 */

public class Constants {

    public static final String UPLOAD_TASKS_NOT_STARTED_NODE = "uploadTasks/notStarted";
    public static final String UPLOAD_TASKS_IN_PROGRESS_NODE = "uploadTasks/inProgress";
    public static final String UPLOAD_TASKS_FINISHED_NODE = "uploadTasks/finished";
    public static final String UPLOAD_TASKS_FAILED_NODE = "uploadTasks/failed";

    public static final String BATCH_GUID_NODE = "batchGuid";
    public static final String SERVICE_ORDER_GUID_NODE = "serviceOrderGuid";
    public static final String ORDER_STEP_GUID_NODE = "orderStepGuid";
    public static final String PHOTO_REQUEST_GUID_NODE = "photoRequestGuid";

    public static final String DEVICE_GUID_NODE = "deviceGuid";
    public static final String DEVICE_FILE_NAME_NODE = "deviceAbsoluteFileName";
    public static final String CLOUD_FILE_NAME_NODE = "cloudStorageFileName";

    public static final String SESSION_URI_STRING_NODE = "sessionUriString";
    public static final String PROGRESS_NODE = "progress";
    public static final String ATTEMPT_COUNT_NODE = "attempts";

    public static final String TIMESTAMP_NODE = "timestamp";

}
