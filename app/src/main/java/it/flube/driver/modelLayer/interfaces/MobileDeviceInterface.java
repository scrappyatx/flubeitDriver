/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.DeviceInfo;

/**
 * Created on 7/3/2017
 * Project : Driver
 */

public interface MobileDeviceInterface {

    void deviceInfoRequest(DeviceInfoRequestComplete response);

    interface DeviceInfoRequestComplete {
        void deviceInfoSuccess(DeviceInfo deviceInfo);

        void deviceInfoFailure(Exception exception);
    }


    ActiveBatchInterface getActiveBatch();

    OffersInterface getOfferLists();

    AppLoggingInterface getAppLogging();

    AppRemoteConfigInterface getAppRemoteConfig();

    CloudAuthInterface getCloudAuth();

    CloudStorageInterface getCloudStorage();

    CloudDatabaseInterface getCloudDatabase();

    DeviceStorageInterface getDeviceStorage();

    UseCaseInterface getUseCaseEngine();

    UserProfileInterface getUserProfile();

    AppUserInterface getUser();

    LocationTelemetryInterface getLocationTelemetry();

    RealtimeMessagingInterface.Connection getRealtimeConnection();

    RealtimeMessagingInterface.OfferChannel getRealtimeOfferMessages();

    RealtimeMessagingInterface.BatchChannel getRealtimeBatchMessages();

}
