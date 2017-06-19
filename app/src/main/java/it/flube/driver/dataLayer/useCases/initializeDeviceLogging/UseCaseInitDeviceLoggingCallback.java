/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCases.initializeDeviceLogging;

/**
 * Created on 6/17/2017
 * Project : Driver
 */

public interface UseCaseInitDeviceLoggingCallback {

    void UseCaseInitDeviceLoggingComplete(Boolean resultSuccess, String resultMessage);
}
