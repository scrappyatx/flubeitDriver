/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.DisplayDistance;
import it.flube.driver.modelLayer.entities.DisplayTiming;
import it.flube.driver.modelLayer.entities.PotentialEarnings;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;

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

        public Builder guid(@NonNull String guid) {
            this.batch.setGuid(guid);
            return this;
        }


        public Builder title(@NonNull String title) {
            this.batch.setTitle(title);
            return this;
        }

        public Builder iconUrl(@NonNull String iconUrl){
            this.batch.setIconUrl(iconUrl);
            return this;
        }



        public Builder displayTiming(@NonNull DisplayTiming displayTiming) {
            this.batch.setDisplayTiming(displayTiming);
            return this;
        }

        public Builder displayDistance(@NonNull DisplayDistance displayDistance) {
            this.batch.setDisplayDistance(displayDistance);
            return this;
        }

        public Builder potentialEarnings(@NonNull PotentialEarnings potentialEarnings){
            this.batch.setPotentialEarnings(potentialEarnings);
            return this;
        }

        public Builder expectedStartTime(@NonNull Date expectedStartTime){
            this.batch.setExpectedStartTime(expectedStartTime);
            return this;
        }

        public Builder expectedStartTime(@NonNull Date initialTime, @NonNull Integer minutesToAdd){
            this.batch.setExpectedStartTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
            return this;
        }

        public Builder expectedFinishTime(@NonNull Date expectedFinishTime){
            this.batch.setExpectedFinishTime(expectedFinishTime);
            return this;
        }

        public Builder expectedFinishTime(@NonNull Date initialTime, @NonNull Integer minutesToAdd ) {
            this.batch.setExpectedFinishTime(BuilderUtilities.addMinutesToDate(initialTime, minutesToAdd));
            return this;
        }

        private void validate(@NonNull Batch batch) {
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
