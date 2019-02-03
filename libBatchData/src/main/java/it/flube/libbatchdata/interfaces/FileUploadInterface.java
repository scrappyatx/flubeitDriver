/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.interfaces;

/**
 * Created on 1/29/2019
 * Project : Driver
 */
public interface FileUploadInterface {
    //the entities that can own a file upload

    enum OwnerType {
        PHOTO_REQUEST,
        SIGNATURE_REQUEST,
        RECEIPT_REQUEST,
    }
}
