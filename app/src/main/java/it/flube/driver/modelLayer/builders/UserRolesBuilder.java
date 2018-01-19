/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.entities.driver.UserRoles;

/**
 * Created on 12/4/2017
 * Project : Driver
 */

public class UserRolesBuilder {
    private UserRoles userRoles;

    private UserRolesBuilder(@NonNull Builder builder){
        this.userRoles = builder.userRoles;
    }

    private UserRoles getUserRoles(){
        return this.userRoles;
    }
    public static class Builder {
        private UserRoles userRoles;

        public Builder(){
            this.userRoles = new UserRoles();
        }

        public Builder qa(@NonNull Boolean qa){
            this.userRoles.setQa(qa);
            return this;
        }

        public Builder dev(@NonNull Boolean dev){
            this.userRoles.setDev(dev);
            return this;
        }

        private void validate(UserRoles userRoles){

        }

        public UserRoles build(){
            UserRoles userRoles = new UserRolesBuilder(this).getUserRoles();
            validate(userRoles);
            return userRoles;
        }
    }
}
