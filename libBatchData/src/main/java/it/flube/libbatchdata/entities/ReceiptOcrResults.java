/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

/**
 * Created on 2/21/2019
 * Project : Driver
 */
public class ReceiptOcrResults {
    private Boolean foundTransactionId;
    private Boolean foundVehicleId;
    private Boolean foundVehicleMileage;
    private Boolean foundVehicleVin;
    private Boolean foundServiceChecklist;
    private Boolean foundTotalCharged;

    private String transactionId;
    private String vehicleId;
    private String vehicleMileage;
    private String vehicleVin;
    private String serviceChecklist;
    private String totalCharged;

    public Boolean getFoundTransactionId() {
        return foundTransactionId;
    }

    public void setFoundTransactionId(Boolean foundTransactionId) {
        this.foundTransactionId = foundTransactionId;
    }

    public Boolean getFoundVehicleId() {
        return foundVehicleId;
    }

    public void setFoundVehicleId(Boolean foundVehicleId) {
        this.foundVehicleId = foundVehicleId;
    }

    public Boolean getFoundVehicleMileage() {
        return foundVehicleMileage;
    }

    public void setFoundVehicleMileage(Boolean foundVehicleMileage) {
        this.foundVehicleMileage = foundVehicleMileage;
    }

    public Boolean getFoundServiceChecklist() {
        return foundServiceChecklist;
    }

    public void setFoundServiceChecklist(Boolean foundServiceChecklist) {
        this.foundServiceChecklist = foundServiceChecklist;
    }

    public Boolean getFoundTotalCharged() {
        return foundTotalCharged;
    }

    public void setFoundTotalCharged(Boolean foundTotalCharged) {
        this.foundTotalCharged = foundTotalCharged;
    }

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

    public String getVehicleMileage() {
        return vehicleMileage;
    }

    public void setVehicleMileage(String vehicleMileage) {
        this.vehicleMileage = vehicleMileage;
    }

    public String getServiceChecklist() {
        return serviceChecklist;
    }

    public void setServiceChecklist(String serviceChecklist) {
        this.serviceChecklist = serviceChecklist;
    }

    public String getTotalCharged() {
        return totalCharged;
    }

    public void setTotalCharged(String totalCharged) {
        this.totalCharged = totalCharged;
    }

    public Boolean getFoundVehicleVin() {
        return foundVehicleVin;
    }

    public void setFoundVehicleVin(Boolean foundVehicleVin) {
        this.foundVehicleVin = foundVehicleVin;
    }

    public String getVehicleVin() {
        return vehicleVin;
    }

    public void setVehicleVin(String vehicleVin) {
        this.vehicleVin = vehicleVin;
    }
}
