/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.asset;

/**
 * Created by Bryan on 3/21/2017.
 */

public class Vehicle extends AbstractAsset {
    private String make;
    private String model;
    private String year;
    private String color;
    private String licensePlate;
    private String licenseState;

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

    /// setters / getters for instance variables from abstract AbstractAsset class
    @Override
    public void setAssetGUID(String assetGUID){
        super.setAssetGUID(assetGUID);
    }

    @Override
    public String getAssetGUID(){
        return super.getAssetGUID();
    }

    @Override
    public void setAssetName(String assetName){
        super.setAssetName(assetName);
    }

    @Override
    public String getAssetName(){
        return super.getAssetName();
    }

    @Override
    public void setAssetDescription(String assetDescription){
        super.setAssetDescription(assetDescription);
    }

    @Override
    public String getAssetDescription(){
        return super.getAssetDescription();
    }

}
