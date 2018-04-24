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

public class ServiceOrderExternalTriggerStep extends AbstractStep
    implements OrderStepInterface {

    public enum TriggerMethod {
        EXTERNAL_TRIGGER_OCCURED,
        USER_TRIGGER_OCCURED,
        NO_TRIGGER_OCCURED,
    }

    private TriggerMethod howTriggered;

    public void setHowTriggered(TriggerMethod howTriggered) {
        this.howTriggered = howTriggered;
    }

    public TriggerMethod getHowTriggered(){
        return howTriggered;
    }

}
