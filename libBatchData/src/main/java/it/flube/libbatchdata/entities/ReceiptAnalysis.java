/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

/**
 * Created on 3/7/2019
 * Project : Driver
 */
public class ReceiptAnalysis {
    /// what types of Ocr recognition will be attempted
    private Boolean doDeviceOcrRecognition;
    private Boolean doCloudOcrRecognition;

    /// provides settings on extracting the desired fields from the results of the raw text OCR
    private ReceiptOcrSettings receiptOcrSettings;

    /// provides settings on fields the user must validate
    private ReceiptValidationSettings receiptValidationSettings;

    /// stores the results of the OCR analysis
    private ReceiptOcrResults deviceOcrResults;
    private ReceiptOcrResults cloudOcrResults;

    /// stores the final validated results
    private ReceiptValidationResults receiptValidationResults;

    public Boolean getDoDeviceOcrRecognition() {
        return doDeviceOcrRecognition;
    }

    public void setDoDeviceOcrRecognition(Boolean doDeviceOcrRecognition) {
        this.doDeviceOcrRecognition = doDeviceOcrRecognition;
    }

    public Boolean getDoCloudOcrRecognition() {
        return doCloudOcrRecognition;
    }

    public void setDoCloudOcrRecognition(Boolean doCloudOcrRecognition) {
        this.doCloudOcrRecognition = doCloudOcrRecognition;
    }

    public ReceiptOcrSettings getReceiptOcrSettings() {
        return receiptOcrSettings;
    }

    public void setReceiptOcrSettings(ReceiptOcrSettings receiptOcrSettings) {
        this.receiptOcrSettings = receiptOcrSettings;
    }

    public ReceiptValidationSettings getReceiptValidationSettings() {
        return receiptValidationSettings;
    }

    public void setReceiptValidationSettings(ReceiptValidationSettings receiptValidationSettings) {
        this.receiptValidationSettings = receiptValidationSettings;
    }

    public ReceiptOcrResults getDeviceOcrResults() {
        return deviceOcrResults;
    }

    public void setDeviceOcrResults(ReceiptOcrResults deviceOcrResults) {
        this.deviceOcrResults = deviceOcrResults;
    }

    public ReceiptOcrResults getCloudOcrResults() {
        return cloudOcrResults;
    }

    public void setCloudOcrResults(ReceiptOcrResults cloudOcrResults) {
        this.cloudOcrResults = cloudOcrResults;
    }

    public ReceiptValidationResults getReceiptValidationResults() {
        return receiptValidationResults;
    }

    public void setReceiptValidationResults(ReceiptValidationResults receiptValidationResults) {
        this.receiptValidationResults = receiptValidationResults;
    }
}
