/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.FileAttachmentList;

/**
 * Created on 9/25/2017
 * Project : Driver
 */

public class FileAttachmentListBuilder {
    private FileAttachmentList fileAttachmentList;

    private FileAttachmentListBuilder(Builder builder){
        this.fileAttachmentList = builder.fileAttachmentList;
    }

    private FileAttachmentList getFileAttachmentList(){
        return fileAttachmentList;
    }

    public static class Builder {
        private FileAttachmentList fileAttachmentList;

        public Builder(){
            this.fileAttachmentList = new FileAttachmentList();
            this.fileAttachmentList.setGuid(BuilderUtilities.generateGuid());
            this.fileAttachmentList.setFileAttachmentGuids(new ArrayList<String>());
        }

        public Builder guid(String guid){
            this.fileAttachmentList.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.fileAttachmentList.setBatchGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(String guid){
            this.fileAttachmentList.setServiceOrderGuid(guid);
            return this;
        }

        public Builder stepGuid(String guid){
            this.fileAttachmentList.setStepGuid(guid);
            return this;
        }

        public Builder photoRequestGuid(String guid){
            this.fileAttachmentList.setPhotoRequestGuid(guid);
            return this;
        }

        public Builder chatHistoryGuid(String guid){
            this.fileAttachmentList.setChatHistoryGuid(guid);
            return this;
        }

        public Builder chatMessageGuid(String guid){
            this.fileAttachmentList.setChatMessageGuid(guid);
            return this;
        }

        public void validate(FileAttachmentList fileAttachmentList){

        }

        public FileAttachmentList build(){
            FileAttachmentList fileAttachmentList = new FileAttachmentListBuilder(this).getFileAttachmentList();
            validate(fileAttachmentList);
            return fileAttachmentList;
        }
    }
}
