/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;


import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.AddressLocation;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.entities.ServiceProvider;

import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.DEFAULT_ICON_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.DEFAULT_ICON_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.DEFAULT_ICON_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.DEFAULT_ICON_URL_STAGING;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_TARGET_ENVIRONMENT;

/**
 * Created on 9/6/2017
 * Project : Driver
 */

public class ServiceProviderBuilder {
    private ServiceProvider serviceProvider;

    private ServiceProviderBuilder(Builder builder){
        this.serviceProvider = builder.serviceProvider;
    }

    private ServiceProvider getServiceProvider(){
        return serviceProvider;
    }

    public static class Builder {
        private ServiceProvider serviceProvider;

        ///public Builder(){
        ///    initializeStuff(DEFAULT_TARGET_ENVIRONMENT);
        ///}

        public Builder(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            initializeStuff(targetEnvironment);
        }

        private void initializeStuff(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            serviceProvider = new ServiceProvider();

            switch (targetEnvironment){
                case PRODUCTION:
                    serviceProvider.setIconURL(DEFAULT_ICON_URL_PRODUCTION);
                    break;
                case DEMO:
                    serviceProvider.setIconURL(DEFAULT_ICON_URL_DEMO);
                    break;
                case STAGING:
                    serviceProvider.setIconURL(DEFAULT_ICON_URL_STAGING);
                    break;
                case DEVELOPMENT:
                    serviceProvider.setIconURL(DEFAULT_ICON_URL_DEVELOPMENT);
                    break;
            }
        }

        public Builder name(String name){
            this.serviceProvider.setName(name);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.serviceProvider.setIconURL(iconURL);
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

            if (serviceProvider.getContactPerson() == null) {
                throw new IllegalStateException("contact person is null");
            }

        }

        public ServiceProvider build(){
            ServiceProvider serviceProvider = new ServiceProviderBuilder(this).getServiceProvider();
            validate(serviceProvider);
            return serviceProvider;
        }

    }
}
