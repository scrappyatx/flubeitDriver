/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.entities.DisplayTiming;

/**
 * Created on 9/16/2017
 * Project : Driver
 */

public class DisplayTimingBuilder {
    private DisplayTiming displayTiming;

    private DisplayTimingBuilder(@NonNull Builder builder){
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

        public Builder date(@NonNull String date){
            this.displayTiming.setDate(date);
            return this;
        }

        public Builder duration(@NonNull String duration){
            this.displayTiming.setDuration(duration);
            return this;
        }

        public Builder hours(@NonNull String hours){
            this.displayTiming.setHours(hours);
            return this;
        }

        public Builder offerExpiryDate(@NonNull String offerExpiryDate) {
            this.displayTiming.setOfferExpiryDate(offerExpiryDate);
            return this;
        }

        private void validate(@NonNull DisplayTiming displayTiming){

        }

        public DisplayTiming build(){
            DisplayTiming displayTiming = new DisplayTimingBuilder(this).getDisplayTiming();
            validate(displayTiming);
            return displayTiming;
        }
    }
}
