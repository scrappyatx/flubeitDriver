/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.DriverInfo;

/**
 * Created on 8/31/2018
 * Project : Driver
 */
public class DriverInfoBuilder {
    private DriverInfo driverInfo;

    private DriverInfoBuilder(Builder builder){
        this.driverInfo = builder.driverInfo;
    }

    private DriverInfo getDriverInfo(){
        return driverInfo;
    }

    public static class Builder {
        private DriverInfo driverInfo;

        public Builder(){
            this.driverInfo = new DriverInfo();
        }

        public Builder clientId(String clientId){
            this.driverInfo.setClientId(clientId);
            return this;
        }

        public Builder photoUrl(String photoUrl){
            this.driverInfo.setPhotoUrl(photoUrl);
            return this;
        }

        public Builder displayName(String displayName){
            this.driverInfo.setDisplayName(displayName);
            return this;
        }

        public Builder proxyDialNumber(String proxyDialNumber){
            this.driverInfo.setProxyDialNumber(proxyDialNumber);
            return this;
        }

        public Builder proxyDisplayNumber(String proxyDisplayNumber){
            this.driverInfo.setProxyDisplayNumber(proxyDisplayNumber);
            return this;
        }

        public Builder proxyParticipantSid(String proxyParticipantSid){
            this.driverInfo.setProxyParticipantSid(proxyParticipantSid);
            return this;
        }

        private void validate(DriverInfo driverInfo){

        }

        public DriverInfo build(){
            DriverInfo driverInfo = new DriverInfoBuilder(this).getDriverInfo();
            validate(driverInfo);
            return driverInfo;
        }

    }
}
