/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.HashMap;

import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.PhotoRequest;

import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.FRONT_CORNER_DRIVER_VIEW_HINT_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.FRONT_CORNER_PASSENGER_VIEW_HINT_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.FRONT_VIEW_HINT_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.REAR_CORNER_DRIVER_VIEW_HINT_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.REAR_CORNER_PASSENGER_VIEW_HINT_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.REAR_VIEW_HINT_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SIDE_DRIVER_VIEW_HINT_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SIDE_PASSENGER_VIEW_HINT_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.FRONT_CORNER_DRIVER_VIEW_HINT_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.FRONT_CORNER_PASSENGER_VIEW_HINT_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.FRONT_VIEW_HINT_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.REAR_CORNER_DRIVER_VIEW_HINT_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.REAR_CORNER_PASSENGER_VIEW_HINT_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.REAR_VIEW_HINT_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SIDE_DRIVER_VIEW_HINT_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SIDE_PASSENGER_VIEW_HINT_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.FRONT_CORNER_DRIVER_VIEW_HINT_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.FRONT_CORNER_PASSENGER_VIEW_HINT_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.FRONT_VIEW_HINT_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.REAR_CORNER_DRIVER_VIEW_HINT_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.REAR_CORNER_PASSENGER_VIEW_HINT_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.REAR_VIEW_HINT_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SIDE_DRIVER_VIEW_HINT_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SIDE_PASSENGER_VIEW_HINT_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.FRONT_CORNER_DRIVER_VIEW_HINT_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.FRONT_CORNER_PASSENGER_VIEW_HINT_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.FRONT_VIEW_HINT_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.REAR_CORNER_DRIVER_VIEW_HINT_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.REAR_CORNER_PASSENGER_VIEW_HINT_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.REAR_VIEW_HINT_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SIDE_DRIVER_VIEW_HINT_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SIDE_PASSENGER_VIEW_HINT_URL_STAGING;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_TARGET_ENVIRONMENT;


/**
 * Created on 1/22/2018
 * Project : Driver
 */

public class PhotoRequestListForVehicleBuilder {
    //// Photo titles and descriptions
    private static final String FRONT_CORNER_DRIVER_TITLE = "Front Corner Driver";
    private static final String FRONT_CORNER_DRIVER_DESCRIPTION = "Take this photo facing the front corner of the vehicle on the driver side";

    private static final String FRONT_CORNER_PASSENGER_TITLE = "Front Corner Passenger";
    private static final String FRONT_CORNER_PASSENGER_DESCRIPTION = "Take this photo facing the front corner of the vehicle on the passenger side";

    private static final String FRONT_TITLE = "Front";
    private static final String FRONT_DESCRIPTION = "Take this photo facing the front of the vehicle";

    private static final String REAR_CORNER_DRIVER_TITLE = "Rear Corner Driver";
    private static final String REAR_CORNER_DRIVER_DESCRIPTION = "Take this photo facing the rear corner of the vehicle on the driver side";

    private static final String REAR_CORNER_PASSENGER_TITLE = "Rear Corner Passenger";
    private static final String REAR_CORNER_PASSENGER_DESCRIPTION = "Take this photo facing the rear corner of the vehicle on the passenger side";

    private static final String REAR_TITLE = "Rear";
    private static final String REAR_DESCRIPTION = "Take this photo facing the rear of the vehicle";

    private static final String SIDE_DRIVER_TITLE = "Side Driver";
    private static final String SIDE_DRIVER_DESCRIPTION = "Take this photo facing the side of the vehicle on the driver side";

    private static final String SIDE_PASSENGER_TITLE = "Side Passenger";
    private static final String SIDE_PASSENGER_DESCRIPTION = "Take this photo facing the side of the vehicle on the passenger side";

    private HashMap<String, PhotoRequest> photoList;

    private PhotoRequestListForVehicleBuilder(Builder builder){
        this.photoList = builder.photoList;
    }

    private HashMap<String, PhotoRequest> getPhotoList(){
        return photoList;
    }

    public static class Builder {
        private TargetEnvironmentConstants.TargetEnvironment targetEnvironment;
        private String frontCornerDriverViewHintUrl;
        private String frontCornerPassengerViewHintUrl;
        private String frontViewHintUrl;
        private String rearCornerDriverViewHintUrl;
        private String rearCornerPassengerViewHintUrl;
        private String readViewHintUrl;
        private String sideDriverViewHintUrl;
        private String sidePassengerViewHintUrl;

        private HashMap<String, PhotoRequest> photoList;

        ///public Builder(){
        ///    initializeStuff(DEFAULT_TARGET_ENVIRONMENT);
        ///}

        public Builder(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            this.targetEnvironment = targetEnvironment;
            initializeStuff(targetEnvironment);
        }

        private void initializeStuff(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            photoList = new HashMap<String, PhotoRequest>();

            switch (targetEnvironment){
                case PRODUCTION:
                    frontCornerDriverViewHintUrl = FRONT_CORNER_DRIVER_VIEW_HINT_URL_PRODUCTION;
                    frontCornerPassengerViewHintUrl = FRONT_CORNER_PASSENGER_VIEW_HINT_URL_PRODUCTION;
                    frontViewHintUrl = FRONT_VIEW_HINT_URL_PRODUCTION;
                    rearCornerDriverViewHintUrl = REAR_CORNER_DRIVER_VIEW_HINT_URL_PRODUCTION;
                    rearCornerPassengerViewHintUrl = REAR_CORNER_PASSENGER_VIEW_HINT_URL_PRODUCTION;
                    readViewHintUrl = REAR_VIEW_HINT_URL_PRODUCTION;
                    sideDriverViewHintUrl = SIDE_DRIVER_VIEW_HINT_URL_PRODUCTION;
                    sidePassengerViewHintUrl = SIDE_PASSENGER_VIEW_HINT_URL_PRODUCTION;
                    break;
                case DEMO:
                    frontCornerDriverViewHintUrl = FRONT_CORNER_DRIVER_VIEW_HINT_URL_DEMO;
                    frontCornerPassengerViewHintUrl = FRONT_CORNER_PASSENGER_VIEW_HINT_URL_DEMO;
                    frontViewHintUrl = FRONT_VIEW_HINT_URL_DEMO;
                    rearCornerDriverViewHintUrl = REAR_CORNER_DRIVER_VIEW_HINT_URL_DEMO;
                    rearCornerPassengerViewHintUrl = REAR_CORNER_PASSENGER_VIEW_HINT_URL_DEMO;
                    readViewHintUrl = REAR_VIEW_HINT_URL_DEMO;
                    sideDriverViewHintUrl = SIDE_DRIVER_VIEW_HINT_URL_DEMO;
                    sidePassengerViewHintUrl = SIDE_PASSENGER_VIEW_HINT_URL_DEMO;
                    break;
                case STAGING:
                    frontCornerDriverViewHintUrl = FRONT_CORNER_DRIVER_VIEW_HINT_URL_STAGING;
                    frontCornerPassengerViewHintUrl = FRONT_CORNER_PASSENGER_VIEW_HINT_URL_STAGING;
                    frontViewHintUrl = FRONT_VIEW_HINT_URL_STAGING;
                    rearCornerDriverViewHintUrl = REAR_CORNER_DRIVER_VIEW_HINT_URL_STAGING;
                    rearCornerPassengerViewHintUrl = REAR_CORNER_PASSENGER_VIEW_HINT_URL_STAGING;
                    readViewHintUrl = REAR_VIEW_HINT_URL_STAGING;
                    sideDriverViewHintUrl = SIDE_DRIVER_VIEW_HINT_URL_STAGING;
                    sidePassengerViewHintUrl = SIDE_PASSENGER_VIEW_HINT_URL_STAGING;
                    break;
                case DEVELOPMENT:
                    frontCornerDriverViewHintUrl = FRONT_CORNER_DRIVER_VIEW_HINT_URL_DEVELOPMENT;
                    frontCornerPassengerViewHintUrl = FRONT_CORNER_PASSENGER_VIEW_HINT_URL_DEVELOPMENT;
                    frontViewHintUrl = FRONT_VIEW_HINT_URL_DEVELOPMENT;
                    rearCornerDriverViewHintUrl = REAR_CORNER_DRIVER_VIEW_HINT_URL_DEVELOPMENT;
                    rearCornerPassengerViewHintUrl = REAR_CORNER_PASSENGER_VIEW_HINT_URL_DEVELOPMENT;
                    readViewHintUrl = REAR_VIEW_HINT_URL_DEVELOPMENT;
                    sideDriverViewHintUrl = SIDE_DRIVER_VIEW_HINT_URL_DEVELOPMENT;
                    sidePassengerViewHintUrl = SIDE_PASSENGER_VIEW_HINT_URL_DEVELOPMENT;
                    break;
            }


        }

        public Builder addFullSetPhotos(){
            addFrontCornerDriver(targetEnvironment);
            addFrontView(targetEnvironment);
            addFrontCornerPassenger(targetEnvironment);

            addRearCornerDriver(targetEnvironment);
            addRearCornerPassenger(targetEnvironment);
            addRearView(targetEnvironment);

            addSideDriver(targetEnvironment);
            addSidePassenger(targetEnvironment);

            return this;
        }

        public Builder addReducedSetPhotos(){
            addFrontView(targetEnvironment);
            addRearView(targetEnvironment);
            addSideDriver(targetEnvironment);
            addSidePassenger(targetEnvironment);

            return this;
        }

        private void addFrontCornerDriver(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder(targetEnvironment)
                            .title(FRONT_CORNER_DRIVER_TITLE)
                            .description(FRONT_CORNER_DRIVER_DESCRIPTION)
                            .sequence(this.photoList.size()+1)
                            .photoHintUrl(frontCornerDriverViewHintUrl)
                            .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addFrontCornerPassenger(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder(targetEnvironment)
                    .title(FRONT_CORNER_PASSENGER_TITLE)
                    .description(FRONT_CORNER_PASSENGER_DESCRIPTION)
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(frontCornerPassengerViewHintUrl)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addFrontView(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder(targetEnvironment)
                    .title(FRONT_TITLE)
                    .description(FRONT_DESCRIPTION)
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(frontViewHintUrl)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addRearCornerDriver(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder(targetEnvironment)
                    .title(REAR_CORNER_DRIVER_TITLE)
                    .description(REAR_CORNER_DRIVER_DESCRIPTION)
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(rearCornerDriverViewHintUrl)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addRearCornerPassenger(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder(targetEnvironment)
                    .title(REAR_CORNER_PASSENGER_TITLE)
                    .description(REAR_CORNER_PASSENGER_DESCRIPTION)
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(rearCornerPassengerViewHintUrl)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addRearView(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder(targetEnvironment)
                    .title(REAR_TITLE)
                    .description(REAR_DESCRIPTION)
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(readViewHintUrl)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addSideDriver(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder(targetEnvironment)
                    .title(SIDE_DRIVER_TITLE)
                    .description(SIDE_DRIVER_DESCRIPTION)
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(sideDriverViewHintUrl)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addSidePassenger(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder(targetEnvironment)
                    .title(SIDE_PASSENGER_TITLE)
                    .description(SIDE_PASSENGER_DESCRIPTION)
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(sidePassengerViewHintUrl)
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
            HashMap<String, PhotoRequest> photoList = new PhotoRequestListForVehicleBuilder(this).getPhotoList();
            validate(photoList);
            return photoList;
        }

    }
}
