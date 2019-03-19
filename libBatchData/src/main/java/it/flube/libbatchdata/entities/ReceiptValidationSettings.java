/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

/**
 * Created on 3/7/2019
 * Project : Driver
 */
public class ReceiptValidationSettings {
    private Boolean userMustValidateTransactionId;
    private Boolean userMustValidateVehicleId;
    private Boolean userMustValidateVehicleMileage;
    private Boolean userMustValidateVehicleVin;
    private Boolean userMustValidateServiceChecklist;
    private Boolean userMustValidateTotalCharged;

    public Boolean getUserMustValidateTransactionId() {
        return userMustValidateTransactionId;
    }

    public void setUserMustValidateTransactionId(Boolean userMustValidateTransactionId) {
        this.userMustValidateTransactionId = userMustValidateTransactionId;
    }

    public Boolean getUserMustValidateVehicleId() {
        return userMustValidateVehicleId;
    }

    public void setUserMustValidateVehicleId(Boolean userMustValidateVehicleId) {
        this.userMustValidateVehicleId = userMustValidateVehicleId;
    }

    public Boolean getUserMustValidateVehicleMileage() {
        return userMustValidateVehicleMileage;
    }

    public void setUserMustValidateVehicleMileage(Boolean userMustValidateVehicleMileage) {
        this.userMustValidateVehicleMileage = userMustValidateVehicleMileage;
    }

    public Boolean getUserMustValidateVehicleVin() {
        return userMustValidateVehicleVin;
    }

    public void setUserMustValidateVehicleVin(Boolean userMustValidateVehicleVin) {
        this.userMustValidateVehicleVin = userMustValidateVehicleVin;
    }

    public Boolean getUserMustValidateServiceChecklist() {
        return userMustValidateServiceChecklist;
    }

    public void setUserMustValidateServiceChecklist(Boolean userMustValidateServiceChecklist) {
        this.userMustValidateServiceChecklist = userMustValidateServiceChecklist;
    }

    public Boolean getUserMustValidateTotalCharged() {
        return userMustValidateTotalCharged;
    }

    public void setUserMustValidateTotalCharged(Boolean userMustValidateTotalCharged) {
        this.userMustValidateTotalCharged = userMustValidateTotalCharged;
    }
}
