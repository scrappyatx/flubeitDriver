/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.batch;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.DriverInfoBuilder;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.Customer;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.DisplayDistance;
import it.flube.libbatchdata.entities.DisplayTiming;
import it.flube.libbatchdata.entities.PotentialEarnings;

/**
 * Created on 9/16/2017
 * Project : Driver
 */

public class BatchDetailBuilder {
    private BatchDetail batchDetail;

    private static final Integer DEFAULT_EARLIEST_START_MINUTES_PRIOR = 15;
    private static final Integer DEFAULT_LATEST_START_MINUTES_AFTER = 5;

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
            this.batchDetail.setEarliestStartMinutesPrior(DEFAULT_EARLIEST_START_MINUTES_PRIOR);
            this.batchDetail.setLatestStartMinutesAfter(DEFAULT_LATEST_START_MINUTES_AFTER);

            //setup driver info
            this.batchDetail.setDriverInfo(new DriverInfoBuilder.Builder().build());

            // a batch can have multiple contact persons
            this.batchDetail.setContactPersons(new HashMap<String, ContactPerson>());
            this.batchDetail.setContactPersonsByServiceOrder(new HashMap<String, Map<String, ContactPerson>>());
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

        public Builder batchType(BatchDetail.BatchType type) {
            this.batchDetail.setBatchType(type);
            return this;
        }

        public Builder claimStatus(BatchDetail.ClaimStatus claimStatus){
            this.batchDetail.setClaimStatus(claimStatus);
            return this;
        }

        public Builder claimedByClientId(String claimedByClientId){
            this.batchDetail.setClaimedByClientId(claimedByClientId);
            return this;
        }

        /// expectedStartTime can be set in millis or Date
        public Builder expectedStartTime(Date expectedStartTime){
            this.batchDetail.setExpectedStartTime(BuilderUtilities.convertDateToMillis(expectedStartTime));
            return this;
        }

        public Builder expectedStartTime(Long expectedStartTime){
            this.batchDetail.setExpectedStartTime(expectedStartTime);
            return this;
        }

        public Builder expectedStartTime(Date initialTime, Integer minutesToAdd){
            this.batchDetail.setExpectedStartTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
            return this;
        }

        public Builder expectedStartTime(Long initialTime, Integer minutesToAdd){
            this.batchDetail.setExpectedStartTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
            return this;
        }

        //// expectedFinishTime can be set in millis or Date
        public Builder expectedFinishTime(Date expectedFinishTime){
            this.batchDetail.setExpectedFinishTime(BuilderUtilities.convertDateToMillis(expectedFinishTime));
            return this;
        }

        public Builder expectedFinishTime(Long expectedFinishTime){
            this.batchDetail.setExpectedFinishTime(expectedFinishTime);
            return this;
        }

        public Builder expectedFinishTime(Date initialTime, Integer minutesToAdd ) {
            this.batchDetail.setExpectedFinishTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
            return this;
        }

        public Builder expectedFinishTime(Long initialTime, Integer minutesToAdd ) {
            this.batchDetail.setExpectedFinishTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
            return this;
        }

        /// offerExpiryTime can be set in millis or Date
        public Builder offerExpiryTime(Date offerExpiryTime){
            this.batchDetail.setOfferExpiryTime(BuilderUtilities.convertDateToMillis(offerExpiryTime));
            return this;
        }

        public Builder offerExpiryTime(Long offerExpiryTime){
            this.batchDetail.setOfferExpiryTime(offerExpiryTime);
            return this;
        }


        public Builder earliestStartMinutesPrior(Integer earliestStartMinutesPrior){
            this.batchDetail.setEarliestStartMinutesPrior(earliestStartMinutesPrior);
            return this;
        }

        public Builder latestStartMinutesAfter(Integer latestStartMinutesAfter){
            this.batchDetail.setLatestStartMinutesAfter(latestStartMinutesAfter);
            return this;
        }

        public Builder customer(Customer customer){
            this.batchDetail.setCustomer(customer);
            return this;
        }

        private void validate(BatchDetail batchDetail){
            if (batchDetail.getGuid() == null) {
                throw new IllegalStateException("batch GUID is null");
            }

            if (batchDetail.getExpectedStartTime() == null) {
                //throw new IllegalStateException("expected start time is null");
            }

            if (batchDetail.getOfferExpiryTime() == null) {
                //throw new IllegalStateException("offer expiry is null");
            }

            if (batchDetail.getEarliestStartMinutesPrior() == null){
                throw new IllegalStateException("earliestStartMinutesPrior is null");
            }

            if (batchDetail.getLatestStartMinutesAfter() == null){
                throw new IllegalStateException("latestStartMinutesAfter is null");
            }

            if (batchDetail.getCustomer() == null){
                //throw new IllegalStateException("customer is null");
            }
        }

        public BatchDetail build(){
            BatchDetail batchDetail = new BatchDetailBuilder(this).getBatchDetail();
            validate(batchDetail);
            return batchDetail;
        }
    }
}
