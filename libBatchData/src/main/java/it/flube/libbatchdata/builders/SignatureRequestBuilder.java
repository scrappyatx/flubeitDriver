/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.utilities.BuilderUtilities;


/**
 * Created on 6/23/2018
 * Project : Driver
 */
public class SignatureRequestBuilder {
    private static final String NOT_ATTEMPTED_ICON_TEXT = "{fa-ban}";
    private static final String COMPLETED_SUCCESS_ICON_TEXT = "{fa-check-circle}";
    private static final String COMPLETED_FAILED_ICON_TEXT = "{fa-question-circle}";

    private static final Boolean DEFAULT_SHARE_WITH_CUSTOMER = false;

    private SignatureRequest signatureRequest;

    private SignatureRequestBuilder(Builder builder){
        this.signatureRequest = builder.signatureRequest;
    }

    private SignatureRequest getSignatureRequest(){
        return this.signatureRequest;
    }

    public static class Builder {
        private SignatureRequest signatureRequest;

        public Builder() {
            signatureRequest = new SignatureRequest();

            signatureRequest.setGuid(BuilderUtilities.generateGuid());
            signatureRequest.setHasCloudFile(false);
            signatureRequest.setHasDeviceFile(false);
            signatureRequest.setShareWithCustomer(DEFAULT_SHARE_WITH_CUSTOMER);

            //build default status icon text
            HashMap<String, String> statusIconText = new HashMap<String, String>();
            statusIconText.put(SignatureRequest.SignatureStatus.NOT_ATTEMPTED.toString(), NOT_ATTEMPTED_ICON_TEXT);
            statusIconText.put(SignatureRequest.SignatureStatus.COMPLETED_SUCCESS.toString(), COMPLETED_SUCCESS_ICON_TEXT);
            statusIconText.put(SignatureRequest.SignatureStatus.COMPLETED_FAILED.toString(), COMPLETED_FAILED_ICON_TEXT);
            signatureRequest.setStatusIconText(statusIconText);

            //set default signatureStatus
            signatureRequest.setSignatureStatus(SignatureRequest.SignatureStatus.NOT_ATTEMPTED);

            //set default attempt count
            signatureRequest.setAttemptCount(0);

        }

        public Builder guid(String guid){
            this.signatureRequest.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String batchGuid){
            this.signatureRequest.setBatchGuid(batchGuid);
            return this;
        }

        public Builder batchDetailGuid(String batchDetailGuid){
            this.signatureRequest.setBatchDetailGuid(batchDetailGuid);
            return this;
        }

        public Builder serviceOrderGuid(String serviceOrderGuid){
            this.signatureRequest.setServiceOrderGuid(serviceOrderGuid);
            return this;
        }

        public Builder stepGuid(String stepGuid){
            this.signatureRequest.setStepGuid(stepGuid);
            return this;
        }

        public Builder hasDeviceFile(Boolean hasDeviceFile){
            this.signatureRequest.setHasDeviceFile(hasDeviceFile);
            return this;
        }

        public Builder deviceAbsoluteFileName(String deviceAbsoluteFileName){
            this.signatureRequest.setDeviceAbsoluteFileName(deviceAbsoluteFileName);
            return this;
        }

        public Builder hasCloudFile(Boolean hasCloudFile){
            this.signatureRequest.setHasCloudFile(hasCloudFile);
            return this;
        }

        public Builder cloudStorageFileName(String cloudStorageFileName){
            this.signatureRequest.setCloudStorageFileName(cloudStorageFileName);
            return this;
        }

        public Builder cloudStorageDownloadUrl(String cloudStorageDownloadUrl){
            this.signatureRequest.setCloudStorageDownloadUrl(cloudStorageDownloadUrl);
            return this;
        }

        public Builder signatureStatus(SignatureRequest.SignatureStatus signatureStatus){
            this.signatureRequest.setSignatureStatus(signatureStatus);
            return this;
        }

        public Builder statusIconText(Map<String, String> statusIconText){
            this.signatureRequest.setStatusIconText(statusIconText);
            return this;
        }

        public Builder shareWithCustomer(Boolean shareWithCustomer){
            this.signatureRequest.setShareWithCustomer(shareWithCustomer);
            return this;
        }

        private void validate(SignatureRequest signatureRequest){
            if (signatureRequest.getGuid() == null) {
                throw new IllegalStateException("GUID is null");
            }
        }

        public SignatureRequest build(){
            SignatureRequest signatureRequest = new SignatureRequestBuilder(this).getSignatureRequest();
            validate(signatureRequest);
            return signatureRequest;
        }

    }
}
