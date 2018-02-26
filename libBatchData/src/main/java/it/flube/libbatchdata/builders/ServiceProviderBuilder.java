/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;


import it.flube.libbatchdata.entities.ServiceProvider;

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

        public Builder(){
            serviceProvider = new ServiceProvider();
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

        public Builder contactName(String contactName) {
            this.serviceProvider.setContactName(contactName);
            return this;
        }

        private void validate(ServiceProvider serviceProvider){

        }

        public ServiceProvider build(){
            ServiceProvider serviceProvider = new ServiceProviderBuilder(this).getServiceProvider();
            validate(serviceProvider);
            return serviceProvider;
        }

    }
}
