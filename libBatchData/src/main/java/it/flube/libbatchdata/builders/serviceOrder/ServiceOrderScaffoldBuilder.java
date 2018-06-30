/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.serviceOrder;



import java.util.Date;
import java.util.HashMap;

import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.StepIdBuilder;
import it.flube.libbatchdata.builders.TimestampBuilder;
import it.flube.libbatchdata.entities.orderStep.StepId;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrderScaffold;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 9/3/2017
 * Project : Driver
 */

public class ServiceOrderScaffoldBuilder {
    private ServiceOrderScaffold serviceOrderScaffold;

    private ServiceOrderScaffoldBuilder(Builder builder) {
        this.serviceOrderScaffold = builder.serviceOrderScaffold;
    }

    private ServiceOrderScaffold getServiceOrderScaffold(){
        return serviceOrderScaffold;
    }

    public static class Builder {
        private ServiceOrderScaffold serviceOrderScaffold;

        public Builder(){
            this.serviceOrderScaffold = new ServiceOrderScaffold();
            this.serviceOrderScaffold.setGuid(BuilderUtilities.generateGuid());
            this.serviceOrderScaffold.setStatus(ServiceOrder.ServiceOrderStatus.NOT_STARTED);

            //create the stepId & step list
            this.serviceOrderScaffold.setSteps(new HashMap<String, OrderStepInterface>());
            this.serviceOrderScaffold.setStepIds(new HashMap<String, StepId>());

        }

        public Builder guid(String guid) {
            this.serviceOrderScaffold.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.serviceOrderScaffold.setBatchGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.serviceOrderScaffold.setBatchDetailGuid(guid);
            return this;
        }

        public Builder title(String title){
            this.serviceOrderScaffold.setTitle(title);
            return this;
        }

        public Builder description(String description) {
            this.serviceOrderScaffold.setDescription(description);
            return this;
        }

        public Builder status(ServiceOrder.ServiceOrderStatus status) {
            this.serviceOrderScaffold.setStatus(status);
            return this;
        }


        public Builder startTime(Date startTime) {
            this.serviceOrderScaffold.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(startTime)
                    .build());
            return this;
        }

        public Builder startTime(Date startTime, Integer minutesToAdd) {
            this.serviceOrderScaffold.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.addMinutesToDate(startTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime) {
            this.serviceOrderScaffold.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(finishTime)
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime, Integer minutesToAdd) {
            this.serviceOrderScaffold.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.addMinutesToDate(finishTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder addStep(OrderStepInterface step){
            //set service order guid for this step
            step.setServiceOrderGuid(this.serviceOrderScaffold.getGuid());

            // build a step id for this step
            StepId stepId = new StepIdBuilder.Builder()
                    .serviceOrderGuid(this.serviceOrderScaffold.getGuid())
                    .stepGuid(step.getGuid())
                    .sequence(this.serviceOrderScaffold.getSteps().size()+1)
                    .taskType(step.getTaskType())
                    .build();

            //add the sequence to the step
            step.setSequence(this.serviceOrderScaffold.getSteps().size()+1);

            // add the stepId & step to the hash maps
            this.serviceOrderScaffold.getStepIds().put(stepId.getGuid(), stepId);
            this.serviceOrderScaffold.getSteps().put(step.getGuid(), step);
            return this;
        }

        private void finalize(ServiceOrderScaffold serviceOrder){

        }

        private void validate(ServiceOrderScaffold serviceOrder){
            // required PRESENT (must not be null)
            if (serviceOrder.getGuid() == null) {
                throw new IllegalStateException("GUID is null");
            }

            if (serviceOrder.getTitle() == null) {
                throw new IllegalStateException("title is null");
            }

            if (serviceOrder.getDescription() == null) {
                throw new IllegalStateException("description is null");
            }

            if (serviceOrder.getStartTime() == null) {
                //throw new IllegalStateException("startTime is null");
            }

            if (serviceOrder.getFinishTime() == null) {
                //throw new IllegalStateException("finishTime is null");
            }

            /*
            if (serviceOrder.getMapPingList() == null) {
                throw new IllegalStateException("mapPingList is null");
            } else {
                if (!serviceOrder.getMapPingList().isEmpty()) {
                    throw new IllegalStateException("mapPingList is not EMPTY");
                }
            }

            if (serviceOrder.getDriverChatHistory() == null) {
                throw new IllegalStateException("ChatHistory is null");
            } else {
                if (!serviceOrder.getDriverChatHistory().isEmpty()) {
                    throw new IllegalStateException("ChatHistory is not EMPTY");
                }
            }

            if (serviceOrder.getCustomerChatHistory() == null) {
                throw new IllegalStateException("ChatHistory is null");
            } else {
                if (!serviceOrder.getCustomerChatHistory().isEmpty()) {
                    throw new IllegalStateException("customerChatHistory is not EMPTY");
                }
            }

            if (serviceOrder.getServiceProviderChatHistory() == null) {
                throw new IllegalStateException("serviceProviderChatHistory is null");
            } else {
                if (!serviceOrder.getServiceProviderChatHistory().isEmpty()) {
                    throw new IllegalStateException("serviceProviderChatHistory is not EMPTY");
                }
            }

            if (serviceOrder.getStepList() == null) {
                throw new IllegalStateException("serviceProviderChatHistory is null");
            } else {
                if (serviceOrder.getStepList().isEmpty()) {
                    throw new IllegalStateException("serviceProviderChatHistory is EMPTY");
                }
            }

            // required SPECIFIC VALUE
            if (serviceOrder.getStepIndex() != 0) {
                throw new IllegalStateException("stepIndex is not zero");
            }
            */

        }

        public ServiceOrderScaffold build(){
            ServiceOrderScaffold serviceOrder = new ServiceOrderScaffoldBuilder(this).getServiceOrderScaffold();
            validate(serviceOrder);
            return serviceOrder;
        }
    }
}
