/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.ArrayList;

import it.flube.libbatchdata.entities.orderStep.StepList;


/**
 * Created on 9/21/2017
 * Project : Driver
 */

public class StepListBuilder {
    private StepList stepList;

    private StepListBuilder(Builder builder){
        this.stepList = builder.stepList;
    }

    private StepList getStepList(){
        return stepList;
    }

    public static class Builder {
        private StepList stepList;

        public Builder(){
            stepList = new StepList();
            stepList.setGuid(BuilderUtilities.generateGuid());
            stepList.setStepIdGuids(new ArrayList<String>());
        }

        public Builder guid(String guid){
            this.stepList.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.stepList.setBatchGuid(guid);
            return this;
        }

        public Builder currentStep(Integer currentStep){
            this.stepList.setCurrentStep(currentStep);
            return this;
        }

        public Builder addStepIdGuid(String guid){
            this.stepList.getStepIdGuids().add(guid);
            return this;
        }

        public Builder addStepIdGuid(Integer index, String guid){
            this.stepList.getStepIdGuids().add(index, guid);
            return this;
        }

        private void validate(StepList stepList){

        }

        public StepList build(){
            StepList stepList = new StepListBuilder(this).getStepList();
            validate(stepList);
            return stepList;
        }
    }
}
