/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.graphics.Bitmap;

import it.flube.driver.modelLayer.entities.driver.Driver;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public interface CloudImageStorageInterface {

    void initialize(CloudConfigInterface cloudConfig, Driver driver);

    void getCloudStorageFileName(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid, GetFileNameResponse response);

    interface GetFileNameResponse {
        void cloudImageGetFileNameResponse(String cloudStorageFileName);
    }

    void saveImageStartRequest(String cloudStorageFileName, String deviceStorageAbsoluteFileName, SaveStartResponse response);


    interface SaveStartResponse {
        void cloudImageStorageSaveSuccess(String cloudStorageDownloadUrl);

        void cloudImageStorageSaveFailure();

        void cloudImageStorageSavePaused(String sessionUriString, String cloudStorageFileName, Double progress);
    }


    void saveImageResumeRequest(String sessionUriString, String deviceStorageAbsoluteFileName, String cloudStorageFileName,
                                SaveResumeResponse response);

    interface SaveResumeResponse {
        void cloudImageStorageResumeSuccess(String cloudStorageDownloadUrl);

        void cloudImageStorageResumeFailure();

        void cloudImageStorageResumePaused(String sessionUriString, String cloudStorageFileName, Double progress);
    }

    ///
    ///  File upload tasks -> for tracking file upload progress
    ///

    void addPhotoUploadTaskToNotStartedRequest(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                               String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                               AddPhotoUploadTaskResponse response);

    interface AddPhotoUploadTaskResponse {
        void cloudImageAddPhotoUploadTaskComplete();
    }

    void movePhotoUploadTaskToInProgress(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                         String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                         String sessionUriString, Double progress,
                                         MovePhotoUploadTaskInProgressResponse response);

    interface MovePhotoUploadTaskInProgressResponse {
        void cloudImageMovePhotoUploadTaskToInProgressComplete();
    }

    void movePhotoUploadTaskToFinished(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                       String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                       MovePhotoUploadTaskFinishedResponse response);


    interface MovePhotoUploadTaskFinishedResponse {
        void cloudImageMovePhotoUploadTaskFinishedComplete();
    }

    void movePhotoUploadTaskToFailed(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                     String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                     MovePhotoUploadTaskFailedResponse response);


    interface MovePhotoUploadTaskFailedResponse {
        void cloudImageMoveUploadTaskFailedComplete(Integer attempts);
    }



}
