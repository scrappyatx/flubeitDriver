/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 8/18/2017
 * Project : Driver
 */

public class DeviceInfo {
    private String deviceGUID;
    private String manufacturer;
    private String marketName;
    private String model;
    private String codeName;
    private String yearWhenDeviceConsideredHighEnd;
    private String versionAPI;
    private String versionReleaseAPI;

    public String getDeviceGUID() {
        return deviceGUID;
    }

    public void setDeviceGUID(String deviceGUID) {
        this.deviceGUID = deviceGUID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getYearWhenDeviceConsideredHighEnd() {
        return yearWhenDeviceConsideredHighEnd;
    }

    public void setYearWhenDeviceConsideredHighEnd(String yearWhenDeviceConsideredHighEnd) {
        this.yearWhenDeviceConsideredHighEnd = yearWhenDeviceConsideredHighEnd;
    }

    public String getVersionAPI() {
        return versionAPI;
    }

    public void setVersionAPI(String versionAPI) {
        this.versionAPI = versionAPI;
    }

    public String getVersionReleaseAPI() {
        return versionReleaseAPI;
    }

    public void setVersionReleaseAPI(String versionReleaseAPI) {
        this.versionReleaseAPI = versionReleaseAPI;
    }
}