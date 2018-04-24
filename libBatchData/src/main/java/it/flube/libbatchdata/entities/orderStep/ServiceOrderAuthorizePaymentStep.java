/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.orderStep;

import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrderAuthorizePaymentStep extends AbstractStep
        implements OrderStepInterface {

    private String maxPaymentAmount;

    public String getMaxPaymentAmount() {
        return maxPaymentAmount;
    }

    public void setMaxPaymentAmount(String maxPaymentAmount) {
        this.maxPaymentAmount = maxPaymentAmount;
    }
}
