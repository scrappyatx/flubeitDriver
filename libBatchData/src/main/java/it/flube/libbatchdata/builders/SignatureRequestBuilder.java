/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.SignatureRequest;


/**
 * Created on 6/23/2018
 * Project : Driver
 */
public class SignatureRequestBuilder {

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
