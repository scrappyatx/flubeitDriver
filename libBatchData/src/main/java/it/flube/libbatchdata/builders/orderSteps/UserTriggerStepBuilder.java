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
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderUserTriggerStep;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 4/24/2018
 * Project : Driver
 */
public class UserTriggerStepBuilder {
    private static final OrderStepInterface.TaskType TASK_TYPE = OrderStepInterface.TaskType.WAIT_FOR_USER_TRIGGER;
    private static final Integer DEFAULT_DURATION_MINUTES = 20;
    private static final String DEFAULT_MILESTONE_WHEN_FINISHED = "User Action Taken";

    /// icon strings use fontawesome.io icon strings
    private static final String TASK_ICON_STRING = "{fa-thumbs-o-up}";

    private static final String TIMING_ON_TIME_ICON_STRING = "{fa-clock-o}";
    private static final String TIMING_LATE_ICON_STRING = "{fa-clock-o}";
    private static final String TIMING_VERY_LATE_ICON_STRING = "{fa-clock-o}";

    private static final String STATUS_NORMAL_ICON_STRING = "";
    private static final String STATUS_CUSTOMER_SUPPORT_ICON_STRING = "{fa-exclamation-circle}";

    private static final String STAGE_NOT_STARTED_ICON_STRING = "{fa-camera}";
    private static final String STAGE_ACTIVE_ICON_STRING = "{fa-camera}";
    private static final String STAGE_COMPLETED_ICON_STRING = "{fa-check-circle}";

    private ServiceOrderUserTriggerStep triggerStep;

    private UserTriggerStepBuilder(Builder builder){
        this.triggerStep = builder.triggerStep;
    }

    private ServiceOrderUserTriggerStep getTriggerStep() {
        return this.triggerStep;
    }

    public static class Builder {
        private ServiceOrderUserTriggerStep triggerStep;

        public Builder(){
            triggerStep = new ServiceOrderUserTriggerStep();
            triggerStep.setTaskType(TASK_TYPE);
            triggerStep.setDurationMinutes(DEFAULT_DURATION_MINUTES);
            triggerStep.setMilestoneWhenFinished(DEFAULT_MILESTONE_WHEN_FINISHED);
            triggerStep.setUserHasTriggered(false);

            triggerStep.setGuid(BuilderUtilities.generateGuid());
            triggerStep.setWorkStage(OrderStepInterface.WorkStage.NOT_STARTED);
            triggerStep.setWorkTiming(OrderStepInterface.WorkTiming.ON_TIME);
            triggerStep.setWorkStatus(OrderStepInterface.WorkStatus.NORMAL);

            //default task type icon string
            triggerStep.setTaskTypeIconText(TASK_ICON_STRING);

            //build default work timing icon text
            HashMap<String, String> workTimingIconTextMap = new HashMap<String, String>();
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.ON_TIME.toString(), TIMING_ON_TIME_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.LATE.toString(), TIMING_LATE_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.VERY_LATE.toString(), TIMING_VERY_LATE_ICON_STRING);
            triggerStep.setWorkTimingIconTextMap(workTimingIconTextMap);

            //build default work status icon strings
            HashMap<String, String> workStatusIconTextMap = new HashMap<String, String>();
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.NORMAL.toString(), STATUS_NORMAL_ICON_STRING);
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.CUSTOMER_SUPPORT.toString(), STATUS_CUSTOMER_SUPPORT_ICON_STRING);
            triggerStep.setWorkStatusIconTextMap(workStatusIconTextMap);

            //build default work stage icon strings
            HashMap<String, String> workStageIconTextMap = new HashMap<String, String>();
            workStageIconTextMap.put(OrderStepInterface.WorkStage.NOT_STARTED.toString(), STAGE_NOT_STARTED_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.ACTIVE.toString(), STAGE_ACTIVE_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.COMPLETED.toString(), STAGE_COMPLETED_ICON_STRING);
            triggerStep.setWorkStageIconTextMap(workStageIconTextMap);
        }

        public Builder workTimingIconTextMap(Map<String, String> workTimingIconTextMap){
            this.triggerStep.setWorkTimingIconTextMap(workTimingIconTextMap);
            return this;
        }

        public Builder workStatusIconTextMap(Map<String, String> workStatusIconTextMap){
            this.triggerStep.setWorkStatusIconTextMap(workStatusIconTextMap);
            return this;
        }

        public Builder workStageIconTextMap(Map<String, String> workStageIconTextMap){
            this.triggerStep.setWorkStageIconTextMap(workStageIconTextMap);
            return this;
        }

        public Builder guid(String guid){
            this.triggerStep.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.triggerStep.setGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.triggerStep.setBatchDetailGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(String guid){
            this.triggerStep.setGuid(guid);
            return this;
        }

        public Builder sequence(Integer sequence){
            this.triggerStep.setSequence(sequence);
            return this;
        }

        public Builder title(String title){
            this.triggerStep.setTitle(title);
            return this;
        }

        public Builder description(String description) {
            this.triggerStep.setDescription(description);
            return this;
        }

        public Builder note(String note) {
            this.triggerStep.setNote(note);
            return this;
        }

        private Date addMinutesToDate(Date initialDate, Integer minutesToAdd){
            Calendar cal = Calendar.getInstance();
            cal.setTime(initialDate);
            cal.add(Calendar.MINUTE, minutesToAdd);
            return cal.getTime();
        }

        public Builder startTime(Date startTime) {
            this.triggerStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(startTime)
                    .build());
            return this;
        }

        public Builder startTime(Date startTime, Integer minutesToAdd) {
            this.triggerStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.addMinutesToDate(startTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime) {
            this.triggerStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(finishTime)
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime, Integer minutesToAdd) {
            this.triggerStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(addMinutesToDate(finishTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder milestoneWhenFinished(String milestoneWhenFinished) {
            this.triggerStep.setMilestoneWhenFinished(milestoneWhenFinished);
            return this;
        }

        private void validate(ServiceOrderUserTriggerStep triggerStep){
// required PRESENT (must not be null)
            if (triggerStep.getGuid() == null) {
                throw new IllegalStateException("GUID is null");
            }

            if (triggerStep.getTitle() == null) {
                throw new IllegalStateException("title is null");
            }

            if (triggerStep.getDescription() == null) {
                throw new IllegalStateException("description is null");
            }

            if (triggerStep.getStartTime() == null) {
                throw new IllegalStateException("startTime is null");
            }

            if (triggerStep.getFinishTime() == null) {
                throw new IllegalStateException("finishTime is null");
            }

            if (triggerStep.getDurationMinutes() == null){
                throw new IllegalStateException("duration minutes is null");
            }

            if (triggerStep.getMilestoneWhenFinished() == null) {
                throw new IllegalStateException("milestoneWhenFinished is null");
            }
            //required ABSENT (must be null)

            //required SPECIFIC VALUE
            if (triggerStep.getTaskType() != OrderStepInterface.TaskType.WAIT_FOR_USER_TRIGGER) {
                throw new IllegalStateException("taskType is not WAIT_FOR_USER_TRIGGER");
            }

        }

        public ServiceOrderUserTriggerStep build(){
            ServiceOrderUserTriggerStep triggerStep = new UserTriggerStepBuilder(this).getTriggerStep();
            validate(triggerStep);
            return triggerStep;
        }

    }
}
