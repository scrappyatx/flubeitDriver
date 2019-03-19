/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.orderStep;

import java.util.Map;

import it.flube.libbatchdata.entities.PaymentAuthorization;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrderAuthorizePaymentStep extends AbstractStep
        implements OrderStepInterface {

    public enum DataSourceType {
        NONE,
        MANUAL_ENTRY,
        DEVICE_OCR,
        CLOUD_OCR
    }

    public enum DataEntryStatus {
        NOT_ATTEMPTED,
        COMPLETE
    }

    private PaymentAuthorization paymentAuthorization;

    /// require receipt photo
    private Boolean requireReceipt;
    private ReceiptRequest receiptRequest;

    /// require transaction ID
    private Boolean requireServiceProviderTransactionId;
    private String serviceProviderTransactionId;
    private DataSourceType transactionIdSourceType;
    private DataEntryStatus transactionIdStatus;

    /// require transaction total
    private Boolean requireServiceProviderTransactionTotal;
    private String serviceProviderTransactionTotal;
    private DataSourceType transactionTotalSourceType;
    private DataEntryStatus transactionTotalStatus;

    // status icon text
    private Map<String, String> statusIconText;


    public PaymentAuthorization getPaymentAuthorization() {
        return paymentAuthorization;
    }

    public void setPaymentAuthorization(PaymentAuthorization paymentAuthorization) {
        this.paymentAuthorization = paymentAuthorization;
    }

    public Boolean getRequireReceipt() {
        return requireReceipt;
    }

    public void setRequireReceipt(Boolean requireReceipt) {
        this.requireReceipt = requireReceipt;
    }

    public ReceiptRequest getReceiptRequest() {
        return receiptRequest;
    }

    public void setReceiptRequest(ReceiptRequest receiptRequest) {
        this.receiptRequest = receiptRequest;
    }

    public Boolean getRequireServiceProviderTransactionId() {
        return requireServiceProviderTransactionId;
    }

    public void setRequireServiceProviderTransactionId(Boolean requireServiceProviderTransactionId) {
        this.requireServiceProviderTransactionId = requireServiceProviderTransactionId;
    }

    public String getServiceProviderTransactionId() {
        return serviceProviderTransactionId;
    }

    public void setServiceProviderTransactionId(String serviceProviderTransactionId) {
        this.serviceProviderTransactionId = serviceProviderTransactionId;
    }


    public DataEntryStatus getTransactionIdStatus() {
        return transactionIdStatus;
    }

    public void setTransactionIdStatus(DataEntryStatus transactionIdStatus) {
        this.transactionIdStatus = transactionIdStatus;
    }

    public Boolean getRequireServiceProviderTransactionTotal() {
        return requireServiceProviderTransactionTotal;
    }

    public void setRequireServiceProviderTransactionTotal(Boolean requireServiceProviderTransactionTotal) {
        this.requireServiceProviderTransactionTotal = requireServiceProviderTransactionTotal;
    }

    public String getServiceProviderTransactionTotal() {
        return serviceProviderTransactionTotal;
    }

    public void setServiceProviderTransactionTotal(String serviceProviderTransactionTotal) {
        this.serviceProviderTransactionTotal = serviceProviderTransactionTotal;
    }


    public DataEntryStatus getTransactionTotalStatus() {
        return transactionTotalStatus;
    }

    public void setTransactionTotalStatus(DataEntryStatus transactionTotalStatus) {
        this.transactionTotalStatus = transactionTotalStatus;
    }

    public DataSourceType getTransactionIdSourceType() {
        return transactionIdSourceType;
    }

    public void setTransactionIdSourceType(DataSourceType transactionIdSourceType) {
        this.transactionIdSourceType = transactionIdSourceType;
    }

    public DataSourceType getTransactionTotalSourceType() {
        return transactionTotalSourceType;
    }

    public void setTransactionTotalSourceType(DataSourceType transactionTotalSourceType) {
        this.transactionTotalSourceType = transactionTotalSourceType;
    }

    public Map<String, String> getStatusIconText() {
        return statusIconText;
    }

    public void setStatusIconText(Map<String, String> statusIconText) {
        this.statusIconText = statusIconText;
    }
}
