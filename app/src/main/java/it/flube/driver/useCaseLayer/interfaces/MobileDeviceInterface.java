/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.interfaces;

import java.util.concurrent.ExecutorService;

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

    RealtimeMessagingInterface.OfferMessages getRealtimeOfferMessages();

}
