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

    ///
    /// app data structures
    ///

    DeviceInfo getDeviceInfo();
    AppUserInterface getUser();
    ActiveBatchInterface getActiveBatch();
    OffersInterface getOfferLists();

    ///
    /// cloud services
    ///

    CloudConfigInterface getCloudConfig();
    CloudAuthInterface getCloudAuth();
    CloudImageStorageInterface getCloudImageStorage();
    CloudUserProfileInterface getCloudUserProfile();
    CloudUserAndDeviceInfoStorageInterface getCloudUserAndDeviceInfoStorage();

    CloudDemoOfferInterface getCloudDemoOffer();
    CloudPersonalOfferInterface getCloudPersonalOffer();
    CloudPublicOfferInterface getCloudPublicOffer();

    CloudScheduledBatchInterface getCloudScheduledBatch();

    CloudActiveBatchInterface getCloudActiveBatch();

    CloudImageDetectionInterface getCloudImageDetection();

    ///
    /// device services
    ///
    ActiveBatchForegroundServiceInterface getActiveBatchForegroundServiceController();
    DeviceStorageInterface getDeviceStorage();
    DeviceImageStorageInterface getDeviceImageStorage();
    LocationTelemetryInterface getLocationTelemetry();
    UseCaseInterface getUseCaseEngine();

}
