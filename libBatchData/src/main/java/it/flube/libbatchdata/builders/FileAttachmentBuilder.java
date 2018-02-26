/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.FileAttachment;

/**
 * Created on 9/25/2017
 * Project : Driver
 */

public class FileAttachmentBuilder {
    private FileAttachment fileAttachment;

    private FileAttachmentBuilder(Builder builder){
        this.fileAttachment = builder.fileAttachment;
    }

    private FileAttachment getFileAttachment(){
        return fileAttachment;
    }

    public static class Builder {
        private FileAttachment fileAttachment;

        public Builder(){
            this.fileAttachment = new FileAttachment();
            this.fileAttachment.setGuid(BuilderUtilities.generateGuid());
        }

        public Builder guid(String guid){
            this.fileAttachment.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.fileAttachment.setBatchGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(String guid){
            this.fileAttachment.setServiceOrderGuid(guid);
            return this;
        }

        public Builder stepGuid(String guid){
            this.fileAttachment.setStepGuid(guid);
            return this;
        }

        public Builder photoRequestGuid(String guid){
            this.fileAttachment.setPhotoRequestGuid(guid);
            return this;
        }

        public Builder chatHistoryGuid(String guid){
            this.fileAttachment.setChatHistoryGuid(guid);
            return this;
        }

        public Builder chatMessageGuid(String guid){
            this.fileAttachment.setChatMessageGuid(guid);
            return this;
        }

        public Builder fileName(String fileName){
            this.fileAttachment.setFileName(fileName);
            return this;
        }

        public Builder filePath(String filePath){
            this.fileAttachment.setFilePath(filePath);
            return this;
        }

        public Builder contentType(String contentType){
            this.fileAttachment.setContentType(contentType);
            return this;
        }
        private void validate(FileAttachment fileAttachment){

        }

        public FileAttachment build(){
            FileAttachment fileAttachment = new FileAttachmentBuilder(this).getFileAttachment();
            validate(fileAttachment);
            return fileAttachment;
        }
    }
}
