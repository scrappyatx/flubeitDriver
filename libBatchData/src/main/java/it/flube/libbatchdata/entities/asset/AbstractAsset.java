/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.asset;

import it.flube.libbatchdata.interfaces.AssetInterface;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public abstract class AbstractAsset {

    protected AssetInterface.AssetType assetType;
    protected String guid;
    protected String displayImageUrl;
    protected String displayTitle;
    protected String displayDescription;
    protected String displayIdentifier;
    protected String detailInfo;

    public AbstractAsset(){ }

    public AssetInterface.AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetInterface.AssetType assetType) {
        this.assetType = assetType;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDisplayImageUrl() {
        return displayImageUrl;
    }

    public void setDisplayImageUrl(String displayImageUrl) {
        this.displayImageUrl = displayImageUrl;
    }

    public String getDisplayTitle() {
        return displayTitle;
    }

    public void setDisplayTitle(String displayTitle) {
        this.displayTitle = displayTitle;
    }

    public String getDisplayDescription() {
        return displayDescription;
    }

    public void setDisplayDescription(String displayDescription) {
        this.displayDescription = displayDescription;
    }

    public String getDisplayIdentifier() {
        return displayIdentifier;
    }

    public void setDisplayIdentifier(String displayIdentifier) {
        this.displayIdentifier = displayIdentifier;
    }

    public String getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        this.detailInfo = detailInfo;
    }
}
