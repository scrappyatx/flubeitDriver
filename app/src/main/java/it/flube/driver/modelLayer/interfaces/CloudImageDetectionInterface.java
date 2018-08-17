/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.entities.ImageLabel;

/**
 * Created on 8/9/2018
 * Project : Driver
 */
public interface CloudImageDetectionInterface {

    ///
    /// detect image label request
    ///
    void detectImageLabelRequest(Bitmap bitmap, DetectImageLabelResponse response);

    interface DetectImageLabelResponse {
        void detectImageLabelSuccess(HashMap<String, ImageLabel> imageLabelMap);

        void detectImageLabelFailure();
    }

    ///
    /// detect image text request
    ///
    void detectImageTextRequest(Bitmap bitmap, DetectImageTextResponse response);

    interface DetectImageTextResponse {
        void detectImageTextSuccess(HashMap<String, String> textMap);

        void detectImageTextFailure();
    }



}
