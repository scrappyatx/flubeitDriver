/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import it.flube.driver.modelLayer.entities.AddressLocation;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.entities.RoutingStop;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class RoutingStopBuilder {
    private RoutingStop routingStop;

    private RoutingStopBuilder(Builder builder){
        this.routingStop = builder.routingStop;
    }

    private RoutingStop getRoutingStop(){
        return routingStop;
    }

    public static class Builder {
        private RoutingStop routingStop;

        public Builder(){
            routingStop = new RoutingStop();
        }

        public Builder latLonLocation(LatLonLocation latLonLocation) {
            this.routingStop.setLatLonLocation(latLonLocation);
            return this;
        }

        public Builder addressLocation(AddressLocation addressLocation) {
            this.routingStop.setAddressLocation(addressLocation);
            return this;
        }

        public Builder description(String description){
            this.routingStop.setDescription(description);
            return this;
        }

        public Builder iconURL(String iconURL){
            this.routingStop.setIconURL(iconURL);
            return this;
        }

        private void validate(RoutingStop routingStop){

        }

        public RoutingStop build(){
            RoutingStop routingStop = new RoutingStopBuilder(this).getRoutingStop();
            validate(routingStop);
            return routingStop;
        }
    }
}
