/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.HashMap;

import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.PhotoRequest;

/**
 * Created on 2/21/2019
 * Project : Driver
 */
public class PhotoRequestListForServiceProviderBuilder {
    //// Photo titles and descriptions
    private static final Boolean SHARE_WITH_CUSTOMER = true;
    private static final String SERVICE_PROVIDER_TITLE = "Service Provider";
    private static final String SERVICE_PROVIDER_DESCRIPTION = "Take this photo facing the service provider building";

    private HashMap<String, PhotoRequest> photoList;

    private PhotoRequestListForServiceProviderBuilder(Builder builder){
        this.photoList = builder.photoList;
    }

    private HashMap<String, PhotoRequest> getPhotoList(){
        return photoList;
    }

    public static class Builder {
        private TargetEnvironmentConstants.TargetEnvironment targetEnvironment;
        private HashMap<String, PhotoRequest> photoList;

        public Builder(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            this.targetEnvironment = targetEnvironment;
            initializeStuff(targetEnvironment);
        }

        private void initializeStuff(TargetEnvironmentConstants.TargetEnvironment targetEnvironment) {
            photoList = new HashMap<String, PhotoRequest>();

            switch (targetEnvironment) {
                case PRODUCTION:
                    break;
                case DEMO:
                    break;
                case STAGING:
                    break;
                case DEVELOPMENT:
                    break;
            }
        }

        public Builder addFullSetPhotos(){
            addServiceProviderBuilding(targetEnvironment);
            return this;
        }

        private void addServiceProviderBuilding(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder(targetEnvironment)
                    .title(SERVICE_PROVIDER_TITLE)
                    .description(SERVICE_PROVIDER_DESCRIPTION)
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(null)
                    .shareWithCustomer(SHARE_WITH_CUSTOMER)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void validate(HashMap<String, PhotoRequest> photoList){
            // required PRESENT (must not be null)
            if (photoList.size() <= 0){
                throw new IllegalStateException("no photos in photoList");
            }
        }

        public HashMap<String, PhotoRequest> build(){
            HashMap<String, PhotoRequest> photoList = new PhotoRequestListForServiceProviderBuilder(this).getPhotoList();
            validate(photoList);
            return photoList;
        }
    }
}
