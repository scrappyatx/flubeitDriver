/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import it.flube.driver.modelLayer.entities.DisplayTiming;

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

        private void validate(DisplayTiming displayTiming){

        }

        public DisplayTiming build(){
            DisplayTiming displayTiming = new DisplayTimingBuilder(this).getDisplayTiming();
            validate(displayTiming);
            return displayTiming;
        }
    }
}
