/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.assetTransfer;

import it.flube.libbatchdata.entities.asset.AbstractAsset;
import it.flube.libbatchdata.interfaces.AssetInterface;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class AssetTransfer {

    private AssetInterface asset;
    private AssetTransferInterface.TransferStatus transferStatus;


    public AssetInterface getAsset(){
        return asset;
    }

    public void setAsset(AssetInterface asset){
        this.asset = asset;
    }

    public AssetTransferInterface.TransferStatus getTransferStatus(){
        return transferStatus;
    }

    public void setTransferStatus(AssetTransferInterface.TransferStatus transferStatus){
        this.transferStatus = transferStatus;
    }

}
