/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.asset;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public abstract class AbstractAsset {

    private String assetGUID;
    private String assetName;
    private String assetDescription;

    protected void setAssetGUID(String assetGUID) {
        this.assetGUID = assetGUID;
    }

    protected String getAssetGUID(){
        return assetGUID;
    }

    protected void setAssetName(String assetName){
        this.assetName = assetName;
    }

    protected String getAssetName(){
        return assetName;
    }

    protected void setAssetDescription(String assetDescription){
        this.assetDescription = assetDescription;
    }

    protected String getAssetDescription(){
        return this.assetDescription;
    }


}
