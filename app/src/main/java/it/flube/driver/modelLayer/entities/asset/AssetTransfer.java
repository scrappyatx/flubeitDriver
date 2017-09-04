/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.asset;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class AssetTransfer {

    public enum TransferStatus {
        COMPLETED_SUCCESS,
        COMPLETED_FAILED,
        NOT_ATTEMPTED
    }

    private AbstractAsset asset;
    private TransferStatus transferStatus;

    public AbstractAsset getAsset(){
        return asset;
    }

    public void setAsset(AbstractAsset asset){
        this.asset = asset;
    }

    public TransferStatus getTransferStatus(){
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus){
        this.transferStatus = transferStatus;
    }

}
