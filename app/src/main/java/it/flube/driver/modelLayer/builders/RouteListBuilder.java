/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import java.util.ArrayList;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.RouteList;
import it.flube.driver.modelLayer.entities.RouteStop;

/**
 * Created on 9/20/2017
 * Project : Driver
 */

public class RouteListBuilder {
    private RouteList routeList;

    private RouteListBuilder(Builder builder){
        this.routeList = builder.routeList;
    }

    private RouteList getRouteList(){
        return routeList;
    }

    public static class Builder {
        private RouteList routeList;

        public Builder(){
            this.routeList = new RouteList();
            this.routeList.setGuid(BuilderUtilities.generateGuid());
        }

        public Builder guid(String guid){
            this.routeList.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.routeList.setBatchGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.routeList.setBatchDetailGuid(guid);
            return this;
        }

        private void validate(RouteList routeList){

        }

        public RouteList build(){
            RouteList routeList = new RouteListBuilder(this).getRouteList();
            validate(routeList);
            return routeList;
        }
    }
}
