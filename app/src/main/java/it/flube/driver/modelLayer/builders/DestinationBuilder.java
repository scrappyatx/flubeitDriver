/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.entities.Destination;

/**
 * Created on 9/1/2017
 * Project : Driver
 */

public class DestinationBuilder {
    private Destination destination;

    private DestinationBuilder(Builder builder){
        this.destination = builder.destination;
    }

    private Destination getDestination(){
        return destination;
    }

    public static class Builder {
        private Destination destination;

        public Builder(@NonNull Double latitude, @NonNull Double longitude, @NonNull Destination.DestinationType destinationType){
            destination = new Destination();
            destination.setTargetLatLon(new LatLonPositionBuilder.Builder(latitude, longitude).build());
            destination.setTargetType(destinationType);
            destination.setTargetVerificationMethod(Destination.VerificationMethod.NO_VERIFICATION_PERFORMED);
        }

        public Builder targetAddress(@NonNull String street1, @NonNull String city, @NonNull String state, @NonNull String zip){
            this.destination.setTargetAddress(new AddressLocationBuilder.Builder(street1, city, state, zip).build());
            return this;
        }

        private void validate(Destination destination) {
            // required PRESENT (must not be null)
            if (destination.getTargetLatLon() == null) {
                throw new IllegalStateException("targetLatLon is null");
            }

            if (destination.getTargetAddress() == null) {
                throw new IllegalStateException("targetAddress is null");
            }

            if (destination.getTargetType() == null) {
                throw new IllegalStateException("targetType is null");
            }

            // required ABSENT (must be null)
            if (destination.getActualLatLon() != null) {
                throw new IllegalStateException("actualLatLon is not null");
            }

            // required SPECIFIC VALUES
            if (destination.getTargetVerificationMethod() != Destination.VerificationMethod.NO_VERIFICATION_PERFORMED) {
                throw new IllegalStateException("targetVerificationMethod is not NO_VERIFICATION_PERFORMED");
            }
        }

        public Destination build(){
            Destination destination = new DestinationBuilder(this).getDestination();
            validate(destination);
            return destination;
        }
    }
}
