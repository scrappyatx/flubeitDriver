/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.ArrayList;

import it.flube.libbatchdata.entities.ImageMatchSettings;
import it.flube.libbatchdata.interfaces.ImageMatchingInterface;

/**
 * Created on 3/4/2019
 * Project : Driver
 */
public class ImageMatchSettingsBuilder {

    /// DEFAULT SETTINGS
    private static final ImageMatchingInterface.MatchPreset DEFAULT_MATCH_PRESET = ImageMatchingInterface.MatchPreset.NONE;
    private static final ImageMatchingInterface.MatchType DEFAULT_MATCH_TYPE = ImageMatchingInterface.MatchType.NO_MATCH_REQUIRED;
    private static final float DEFAULT_CONFIDENCE_THRESHOLD = 0.5f;

    /// NONE SETTINGS
    private static final ImageMatchingInterface.MatchType MATCH_TYPE_NONE = ImageMatchingInterface.MatchType.NO_MATCH_REQUIRED;
    private static final String MATCH_DESCRIPTION_NONE = null;
    private static final float CONFIDENCE_THRESHOLD_NONE = 0.5f;


    ///  have to base64 encode the entity ids, because they slashes in the native entity ids can't be stored in firebase
    ///
    ///  Land Vehicle = "/m/01prls" = "L20vMDFwcmxz";
    ///  Vehicle = "/m/07yv9" = "L20vMDd5djk=";
    ///  Car = "/m/0k4j" = "L20vMGs0ag==";
    ///  Motor Vehicle = "/m/012f08" = "L20vMDEyZjA4";
    ///  Vehicle Registration Plate = "/m/01jfm_" = "L20vMDFqZm1f";

    /// VEHICLE SETTINGS
    private static final ImageMatchingInterface.MatchType MATCH_TYPE_VEHICLE = ImageMatchingInterface.MatchType.MATCH_ANY_LABEL;
    private static final String MATCH_DESCRIPTION_VEHICLE = "Vehicle";
    private static final float CONFIDENCE_THRESHOLD_VEHICLE = 0.5f;

    private static final String ENTITY_ID_LAND_VEHICLE = "L20vMDFwcmxz";
    private static final String ENTITY_ID_VEHICLE = "L20vMDd5djk=";
    private static final String ENTITY_ID_CAR = "L20vMGs0ag==";
    private static final String ENTITY_ID_MOTOR_VEHICLE = "L20vMDEyZjA4";

    /// LICENSE PLATE SETTINGS
    private static final ImageMatchingInterface.MatchType MATCH_TYPE_VEHICLE_REGISTRATION_PLATE = ImageMatchingInterface.MatchType.MATCH_ANY_LABEL;
    private static final String MATCH_DESCRIPTION_VEHICLE_REGISTRATION_PLATE = "License Plate";
    private static final float CONFIDENCE_THRESHOLD_VEHICLE_REGISTRATION_PLATE = 0.5f;

    private static final String ENTITY_ID_VEHICLE_REGISTRATION_PLATE = "L20vMDFqZm1f";


    private ImageMatchSettings imageMatchSettings;

    private ImageMatchSettingsBuilder(Builder builder){
        this.imageMatchSettings = builder.imageMatchSettings;
    }

    private ImageMatchSettings getImageMatchSettings(){
        return this.imageMatchSettings;
    }

    public static class Builder {
        private ImageMatchSettings imageMatchSettings;

        public Builder(){
            this.imageMatchSettings = new ImageMatchSettings();

            //set the preset
            this.matchPreset(DEFAULT_MATCH_PRESET);
            //set match type & confidence threshold
            this.imageMatchSettings.setMatchType(DEFAULT_MATCH_TYPE);
            this.imageMatchSettings.setConfidenceThreshold(DEFAULT_CONFIDENCE_THRESHOLD);

            //create empty entity match list
            this.imageMatchSettings.setMatchEntityIds(new ArrayList<String>());

        }

        public Builder matchPreset(ImageMatchingInterface.MatchPreset matchPreset){

            switch (matchPreset){
                case NONE:
                    //no matching
                    this.imageMatchSettings.setMatchType(MATCH_TYPE_NONE);

                    //set description and confidence threshold
                    this.imageMatchSettings.setMatchDescription(MATCH_DESCRIPTION_NONE);
                    this.imageMatchSettings.setConfidenceThreshold(CONFIDENCE_THRESHOLD_NONE);

                    //now create an empty match entity id list
                    this.imageMatchSettings.setMatchEntityIds(new ArrayList<String>());

                case VEHICLE:
                    //we can match any of the vehicle labels
                    this.imageMatchSettings.setMatchType(MATCH_TYPE_VEHICLE);

                    //set description and confidence threshold
                    this.imageMatchSettings.setMatchDescription(MATCH_DESCRIPTION_VEHICLE);
                    this.imageMatchSettings.setConfidenceThreshold(CONFIDENCE_THRESHOLD_VEHICLE);

                    //now create & populate the match entity id list
                    this.imageMatchSettings.setMatchEntityIds(new ArrayList<String>());

                    this.imageMatchSettings.getMatchEntityIds().add(ENTITY_ID_LAND_VEHICLE);
                    this.imageMatchSettings.getMatchEntityIds().add(ENTITY_ID_VEHICLE);
                    this.imageMatchSettings.getMatchEntityIds().add(ENTITY_ID_CAR);
                    this.imageMatchSettings.getMatchEntityIds().add(ENTITY_ID_MOTOR_VEHICLE);
                    break;
                case VEHICLE_REGISTRATION_PLATE:
                    //we can match any of the plate labels
                    this.imageMatchSettings.setMatchType(MATCH_TYPE_VEHICLE_REGISTRATION_PLATE);

                    //set description and confidence threshold
                    this.imageMatchSettings.setMatchDescription(MATCH_DESCRIPTION_VEHICLE_REGISTRATION_PLATE);
                    this.imageMatchSettings.setConfidenceThreshold(CONFIDENCE_THRESHOLD_VEHICLE_REGISTRATION_PLATE);

                    //now create & populate the match entity id list
                    this.imageMatchSettings.setMatchEntityIds(new ArrayList<String>());

                    this.imageMatchSettings.getMatchEntityIds().add(ENTITY_ID_VEHICLE_REGISTRATION_PLATE);
                    break;

            }
            return this;
        }

        public Builder matchType(ImageMatchingInterface.MatchType matchType){
            this.imageMatchSettings.setMatchType(matchType);
            return this;
        }

        public Builder matchDescription(String matchDescription){
            this.imageMatchSettings.setMatchDescription(matchDescription);
            return this;
        }

        public Builder confidenceThreshold(float confidenceThreshold){
            this.imageMatchSettings.setConfidenceThreshold(confidenceThreshold);
            return this;
        }

        public Builder addMatchingEntityId(String matchingEntityId){
            this.imageMatchSettings.getMatchEntityIds().add(matchingEntityId);
            return this;
        }

        private void validate(ImageMatchSettings imageMatchSettings){
            //do nothing
        }

        public ImageMatchSettings build(){
            ImageMatchSettings imageMatchSettings = new ImageMatchSettingsBuilder(this).getImageMatchSettings();
            validate(imageMatchSettings);
            return imageMatchSettings;
        }
    }
}
