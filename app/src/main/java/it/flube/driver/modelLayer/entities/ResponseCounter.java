/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created on 9/29/2017
 * Project : Driver
 */

public class ResponseCounter {
    private AtomicLong countdownLong;
    private AtomicInteger countdown;
    private AtomicBoolean finished;
    private AtomicBoolean usingLong;

    public ResponseCounter(Integer countdown) {
        this.countdown = new AtomicInteger(countdown);
        finished = new AtomicBoolean(false);
        usingLong = new AtomicBoolean(false);
    }

    public ResponseCounter(Long countdownLong) {
        this.countdownLong = new AtomicLong(countdownLong);
        finished = new AtomicBoolean(false);
        usingLong = new AtomicBoolean(true);
    }

    public void onResponse() {
        if (usingLong.get()) {
            countdownLong.decrementAndGet();
            if (countdownLong.get() == 0){
                finished.set(true);
            }

        } else {
            countdown.decrementAndGet();
            if (countdown.get() == 0) {
                finished.set(true);
            }
        }
    }

    public Boolean isFinished(){
        return finished.get();
    }

    public Integer getCount(){
        if (usingLong.get()) {
            return countdownLong.intValue();

        } else {
            return countdown.get();
        }
    }

}

