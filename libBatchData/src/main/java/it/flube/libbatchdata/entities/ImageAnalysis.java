/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

/**
 * Created on 3/7/2019
 * Project : Driver
 */
public class ImageAnalysis {
    // image label detection
    private Boolean doDeviceImageLabelDetection;
    private Boolean doCloudImageLabelDetection;

    private ImageDetectionResults deviceImageDetectionResults;
    private ImageDetectionResults cloudImageDetectionResults;

    // text detection
    private Boolean doDeviceTextDetection;
    private Boolean doCloudTextDetection;

    private TextDetectionResults deviceTextDetectionResults;
    private TextDetectionResults cloudTextDetectionResults;

    //image label matching
    private Boolean doDeviceImageLabelMatching;
    private Boolean doCloudImageLabelMatching;

    private ImageMatchSettings imageMatchSettings;
    private ImageMatchResults deviceImageMatchResults;
    private ImageMatchResults cloudImageMatchResults;

    public Boolean getDoDeviceImageLabelDetection() {
        return doDeviceImageLabelDetection;
    }

    public void setDoDeviceImageLabelDetection(Boolean doDeviceImageLabelDetection) {
        this.doDeviceImageLabelDetection = doDeviceImageLabelDetection;
    }

    public Boolean getDoCloudImageLabelDetection() {
        return doCloudImageLabelDetection;
    }

    public void setDoCloudImageLabelDetection(Boolean doCloudImageLabelDetection) {
        this.doCloudImageLabelDetection = doCloudImageLabelDetection;
    }

    public ImageDetectionResults getDeviceImageDetectionResults() {
        return deviceImageDetectionResults;
    }

    public void setDeviceImageDetectionResults(ImageDetectionResults deviceImageDetectionResults) {
        this.deviceImageDetectionResults = deviceImageDetectionResults;
    }

    public ImageDetectionResults getCloudImageDetectionResults() {
        return cloudImageDetectionResults;
    }

    public void setCloudImageDetectionResults(ImageDetectionResults cloudImageDetectionResults) {
        this.cloudImageDetectionResults = cloudImageDetectionResults;
    }

    public Boolean getDoDeviceTextDetection() {
        return doDeviceTextDetection;
    }

    public void setDoDeviceTextDetection(Boolean doDeviceTextDetection) {
        this.doDeviceTextDetection = doDeviceTextDetection;
    }

    public Boolean getDoCloudTextDetection() {
        return doCloudTextDetection;
    }

    public void setDoCloudTextDetection(Boolean doCloudTextDetection) {
        this.doCloudTextDetection = doCloudTextDetection;
    }

    public TextDetectionResults getDeviceTextDetectionResults() {
        return deviceTextDetectionResults;
    }

    public void setDeviceTextDetectionResults(TextDetectionResults deviceTextDetectionResults) {
        this.deviceTextDetectionResults = deviceTextDetectionResults;
    }

    public TextDetectionResults getCloudTextDetectionResults() {
        return cloudTextDetectionResults;
    }

    public void setCloudTextDetectionResults(TextDetectionResults cloudTextDetectionResults) {
        this.cloudTextDetectionResults = cloudTextDetectionResults;
    }

    public Boolean getDoDeviceImageLabelMatching() {
        return doDeviceImageLabelMatching;
    }

    public void setDoDeviceImageLabelMatching(Boolean doDeviceImageLabelMatching) {
        this.doDeviceImageLabelMatching = doDeviceImageLabelMatching;
    }

    public Boolean getDoCloudImageLabelMatching() {
        return doCloudImageLabelMatching;
    }

    public void setDoCloudImageLabelMatching(Boolean doCloudImageLabelMatching) {
        this.doCloudImageLabelMatching = doCloudImageLabelMatching;
    }

    public ImageMatchSettings getImageMatchSettings() {
        return imageMatchSettings;
    }

    public void setImageMatchSettings(ImageMatchSettings imageMatchSettings) {
        this.imageMatchSettings = imageMatchSettings;
    }

    public ImageMatchResults getDeviceImageMatchResults() {
        return deviceImageMatchResults;
    }

    public void setDeviceImageMatchResults(ImageMatchResults deviceImageMatchResults) {
        this.deviceImageMatchResults = deviceImageMatchResults;
    }

    public ImageMatchResults getCloudImageMatchResults() {
        return cloudImageMatchResults;
    }

    public void setCloudImageMatchResults(ImageMatchResults cloudImageMatchResults) {
        this.cloudImageMatchResults = cloudImageMatchResults;
    }
}
