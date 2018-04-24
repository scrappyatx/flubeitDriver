/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.batch;

import java.util.Date;

import it.flube.libbatchdata.builders.BuilderUtilities;
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



        public Builder displayTiming(DisplayTiming displayTiming) {
            this.batch.setDisplayTiming(displayTiming);
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

        public Builder expectedStartTime(Date expectedStartTime){
            this.batch.setExpectedStartTime(expectedStartTime);
            return this;
        }

        public Builder expectedStartTime(Date initialTime, Integer minutesToAdd){
            this.batch.setExpectedStartTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
            return this;
        }

        public Builder expectedFinishTime(Date expectedFinishTime){
            this.batch.setExpectedFinishTime(expectedFinishTime);
            return this;
        }

        public Builder expectedFinishTime(Date initialTime, Integer minutesToAdd ) {
            this.batch.setExpectedFinishTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
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

        }

        public Batch build(){
            Batch batch = new BatchBuilder(this).getBatch();
            validate(batch);
            return batch;
        }
    }
}
