/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.entities.LatLonPosition;

/**
 * Created on 9/1/2017
 * Project : Driver
 */

public class LatLonPositionBuilder {
    private LatLonPosition latLonPosition;

    private LatLonPositionBuilder(@NonNull Builder builder){
        this.latLonPosition = builder.latLonPosition;
    }

    private LatLonPosition getLatLonPosition(){
        return latLonPosition;
    }

    public static class Builder {
        private LatLonPosition latLonPosition;

        public Builder(@NonNull double latitude, @NonNull double longitude){
            latLonPosition = new LatLonPosition();
            latLonPosition.setLatitude(latitude);
            latLonPosition.setLongitude(longitude);
        }

        private void validate(LatLonPosition latLonPosition){

        }

        public LatLonPosition build(){
            LatLonPosition latLonPosition = new LatLonPositionBuilder(this).getLatLonPosition();
            validate(latLonPosition);
            return latLonPosition;
        }
    }
}
