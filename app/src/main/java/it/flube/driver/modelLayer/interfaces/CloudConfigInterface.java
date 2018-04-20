/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public interface CloudConfigInterface {

    String getDriverProfileUrl();

    String getRealtimeMessagingAuthTokenUrl();

    String getCloudStorageAuthTokenUrl();

    String getCloudDatabaseBaseNodePublicOffers();

    String getCloudDatabaseBaseNodePersonalOffers();

    String getCloudDatabaseBaseNodeDemoOffers();

    String getCloudDatabaseBaseNodeScheduledBatches();

    String getCloudDatabaseBaseNodeActiveBatch();

    String getCloudDatabaseBaseNodeBatchData();

    String getCloudDatabaseBaseNodeUserData();

    String getCloudDatabaseBaseNodeDeviceData();

    Boolean getLoggingDebugActive();

    Boolean getLoggingReleaseActive();

    Boolean getErrorReportingDebugActive();

    Boolean getErrorReportingReleaseActive();

    String getRealtimeMessagingLookingForOffersChannelName();

    String getRealtimeMessagingBatchActivityChannelName();

    String getRealtimeMessagingLookingForOffersDemoChannelName();



}
