/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.entities.FileAttachment;

/**
 * Created on 9/25/2017
 * Project : Driver
 */

public class FileAttachmentBuilder {
    private FileAttachment fileAttachment;

    private FileAttachmentBuilder(@NonNull Builder builder){
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

        public Builder guid(@NonNull String guid){
            this.fileAttachment.setGuid(guid);
            return this;
        }

        public Builder batchGuid(@NonNull String guid){
            this.fileAttachment.setBatchGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(@NonNull String guid){
            this.fileAttachment.setServiceOrderGuid(guid);
            return this;
        }

        public Builder stepGuid(@NonNull String guid){
            this.fileAttachment.setStepGuid(guid);
            return this;
        }

        public Builder photoRequestGuid(@NonNull String guid){
            this.fileAttachment.setPhotoRequestGuid(guid);
            return this;
        }

        public Builder chatHistoryGuid(@NonNull String guid){
            this.fileAttachment.setChatHistoryGuid(guid);
            return this;
        }

        public Builder chatMessageGuid(@NonNull String guid){
            this.fileAttachment.setChatMessageGuid(guid);
            return this;
        }

        public Builder fileName(@NonNull String fileName){
            this.fileAttachment.setFileName(fileName);
            return this;
        }

        public Builder filePath(@NonNull String filePath){
            this.fileAttachment.setFilePath(filePath);
            return this;
        }

        public Builder contentType(@NonNull String contentType){
            this.fileAttachment.setContentType(contentType);
            return this;
        }
        private void validate(@NonNull FileAttachment fileAttachment){

        }

        public FileAttachment build(){
            FileAttachment fileAttachment = new FileAttachmentBuilder(this).getFileAttachment();
            validate(fileAttachment);
            return fileAttachment;
        }
    }
}
