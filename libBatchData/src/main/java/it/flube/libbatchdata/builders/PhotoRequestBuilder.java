/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;


import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.ImageLabel;
import it.flube.libbatchdata.entities.PhotoRequest;

import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_NO_ATTEMPT_IMAGE_URL_DEMO;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_NO_ATTEMPT_IMAGE_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_NO_ATTEMPT_IMAGE_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_NO_ATTEMPT_IMAGE_URL_STAGING;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_TARGET_ENVIRONMENT;

/**
 * Created on 9/2/2017
 * Project : Driver
 */

public class PhotoRequestBuilder {
    private static final String NO_ATTEMPTS_ICON_TEXT = "{fa-ban}";
    private static final String PHOTO_SUCCESS_ICON_TEXT = "{fa-check-circle}";
    private static final String FAILED_ATTEMPTS_ICON_TEXT = "{fa-question-circle}";

    private static final Boolean DEFAULT_DO_DEVICE_IMAGE_DETECTION = true;

    private PhotoRequest photoRequest;

    private PhotoRequestBuilder(Builder builder){
        this.photoRequest = builder.photoRequest;
    }

    private PhotoRequest getPhotoRequest(){
        return photoRequest;
    }

    public static class Builder {
        private PhotoRequest photoRequest;

        ///public Builder(){
        ///    initializeStuff(DEFAULT_TARGET_ENVIRONMENT);
        ///}

        public Builder(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            initializeStuff(targetEnvironment);
        }

        private void initializeStuff(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            photoRequest = new PhotoRequest();
            photoRequest.setGuid(BuilderUtilities.generateGuid());
            photoRequest.setStatus(PhotoRequest.PhotoStatus.NO_ATTEMPTS);

            //build default status icon text
            HashMap<String, String> statusIconText = new HashMap<String, String>();
            statusIconText.put(PhotoRequest.PhotoStatus.NO_ATTEMPTS.toString(), NO_ATTEMPTS_ICON_TEXT);
            statusIconText.put(PhotoRequest.PhotoStatus.PHOTO_SUCCESS.toString(), PHOTO_SUCCESS_ICON_TEXT);
            statusIconText.put(PhotoRequest.PhotoStatus.FAILED_ATTEMPTS.toString(), FAILED_ATTEMPTS_ICON_TEXT);
            photoRequest.setStatusIconText(statusIconText);

            photoRequest.setAttemptCount(0);
            photoRequest.setHasPhotoHint(false);

            photoRequest.setHasDeviceFile(false);
            photoRequest.setHasCloudFile(false);

            photoRequest.setDoDeviceImageDetection(DEFAULT_DO_DEVICE_IMAGE_DETECTION);
            photoRequest.setHasLabelMap(false);
            photoRequest.setLabelMap(new HashMap<String, ImageLabel>());

            photoRequest.setHasNoAttemptImage(true);
            //set imageUrl based on target environment
            switch (targetEnvironment){
                case PRODUCTION:
                    photoRequest.setNoAttemptImageUrl(DEFAULT_NO_ATTEMPT_IMAGE_URL_PRODUCTION);
                    break;
                case DEMO:
                    photoRequest.setNoAttemptImageUrl(DEFAULT_NO_ATTEMPT_IMAGE_URL_DEMO);
                    break;
                case STAGING:
                    photoRequest.setNoAttemptImageUrl(DEFAULT_NO_ATTEMPT_IMAGE_URL_STAGING);
                    break;
                case DEVELOPMENT:
                    photoRequest.setNoAttemptImageUrl(DEFAULT_NO_ATTEMPT_IMAGE_URL_DEVELOPMENT);
                    break;
            }
        }

        public Builder guid(String guid) {
            this.photoRequest.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.photoRequest.setBatchGuid(guid);
            return this;
        }

        public Builder title(String title){
            this.photoRequest.setTitle(title);
            return this;
        }

        public Builder description(String description){
            this.photoRequest.setDescription(description);
            return this;
        }

        public Builder sequence(Integer sequence){
            this.photoRequest.setSequence(sequence);
            return this;
        }

        public Builder status(PhotoRequest.PhotoStatus status) {
            this.photoRequest.setStatus(status);
            return this;
        }

        public Builder statusIconText(Map<String, String> statusIconText){
            this.photoRequest.setStatusIconText(statusIconText);
            return this;
        }

        public Builder photoHintUrl(String photoHintUrl){
            this.photoRequest.setPhotoHintUrl(photoHintUrl);
            this.photoRequest.setHasPhotoHint(true);
            return this;
        }

        public Builder noAttemptImageUrl(String noAttemptImageUrl){
            this.photoRequest.setNoAttemptImageUrl(noAttemptImageUrl);
            this.photoRequest.setHasNoAttemptImage(true);
            return this;
        }

        public Builder doDeviceImageDetection(Boolean deviceImageDetection){
            this.photoRequest.setDoDeviceImageDetection(deviceImageDetection);
            return this;
        }

        private void validate(PhotoRequest photoRequest){
            // required PRESENT (must not be null)
            if (photoRequest.getGuid() == null){
                throw new IllegalStateException("GUID is null");
            }

            if (photoRequest.getTitle() == null){
                throw new IllegalStateException("title is null");
            }

            if (photoRequest.getDescription() == null){
                throw new IllegalStateException("description is null");
            }

            ///if (photoRequest.getSequence() == null){
            ///    throw new IllegalStateException("sequence is null");
            ///}

            //required SPECIFIC VALUE
            if (photoRequest.getStatus() != PhotoRequest.PhotoStatus.NO_ATTEMPTS) {
                throw new IllegalStateException("status is not NO_ATTEMPTS");
            }
        }

        public PhotoRequest build(){
            PhotoRequest photoRequest = new PhotoRequestBuilder(this).getPhotoRequest();
            validate(photoRequest);
            return photoRequest;
        }

    }
}
