/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.entities.PhotoRequest;

/**
 * Created on 9/2/2017
 * Project : Driver
 */

public class PhotoRequestBuilder {
    private PhotoRequest photoRequest;

    private PhotoRequestBuilder(@NonNull Builder builder){
        this.photoRequest = builder.photoRequest;
    }

    private PhotoRequest getPhotoRequest(){
        return photoRequest;
    }

    public static class Builder {
        private PhotoRequest photoRequest;

        public Builder(){
            photoRequest = new PhotoRequest();
            photoRequest.setGuid(BuilderUtilities.generateGuid());
            photoRequest.setStatus(PhotoRequest.PhotoStatus.NO_ATTEMPTS);
        }

        public Builder guid(@NonNull String guid) {
            this.photoRequest.setGuid(guid);
            return this;
        }

        public Builder batchGuid(@NonNull String guid){
            this.photoRequest.setBatchGuid(guid);
            return this;
        }

        public Builder title(@NonNull String title){
            this.photoRequest.setTitle(title);
            return this;
        }

        public Builder description(@NonNull String description){
            this.photoRequest.setDescription(description);
            return this;
        }

        public Builder status(@NonNull PhotoRequest.PhotoStatus status) {
            this.photoRequest.setStatus(status);
            return this;
        }

        private void validate(@NonNull PhotoRequest photoRequest){
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
