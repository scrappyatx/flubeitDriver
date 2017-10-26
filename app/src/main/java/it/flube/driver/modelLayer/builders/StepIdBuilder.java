/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderAbstractStep;
import it.flube.driver.modelLayer.entities.orderStep.StepId;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;

/**
 * Created on 9/17/2017
 * Project : Driver
 */

public class StepIdBuilder {
    private StepId stepId;

    private StepIdBuilder(Builder builder){
        this.stepId = builder.stepId;
    }

    private StepId getStepId(){
        return stepId;
    }

    public static class Builder{
        private StepId stepId;

        public Builder(){
            this.stepId = new StepId();
            this.stepId.setGuid(BuilderUtilities.generateGuid());
        }

        public Builder guid(String guid){
            this.stepId.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.stepId.setBatchGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.stepId.setBatchDetailGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(String guid){
            this.stepId.setServiceOrderGuid(guid);
            return this;
        }

        public Builder stepGuid(String guid){
            this.stepId.setStepGuid(guid);
            return this;
        }

        public Builder sequence(Integer sequence){
            this.stepId.setSequence(sequence);
            return this;
        }

        public Builder taskType(OrderStepInterface.TaskType taskType){
            this.stepId.setTaskType(taskType);
            return this;
        }

        public Builder stepClassName(String stepClassName){
            this.stepId.setStepClassName(stepClassName);
            return this;
        }

        private void validate(StepId stepId){

        }

        public StepId build(){
            StepId stepId = new StepIdBuilder(this).getStepId();
            validate(stepId);
            return stepId;
        }

    }
}
