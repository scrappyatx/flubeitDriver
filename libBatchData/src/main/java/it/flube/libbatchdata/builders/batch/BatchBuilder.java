/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.libbatchdata.entities.DisplayDistance;
import it.flube.libbatchdata.entities.DisplayTiming;
import it.flube.libbatchdata.entities.PotentialEarnings;


/**
 * Created on 9/4/2017
 * Project : Driver
 */

public class BatchBuilder {
    private Batch batch;

    private BatchBuilder(Builder builder) {
        this.batch = builder.batch;
    }

    private Batch getBatch() {
        return batch;
    }

    public static class Builder {
        private Batch batch;

        public Builder(){
            this.batch = new Batch();
            this.batch.setGuid(BuilderUtilities.generateGuid());

        }

        public Builder guid(String guid) {
            this.batch.setGuid(guid);
            return this;
        }


        public Builder title(String title) {
            this.batch.setTitle(title);
            return this;
        }

        public Builder iconUrl(String iconUrl){
            this.batch.setIconUrl(iconUrl);
            return this;
        }


        public Builder displayDistance(DisplayDistance displayDistance) {
            this.batch.setDisplayDistance(displayDistance);
            return this;
        }

        public Builder potentialEarnings(PotentialEarnings potentialEarnings){
            this.batch.setPotentialEarnings(potentialEarnings);
            return this;
        }

        //// expectedStartTime, can be set in either millis or date
        public Builder expectedStartTime(Long expectedStartTime){
            this.batch.setExpectedStartTime(expectedStartTime);
            return this;
        }

        public Builder expectedStartTime(Date expectedStartTime){
            this.batch.setExpectedStartTime(BuilderUtilities.convertDateToMillis(expectedStartTime));
            return this;
        }

        public Builder expectedStartTime(Date initialTime, Integer minutesToAdd){
            this.batch.setExpectedStartTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
            return this;
        }

        public Builder expectedStartTime(Long initialTime, Integer minutesToAdd){
            this.batch.setExpectedStartTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
            return this;
        }

        //// expectedFinishTime, can be set in either millis or date
        public Builder expectedFinishTime(Long expectedFinishTime){
            this.batch.setExpectedFinishTime(expectedFinishTime);
            return this;
        }

        public Builder expectedFinishTime(Date expectedFinishTime){
            this.batch.setExpectedFinishTime(BuilderUtilities.convertDateToMillis(expectedFinishTime));
            return this;
        }

        public Builder expectedFinishTime(Date initialTime, Integer minutesToAdd ) {
            this.batch.setExpectedFinishTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
            return this;
        }

        public Builder expectedFinishTime(Long initialTime, Integer minutesToAdd) {
            this.batch.setExpectedFinishTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
            return this;
        }

        /// offer expiry time, can be set in either millis or date
        public Builder offerExpiryTime(Long offerExpiryTime){
            this.batch.setOfferExpiryTime(offerExpiryTime);
            return this;
        }

        public Builder offerExpiryTime(Date offerExpiryTime){
            this.batch.setOfferExpiryTime(BuilderUtilities.convertDateToMillis(offerExpiryTime));
            return this;
        }

        private void validate(Batch batch) {
            // required PRESENT (must not be null)
            if (batch.getGuid() == null) {
                throw new IllegalStateException("batch GUID is null");
            }

            if (batch.getTitle() == null) {
                //throw new IllegalStateException("batch title is null");
            }


            if (batch.getPotentialEarnings() == null) {
                //throw new IllegalStateException("batch potentialEarnings is null");
            }

            if (batch.getOfferExpiryTime() == null) {
                //throw new IllegalStateException("offer expiry is null");
            }

        }

        public Batch build(){
            Batch batch = new BatchBuilder(this).getBatch();
            validate(batch);
            return batch;
        }
    }
}
