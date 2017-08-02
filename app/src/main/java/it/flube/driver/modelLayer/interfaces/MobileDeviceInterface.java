/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

/**
 * Created on 7/3/2017
 * Project : Driver
 */

public interface MobileDeviceInterface {


    AppLoggingInterface getAppLogging();

    AppRemoteConfigInterface getAppRemoteConfig();

    CloudAuthInterface getCloudAuth();

    CloudStorageInterface getCloudStorage();

    CloudDatabaseInterface getCloudDatabase();

    DeviceStorageInterface getDeviceStorage();

    UserProfileInterface getUserProfile();

    AppUserInterface getUser();

    LocationTelemetryInterface getLocationTelemetry();

    RealtimeMessagingInterface.Connection getRealtimeConnection();

    RealtimeMessagingInterface.OfferChannel getRealtimeOfferMessages();

    RealtimeMessagingInterface.BatchChannel getRealtimeBatchMessages();

}
