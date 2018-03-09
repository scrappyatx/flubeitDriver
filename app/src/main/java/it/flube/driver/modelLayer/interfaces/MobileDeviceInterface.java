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

    String getDeviceGuid();

    void deviceInfoRequest(DeviceInfoRequestComplete response);

    interface DeviceInfoRequestComplete {
        void deviceInfoSuccess(DeviceInfo deviceInfo);

        void deviceInfoFailure(Exception exception);
    }

    ActiveBatchInterface getActiveBatch();

    ActiveBatchForegroundServiceInterface getActiveBatchForegroundServiceController();

    OffersInterface getOfferLists();

    AppLoggingInterface getAppLogging();

    AppRemoteConfigInterface getAppRemoteConfig();

    CloudAuthInterface getCloudAuth();

    CloudImageStorageInterface getCloudStorage();

    CloudDatabaseInterface getCloudDatabase();

    DeviceStorageInterface getDeviceStorage();

    DeviceImageStorageInterface getDeviceImageStorage();

    UseCaseInterface getUseCaseEngine();

    UserProfileInterface getUserProfile();

    AppUserInterface getUser();

    LocationTelemetryInterface getLocationTelemetry();

    //RealtimeMessagingInterface.Connection getRealtimeConnection();

    //RealtimeMessagingInterface.OfferChannel getRealtimeOfferMessages();

    //RealtimeMessagingInterface.BatchChannel getRealtimeBatchMessages();

    //RealtimeMessagingInterface.ActiveBatchChannel getRealtimeActiveBatchMessages();

}
