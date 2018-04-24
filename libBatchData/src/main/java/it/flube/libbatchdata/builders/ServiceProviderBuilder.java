/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;


import it.flube.libbatchdata.entities.AddressLocation;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.ServiceProvider;

/**
 * Created on 9/6/2017
 * Project : Driver
 */

public class ServiceProviderBuilder {
    private ServiceProvider serviceProvider;

    private static final String DEFAULT_ICON_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Foil-change-icon.png?alt=media&token=b1599ce2-67ec-4bda-9a4b-d3a4ae8cdea3";

    private ServiceProviderBuilder(Builder builder){
        this.serviceProvider = builder.serviceProvider;
    }

    private ServiceProvider getServiceProvider(){
        return serviceProvider;
    }

    public static class Builder {
        private ServiceProvider serviceProvider;

        public Builder(){
            serviceProvider = new ServiceProvider();
            serviceProvider.setIconURL(DEFAULT_ICON_URL);
        }

        public Builder name(String name){
            this.serviceProvider.setName(name);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.serviceProvider.setIconURL(iconURL);
            return this;
        }

        public Builder contactPhone(String contactPhone){
            this.serviceProvider.setContactPhone(contactPhone);
            return this;
        }

        public Builder contactText(String contactText){
            this.serviceProvider.setContactText(contactText);
            return this;
        }

        public Builder contactPerson(ContactPerson contactPerson) {
            this.serviceProvider.setContactPerson(contactPerson);
            return this;
        }

        public Builder addressLocation(AddressLocation addressLocation){
            this.serviceProvider.setAddressLocation(addressLocation);
            return this;
        }

        public Builder latLonLocation(LatLonLocation latLonLocation){
            this.serviceProvider.setLatLonLocation(latLonLocation);
            return this;
        }

        private void validate(ServiceProvider serviceProvider){
            if (serviceProvider.getName() == null){
                throw new IllegalStateException("name is null");
            }

            if (serviceProvider.getName() == null){
                throw new IllegalStateException("name is null");
            }

            if (serviceProvider.getIconURL() == null){
                throw new IllegalStateException("iconURL is null");
            }

            if (serviceProvider.getContactPhone() == null){
                throw new IllegalStateException("contact phone is null");
            }

        }

        public ServiceProvider build(){
            ServiceProvider serviceProvider = new ServiceProviderBuilder(this).getServiceProvider();
            validate(serviceProvider);
            return serviceProvider;
        }

    }
}
