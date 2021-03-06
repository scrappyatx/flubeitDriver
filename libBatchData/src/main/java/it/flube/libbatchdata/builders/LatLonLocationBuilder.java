/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;


import it.flube.libbatchdata.entities.LatLonLocation;

/**
 * Created on 9/1/2017
 * Project : Driver
 */

public class LatLonLocationBuilder {
    private LatLonLocation latLonLocation;

    private LatLonLocationBuilder(Builder builder){
        this.latLonLocation = builder.latLonLocation;
    }

    private LatLonLocation getLatLonLocation(){
        return latLonLocation;
    }

    public static class Builder {
        private LatLonLocation latLonLocation;

        public Builder(){
            latLonLocation = new LatLonLocation();
        }

        public Builder location(Double latitude, Double longitude) {
            this.latLonLocation.setLatitude(latitude);
            this.latLonLocation.setLongitude(longitude);
            return this;
        }

        private void validate(LatLonLocation latLonLocation){
            // required SPECIFIC VALUE (must not be zero)
            if (latLonLocation.getLatitude() == 0) {
                throw new IllegalStateException("latitude is zero");
            }

            if (latLonLocation.getLongitude() == 0) {
                throw new IllegalStateException("longitude is zero");
            }
        }

        public LatLonLocation build(){
            LatLonLocation latLonLocation = new LatLonLocationBuilder(this).getLatLonLocation();
            validate(latLonLocation);
            return latLonLocation;
        }
    }
}
