/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage;

/**
 * Created on 5/26/2018
 * Project : Driver
 */
public class CloudImageStorageConstants {
    //// STOR prefix = firebase STORAGE
    public static final String STOR_MOBILE_UPLOAD_PATH = "mobileAppDriver/imageUploads";

    // RTD prefix = firebase realtime database
    public static final String RTD_UPLOAD_TASKS_PATH = "mobileData/userOwned/users/%s/device/%s/batch/%s/uploadTasks";

    public static final String RTD_UPLOAD_TASKS_NOT_STARTED_LISTENER_NODE = "mobileData/userOwned/users/%s/device/%s/batch/%s/uploadTasks/progress/notStarted";
    public static final String RTD_UPLOAD_TASKS_PAUSED_LISTENER_NODE = "mobileData/userOwned/users/%s/device/%s/batch/%s/uploadTasks/progress/paused";
    public static final String RTD_UPLOAD_TASKS_FAILED_LISTENER_NODE = "mobileData/userOwned/users/%s/device/%s/batch/%s/uploadTasks/progress/failed";
    public static final String RTD_UPLOAD_TASKS_SUCCESS_LISTENER_NODE = "mobileData/userOwned/users/%s/device/%s/batch/%s/uploadTasks/progress/success";
    public static final String RTD_UPLOAD_TASKS_CANCELED_LISTENER_NODE = "mobileData/userOwned/users/%s/device/%s/batch/%s/uploadTasks/progress/canceled";
    public static final String RTD_UPLOAD_TASKS_PERMANENTLY_FAILED_LISTENER_NODE = "mobileData/userOwned/users/%s/device/%s/batch/%s/uploadTasks/progress/permanentlyFailed";
    public static final String RTD_UPLOAD_TASKS_FINISHED_LISTENER_NODE = "mobileData/userOwned/users/%s/device/%s/batch/%s/uploadTasks/progress/finished";

    public static final String RTD_FILE_INFO_NODE = "fileInfo";

    public static final String RTD_NOT_STARTED_NODE = "progress/notStarted/%s";
    public static final String RTD_PAUSED_NODE = "progress/paused/%s";
    public static final String RTD_IN_PROGRESS_NODE = "progress/inProgress/%s";
    public static final String RTD_SUCCESS_NODE = "progress/success/%s";
    public static final String RTD_FINISHED_NODE = "progress/finished/%s";
    public static final String RTD_FAILED_NODE = "progress/failed/%s";
    public static final String RTD_CANCELED_NODE = "progress/canceled/%s";
    public static final String RTD_PERMANENTLY_FAILED_NODE = "progress/permanentlyFailed/%s";

    public static final String RTD_FILE_INFO_ATTEMPT_NODE = "attempts";
    public static final String RTD_FILE_INFO_PROGRESS_NODE = "progress";
    public static final String RTD_FILE_INFO_SESSION_URI_NODE = "sessionUriString";
    public static final String RTD_FILE_INFO_CLOUD_DOWNLOAD_URL_NODE = "cloudDownloadUrl";
    public static final String RTD_FILE_INFO_UPLOAD_SUCCESS_NODE = "uploadSuccess";
    public static final String RTD_FILE_INFO_BYTES_TRANSFERRED_NODE = "bytesTransferred";
    public static final String RTD_FILE_INFO_FILE_SIZE_BYTES_NODE = "fileSizeBytes";
    public static final String RTD_FILE_INFO_CONTENT_TYPE_NODE = "contentType";


    public static final int RTD_MAX_FILE_UPLOAD_ATTEMPTS = 5;

    // RTD prefix = firebase realtime database
    public static final String RTD_DEVICE_FILES_TO_DELETE_PATH = "mobileData/userOwned/users/%s/device/%s/filesToDelete";

}
