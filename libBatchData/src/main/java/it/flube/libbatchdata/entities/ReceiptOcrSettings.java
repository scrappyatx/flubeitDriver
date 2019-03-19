/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

/**
 * Created on 2/21/2019
 * Project : Driver
 */
public class ReceiptOcrSettings {

    private Boolean hasTransactionId;
    private Boolean hasVehicleId;
    private Boolean hasVehicleMileage;
    private Boolean hasVehicleVin;
    private Boolean hasServiceChecklist;
    private Boolean hasTotalCharged;

    private String transactionIdStartSentinal;
    private String transactionIdEndSentinal;
    private String transactionIdPattern;

    private String vehicleIdStartSentinal;
    private String vehicleIdEndSentinal;
    private String vehicleIdPattern;

    private String vehicleMileageStartSentinal;
    private String vehicleMileageEndSentinal;
    private String vehicleMileagePattern;

    private String vehicleVinStartSentinal;
    private String vehicleVinEndSentinal;
    private String vehicleVinPattern;

    private String serviceChecklistStartSentinal;
    private String serviceChecklistEndSentinal;
    private String serviceChecklistPattern;

    private String totalChargedStartSentinal;
    private String totalChargedEndSentinal;
    private String totalChargedPattern;

    public Boolean getHasTransactionId() {
        return hasTransactionId;
    }

    public void setHasTransactionId(Boolean hasTransactionId) {
        this.hasTransactionId = hasTransactionId;
    }

    public String getTransactionIdPattern() {
        return transactionIdPattern;
    }

    public void setTransactionIdPattern(String transactionIdPattern) {
        this.transactionIdPattern = transactionIdPattern;
    }

    public Boolean getHasVehicleId() {
        return hasVehicleId;
    }

    public void setHasVehicleId(Boolean hasVehicleId) {
        this.hasVehicleId = hasVehicleId;
    }

    public String getVehicleIdPattern() {
        return vehicleIdPattern;
    }

    public void setVehicleIdPattern(String vehicleIdPattern) {
        this.vehicleIdPattern = vehicleIdPattern;
    }

    public Boolean getHasVehicleMileage() {
        return hasVehicleMileage;
    }

    public void setHasVehicleMileage(Boolean hasVehicleMileage) {
        this.hasVehicleMileage = hasVehicleMileage;
    }

    public Boolean getHasServiceChecklist() {
        return hasServiceChecklist;
    }

    public void setHasServiceChecklist(Boolean hasServiceChecklist) {
        this.hasServiceChecklist = hasServiceChecklist;
    }

    public Boolean getHasTotalCharged() {
        return hasTotalCharged;
    }

    public void setHasTotalCharged(Boolean hasTotalCharged) {
        this.hasTotalCharged = hasTotalCharged;
    }

    public String getTransactionIdStartSentinal() {
        return transactionIdStartSentinal;
    }

    public void setTransactionIdStartSentinal(String transactionIdStartSentinal) {
        this.transactionIdStartSentinal = transactionIdStartSentinal;
    }

    public String getTransactionIdEndSentinal() {
        return transactionIdEndSentinal;
    }

    public void setTransactionIdEndSentinal(String transactionIdEndSentinal) {
        this.transactionIdEndSentinal = transactionIdEndSentinal;
    }

    public String getVehicleIdStartSentinal() {
        return vehicleIdStartSentinal;
    }

    public void setVehicleIdStartSentinal(String vehicleIdStartSentinal) {
        this.vehicleIdStartSentinal = vehicleIdStartSentinal;
    }

    public String getVehicleIdEndSentinal() {
        return vehicleIdEndSentinal;
    }

    public void setVehicleIdEndSentinal(String vehicleIdEndSentinal) {
        this.vehicleIdEndSentinal = vehicleIdEndSentinal;
    }

    public String getVehicleMileageStartSentinal() {
        return vehicleMileageStartSentinal;
    }

    public void setVehicleMileageStartSentinal(String vehicleMileageStartSentinal) {
        this.vehicleMileageStartSentinal = vehicleMileageStartSentinal;
    }

    public String getVehicleMileageEndSentinal() {
        return vehicleMileageEndSentinal;
    }

    public void setVehicleMileageEndSentinal(String vehicleMileageEndSentinal) {
        this.vehicleMileageEndSentinal = vehicleMileageEndSentinal;
    }

    public String getServiceChecklistStartSentinal() {
        return serviceChecklistStartSentinal;
    }

    public void setServiceChecklistStartSentinal(String serviceChecklistStartSentinal) {
        this.serviceChecklistStartSentinal = serviceChecklistStartSentinal;
    }

    public String getServiceChecklistEndSentinal() {
        return serviceChecklistEndSentinal;
    }

    public void setServiceChecklistEndSentinal(String serviceChecklistEndSentinal) {
        this.serviceChecklistEndSentinal = serviceChecklistEndSentinal;
    }

    public String getTotalChargedStartSentinal() {
        return totalChargedStartSentinal;
    }

    public void setTotalChargedStartSentinal(String totalChargedStartSentinal) {
        this.totalChargedStartSentinal = totalChargedStartSentinal;
    }

    public String getTotalChargedEndSentinal() {
        return totalChargedEndSentinal;
    }

    public void setTotalChargedEndSentinal(String totalChargedEndSentinal) {
        this.totalChargedEndSentinal = totalChargedEndSentinal;
    }

    public Boolean getHasVehicleVin() {
        return hasVehicleVin;
    }

    public void setHasVehicleVin(Boolean hasVehicleVin) {
        this.hasVehicleVin = hasVehicleVin;
    }

    public String getVehicleVinStartSentinal() {
        return vehicleVinStartSentinal;
    }

    public void setVehicleVinStartSentinal(String vehicleVinStartSentinal) {
        this.vehicleVinStartSentinal = vehicleVinStartSentinal;
    }

    public String getVehicleVinEndSentinal() {
        return vehicleVinEndSentinal;
    }

    public void setVehicleVinEndSentinal(String vehicleVinEndSentinal) {
        this.vehicleVinEndSentinal = vehicleVinEndSentinal;
    }

    public String getVehicleMileagePattern() {
        return vehicleMileagePattern;
    }

    public void setVehicleMileagePattern(String vehicleMileagePattern) {
        this.vehicleMileagePattern = vehicleMileagePattern;
    }

    public String getVehicleVinPattern() {
        return vehicleVinPattern;
    }

    public void setVehicleVinPattern(String vehicleVinPattern) {
        this.vehicleVinPattern = vehicleVinPattern;
    }

    public String getServiceChecklistPattern() {
        return serviceChecklistPattern;
    }

    public void setServiceChecklistPattern(String serviceChecklistPattern) {
        this.serviceChecklistPattern = serviceChecklistPattern;
    }

    public String getTotalChargedPattern() {
        return totalChargedPattern;
    }

    public void setTotalChargedPattern(String totalChargedPattern) {
        this.totalChargedPattern = totalChargedPattern;
    }
}
