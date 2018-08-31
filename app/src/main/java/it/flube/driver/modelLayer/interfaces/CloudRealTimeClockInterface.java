/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.driver.Driver;

/**
 * Created on 8/21/2018
 * Project : Driver
 */
public interface CloudRealTimeClockInterface {
    void getServerTimeRequest(Driver driver, ServerTimeResponse response);

    interface ServerTimeResponse {
        void getServerTimeSuccess(Long serverTimeInMillis);

        void getServerTimeFailure();
    }
}
