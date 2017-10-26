/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import it.flube.driver.modelLayer.entities.DisplayDistance;

/**
 * Created on 9/16/2017
 * Project : Driver
 */

public class DisplayDistanceBuilder {
    private DisplayDistance displayDistance;

    private DisplayDistanceBuilder(Builder builder){
        this.displayDistance = builder.displayDistance;
    }

    private DisplayDistance getDisplayDistance(){
        return displayDistance;
    }

    public static class Builder{
        private DisplayDistance displayDistance;

        public Builder(){
            displayDistance = new DisplayDistance();
        }

        public Builder distanceIndicatorUrl(String distanceIndicatorUrl){
            this.displayDistance.setDistanceIndicatorUrl(distanceIndicatorUrl);
            return this;
        }

        public Builder distanceToTravel(String distanceToTravel){
            this.displayDistance.setDistanceToTravel(distanceToTravel);
            return this;
        }

        private void validate(DisplayDistance displayDistance){

        }

        public DisplayDistance build(){
            DisplayDistance displayDistance = new DisplayDistanceBuilder(this).getDisplayDistance();
            validate(displayDistance);
            return displayDistance;
        }
    }
}
