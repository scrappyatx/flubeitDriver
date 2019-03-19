/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.interfaces;

/**
 * Created on 3/8/2019
 * Project : Driver
 */
public interface ReceiptValidationInterface {
    public enum DataSource {
        NONE,
        MANUAL_ENTRY,
        OCR_CLOUD,
        OCR_DEVICE
    }
}
