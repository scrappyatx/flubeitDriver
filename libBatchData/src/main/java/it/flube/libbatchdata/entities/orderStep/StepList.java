/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.orderStep;

import java.util.ArrayList;

/**
 * Created on 9/17/2017
 * Project : Driver
 */

public class StepList {
    private String guid;
    private String batchGuid;
    private String batchDetailGuid;
    private String serviceOrderGuid;

    private Integer currentStep;
    private ArrayList<String> stepIdGuids;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getBatchGuid() {
        return batchGuid;
    }

    public void setBatchGuid(String batchGuid) {
        this.batchGuid = batchGuid;
    }

    public String getBatchDetailGuid() {
        return batchDetailGuid;
    }

    public void setBatchDetailGuid(String batchDetailGuid) {
        this.batchDetailGuid = batchDetailGuid;
    }

    public String getServiceOrderGuid() {
        return serviceOrderGuid;
    }

    public void setServiceOrderGuid(String serviceOrderGuid) {
        this.serviceOrderGuid = serviceOrderGuid;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

    public ArrayList<String> getStepIdGuids() {
        return stepIdGuids;
    }

    public void setStepIdGuids(ArrayList<String> stepIdGuids) {
        this.stepIdGuids = stepIdGuids;
    }
}
