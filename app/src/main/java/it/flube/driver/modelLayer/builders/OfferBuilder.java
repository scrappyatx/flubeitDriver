/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import java.util.ArrayList;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.entities.RouteStop;
import it.flube.driver.modelLayer.entities.ServiceProvider;

/**
 * Created on 9/6/2017
 * Project : Driver
 */

public class OfferBuilder {
    private Offer offer;

    private OfferBuilder(Builder builder){
        this.offer = builder.offer;
    }

    private Offer getOffer(){
        return offer;
    }

    public static class Builder {
        private Offer offer;

        public Builder(){
            offer = new Offer();
            offer.setGUID(BuilderUtilities.generateGuid());
            offer.setRouteList(new ArrayList<RouteStop>());
        }

        public Builder offerOID(String offerOID){
            this.offer.setGUID(offerOID);
            return this;
        }

        public Builder serviceProvider(ServiceProvider serviceProvider){
            this.offer.setServiceProvider(serviceProvider);
            return this;
        }

        public Builder offerDate(String offerDate) {
            this.offer.setOfferDate(offerDate);
            return this;
        }

        public Builder offerTime(String offerTime) {
            this.offer.setOfferTime(offerTime);
            return this;
        }

        public Builder offerDuration(String offerDuration){
            this.offer.setOfferDuration(offerDuration);
            return this;
        }

        public Builder offerType(Offer.OfferType offerType) {
            this.offer.setOfferType(offerType);
            return this;
        }

        public Builder serviceDescription(String serviceDescription) {
            this.offer.setServiceDescription(serviceDescription);
            return this;
        }

        public Builder estimatedEarnings(String estimatedEarnings) {
            this.offer.setEstimatedEarnings(estimatedEarnings);
            return this;
        }

        public Builder estimatedEarningsExtra(String estimatedEarningsExtra) {
            this.offer.setEstimatedEarningsExtra(estimatedEarningsExtra);
            return this;
        }

        public Builder addStopToRoute(Integer index, RouteStop routeStop){
            this.offer.getRouteList().add(index, routeStop);
            return this;
        }

        public Builder addStopToRoute(RouteStop routeStop){
            this.offer.getRouteList().add(routeStop);
            return this;
        }


        private void validate(Offer offer) {

        }

        public Offer build(){
            Offer offer = new OfferBuilder(this).getOffer();
            validate(offer);
            return offer;
        }

    }
}
