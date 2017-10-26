/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import java.util.UUID;

import it.flube.driver.modelLayer.entities.AddressLocation;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.entities.RouteStop;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class RouteStopBuilder {
    private RouteStop routeStop;

    private RouteStopBuilder(Builder builder){
        this.routeStop = builder.routeStop;
    }

    private RouteStop getRouteStop(){
        return routeStop;
    }

    public static class Builder {
        private RouteStop routeStop;

        public Builder(){
            this.routeStop = new RouteStop();
            this.routeStop.setGuid(BuilderUtilities.generateGuid());
        }

        public Builder guid(String guid){
            this.routeStop.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.routeStop.setBatchGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.routeStop.setBatchDetailGuid(guid);
            return this;
        }

        public Builder sequence(Integer sequence){
            this.routeStop.setSequence(sequence);
            return this;
        }

        public Builder latLonLocation(LatLonLocation latLonLocation) {
            this.routeStop.setLatLonLocation(latLonLocation);
            return this;
        }

        public Builder addressLocation(AddressLocation addressLocation) {
            this.routeStop.setAddressLocation(addressLocation);
            return this;
        }

        public Builder description(String description){
            this.routeStop.setDescription(description);
            return this;
        }

        public Builder iconUrl(String iconUrl){
            this.routeStop.setIconUrl(iconUrl);
            return this;
        }

        private void validate(RouteStop routeStop){

        }

        public RouteStop build(){
            RouteStop routeStop = new RouteStopBuilder(this).getRouteStop();
            validate(routeStop);
            return routeStop;
        }
    }
}
