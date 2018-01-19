/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.entities.driver.NameSettings;

/**
 * Created on 12/4/2017
 * Project : Driver
 */

public class NameSettingsBuilder {
    private NameSettings nameSettings;

    private NameSettingsBuilder(@NonNull Builder builder){
        this.nameSettings = builder.nameSettings;
    }

    private NameSettings getNameSettings(){
        return this.nameSettings;
    }

    public static class Builder {
        private NameSettings nameSettings;

        public Builder(){
            this.nameSettings = new NameSettings();
        }

        public Builder firstName(@NonNull String firstName){
            this.nameSettings.setFirstName(firstName);
            return this;
        }

        public Builder lastName(@NonNull String lastName){
            this.nameSettings.setLastName(lastName);
            return this;
        }

        public Builder displayName(@NonNull String displayName){
            this.nameSettings.setDisplayName(displayName);
            return this;
        }

        private void validate(@NonNull NameSettings nameSettings){

        }

        public NameSettings build(){
            NameSettings nameSettings = new NameSettingsBuilder(this).getNameSettings();
            validate(nameSettings);
            return nameSettings;
        }

    }
}
