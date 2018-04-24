/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.orderStep;

import it.flube.libbatchdata.entities.Timestamp;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrderUserTriggerStep extends AbstractStep implements OrderStepInterface {

    private Boolean userHasTriggered;

    public Boolean getUserHasTriggered() {
        return userHasTriggered;
    }

    public void setUserHasTriggered(Boolean userHasTriggered) {
        this.userHasTriggered = userHasTriggered;
    }
}
