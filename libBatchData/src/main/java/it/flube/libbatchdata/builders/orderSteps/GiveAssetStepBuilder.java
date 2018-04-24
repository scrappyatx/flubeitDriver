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
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 4/24/2018
 * Project : Driver
 */
public class GiveAssetStepBuilder {
    private static final OrderStepInterface.TaskType TASK_TYPE = OrderStepInterface.TaskType.GIVE_ASSET;
    private static final Integer DEFAULT_DURATION_MINUTES = 10;
    private static final String DEFAULT_MILESTONE_WHEN_FINISHED = "Items Given";

    /// icon strings use fontawesome.io icon strings
    private static final String TASK_ICON_STRING = "{fa-hand-o-right}";

    private static final String TIMING_ON_TIME_ICON_STRING = "{fa-clock-o}";
    private static final String TIMING_LATE_ICON_STRING = "{fa-clock-o}";
    private static final String TIMING_VERY_LATE_ICON_STRING = "{fa-clock-o}";

    private static final String STATUS_NORMAL_ICON_STRING = "";
    private static final String STATUS_CUSTOMER_SUPPORT_ICON_STRING = "{fa-exclamation-circle}";

    private static final String STAGE_NOT_STARTED_ICON_STRING = "{fa-camera}";
    private static final String STAGE_ACTIVE_ICON_STRING = "{fa-camera}";
    private static final String STAGE_COMPLETED_ICON_STRING = "{fa-check-circle}";

    private ServiceOrderGiveAssetStep giveStep;

    private GiveAssetStepBuilder(Builder builder){
        this.giveStep = builder.giveStep;
    }

    private ServiceOrderGiveAssetStep getGiveAssetStep(){
        return this.giveStep;
    }

    public static class Builder {
        private ServiceOrderGiveAssetStep giveStep;

        public Builder(){
            // create the asset receive step
            this.giveStep = new ServiceOrderGiveAssetStep();
            this.giveStep.setTaskType(TASK_TYPE);
            this.giveStep.setDurationMinutes(DEFAULT_DURATION_MINUTES);
            this.giveStep.setMilestoneWhenFinished(DEFAULT_MILESTONE_WHEN_FINISHED);

            //generate guid & set work stage, work timing & work status
            this.giveStep.setGuid(BuilderUtilities.generateGuid());
            this.giveStep.setWorkStage(OrderStepInterface.WorkStage.NOT_STARTED);
            this.giveStep.setWorkTiming(OrderStepInterface.WorkTiming.ON_TIME);
            this.giveStep.setWorkStatus(OrderStepInterface.WorkStatus.NORMAL);

            //create storage for asset list
            this.giveStep.setAssetList(new ArrayList<AssetTransfer>());


            //default task type icon string
            this.giveStep.setTaskTypeIconText(TASK_ICON_STRING);

            //build default work timing icon text
            HashMap<String, String> workTimingIconTextMap = new HashMap<String, String>();
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.ON_TIME.toString(), TIMING_ON_TIME_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.LATE.toString(), TIMING_LATE_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.VERY_LATE.toString(), TIMING_VERY_LATE_ICON_STRING);
            this.giveStep.setWorkTimingIconTextMap(workTimingIconTextMap);

            //build default work status icon strings
            HashMap<String, String> workStatusIconTextMap = new HashMap<String, String>();
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.NORMAL.toString(), STATUS_NORMAL_ICON_STRING);
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.CUSTOMER_SUPPORT.toString(), STATUS_CUSTOMER_SUPPORT_ICON_STRING);
            this.giveStep.setWorkStatusIconTextMap(workStatusIconTextMap);

            //build default work stage icon strings
            HashMap<String, String> workStageIconTextMap = new HashMap<String, String>();
            workStageIconTextMap.put(OrderStepInterface.WorkStage.NOT_STARTED.toString(), STAGE_NOT_STARTED_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.ACTIVE.toString(), STAGE_ACTIVE_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.COMPLETED.toString(), STAGE_COMPLETED_ICON_STRING);
            this.giveStep.setWorkStageIconTextMap(workStageIconTextMap);

            //set default transfer type
            this.giveStep.setTransferType(AssetTransferInterface.TransferType.TRANSER_TO_CUSTOMER);
        }

        public Builder workTimingIconTextMap(Map<String, String> workTimingIconTextMap){
            this.giveStep.setWorkTimingIconTextMap(workTimingIconTextMap);
            return this;
        }

        public Builder workStatusIconTextMap(Map<String, String> workStatusIconTextMap){
            this.giveStep.setWorkStatusIconTextMap(workStatusIconTextMap);
            return this;
        }

        public Builder workStageIconTextMap(Map<String, String> workStageIconTextMap){
            this.giveStep.setWorkStageIconTextMap(workStageIconTextMap);
            return this;
        }

        public Builder guid(String guid){
            this.giveStep.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.giveStep.setGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.giveStep.setBatchDetailGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(String guid){
            this.giveStep.setGuid(guid);
            return this;
        }

        public Builder sequence(Integer sequence){
            this.giveStep.setSequence(sequence);
            return this;
        }

        public Builder title(String title){
            this.giveStep.setTitle(title);
            return this;
        }

        public Builder description(String description) {
            this.giveStep.setDescription(description);
            return this;
        }

        public Builder note(String note) {
            this.giveStep.setNote(note);
            return this;
        }

        private Date addMinutesToDate(Date initialDate, Integer minutesToAdd){
            Calendar cal = Calendar.getInstance();
            cal.setTime(initialDate);
            cal.add(Calendar.MINUTE, minutesToAdd);
            return cal.getTime();
        }

        public Builder startTime(Date startTime) {
            this.giveStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(startTime)
                    .build());
            return this;
        }

        public Builder startTime(Date startTime, Integer minutesToAdd) {
            this.giveStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.addMinutesToDate(startTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime) {
            this.giveStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(finishTime)
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime, Integer minutesToAdd) {
            this.giveStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(addMinutesToDate(finishTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder milestoneWhenFinished(String milestoneWhenFinished) {
            this.giveStep.setMilestoneWhenFinished(milestoneWhenFinished);
            return this;
        }

        public Builder transferType(AssetTransferInterface.TransferType transferType){
            this.giveStep.setTransferType(transferType);
            return this;
        }

        public Builder contactPerson(ContactPerson contactPerson){
            this.giveStep.setContactPerson(contactPerson);
            return this;
        }

        public Builder addAssetTransfer(AssetTransfer assetTransfer){
            this.giveStep.getAssetList().add(assetTransfer);
            return this;
        }

        private void validate(ServiceOrderGiveAssetStep giveStep){
            // required PRESENT (must not be null)
            if (giveStep.getGuid() == null) {
                throw new IllegalStateException("GUID is null");
            }

            if (giveStep.getTitle() == null) {
                throw new IllegalStateException("title is null");
            }

            if (giveStep.getDescription() == null) {
                throw new IllegalStateException("description is null");
            }

            if (giveStep.getStartTime() == null) {
                throw new IllegalStateException("startTime is null");
            }

            if (giveStep.getFinishTime() == null) {
                throw new IllegalStateException("finishTime is null");
            }

            if (giveStep.getDurationMinutes() == null){
                throw new IllegalStateException("duration minutes is null");
            }

            if (giveStep.getMilestoneWhenFinished() == null) {
                throw new IllegalStateException("milestoneWhenFinished is null");
            }

            if (giveStep.getContactPerson() == null) {
                throw new IllegalStateException("contactPerson is null");
            }

            if (giveStep.getAssetList() == null) {
                throw new IllegalStateException("assetList is null");
            } else {
                if (giveStep.getAssetList().isEmpty()){
                    throw new IllegalStateException("assetList is empty");
                }
            }

            //required ABSENT (must be null)

            //required SPECIFIC VALUE
            if (giveStep.getTaskType() != OrderStepInterface.TaskType.GIVE_ASSET) {
                throw new IllegalStateException("taskType is not GIVE_ASSET");
            }
        }

        public ServiceOrderGiveAssetStep build(){
            ServiceOrderGiveAssetStep giveStep = new GiveAssetStepBuilder(this).getGiveAssetStep();
            validate(giveStep);
            return giveStep;
        }
    }
}
