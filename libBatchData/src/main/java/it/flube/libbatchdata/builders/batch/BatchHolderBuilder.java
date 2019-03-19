/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.utilities.BuilderUtilities;
import it.flube.libbatchdata.builders.RouteStopBuilder;
import it.flube.libbatchdata.builders.serviceOrder.ServiceOrderBuilder;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.BatchNotificationSettings;
import it.flube.libbatchdata.entities.ChatHistory;
import it.flube.libbatchdata.entities.ChatMessage;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.Customer;
import it.flube.libbatchdata.entities.FileUpload;
import it.flube.libbatchdata.entities.MapPing;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.entities.DisplayDistance;
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
        private TargetEnvironmentConstants.TargetEnvironment targetEnvironment;

        public Builder(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            this.batchHolder = new BatchHolder();
            this.targetEnvironment = targetEnvironment;

            initializeData();
        }

        private void initializeData(){
            //create a single batch & batch detail object
            this.batchHolder.setBatch(new BatchBuilder.Builder().build());
            this.batchHolder.setBatchDetail(new BatchDetailBuilder.Builder(targetEnvironment).build());

            // link batch detail to the batch
            this.batchHolder.getBatchDetail().setBatchGuid(batchHolder.getBatch().getGuid());

            //a batch can have multiple route stops
            this.batchHolder.setRouteStops(new HashMap<String, RouteStop>());

            //a batch can have multiple service orders
            this.batchHolder.setServiceOrders(new HashMap<String, ServiceOrder>());

            // a batch can have multiple stepIds & steps
            this.batchHolder.setStepIds(new HashMap<String, StepId>());
            this.batchHolder.setSteps(new HashMap<String, OrderStepInterface>());

            // a batch can have multiple contact persons
            this.batchHolder.setContactPersons(new HashMap<String, ContactPerson>());
            this.batchHolder.setContactPersonsByServiceOrder(new HashMap<String, Map<String, ContactPerson>>());

            // a batch can have multiple mapPings
            this.batchHolder.setMapPings(new HashMap<String, MapPing>());

            // a batch can have multiples of each type of chat history
            this.batchHolder.setCustomerChatHistories(new HashMap<String, ChatHistory>());
            this.batchHolder.setServiceProviderChatHistories(new HashMap<String, ChatHistory>());
            this.batchHolder.setDriverChatHistories(new HashMap<String, ChatHistory>());

            // a batch can have multiple chat messages
            this.batchHolder.setChatMessages(new HashMap<String, ChatMessage>());

            //a batch can have multiple file attachments
            this.batchHolder.setFileAttachments(new HashMap<String, FileUpload>());
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

        public Builder customer(Customer customer){
            this.batchHolder.getBatchDetail().setCustomer(customer);
            return this;
        }

        /// expectedStartTime in millis or Date
        ///
        public Builder expectedStartTime(Date expectedStartTime){
            this.batchHolder.getBatch().setExpectedStartTime(BuilderUtilities.convertDateToMillis(expectedStartTime));
            this.batchHolder.getBatchDetail().setExpectedStartTime(BuilderUtilities.convertDateToMillis(expectedStartTime));
            return this;
        }

        public Builder expectedStartTime(Long expectedStartTime){
            this.batchHolder.getBatch().setExpectedStartTime(expectedStartTime);
            this.batchHolder.getBatchDetail().setExpectedStartTime(expectedStartTime);
            return this;
        }

        public Builder expectedStartTime(Date expectedStartTime, Integer minutesToAdd){
            this.batchHolder.getBatch().setExpectedStartTime(BuilderUtilities.addMinutesToDate(expectedStartTime, minutesToAdd));
            this.batchHolder.getBatchDetail().setExpectedStartTime(BuilderUtilities.addMinutesToDate(expectedStartTime, minutesToAdd));
            return this;
        }

        public Builder expectedStartTime(Long expectedStartTime, Integer minutesToAdd){
            this.batchHolder.getBatch().setExpectedStartTime(BuilderUtilities.addMinutesToDate(expectedStartTime, minutesToAdd));
            this.batchHolder.getBatchDetail().setExpectedStartTime(BuilderUtilities.addMinutesToDate(expectedStartTime, minutesToAdd));
            return this;
        }

        /// expectedFinishTime in millis or Date
        ///
        public Builder expectedFinishTime(Date expectedFinishTime){
            this.batchHolder.getBatch().setExpectedFinishTime(BuilderUtilities.convertDateToMillis(expectedFinishTime));
            this.batchHolder.getBatchDetail().setExpectedFinishTime(BuilderUtilities.convertDateToMillis(expectedFinishTime));
            return this;
        }

        public Builder expectedFinishTime(Long expectedFinishTime){
            this.batchHolder.getBatch().setExpectedFinishTime(expectedFinishTime);
            this.batchHolder.getBatchDetail().setExpectedFinishTime(expectedFinishTime);
            return this;
        }

        public Builder expectedFinishTime(Date expectedFinishTime, Integer minutesToAdd){
            this.batchHolder.getBatch().setExpectedFinishTime(BuilderUtilities.addMinutesToDate(expectedFinishTime, minutesToAdd));
            this.batchHolder.getBatchDetail().setExpectedFinishTime(BuilderUtilities.addMinutesToDate(expectedFinishTime, minutesToAdd));
            return this;
        }

        public Builder expectedFinishTime(Long expectedFinishTime, Integer minutesToAdd){
            this.batchHolder.getBatch().setExpectedFinishTime(BuilderUtilities.addMinutesToDate(expectedFinishTime, minutesToAdd));
            this.batchHolder.getBatchDetail().setExpectedFinishTime(BuilderUtilities.addMinutesToDate(expectedFinishTime, minutesToAdd));
            return this;
        }

        /// offerExpiryTime in millis or Date
        public Builder offerExpiryTime(Date offerExpiryTime){
            this.batchHolder.getBatch().setOfferExpiryTime(BuilderUtilities.convertDateToMillis(offerExpiryTime));
            this.batchHolder.getBatchDetail().setOfferExpiryTime(BuilderUtilities.convertDateToMillis(offerExpiryTime));
            return this;
        }

        public Builder offerExpiryTime(Long offerExpiryTime){
            this.batchHolder.getBatch().setOfferExpiryTime(offerExpiryTime);
            this.batchHolder.getBatchDetail().setOfferExpiryTime(offerExpiryTime);
            return this;
        }

        public Builder batchNotificationSettings(BatchNotificationSettings batchNotificationSettings){
            this.batchHolder.getBatchDetail().setBatchNotificationSettings(batchNotificationSettings);
            return this;
        }


        public Builder addServiceOrder(ServiceOrderScaffold serviceOrderScaffold) {


            // build a service order object
            ServiceOrder serviceOrder = new ServiceOrderBuilder.Builder(targetEnvironment)
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
                    .productList(serviceOrderScaffold.getProductList())
                    .serviceOrderNotificationSettings(serviceOrderScaffold.getServiceOrderNotificationSettings())
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

            // create hash maps for ContactPersons in this service order
            this.batchHolder.getContactPersonsByServiceOrder().put(serviceOrder.getGuid(), new HashMap<String, ContactPerson>());
            this.batchHolder.getBatchDetail().getContactPersonsByServiceOrder().put(serviceOrder.getGuid(), new HashMap<String, ContactPerson>());

            //add all the navigation steps to route stop list
            addNavigationStepDataToRouteStopList(serviceOrderScaffold.getSteps());

            return this;
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

        private void prevalidationCheck(BatchHolder batchHolder){
            //make batch has an expected start time & offerExpiry
            if (batchHolder.getBatch().getExpectedStartTime() == null){
                throw new IllegalStateException("expectedStartTime is null");
            }

            if (batchHolder.getBatch().getOfferExpiryTime() == null) {
                throw new IllegalStateException("offerExpiryTime is null");
            }

            if (batchHolder.getBatchDetail().getCustomer() == null){
                throw new IllegalStateException("customer is null");
            }

        }

        private void finalize(BatchHolder batchHolder){


            //set route stop & service order counts
            batchHolder.getBatchDetail().setServiceOrderCount(batchHolder.getServiceOrders().size());
            batchHolder.getBatchDetail().setRouteStopCount(batchHolder.getRouteStops().size());

            //set guids for all the steps
            SetStepGuids.setStepGuids(batchHolder);

            //add all contact persons to the contactPersonsByServiceOrder hashmap
            ExtractContactPersonsFromSteps.extractContactPersonsFromSteps(batchHolder);

            //set expected Start & Finish time on the batch, batchDetail, serviceOrders, and each step
            CalculateStartAndStopTimes.calculateStartAndStopTimes(batchHolder);
        }

        private void validate(BatchHolder batchHolder){

            if (!ValidateBatchHolder.isServiceOrderCountValid(batchHolder)){
                throw new IllegalStateException("too many service orders in this batch");
            }
            if (!ValidateBatchHolder.isContactPersonCountValid(batchHolder)){
                throw new IllegalStateException("too many ContactPersons in one or more service orders");
            }
        }

        public BatchHolder build(){
            BatchHolder batchHolder = new BatchHolderBuilder(this).getBatchHolder();
            prevalidationCheck(batchHolder);
            finalize(batchHolder);
            validate(batchHolder);
            return batchHolder;
        }
    }
}
