/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.assetTransfer;

import java.util.Map;

import it.flube.libbatchdata.entities.asset.AbstractAsset;
import it.flube.libbatchdata.entities.asset.Vehicle;
import it.flube.libbatchdata.interfaces.AssetInterface;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class AssetTransfer {

    ///TODO this should be AssetInterface but that makes reading in firebase realtime database more complex, for now just support vehicle
    private Vehicle asset;
    private AssetTransferInterface.TransferStatus transferStatus;
    private Map<String, String> statusIconText;

    public AssetTransfer(){}

    public Vehicle getAsset(){
        return asset;
    }

    public void setAsset(Vehicle asset){
        this.asset = asset;
    }

    public AssetTransferInterface.TransferStatus getTransferStatus(){
        return transferStatus;
    }

    public void setTransferStatus(AssetTransferInterface.TransferStatus transferStatus){
        this.transferStatus = transferStatus;
    }

    public Map<String, String> getStatusIconText() {
        return statusIconText;
    }

    public void setStatusIconText(Map<String, String> statusIconText) {
        this.statusIconText = statusIconText;
    }
}
