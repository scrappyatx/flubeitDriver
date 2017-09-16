/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

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
            batch = new Batch();
            batch.setGUID(UUID.randomUUID().toString());
            batch.setStatus(Batch.BatchStatus.NOT_STARTED);
            batch.setType(Batch.BatchType.MOBILE_DEMO);
            batch.setServiceOrderList(new ArrayList<ServiceOrder>());
            batch.setServiceOrderIndex(0);
        }

        public Builder guid(@NonNull String guid) {
            this.batch.setGUID(guid);
            return this;
        }

        public Builder status(@NonNull Batch.BatchStatus status){
            this.batch.setStatus(status);
            return this;
        }

        public Builder type(@NonNull Batch.BatchType type) {
            this.batch.setType(type);
            return this;
        }

        public Builder title(@NonNull String title) {
            this.batch.setTitle(title);
            return this;
        }

        public Builder description(@NonNull String description) {
            this.batch.setDescription(description);
            return this;
        }

        private Date addMinutesToDate(@NonNull Date initialDate, @NonNull Integer minutesToAdd){
            Calendar cal = Calendar.getInstance();
            cal.setTime(initialDate);
            cal.add(Calendar.MINUTE, minutesToAdd);
            return cal.getTime();
        }

        public Builder startTime(@NonNull Date startTime) {
            this.batch.setStartTime(new TimestampBuilder.Builder(startTime).build());
            return this;
        }

        public Builder startTime(@NonNull Date startTime, @NonNull Integer minutesToAdd) {
            this.batch.setStartTime(new TimestampBuilder.Builder(addMinutesToDate(startTime, minutesToAdd)).build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime) {
            this.batch.setFinishTime(new TimestampBuilder.Builder(finishTime).build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime, @NonNull Integer minutesToAdd) {
            this.batch.setStartTime(new TimestampBuilder.Builder(addMinutesToDate(finishTime, minutesToAdd)).build());
            return this;
        }


        public Builder potentialEarnings(@NonNull PotentialEarnings potentialEarnings){
            this.batch.setPotentialEarnings(potentialEarnings);
            return this;
        }

        public Builder addServiceOrder(@NonNull Integer index, @NonNull ServiceOrder serviceOrder){
            this.batch.getServiceOrderList().add(index, serviceOrder);
            return this;
        }

        private void validate(@NonNull Batch batch) {
            // required PRESENT (must not be null)
            if (batch.getGUID() == null) {
                throw new IllegalStateException("GUID is null");
            }

            if (batch.getTitle() == null) {
                throw new IllegalStateException("title is null");
            }

            if (batch.getDescription() == null) {
                throw new IllegalStateException("description is null");
            }

            if (batch.getStartTime() == null) {
                throw new IllegalStateException("startTime is null");
            }

            if (batch.getFinishTime() == null) {
                throw new IllegalStateException("finishTime is null");
            }

            if (batch.getPotentialEarnings() == null) {
                throw new IllegalStateException("potentialEarnings is null");
            }

            if (batch.getServiceOrderList() == null) {
                throw new IllegalStateException("serviceOrderList is null");
            } else {
                if (batch.getServiceOrderList().isEmpty()) {
                    throw new IllegalStateException("serviceOrderList is EMPTY");
                }
            }
        }

        public Batch build(){
            Batch batch = new BatchBuilder(this).getBatch();
            validate(batch);
            return batch;
        }
    }
}
