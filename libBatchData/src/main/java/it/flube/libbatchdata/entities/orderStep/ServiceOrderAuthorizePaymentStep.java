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

    public enum ServiceProviderTransactionIdSourceType {
        NOT_ATTEMPTED,
        MANUAL_ENTRY,
        DEVICE_OCR,
        CLOUD_OCR
    }

    private PaymentAuthorization paymentAuthorization;

    private Boolean requireReceipt;
    private ReceiptRequest receiptRequest;

    private Boolean requireServiceProviderTransactionId;
    private String serviceProviderTransactionId;
    private ServiceProviderTransactionIdSourceType serviceProviderTransactionIdSourceType;


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

    public ServiceProviderTransactionIdSourceType getServiceProviderTransactionIdSourceType() {
        return serviceProviderTransactionIdSourceType;
    }

    public void setServiceProviderTransactionIdSourceType(ServiceProviderTransactionIdSourceType serviceProviderTransactionIdSourceType) {
        this.serviceProviderTransactionIdSourceType = serviceProviderTransactionIdSourceType;
    }
}
