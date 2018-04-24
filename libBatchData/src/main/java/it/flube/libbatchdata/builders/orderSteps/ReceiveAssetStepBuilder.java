/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.orderSteps;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.TimestampBuilder;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 4/21/2018
 * Project : Driver
 */
public class ReceiveAssetStepBuilder {
    private static final OrderStepInterface.TaskType TASK_TYPE = OrderStepInterface.TaskType.RECEIVE_ASSET;
    private static final Integer DEFAULT_DURATION_MINUTES = 10;
    private static final String DEFAULT_MILESTONE_WHEN_FINISHED = "Items Received";

    /// icon strings use fontawesome.io icon strings
    private static final String TASK_ICON_STRING = "{fa-hand-o-left}";

    private static final String TIMING_ON_TIME_ICON_STRING = "{fa-clock-o}";
    private static final String TIMING_LATE_ICON_STRING = "{fa-clock-o}";
    private static final String TIMING_VERY_LATE_ICON_STRING = "{fa-clock-o}";

    private static final String STATUS_NORMAL_ICON_STRING = "";
    private static final String STATUS_CUSTOMER_SUPPORT_ICON_STRING = "{fa-exclamation-circle}";

    private static final String STAGE_NOT_STARTED_ICON_STRING = "{fa-camera}";
    private static final String STAGE_ACTIVE_ICON_STRING = "{fa-camera}";
    private static final String STAGE_COMPLETED_ICON_STRING = "{fa-check-circle}";

    private ServiceOrderReceiveAssetStep receiveStep;

    private ReceiveAssetStepBuilder(Builder builder){ this.receiveStep = builder.receiveStep; }

    private ServiceOrderReceiveAssetStep getReceiveAssetStep(){ return this.receiveStep; }

    public static class Builder {
        private ServiceOrderReceiveAssetStep receiveStep;

        public Builder(){
            // create the asset receive step
            this.receiveStep = new ServiceOrderReceiveAssetStep();
            this.receiveStep.setTaskType(TASK_TYPE);
            this.receiveStep.setDurationMinutes(DEFAULT_DURATION_MINUTES);
            this.receiveStep.setMilestoneWhenFinished(DEFAULT_MILESTONE_WHEN_FINISHED);

            //generate guid & set work stage, work timing & work status
            this.receiveStep.setGuid(BuilderUtilities.generateGuid());
            this.receiveStep.setWorkStage(OrderStepInterface.WorkStage.NOT_STARTED);
            this.receiveStep.setWorkTiming(OrderStepInterface.WorkTiming.ON_TIME);
            this.receiveStep.setWorkStatus(OrderStepInterface.WorkStatus.NORMAL);

            //create storage for asset list
            this.receiveStep.setAssetList(new ArrayList<AssetTransfer>());


            //default task type icon string
            this.receiveStep.setTaskTypeIconText(TASK_ICON_STRING);

            //build default work timing icon text
            HashMap<String, String> workTimingIconTextMap = new HashMap<String, String>();
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.ON_TIME.toString(), TIMING_ON_TIME_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.LATE.toString(), TIMING_LATE_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.VERY_LATE.toString(), TIMING_VERY_LATE_ICON_STRING);
            this.receiveStep.setWorkTimingIconTextMap(workTimingIconTextMap);

            //build default work status icon strings
            HashMap<String, String> workStatusIconTextMap = new HashMap<String, String>();
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.NORMAL.toString(), STATUS_NORMAL_ICON_STRING);
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.CUSTOMER_SUPPORT.toString(), STATUS_CUSTOMER_SUPPORT_ICON_STRING);
            this.receiveStep.setWorkStatusIconTextMap(workStatusIconTextMap);

            //build default work stage icon strings
            HashMap<String, String> workStageIconTextMap = new HashMap<String, String>();
            workStageIconTextMap.put(OrderStepInterface.WorkStage.NOT_STARTED.toString(), STAGE_NOT_STARTED_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.ACTIVE.toString(), STAGE_ACTIVE_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.COMPLETED.toString(), STAGE_COMPLETED_ICON_STRING);
            this.receiveStep.setWorkStageIconTextMap(workStageIconTextMap);

            //set default transfer type
            this.receiveStep.setTransferType(AssetTransferInterface.TransferType.TRANSFER_FROM_CUSTOMER);

        }

        public Builder workTimingIconTextMap(Map<String, String> workTimingIconTextMap){
            this.receiveStep.setWorkTimingIconTextMap(workTimingIconTextMap);
            return this;
        }

        public Builder workStatusIconTextMap(Map<String, String> workStatusIconTextMap){
            this.receiveStep.setWorkStatusIconTextMap(workStatusIconTextMap);
            return this;
        }

        public Builder workStageIconTextMap(Map<String, String> workStageIconTextMap){
            this.receiveStep.setWorkStageIconTextMap(workStageIconTextMap);
            return this;
        }

        public Builder guid(String guid){
            this.receiveStep.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.receiveStep.setGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.receiveStep.setBatchDetailGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(String guid){
            this.receiveStep.setGuid(guid);
            return this;
        }

        public Builder sequence(Integer sequence){
            this.receiveStep.setSequence(sequence);
            return this;
        }

        public Builder title(String title){
            this.receiveStep.setTitle(title);
            return this;
        }

        public Builder description(String description) {
            this.receiveStep.setDescription(description);
            return this;
        }

        public Builder note(String note) {
            this.receiveStep.setNote(note);
            return this;
        }

        private Date addMinutesToDate(Date initialDate, Integer minutesToAdd){
            Calendar cal = Calendar.getInstance();
            cal.setTime(initialDate);
            cal.add(Calendar.MINUTE, minutesToAdd);
            return cal.getTime();
        }

        public Builder startTime(Date startTime) {
            this.receiveStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(startTime)
                    .build());
            return this;
        }

        public Builder startTime(Date startTime, Integer minutesToAdd) {
            this.receiveStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.addMinutesToDate(startTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime) {
            this.receiveStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(finishTime)
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime, Integer minutesToAdd) {
            this.receiveStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(addMinutesToDate(finishTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder milestoneWhenFinished(String milestoneWhenFinished) {
            this.receiveStep.setMilestoneWhenFinished(milestoneWhenFinished);
            return this;
        }

        public Builder transferType(AssetTransferInterface.TransferType transferType){
            this.receiveStep.setTransferType(transferType);
            return this;
        }

        public Builder contactPerson(ContactPerson contactPerson){
            this.receiveStep.setContactPerson(contactPerson);
            return this;
        }

        public Builder addAssetTransfer(AssetTransfer assetTransfer){
            this.receiveStep.getAssetList().add(assetTransfer);
            return this;
        }
        ////
        ////    validation
        ////

        private void validate(ServiceOrderReceiveAssetStep receiveStep){
            // required PRESENT (must not be null)
            if (receiveStep.getGuid() == null) {
                throw new IllegalStateException("GUID is null");
            }

            if (receiveStep.getTitle() == null) {
                throw new IllegalStateException("title is null");
            }

            if (receiveStep.getDescription() == null) {
                throw new IllegalStateException("description is null");
            }

            if (receiveStep.getStartTime() == null) {
                throw new IllegalStateException("startTime is null");
            }

            if (receiveStep.getFinishTime() == null) {
                throw new IllegalStateException("finishTime is null");
            }

            if (receiveStep.getDurationMinutes() == null){
                throw new IllegalStateException("duration minutes is null");
            }

            if (receiveStep.getMilestoneWhenFinished() == null) {
                throw new IllegalStateException("milestoneWhenFinished is null");
            }

            if (receiveStep.getContactPerson() == null) {
                throw new IllegalStateException("contactPerson is null");
            }

            if (receiveStep.getAssetList() == null) {
                throw new IllegalStateException("assetList is null");
            } else {
                if (receiveStep.getAssetList().isEmpty()){
                    throw new IllegalStateException("assetList is empty");
                }
            }

            //required ABSENT (must be null)

            //required SPECIFIC VALUE
            if (receiveStep.getTaskType() != OrderStepInterface.TaskType.RECEIVE_ASSET) {
                throw new IllegalStateException("taskType is not RECEIVE_ASSET");
            }

        }

        public ServiceOrderReceiveAssetStep build(){
            ServiceOrderReceiveAssetStep receiveStep = new ReceiveAssetStepBuilder(this).getReceiveAssetStep();
            validate(receiveStep);
            return receiveStep;
        }


    }
}
