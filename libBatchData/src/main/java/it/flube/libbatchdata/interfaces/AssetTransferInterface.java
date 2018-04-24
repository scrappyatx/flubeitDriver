/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.interfaces;

/**
 * Created on 4/21/2018
 * Project : Driver
 */
public interface AssetTransferInterface {
    public enum TransferType {
        TRANSER_TO_CUSTOMER,
        TRANSFER_FROM_CUSTOMER,
        TRANSFER_TO_SERVICE_PROVIDER,
        TRANSFER_FROM_SERVICE_PROVIDER
    }

    public enum TransferStatus {
        COMPLETED_SUCCESS,
        COMPLETED_FAILED,
        NOT_ATTEMPTED
    }
}
