/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.asset;

import it.flube.libbatchdata.interfaces.AssetInterface;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;

/**
 * Created by Bryan on 3/21/2017.
 */

public class Vehicle extends AbstractAsset implements AssetInterface {
    private static final AssetType ASSET_TYPE = AssetType.VEHICLE;

    private String make;
    private String model;
    private String year;
    private String color;
    private String licensePlate;
    private String licenseState;

    public Vehicle(){}

    public AssetType getAssetType(){ return ASSET_TYPE; }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicenseState() {
        return licenseState;
    }

    public void setLicenseState(String licenseState) {
        this.licenseState = licenseState;
    }


}
