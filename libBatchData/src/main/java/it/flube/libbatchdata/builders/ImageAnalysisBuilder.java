/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.ImageAnalysis;
import it.flube.libbatchdata.interfaces.ImageMatchingInterface;

/**
 * Created on 3/7/2019
 * Project : Driver
 */
public class ImageAnalysisBuilder {
    private static final Boolean DEFAULT_DO_DEVICE_IMAGE_LABEL_DETECTION = true;
    private static final Boolean DEFAULT_DO_DEVICE_TEXT_DETECTION = true;
    private static final Boolean DEFAULT_DO_IMAGE_LABEL_MATCHING = false;

    private static final Boolean DEFAULT_DO_CLOUD_IMAGE_LABEL_DETECTION = false;
    private static final Boolean DEFAULT_DO_CLOUD_TEXT_DETECTION = false;
    private static final Boolean DEFAULT_DO_CLOUD_IMAGE_LABEL_MATCHING = false;

    private static final ImageMatchingInterface.MatchPreset DEFAULT_MATCH_PRESET = ImageMatchingInterface.MatchPreset.NONE;

    private ImageAnalysis imageAnalysis;

    private ImageAnalysisBuilder(Builder builder){
        this.imageAnalysis = builder.imageAnalysis;
    }

    private ImageAnalysis getImageAnalysis(){
        return this.imageAnalysis;
    }

    public static class Builder {
        private ImageAnalysis imageAnalysis;

        public Builder(){
            this.imageAnalysis = new ImageAnalysis();

            //set defaults for device activities
            this.imageAnalysis.setDoDeviceImageLabelDetection(DEFAULT_DO_DEVICE_IMAGE_LABEL_DETECTION);
            this.imageAnalysis.setDoDeviceTextDetection(DEFAULT_DO_DEVICE_TEXT_DETECTION);
            this.imageAnalysis.setDoDeviceImageLabelMatching(DEFAULT_DO_IMAGE_LABEL_MATCHING);

            //set defaults for cloud activities
            this.imageAnalysis.setDoCloudTextDetection(DEFAULT_DO_CLOUD_TEXT_DETECTION);
            this.imageAnalysis.setDoCloudImageLabelDetection(DEFAULT_DO_CLOUD_IMAGE_LABEL_DETECTION);
            this.imageAnalysis.setDoCloudImageLabelMatching(DEFAULT_DO_CLOUD_IMAGE_LABEL_MATCHING);

            //set image match settings
            this.imageAnalysis.setImageMatchSettings(new ImageMatchSettingsBuilder.Builder().matchPreset(DEFAULT_MATCH_PRESET).build());
        }

        public Builder doDeviceImageLabelDetection(Boolean doDeviceImageLabelDetection){
            this.imageAnalysis.setDoDeviceImageLabelDetection(doDeviceImageLabelDetection);
            return this;
        }

        public Builder doDeviceTextDetection(Boolean doDeviceTextDetection){
            this.imageAnalysis.setDoDeviceTextDetection(true);
            return this;
        }

        private void validate(ImageAnalysis imageAnalysis){
            //do nothing
        }

        public ImageAnalysis build(){
            ImageAnalysis imageAnalysis = new ImageAnalysisBuilder(this).getImageAnalysis();
            validate(imageAnalysis);
            return imageAnalysis;
        }

    }

}
