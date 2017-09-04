/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.Date;

import it.flube.driver.modelLayer.entities.Timestamp;

/**
 * Created on 9/1/2017
 * Project : Driver
 */

public class TimestampBuilder {
    private Timestamp timestamp;

    private TimestampBuilder(@NonNull Builder builder) {
        this.timestamp = builder.timestamp;
    }

    private Timestamp getTimestamp(){
        return timestamp;
    }

    public static class Builder {
        private Timestamp timestamp;

        public Builder(@NonNull Date scheduledTime){
            timestamp = new Timestamp();
            timestamp.setScheduledTime(scheduledTime);
        }

        private void validate(@NonNull Timestamp timestamp){
            // required PRESENT (must not be null)
            if (timestamp.getScheduledTime() == null){
                throw new IllegalStateException("scheduledTime is null");
            }

            //required ABSENT (must be null)
            if (timestamp.getActualTime() != null) {
                throw new IllegalStateException("actualTime is not null");
            }
        }

        public Timestamp build(){
            Timestamp timestamp = new TimestampBuilder(this).getTimestamp();
            validate(timestamp);
            return timestamp;
        }
    }
}
