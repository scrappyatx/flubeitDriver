/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.driver;

/**
 * Created on 12/3/2017
 * Project : Driver
 */

public class UserRoles {

    private Boolean qa;
    private Boolean dev;
    private Boolean eligibleForProductionBatches;
    private Boolean eligibleForProductionTestBatches;

    public Boolean getQa() {
        return qa;
    }

    public void setQa(Boolean qa) {
        this.qa = qa;
    }

    public Boolean getDev() {
        return dev;
    }

    public void setDev(Boolean dev) {
        this.dev = dev;
    }

    public Boolean getEligibleForProductionBatches() {
        return eligibleForProductionBatches;
    }

    public void setEligibleForProductionBatches(Boolean eligibleForProductionBatches) {
        this.eligibleForProductionBatches = eligibleForProductionBatches;
    }

    public Boolean getEligibleForProductionTestBatches() {
        return eligibleForProductionTestBatches;
    }

    public void setEligibleForProductionTestBatches(Boolean eligibleForProductionTestBatches) {
        this.eligibleForProductionTestBatches = eligibleForProductionTestBatches;
    }
}
