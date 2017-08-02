/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public interface AppRemoteConfigInterface {

    void getUpdatedValuesFromRemoteServerRequest(Response response);

    String getDriverProfileUrl();

    String getRealtimeMessagingAuthTokenUrl();

    String getCloudStorageAuthTokenUrl();

    Boolean getLoggingDebugActive();

    Boolean getLoggingReleaseActive();

    Boolean getErrorReportingDebugActive();

    Boolean getErrorReportingReleaseActive();

    String getRealtimeMessagingLookingForOffersChannelName();

    String getRealtimeMessagingBatchActivityChannelName();

    String getRealtimeMessagingLookingForOffersDemoChannelName();

    public interface Response {
        void getUpdatedValuesFromRemoteServerComplete();
    }


}
