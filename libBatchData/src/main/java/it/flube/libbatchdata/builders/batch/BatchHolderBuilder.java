/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.RouteStopBuilder;
import it.flube.libbatchdata.builders.serviceOrder.ServiceOrderBuilder;
import it.flube.libbatchdata.entities.ChatHistory;
import it.flube.libbatchdata.entities.ChatMessage;
import it.flube.libbatchdata.entities.FileAttachment;
import it.flube.libbatchdata.entities.MapPing;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.entities.DisplayDistance;
import it.flube.libbatchdata.entities.DisplayTiming;
import it.flube.libbatchdata.entities.PotentialEarnings;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.libbatchdata.entities.orderStep.StepId;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrderScaffold;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 9/20/2017
 * Project : Driver
 */

public class BatchHolderBuilder {
    private BatchHolder batchHolder;

    private BatchHolderBuilder(Builder builder){
        this.batchHolder = builder.batchHolder;
    }

    private BatchHolder getBatchHolder(){
        return batchHolder;
    }
    public static class Builder{
        private BatchHolder batchHolder;

        public Builder(){
            this.batchHolder = new BatchHolder();
            initializeData();
        }

        private void initializeData(){
            //create a single batch & batch detail object
            this.batchHolder.setBatch(new BatchBuilder.Builder().build());
            this.batchHolder.setBatchDetail(new BatchDetailBuilder.Builder().build());

            // link batch detail to the batch
            this.batchHolder.getBatchDetail().setBatchGuid(batchHolder.getBatch().getGuid());

            //a batch can have multiple route stops
            this.batchHolder.setRouteStops(new HashMap<String, RouteStop>());

            //a batch can have multiple service orders
            this.batchHolder.setServiceOrders(new HashMap<String, ServiceOrder>());

            // a batch can have multiple stepIds & steps
            this.batchHolder.setStepIds(new HashMap<String, StepId>());
            this.batchHolder.setSteps(new HashMap<String, OrderStepInterface>());

            // a batch can have multiple mapPings
            this.batchHolder.setMapPings(new HashMap<String, MapPing>());

            // a batch can have multiples of each type of chat history
            this.batchHolder.setCustomerChatHistories(new HashMap<String, ChatHistory>());
            this.batchHolder.setServiceProviderChatHistories(new HashMap<String, ChatHistory>());
            this.batchHolder.setDriverChatHistories(new HashMap<String, ChatHistory>());

            // a batch can have multiple chat messages
            this.batchHolder.setChatMessages(new HashMap<String, ChatMessage>());

            //a batch can have multiple file attachments
            this.batchHolder.setFileAttachments(new HashMap<String, FileAttachment>());
        }

        public Builder batchType(BatchDetail.BatchType batchType){
            this.batchHolder.getBatchDetail().setBatchType(batchType);
            return this;
        }

        public Builder claimStatus(BatchDetail.ClaimStatus claimStatus){
            this.batchHolder.getBatchDetail().setClaimStatus(claimStatus);
            return this;
        }

        public Builder claimedByClientId(String claimedByClientId){
            this.batchHolder.getBatchDetail().setClaimedByClientId(claimedByClientId);
            return this;
        }

        public Builder guid(String guid){
            this.batchHolder.getBatch().setGuid(guid);
            this.batchHolder.getBatchDetail().setBatchGuid(guid);
            return this;
        }

        public Builder title(String title) {
            this.batchHolder.getBatch().setTitle(title);
            this.batchHolder.getBatchDetail().setTitle(title);
            return this;
        }

        public Builder description(String description){
            this.batchHolder.getBatchDetail().setDescription(description);
            return this;
        }

        public Builder iconUrl(String iconUrl){
            this.batchHolder.getBatch().setIconUrl(iconUrl);
            this.batchHolder.getBatchDetail().setIconUrl(iconUrl);
            return this;
        }

        public Builder workStatus(BatchDetail.WorkStatus workStatus){
            this.batchHolder.getBatchDetail().setWorkStatus(workStatus);
            return this;
        }

        public Builder displayTiming(DisplayTiming displayTiming){
            this.batchHolder.getBatch().setDisplayTiming(displayTiming);
            this.batchHolder.getBatchDetail().setDisplayTiming(displayTiming);
            return this;
        }

        public Builder displayDistance(DisplayDistance displayDistance){
            this.batchHolder.getBatch().setDisplayDistance(displayDistance);
            this.batchHolder.getBatchDetail().setDisplayDistance(displayDistance);
            return this;
        }

        public Builder potentialEarnings(PotentialEarnings potentialEarnings){
            this.batchHolder.getBatch().setPotentialEarnings(potentialEarnings);
            this.batchHolder.getBatchDetail().setPotentialEarnings(potentialEarnings);
            return this;
        }

        public Builder expectedStartTime(Date expectedStartTime){
            this.batchHolder.getBatch().setExpectedStartTime(expectedStartTime);
            return this;
        }

        public Builder expectedStartTime(Date expectedStartTime, Integer minutesToAdd){
            this.batchHolder.getBatch().setExpectedStartTime(BuilderUtilities.addMinutesToDate(expectedStartTime, minutesToAdd));
            return this;
        }

        public Builder expectedFinishTime(Date expectedFinishTime){
            this.batchHolder.getBatch().setExpectedFinishTime(expectedFinishTime);
            return this;
        }

        public Builder expectedFinishTime(Date expectedFinishTime, Integer minutesToAdd){
            this.batchHolder.getBatch().setExpectedFinishTime(BuilderUtilities.addMinutesToDate(expectedFinishTime, minutesToAdd));
            return this;
        }

        public Builder addServiceOrder(ServiceOrderScaffold serviceOrderScaffold) {


            // build a service order object
            ServiceOrder serviceOrder = new ServiceOrderBuilder.Builder()
                    .guid(serviceOrderScaffold.getGuid())
                    .batchGuid(this.batchHolder.getBatch().getGuid())
                    .batchDetailGuid(this.batchHolder.getBatchDetail().getGuid())
                    .status(serviceOrderScaffold.getStatus())
                    .title(serviceOrderScaffold.getTitle())
                    .description(serviceOrderScaffold.getDescription())
                    .startTime(serviceOrderScaffold.getStartTime())
                    .finishTime(serviceOrderScaffold.getFinishTime())
                    .sequence(this.batchHolder.getServiceOrders().size()+1)
                    .totalSteps(serviceOrderScaffold.getStepIds().size())
                    .build();

            //put this service order in the service order list & the service order hash map
            this.batchHolder.getServiceOrders().put(serviceOrder.getGuid(), serviceOrder);


            // assign batchGuid & batchDetailGuid to all the stepIds
            for (StepId stepId : serviceOrderScaffold.getStepIds().values()) {
                stepId.setBatchGuid(this.batchHolder.getBatch().getGuid());
                stepId.setBatchDetailGuid(this.batchHolder.getBatchDetail().getGuid());
            }

            //assign batchGuid & batchDetailGuid to all the steps
            for (OrderStepInterface step : serviceOrderScaffold.getSteps().values()) {
                step.setBatchGuid(this.batchHolder.getBatch().getGuid());
                step.setBatchDetailGuid(this.batchHolder.getBatchDetail().getGuid());
            }

            //put all the stepIds and steps for this service order into the hash maps
            this.batchHolder.getStepIds().putAll(serviceOrderScaffold.getStepIds());
            this.batchHolder.getSteps().putAll(serviceOrderScaffold.getSteps());

            //add all the navigation steps to route stop list
            addNavigationStepDataToRouteStopList(serviceOrderScaffold.getSteps());

            return this;
        }

        private void finalize(BatchHolder batchHolder){


            //set route stop & service order counts
            batchHolder.getBatchDetail().setServiceOrderCount(batchHolder.getServiceOrders().size());
            batchHolder.getBatchDetail().setRouteStopCount(batchHolder.getRouteStops().size());

            //set expected Start & Finish time on the batch, batchDetail, serviceOrders, and each step
            batchHolder = CalculateStartAndStopTimes.calculateStartAndStopTimes(batchHolder);

        }




        private void addNavigationStepDataToRouteStopList(Map<String, OrderStepInterface> steps){

            for (OrderStepInterface step : steps.values()){

                if (step.getTaskType() == OrderStepInterface.TaskType.NAVIGATION) {

                    ServiceOrderNavigationStep navStep = (ServiceOrderNavigationStep) step;

                    RouteStop routeStop = new RouteStopBuilder.Builder()
                            .batchGuid(navStep.getBatchGuid())
                            .batchDetailGuid(navStep.getBatchDetailGuid())
                            .latLonLocation(navStep.getDestination().getTargetLatLon())
                            .addressLocation(navStep.getDestination().getTargetAddress())
                            .description(navStep.getDescription())
                            .sequence(this.batchHolder.getRouteStops().size()+1)
                            .build();

                    this.batchHolder.getRouteStops().put(routeStop.getGuid(), routeStop);

                }
            }
        }

        private void validate(BatchHolder batchHolder){

        }

        public BatchHolder build(){
            BatchHolder batchHolder = new BatchHolderBuilder(this).getBatchHolder();
            finalize(batchHolder);
            validate(batchHolder);
            return batchHolder;
        }
    }
}
