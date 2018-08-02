/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.asset.Vehicle;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import it.flube.libbatchdata.interfaces.AssetInterface;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;

/**
 * Created on 4/21/2018
 * Project : Driver
 */
public class AssetTransferBuilder {
    private static final String NOT_ATTEMPTED_ICON_TEXT = "{fa-ban}";
    private static final String COMPLETED_SUCCESS_ICON_TEXT = "{fa-check-circle}";
    private static final String COMPLETED_FAILED_ICON_TEXT = "{fa-question-circle-o}";

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

            //build default status icon text
            HashMap<String, String> statusIconText = new HashMap<String, String>();
            statusIconText.put(AssetTransferInterface.TransferStatus.NOT_ATTEMPTED.toString(), NOT_ATTEMPTED_ICON_TEXT);
            statusIconText.put(AssetTransferInterface.TransferStatus.COMPLETED_SUCCESS.toString(), COMPLETED_SUCCESS_ICON_TEXT);
            statusIconText.put(AssetTransferInterface.TransferStatus.COMPLETED_FAILED.toString(), COMPLETED_FAILED_ICON_TEXT);
            assetTransfer.setStatusIconText(statusIconText);
        }

        public Builder asset(Vehicle asset){
            this.assetTransfer.setAsset(asset);
            return this;
        }

        public Builder transferStatus(AssetTransferInterface.TransferStatus transferStatus){
            this.assetTransfer.setTransferStatus(transferStatus);
            return this;
        }

        public Builder statusIconText(Map<String, String> statusIconText){
            this.assetTransfer.setStatusIconText(statusIconText);
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
