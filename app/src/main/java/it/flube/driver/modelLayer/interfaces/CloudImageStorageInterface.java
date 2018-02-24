/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.graphics.Bitmap;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public interface CloudImageStorageInterface {

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


}
