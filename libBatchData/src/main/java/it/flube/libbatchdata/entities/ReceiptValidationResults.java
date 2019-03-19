/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import it.flube.libbatchdata.interfaces.ReceiptValidationInterface;

/**
 * Created on 3/8/2019
 * Project : Driver
 */
public class ReceiptValidationResults {
    private String transactionId;
    private String vehicleId;
    private String vehicleVin;
    private String vehicleMileage;
    private String totalCharged;
    private String serviceChecklist;

    private ReceiptValidationInterface.DataSource transactionIdSource;
    private ReceiptValidationInterface.DataSource vehicleIdSource;
    private ReceiptValidationInterface.DataSource vehicleVinSource;
    private ReceiptValidationInterface.DataSource vehicleMileageSource;
    private ReceiptValidationInterface.DataSource totalChargeSource;
    private ReceiptValidationInterface.DataSource serviceChecklistSource;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleVin() {
        return vehicleVin;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }

    public String getVehicleMileage() {
        return vehicleMileage;
    }

    public void setVehicleMileage(String vehicleMileage) {
        this.vehicleMileage = vehicleMileage;
    }

    public String getTotalCharged() {
        return totalCharged;
    }

    public void setTotalCharged(String totalCharged) {
        this.totalCharged = totalCharged;
    }

    public String getServiceChecklist() {
        return serviceChecklist;
    }

    public void setServiceChecklist(String serviceChecklist) {
        this.serviceChecklist = serviceChecklist;
    }

    public ReceiptValidationInterface.DataSource getTransactionIdSource() {
        return transactionIdSource;
    }

    public void setTransactionIdSource(ReceiptValidationInterface.DataSource transactionIdSource) {
        this.transactionIdSource = transactionIdSource;
    }

    public ReceiptValidationInterface.DataSource getVehicleIdSource() {
        return vehicleIdSource;
    }

    public void setVehicleIdSource(ReceiptValidationInterface.DataSource vehicleIdSource) {
        this.vehicleIdSource = vehicleIdSource;
    }

    public ReceiptValidationInterface.DataSource getVehicleVinSource() {
        return vehicleVinSource;
    }

    public void setVehicleVinSource(ReceiptValidationInterface.DataSource vehicleVinSource) {
        this.vehicleVinSource = vehicleVinSource;
    }

    public ReceiptValidationInterface.DataSource getVehicleMileageSource() {
        return vehicleMileageSource;
    }

    public void setVehicleMileageSource(ReceiptValidationInterface.DataSource vehicleMileageSource) {
        this.vehicleMileageSource = vehicleMileageSource;
    }

    public ReceiptValidationInterface.DataSource getTotalChargeSource() {
        return totalChargeSource;
    }

    public void setTotalChargeSource(ReceiptValidationInterface.DataSource totalChargeSource) {
        this.totalChargeSource = totalChargeSource;
    }

    public ReceiptValidationInterface.DataSource getServiceChecklistSource() {
        return serviceChecklistSource;
    }

    public void setServiceChecklistSource(ReceiptValidationInterface.DataSource serviceChecklistSource) {
        this.serviceChecklistSource = serviceChecklistSource;
    }
}
