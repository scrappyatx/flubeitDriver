/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.libbatchdata.entities.ImageDetectionResults;
import it.flube.libbatchdata.entities.ImageMatchResults;
import it.flube.libbatchdata.entities.ImageMatchSettings;

/**
 * Created on 8/9/2018
 * Project : Driver
 */
public interface CloudImageDetectionInterface {

    ///
    /// detect image label request
    ///
    void detectImageLabelRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, CloudDetectImageLabelResponse response);

    interface CloudDetectImageLabelResponse {
        void cloudDetectImageLabelSuccess(ImageDetectionResults imageDetectionResults);

        void cloudDetectImageLabelFailure();
    }

    void matchImageLabelRequest(DeviceImageStorageInterface deviceImageStorage, String absoluteFilename, ImageMatchSettings imageMatchSettings, CloudMatchImageLabelResponse response);

    interface CloudMatchImageLabelResponse {
        void cloudMatchImageLabelSuccess(ImageMatchResults imageMatchResults);

        void cloudMatchImageLabelFailure();
    }
}
