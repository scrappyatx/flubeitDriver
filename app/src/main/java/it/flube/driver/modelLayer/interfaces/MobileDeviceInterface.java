/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;

/**
 * Created on 7/3/2017
 * Project : Driver
 */

public interface MobileDeviceInterface {

    ///
    /// app data structures
    ///

    DeviceInfo getDeviceInfo();
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
    CloudServerMonitoringInterface getCloudServerMonitoring();


    CloudRealTimeClockInterface getCloudRealTimeClock();

    CloudImageDetectionInterface getCloudImageDetection();
    CloudTextDetectionInterface getCloudTextDetection();



    ///
    /// device services
    ///
    TargetEnvironmentConstants.TargetEnvironment getTargetEnvironment();
    ActiveBatchForegroundServiceInterface getActiveBatchForegroundServiceController();
    DeviceStorageInterface getDeviceStorage();
    DeviceImageStorageInterface getDeviceImageStorage();
    LocationTelemetryInterface getLocationTelemetry();
    UseCaseInterface getUseCaseEngine();

    DeviceImageDetectionInterface getDeviceImageDetection();
    DeviceTextDetectionInterface getDeviceTextDetection();

}
