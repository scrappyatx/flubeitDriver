/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.ReceiptAnalysis;
import it.flube.libbatchdata.entities.ReceiptOcrResults;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import it.flube.libbatchdata.entities.ReceiptValidationSettings;

/**
 * Created on 3/8/2019
 * Project : Driver
 */
public class ReceiptAnalysisBuilder {
    private static final Boolean DEFAULT_DO_DEVICE_OCR_RECOGNITION = true;
    private static final Boolean DEFAULT_DO_CLOUD_OCR_RECOGNITION = false;

    private ReceiptAnalysis receiptAnalysis;

    private ReceiptAnalysisBuilder(Builder builder){
        this.receiptAnalysis = builder.receiptAnalysis;
    }

    private ReceiptAnalysis getReceiptAnalysis(){
        return this.receiptAnalysis;
    }

    public static class Builder {
        private ReceiptAnalysis receiptAnalysis;

        public Builder(){
            this.receiptAnalysis = new ReceiptAnalysis();

            //set defaults for device & cloud ocr recognition
            this.receiptAnalysis.setDoCloudOcrRecognition(DEFAULT_DO_CLOUD_OCR_RECOGNITION);
            this.receiptAnalysis.setDoDeviceOcrRecognition(DEFAULT_DO_DEVICE_OCR_RECOGNITION);

            //set defaults for ocr settings
            this.receiptAnalysis.setReceiptOcrSettings(new ReceiptOcrSettingsBuilder.Builder().build());

            //set defaults for validation settings
            this.receiptAnalysis.setReceiptValidationSettings(new ReceiptValidationSettingsBuilder.Builder().build());

            //set defaults for ocr results
            this.receiptAnalysis.setDeviceOcrResults(new ReceiptOcrResults());
            this.receiptAnalysis.setCloudOcrResults(new ReceiptOcrResults());

            //set default for validation results
            this.receiptAnalysis.setReceiptValidationResults(new ReceiptValidationResultsBuilder.Builder().build());
        }

        public Builder doDeviceOcrRecognition(Boolean doDeviceOcrRecognition){
            this.receiptAnalysis.setDoDeviceOcrRecognition(doDeviceOcrRecognition);
            return this;
        }

        public Builder doCloudOcrRecognition(Boolean doCloudOcrRecognition){
            this.receiptAnalysis.setDoCloudOcrRecognition(doCloudOcrRecognition);
            return this;
        }

        public Builder receiptOcrSettings(ReceiptOcrSettings receiptOcrSettings){
            this.receiptAnalysis.setReceiptOcrSettings(receiptOcrSettings);
            return this;
        }

        public Builder receiptValidationSettings(ReceiptValidationSettings receiptValidationSettings){
            this.receiptAnalysis.setReceiptValidationSettings(receiptValidationSettings);
            return this;
        }

        private void validate(ReceiptAnalysis receiptAnalysis){
            //do nothing
        }

        public ReceiptAnalysis build(){
            ReceiptAnalysis receiptAnalysis = new ReceiptAnalysisBuilder(this).getReceiptAnalysis();
            validate(receiptAnalysis);
            return receiptAnalysis;
        }
    }
}
