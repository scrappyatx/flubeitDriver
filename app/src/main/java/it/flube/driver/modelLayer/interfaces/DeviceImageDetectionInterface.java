/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.libbatchdata.entities.ImageDetectionResults;
import it.flube.libbatchdata.entities.ImageMatchResults;
import it.flube.libbatchdata.entities.ImageMatchSettings;

/**
 * Created on 2/28/2019
 * Project : Driver
 */
public interface DeviceImageDetectionInterface {
    ///
    /// detect image label request
    ///
    void detectImageLabelRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, DeviceDetectImageLabelResponse response);

    interface DeviceDetectImageLabelResponse {
        void deviceDetectImageLabelSuccess(ImageDetectionResults imageDetectionResults);

        void deviceDetectImageLabelFailure();
    }

    void matchImageLabelRequest(DeviceImageStorageInterface deviceImageStorage, String absoluteFilename, ImageMatchSettings imageMatchSettings, DeviceMatchImageLabelResponse response);

    interface DeviceMatchImageLabelResponse {
        void deviceMatchImageLabelSuccess(ImageMatchResults imageMatchResults);

        void deviceMatchImageLabelFailure();
    }
}
