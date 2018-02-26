/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;


import it.flube.libbatchdata.entities.AddressLocation;

/**
 * Created on 9/1/2017
 * Project : Driver
 */

public class AddressLocationBuilder {
    private AddressLocation addressLocation;

    private AddressLocationBuilder(Builder builder){
        this.addressLocation = builder.addressLocation;
    }

    private AddressLocation getAddressLocation(){
        return addressLocation;
    }

    public static class Builder {
        private AddressLocation addressLocation;

        public Builder(){
            addressLocation = new AddressLocation();
        }

        public Builder street(String street) {
            this.addressLocation.setStreet1(street);
            return this;
        }

        public Builder city(String city){
            this.addressLocation.setCity(city);
            return this;
        }

        public Builder state(String state){
            this.addressLocation.setState(state);
            return this;
        }

        public Builder zip(String zip){
            this.addressLocation.setZip(zip);
            return this;
        }

        public Builder streetSecondLine(String street2) {
            this.addressLocation.setStreet2(street2);
            return this;
        }

        public Builder note(String note) {
            this.addressLocation.setNote(note);
            return this;
        }

        private void validate(AddressLocation addressLocation) {
            // required PRESENT (must not be null)
            if (addressLocation.getStreet1() == null) {
                throw new IllegalStateException("street1 is null");
            }

            if (addressLocation.getCity() == null) {
                throw new IllegalStateException("city is null");
            }

            if (addressLocation.getState() == null) {
                throw new IllegalStateException("state is null");
            }

            if (addressLocation.getZip() == null) {
                throw new IllegalStateException("zip is null");
            }
        }

        public AddressLocation build(){
            AddressLocation addressLocation = new AddressLocationBuilder(this).getAddressLocation();
            validate(addressLocation);
            return addressLocation;
        }
    }
}
