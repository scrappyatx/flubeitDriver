/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.FileUpload;
import it.flube.libbatchdata.interfaces.FileUploadInterface;

/**
 * Created on 9/25/2017
 * Project : Driver
 */

public class FileUploadBuilder {
    private FileUpload fileUpload;

    private FileUploadBuilder(Builder builder){
        this.fileUpload = builder.fileUpload;
    }

    private FileUpload getFileUpload(){
        return fileUpload;
    }

    public static class Builder {
        private FileUpload fileUpload;

        public Builder(){
            this.fileUpload = new FileUpload();
            this.fileUpload.setGuid(BuilderUtilities.generateGuid());
        }

        public Builder guid(String guid){
            this.fileUpload.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.fileUpload.setBatchGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.fileUpload.setBatchDetailGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(String guid){
            this.fileUpload.setServiceOrderGuid(guid);
            return this;
        }

        public Builder stepGuid(String guid){
            this.fileUpload.setStepGuid(guid);
            return this;
        }

        public Builder ownerGuid(String guid){
            this.fileUpload.setOwnerGuid(guid);
            return this;
        }

        public Builder ownerType(FileUploadInterface.OwnerType ownerType){
            this.fileUpload.setOwnerType(ownerType);
            return this;
        }

        public Builder cloudStorageFileName(String cloudStorageFileName){
            this.fileUpload.setCloudStorageFileName(cloudStorageFileName);
            return this;
        }

        public Builder cloudStorageDownloadUrl(String cloudStorageDownloadUrl){
            this.fileUpload.setCloudStorageDownloadUrl(cloudStorageDownloadUrl);
            return this;
        }

        public Builder fileSizeBytes(long fileSizeBytes){
            this.fileUpload.setFileSizeBytes(fileSizeBytes);
            return this;
        }

        public Builder bytesTransferred(long bytesTransferred){
            this.fileUpload.setBytesTransferred(bytesTransferred);
            return this;
        }

        public Builder contentType(String contentType){
            this.fileUpload.setContentType(contentType);
            return this;
        }
        private void validate(FileUpload fileUpload){

        }

        public FileUpload build(){
            FileUpload fileUpload = new FileUploadBuilder(this).getFileUpload();
            validate(fileUpload);
            return fileUpload;
        }
    }
}
