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

    AssetInterface.AssetType getAssetType();

    String getGuid();

    void setGuid(String guid);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

}
