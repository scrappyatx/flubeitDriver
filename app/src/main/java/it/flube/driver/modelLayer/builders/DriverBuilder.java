/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.entities.driver.CloudDatabaseSettings;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.entities.driver.NameSettings;
import it.flube.driver.modelLayer.entities.driver.UserRoles;

/**
 * Created on 11/30/2017
 * Project : Driver
 */

public class DriverBuilder {
    private Driver driver;

    private DriverBuilder(@NonNull Builder builder){
        this.driver = builder.driver;
    }

    private Driver getDriver(){
        return driver;
    }

    public static class Builder {
        private Driver driver;

        public Builder(){
            this.driver = new Driver();
        }


        public Builder clientId(@NonNull String clientId){
            this.driver.setClientId(clientId);
            return this;
        }

        public Builder email(@NonNull String email){
            this.driver.setEmail(email);
            return this;
        }

        public Builder photoUrl(@NonNull String photoUrl){
            this.driver.setPhotoUrl(photoUrl);
            return this;
        }

        public Builder nameSettings(@NonNull NameSettings nameSettings){
            this.driver.setNameSettings(nameSettings);
            return this;
        }

        public Builder userRoles(@NonNull UserRoles userRoles){
            this.driver.setUserRoles(userRoles);
            return this;
        }

        public Builder cloudDatabaseSettings(@NonNull CloudDatabaseSettings cloudDatabaseSettings){
            this.driver.setCloudDatabaseSettings(cloudDatabaseSettings);
            return this;
        }

        private void validate(@NonNull Driver driver){

        }

        public Driver build(){
            Driver driver = new DriverBuilder(this).getDriver();
            validate(driver);
            return driver;
        }
    }

}
