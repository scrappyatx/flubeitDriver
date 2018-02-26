/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.Date;

import it.flube.libbatchdata.entities.MapPing;

/**
 * Created on 9/3/2017
 * Project : Driver
 */

public class MapPingBuilder {
    private MapPing mapPing;

    private MapPingBuilder(Builder builder) {
        this.mapPing = builder.mapPing;
    }

    private MapPing getMapPing(){
        return mapPing;
    }

    public static class Builder {
        private MapPing mapPing;

        public Builder(Double latitude, Double longitude, Date timestamp){
            mapPing = new MapPing();
            mapPing.setGuid(BuilderUtilities.generateGuid());
            mapPing.setLatLonLocation(new LatLonLocationBuilder.Builder().location(latitude, longitude).build());
            mapPing.setPingTime(timestamp);
        }

        private void validate(MapPing mapPing) {
            // required PRESENT (must not be null)
            if (mapPing.getGuid() == null){
                throw new IllegalStateException("GUID is null");
            }
            if (mapPing.getPingTime() == null){
                throw new IllegalStateException("timestamp is null");
            }
            if (mapPing.getLatLonLocation() == null){
                throw new IllegalStateException("latLonPosition is null");
            }
        }

        private MapPing build(){
            MapPing mapPing = new MapPingBuilder(this).getMapPing();
            validate(mapPing);
            return mapPing;
        }
    }
}
