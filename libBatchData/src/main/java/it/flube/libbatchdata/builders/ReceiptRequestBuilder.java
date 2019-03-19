/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.entities.ReceiptAnalysis;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.utilities.BuilderUtilities;

/**
 * Created on 8/11/2018
 * Project : Driver
 */
public class ReceiptRequestBuilder {
    private static final String NOT_ATTEMPTED_ICON_TEXT = "{fa-ban}";
    private static final String COMPLETED_SUCCESS_ICON_TEXT = "{fa-check-circle}";
    private static final String COMPLETED_FAILED_ICON_TEXT = "{fa-question-circle}";

    private static final Boolean DEFAULT_SHARE_WITH_CUSTOMER = false;

    private ReceiptRequest receiptRequest;

    private ReceiptRequestBuilder(Builder builder){
        this.receiptRequest = builder.receiptRequest;
    }

    private ReceiptRequest getReceiptRequest(){
        return this.receiptRequest;
    }

    public static class Builder {
        private ReceiptRequest receiptRequest;

        public Builder(){
            receiptRequest = new ReceiptRequest();

            receiptRequest.setGuid(BuilderUtilities.generateGuid());
            receiptRequest.setHasCloudFile(false);
            receiptRequest.setHasDeviceFile(false);

            //build default status icon text
            HashMap<String, String> statusIconText = new HashMap<String, String>();
            statusIconText.put(ReceiptRequest.ReceiptStatus.NOT_ATTEMPTED.toString(), NOT_ATTEMPTED_ICON_TEXT);
            statusIconText.put(ReceiptRequest.ReceiptStatus.COMPLETED_SUCCESS.toString(), COMPLETED_SUCCESS_ICON_TEXT);
            statusIconText.put(ReceiptRequest.ReceiptStatus.COMPLETED_FAILED.toString(), COMPLETED_FAILED_ICON_TEXT);
            receiptRequest.setStatusIconText(statusIconText);

            //set default receiptRequest status
            receiptRequest.setReceiptStatus(ReceiptRequest.ReceiptStatus.NOT_ATTEMPTED);

            //set default attempt count
            receiptRequest.setAttemptCount(0);

            //set share with customer default
            receiptRequest.setShareWithCustomer(DEFAULT_SHARE_WITH_CUSTOMER);

            //set receipt analysis
            receiptRequest.setReceiptAnalysis(new ReceiptAnalysisBuilder.Builder().build());

        }

        public Builder guid(String guid){
            this.receiptRequest.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String batchGuid){
            this.receiptRequest.setBatchGuid(batchGuid);
            return this;
        }

        public Builder batchDetailGuid(String batchDetailGuid){
            this.receiptRequest.setBatchDetailGuid(batchDetailGuid);
            return this;
        }

        public Builder serviceOrderGuid(String serviceOrderGuid){
            this.receiptRequest.setServiceOrderGuid(serviceOrderGuid);
            return this;
        }

        public Builder stepGuid(String stepGuid){
            this.receiptRequest.setStepGuid(stepGuid);
            return this;
        }

        public Builder hasDeviceFile(Boolean hasDeviceFile){
            this.receiptRequest.setHasDeviceFile(hasDeviceFile);
            return this;
        }

        public Builder deviceAbsoluteFileName(String deviceAbsoluteFileName){
            this.receiptRequest.setDeviceAbsoluteFileName(deviceAbsoluteFileName);
            return this;
        }

        public Builder hasCloudFile(Boolean hasCloudFile){
            this.receiptRequest.setHasCloudFile(hasCloudFile);
            return this;
        }

        public Builder cloudStorageFileName(String cloudStorageFileName){
            this.receiptRequest.setCloudStorageFileName(cloudStorageFileName);
            return this;
        }

        public Builder cloudStorageDownloadUrl(String cloudStorageDownloadUrl){
            this.receiptRequest.setCloudStorageDownloadUrl(cloudStorageDownloadUrl);
            return this;
        }

        public Builder signatureStatus(ReceiptRequest.ReceiptStatus receiptStatus){
            this.receiptRequest.setReceiptStatus(receiptStatus);
            return this;
        }

        public Builder statusIconText(Map<String, String> statusIconText){
            this.receiptRequest.setStatusIconText(statusIconText);
            return this;
        }

        public Builder receiptAnalysis(ReceiptAnalysis receiptAnalysis){
            this.receiptRequest.setReceiptAnalysis(receiptAnalysis);
            return this;
        }

        private void validate(ReceiptRequest receiptRequest){
            if (receiptRequest.getGuid() == null) {
                throw new IllegalStateException("GUID is null");
            }
        }

        public ReceiptRequest build(){
            ReceiptRequest receiptRequest = new ReceiptRequestBuilder(this).getReceiptRequest();
            validate(receiptRequest);
            return receiptRequest;
        }
    }
}
