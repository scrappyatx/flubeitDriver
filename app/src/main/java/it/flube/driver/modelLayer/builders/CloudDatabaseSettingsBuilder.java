/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.entities.driver.CloudDatabaseSettings;

/**
 * Created on 12/4/2017
 * Project : Driver
 */

public class CloudDatabaseSettingsBuilder {
    private CloudDatabaseSettings cloudDatabaseSettings;

    private CloudDatabaseSettingsBuilder(@NonNull Builder builder){
        this.cloudDatabaseSettings = builder.cloudDatabaseSettings;
    }

    private CloudDatabaseSettings getCloudDatabaseSettings(){
        return this.cloudDatabaseSettings;
    }

    public static class Builder {
        private CloudDatabaseSettings cloudDatabaseSettings;

        public Builder(){
            this.cloudDatabaseSettings = new CloudDatabaseSettings();
        }

        public Builder publicOffersNode(@NonNull String publicOffersNode){
            this.cloudDatabaseSettings.setPublicOffersNode(publicOffersNode);
            return this;
        }

        public Builder personalOffersNode(@NonNull String personalOffersNode){
            this.cloudDatabaseSettings.setPersonalOffersNode(personalOffersNode);
            return this;
        }

        public Builder demoOffersNode(@NonNull String demoOffersNode){
            this.cloudDatabaseSettings.setDemoOffersNode(demoOffersNode);
            return this;
        }

        public Builder scheduledBatchesNode(@NonNull String scheduledBatchesNode){
            this.cloudDatabaseSettings.setScheduledBatchesNode(scheduledBatchesNode);
            return this;
        }

        public Builder activeBatchNode(@NonNull String activeBatchNode){
            this.cloudDatabaseSettings.setActiveBatchNode(activeBatchNode);
            return this;
        }

        public Builder batchDataNode(@NonNull String batchDataNode){
            this.cloudDatabaseSettings.setBatchDataNode(batchDataNode);
            return this;
        }

        private void validate(@NonNull CloudDatabaseSettings cloudDatabaseSettings){

        }

        public CloudDatabaseSettings build(){
            CloudDatabaseSettings cloudDatabaseSettings = new CloudDatabaseSettingsBuilder(this).getCloudDatabaseSettings();
            validate(cloudDatabaseSettings);
            return cloudDatabaseSettings;
        }
    }
}
