/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.deviceImageDetection;

import it.flube.driver.modelLayer.interfaces.DeviceImageDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.libbatchdata.entities.ImageMatchSettings;
import timber.log.Timber;

/**
 * Created on 3/1/2019
 * Project : Driver
 */
public class FirebaseDeviceImageLabelWrapper implements DeviceImageDetectionInterface {
    public static final String TAG="FirebaseDeviceImageLabelWrapper";

    public void detectImageLabelRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, DeviceDetectImageLabelResponse response){
        Timber.tag(TAG).d("detectImageLabelRequest");
        new FirebaseDeviceImageLabelStart().imageLabelRequest(deviceImageStorage, imageAbsoluteFilename, response);
    }

    public void matchImageLabelRequest(DeviceImageStorageInterface deviceImageStorage, String absoluteFilename, ImageMatchSettings imageMatchSettings, DeviceMatchImageLabelResponse response){
        Timber.tag(TAG).d("matchImageLabelRequest");
        new FirebaseDeviceImageMatchStart().imageMatchRequest(deviceImageStorage, absoluteFilename, imageMatchSettings, response);
    }
}
