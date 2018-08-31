/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;



import java.util.Date;

import it.flube.libbatchdata.entities.Timestamp;


/**
 * Created on 9/1/2017
 * Project : Driver
 */

public class TimestampBuilder {
    private Timestamp timestamp;

    private TimestampBuilder(Builder builder) {
        this.timestamp = builder.timestamp;
    }

    private Timestamp getTimestamp(){
        return timestamp;
    }

    public static class Builder {
        private Timestamp timestamp;

        public Builder(){
            timestamp = new Timestamp();
        }

        /// can set scheduledTime in either millis or Date
        public Builder scheduledTime(Long scheduledTime){
            this.timestamp.setScheduledTime(scheduledTime);
            return this;
        }

        public Builder scheduledTime(Date scheduledTime){
            this.timestamp.setScheduledTime(BuilderUtilities.convertDateToMillis(scheduledTime));
            return this;
        }


        private void validate(Timestamp timestamp){
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
