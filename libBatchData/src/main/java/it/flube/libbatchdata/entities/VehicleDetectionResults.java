/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

/**
 * Created on 2/28/2019
 * Project : Driver
 */
public class VehicleDetectionResults {
    private Boolean foundLicensePlate;
    private Boolean foundMake;
    private Boolean foundModel;

    private String licensePlate;
    private String make;
    private String model;

    public Boolean getFoundLicensePlate() {
        return foundLicensePlate;
    }

    public void setFoundLicensePlate(Boolean foundLicensePlate) {
        this.foundLicensePlate = foundLicensePlate;
    }

    public Boolean getFoundMake() {
        return foundMake;
    }

    public void setFoundMake(Boolean foundMake) {
        this.foundMake = foundMake;
    }

    public Boolean getFoundModel() {
        return foundModel;
    }

    public void setFoundModel(Boolean foundModel) {
        this.foundModel = foundModel;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

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
}
