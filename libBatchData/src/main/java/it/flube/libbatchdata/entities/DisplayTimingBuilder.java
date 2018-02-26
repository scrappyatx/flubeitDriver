/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

/**
 * Created on 9/16/2017
 * Project : Driver
 */

public class DisplayTimingBuilder {
    private DisplayTiming displayTiming;

    private DisplayTimingBuilder(Builder builder){
        this.displayTiming = builder.displayTiming;
    }

    private DisplayTiming getDisplayTiming(){
        return displayTiming;
    }

    public static class Builder {
        private DisplayTiming displayTiming;

        public Builder(){
            displayTiming = new DisplayTiming();
        }

        public Builder date(String date){
            this.displayTiming.setDate(date);
            return this;
        }

        public Builder duration(String duration){
            this.displayTiming.setDuration(duration);
            return this;
        }

        public Builder hours(String hours){
            this.displayTiming.setHours(hours);
            return this;
        }

        public Builder offerExpiryDate(String offerExpiryDate) {
            this.displayTiming.setOfferExpiryDate(offerExpiryDate);
            return this;
        }

        private void validate(DisplayTiming displayTiming){

        }

        public DisplayTiming build(){
            DisplayTiming displayTiming = new DisplayTimingBuilder(this).getDisplayTiming();
            validate(displayTiming);
            return displayTiming;
        }
    }
}
