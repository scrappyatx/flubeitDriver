/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.interfaces;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public interface AssetInterface {

    ////
    //// This is where we will define the various supported asset types
    //// For now we only have vehicles
    ////
    public enum AssetType {
        VEHICLE
    }

    ///
    ///  fields shared by all assets
    ///
    ///  AssetType assetType;
    ///  String guid
    ///  String displayImageUrl
    ///  String displayTitle
    ///  String displayDescription
    ///  String displayIdentifier
    ///  String detailInfo

    AssetInterface.AssetType getAssetType();

    void setAssetType(AssetInterface.AssetType assetType);

    String getGuid();

    void setGuid(String guid);

    String getDisplayImageUrl();

    void setDisplayImageUrl(String displayImageUrl);

    String getDisplayTitle();

    void setDisplayTitle(String displayTitle);

    String getDisplayDescription();

    void setDisplayDescription(String displayDescription);

    String getDisplayIdentifier();

    void setDisplayIdentifier(String displayIdentifier);

    String getDetailInfo();

    void setDetailInfo(String detailInfo);

}
