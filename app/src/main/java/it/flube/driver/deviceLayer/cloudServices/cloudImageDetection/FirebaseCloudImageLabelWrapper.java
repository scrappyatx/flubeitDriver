/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageDetection;

import it.flube.driver.modelLayer.interfaces.CloudImageDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.libbatchdata.entities.ImageMatchSettings;
import timber.log.Timber;

/**
 * Created on 8/9/2018
 * Project : Driver
 */
public class FirebaseCloudImageLabelWrapper implements CloudImageDetectionInterface {
    public static final String TAG="FirebaseCloudImageLabelWrapper";

    public void detectImageLabelRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, CloudDetectImageLabelResponse response){
        Timber.tag(TAG).d("detectImageLabelRequest");
        new FirebaseCloudImageLabelStart().imageLabelRequest(deviceImageStorage, imageAbsoluteFilename, response);
    }

    public void matchImageLabelRequest(DeviceImageStorageInterface deviceImageStorage, String absoluteFilename, ImageMatchSettings imageMatchSettings, CloudMatchImageLabelResponse response){
        Timber.tag(TAG).d("matchImageLabelRequest");
        new FirebaseCloudImageMatchStart().imageMatchRequest(deviceImageStorage, absoluteFilename, imageMatchSettings, response);
    }
}
