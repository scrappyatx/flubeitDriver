/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.asset.Vehicle;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import it.flube.libbatchdata.interfaces.AssetInterface;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;

/**
 * Created on 4/21/2018
 * Project : Driver
 */
public class AssetTransferBuilder {
    private AssetTransfer assetTransfer;

    private AssetTransferBuilder(Builder builder){
        this.assetTransfer = builder.assetTransfer;
    }

    private AssetTransfer getAssetTranser(){
        return this.assetTransfer;
    }

    public static class Builder {
        private AssetTransfer assetTransfer;

        public Builder(){
            assetTransfer = new AssetTransfer();
            assetTransfer.setTransferStatus(AssetTransferInterface.TransferStatus.NOT_ATTEMPTED);
        }

        public Builder asset(Vehicle asset){
            this.assetTransfer.setAsset(asset);
            return this;
        }

        public Builder transferStatus(AssetTransferInterface.TransferStatus transferStatus){
            this.transferStatus(transferStatus);
            return this;
        }

        private void validate(AssetTransfer assetTransfer){
            if (assetTransfer.getAsset() == null){
                throw new IllegalStateException("asset is null");
            }
        }

        public AssetTransfer build(){
            AssetTransfer assetTransfer = new AssetTransferBuilder(this).getAssetTranser();
            validate(assetTransfer);
            return assetTransfer;
        }
    }
}
