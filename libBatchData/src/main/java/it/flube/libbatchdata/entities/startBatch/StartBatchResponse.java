/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.startBatch;

/**
 * Created on 6/9/2018
 * Project : Driver
 */
public class StartBatchResponse {
    private Boolean approved;
    private String reason;
    private Long timestamp;
    private String driverProxyDialNumber;
    private String driverProxyDisplayNumber;

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDriverProxyDialNumber() {
        return driverProxyDialNumber;
    }

    public void setDriverProxyDialNumber(String driverProxyDialNumber) {
        this.driverProxyDialNumber = driverProxyDialNumber;
    }

    public String getDriverProxyDisplayNumber() {
        return driverProxyDisplayNumber;
    }

    public void setDriverProxyDisplayNumber(String driverProxyDisplayNumber) {
        this.driverProxyDisplayNumber = driverProxyDisplayNumber;
    }
}
