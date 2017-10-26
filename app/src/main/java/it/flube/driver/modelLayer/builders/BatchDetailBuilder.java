/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.DisplayDistance;
import it.flube.driver.modelLayer.entities.DisplayTiming;
import it.flube.driver.modelLayer.entities.PotentialEarnings;
import it.flube.driver.modelLayer.entities.RouteList;
import it.flube.driver.modelLayer.entities.RouteStop;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;

/**
 * Created on 9/16/2017
 * Project : Driver
 */

public class BatchDetailBuilder {
    private BatchDetail batchDetail;

    private BatchDetailBuilder(Builder builder){
        this.batchDetail = builder.batchDetail;
    }

    private BatchDetail getBatchDetail(){
        return batchDetail;
    }

    public static class Builder{
        private BatchDetail batchDetail;

        public Builder(){
            this.batchDetail = new BatchDetail();
            this.batchDetail.setGuid(BuilderUtilities.generateGuid());
            this.batchDetail.setWorkStatus(BatchDetail.WorkStatus.NOT_STARTED);
            this.batchDetail.setBatchType(BatchDetail.BatchType.MOBILE_DEMO);
            this.batchDetail.setClaimStatus(BatchDetail.ClaimStatus.NOT_CLAIMED);
        }

        public Builder batchGuid(String guid){
            this.batchDetail.setBatchGuid(guid);
            return this;
        }

        public Builder guid(String guid){
            this.batchDetail.setGuid(guid);
            return this;
        }


        public Builder title(String title){
            this.batchDetail.setTitle(title);
            return this;
        }

        public Builder description(String description){
            this.batchDetail.setDescription(description);
            return this;
        }

        public Builder iconUrl(String iconUrl){
            this.batchDetail.setIconUrl(iconUrl);
            return this;
        }

        public Builder displayTiming(DisplayTiming displayTiming){
            this.batchDetail.setDisplayTiming(displayTiming);
            return this;
        }

        public Builder displayDistance(DisplayDistance displayDistance){
            this.batchDetail.setDisplayDistance(displayDistance);
            return this;
        }

        public Builder potentialEarnings(PotentialEarnings potentialEarnings){
            this.batchDetail.setPotentialEarnings(potentialEarnings);
            return this;
        }

        public Builder serviceOrderCount(Integer serviceOrderCount){
            this.batchDetail.setServiceOrderCount(serviceOrderCount);
            return this;
        }

        public Builder routeStopCount(Integer routeStopCount){
            this.batchDetail.setRouteStopCount(routeStopCount);
            return this;
        }

        public Builder workStatus(BatchDetail.WorkStatus workStatus){
            this.batchDetail.setWorkStatus(workStatus);
            return this;
        }

        public Builder batchType(@NonNull BatchDetail.BatchType type) {
            this.batchDetail.setBatchType(type);
            return this;
        }

        public Builder claimStatus(@NonNull BatchDetail.ClaimStatus claimStatus){
            this.batchDetail.setClaimStatus(claimStatus);
            return this;
        }

        public Builder claimedByClientId(@NonNull String claimedByClientId){
            this.batchDetail.setClaimedByClientId(claimedByClientId);
            return this;
        }

        private void validate(BatchDetail batchDetail){

        }

        public BatchDetail build(){
            BatchDetail batchDetail = new BatchDetailBuilder(this).getBatchDetail();
            validate(batchDetail);
            return batchDetail;
        }
    }
}
