/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCases.initializeRemoteLoggingAndErrorReporting;

/**
 * Created on 6/16/2017
 * Project : Driver
 */

public interface UseCaseInitRemoteLoggingAndErrorReportingCallback {

    void UseCaseInitRemoteLoggingAndErrorReportingComplete(Boolean resultSuccess, String resultMessage);

}
