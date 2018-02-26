/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;


import it.flube.libbatchdata.entities.AddressLocation;
import it.flube.libbatchdata.entities.Destination;
import it.flube.libbatchdata.entities.LatLonLocation;

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

        public Builder(){
            destination = new Destination();
            destination.setTargetVerificationMethod(Destination.VerificationMethod.NO_VERIFICATION_PERFORMED);
        }

        public Builder targetLatLon(LatLonLocation latLonLocation){
            this.destination.setTargetLatLon(latLonLocation);
            return this;
        }

        public Builder targetAddress(AddressLocation addressLocation){
            this.destination.setTargetAddress(addressLocation);
            return this;
        }

        public Builder targetType(Destination.DestinationType destinationType) {
            this.destination.setTargetType(destinationType);
            return this;
        }

        public Builder targetVerificationMethod(Destination.VerificationMethod verificationMethod) {
            this.destination.setTargetVerificationMethod(verificationMethod);
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
