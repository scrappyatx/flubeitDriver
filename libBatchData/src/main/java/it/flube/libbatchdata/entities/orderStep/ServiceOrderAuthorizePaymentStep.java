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


    private PaymentAuthorization paymentAuthorization;

    private Boolean requireReceipt;
    private ReceiptRequest receiptRequest;

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
}
