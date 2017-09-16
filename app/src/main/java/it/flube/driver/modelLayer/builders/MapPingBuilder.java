/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.MapPing;

/**
 * Created on 9/3/2017
 * Project : Driver
 */

public class MapPingBuilder {
    private MapPing mapPing;

    private MapPingBuilder(@NonNull Builder builder) {
        this.mapPing = builder.mapPing;
    }

    private MapPing getMapPing(){
        return mapPing;
    }

    public static class Builder {
        private MapPing mapPing;

        public Builder(@NonNull Double latitude, @NonNull Double longitude, @NonNull Date timestamp){
            mapPing = new MapPing();
            mapPing.setGUID(UUID.randomUUID().toString());
            mapPing.setLatLonLocation(new LatLonLocationBuilder.Builder().location(latitude, longitude).build());
            mapPing.setTimestamp(timestamp);
        }

        private void validate(@NonNull MapPing mapPing) {
            // required PRESENT (must not be null)
            if (mapPing.getGUID() == null){
                throw new IllegalStateException("GUID is null");
            }
            if (mapPing.getTimestamp() == null){
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
