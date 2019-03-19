/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.orderSteps;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.utilities.BuilderUtilities;
import it.flube.libbatchdata.builders.TimestampBuilder;
import it.flube.libbatchdata.entities.Destination;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 8/24/2017
 * Project : Driver
 */

public class NavigationStepBuilder {
    private static final OrderStepInterface.TaskType TASK_TYPE = OrderStepInterface.TaskType.NAVIGATION;
    private static final Integer DEFAULT_DURATION_MINUTES = 20;
    private static final String DEFAULT_MILESTONE_WHEN_FINISHED = "Arrived at Destination";

    /// icon strings use fontawesome.io icon strings
    private static final String TASK_ICON_STRING = "{fa-road}";

    private static final String TIMING_ON_TIME_ICON_STRING = "{fa-clock-o}";
    private static final String TIMING_LATE_ICON_STRING = "{fa-clock-o}";
    private static final String TIMING_VERY_LATE_ICON_STRING = "{fa-clock-o}";

    private static final String STATUS_NORMAL_ICON_STRING = "";
    private static final String STATUS_CUSTOMER_SUPPORT_ICON_STRING = "{fa-exclamation-circle}";

    private static final String STAGE_NOT_STARTED_ICON_STRING = "{fa-road}";
    private static final String STAGE_ACTIVE_ICON_STRING = "{fa-road}";
    private static final String STAGE_COMPLETED_ICON_STRING = "{fa-check-circle}";

    private ServiceOrderNavigationStep navStep;

    private NavigationStepBuilder(Builder builder) {
        this.navStep = builder.navStep;
    }

    private ServiceOrderNavigationStep getNavStep(){
        return navStep;
    }

    public static class Builder {
        private ServiceOrderNavigationStep navStep;

        public Builder(){
            navStep = new ServiceOrderNavigationStep();
            navStep.setTaskType(TASK_TYPE);
            navStep.setDurationMinutes(DEFAULT_DURATION_MINUTES);
            navStep.setMilestoneWhenFinished(DEFAULT_MILESTONE_WHEN_FINISHED);

            navStep.setGuid(BuilderUtilities.generateGuid());

            navStep.setWorkStage(OrderStepInterface.WorkStage.NOT_STARTED);
            navStep.setWorkTiming(OrderStepInterface.WorkTiming.ON_TIME);
            navStep.setWorkStatus(OrderStepInterface.WorkStatus.NORMAL);

            navStep.setAtDestination(false);
            navStep.setCloseEnoughInFeet(300); //default value is 300 feet

            //default task type icon string
            navStep.setTaskTypeIconText(TASK_ICON_STRING);

            //build default work timing icon text
            HashMap<String, String> workTimingIconTextMap = new HashMap<String, String>();
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.ON_TIME.toString(), TIMING_ON_TIME_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.LATE.toString(), TIMING_LATE_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.VERY_LATE.toString(), TIMING_VERY_LATE_ICON_STRING);
            navStep.setWorkTimingIconTextMap(workTimingIconTextMap);

            //build default work status icon strings
            HashMap<String, String> workStatusIconTextMap = new HashMap<String, String>();
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.NORMAL.toString(), STATUS_NORMAL_ICON_STRING);
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.CUSTOMER_SUPPORT.toString(), STATUS_CUSTOMER_SUPPORT_ICON_STRING);
            navStep.setWorkStatusIconTextMap(workStatusIconTextMap);

            //build default work stage icon strings
            HashMap<String, String> workStageIconTextMap = new HashMap<String, String>();
            workStageIconTextMap.put(OrderStepInterface.WorkStage.NOT_STARTED.toString(), STAGE_NOT_STARTED_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.ACTIVE.toString(), STAGE_ACTIVE_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.COMPLETED.toString(), STAGE_COMPLETED_ICON_STRING);
            navStep.setWorkStageIconTextMap(workStageIconTextMap);
        }

        public Builder taskTypeIconText(String taskTypeIconText){
            this.navStep.setTaskTypeIconText(taskTypeIconText);
            return this;
        }

        public Builder workTimingIconTextMap(Map<String, String> workTimingIconTextMap){
            this.navStep.setWorkTimingIconTextMap(workTimingIconTextMap);
            return this;
        }

        public Builder workStatusIconTextMap(Map<String, String> workStatusIconTextMap) {
            this.navStep.setWorkStatusIconTextMap(workStatusIconTextMap);
            return this;
        }

        public Builder workStageIconTextMap(Map<String, String> workStageIconTextMap) {
            this.navStep.setWorkStageIconTextMap(workStageIconTextMap);
            return this;
        }

        public Builder guid(String guid) {
            this.navStep.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.navStep.setBatchGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.navStep.setBatchDetailGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(String guid){
            this.navStep.setGuid(guid);
            return this;
        }

        public Builder sequence(Integer sequence){
            this.navStep.setSequence(sequence);
            return this;
        }

        public Builder title(String title) {
            this.navStep.setTitle(title);
            return this;
        }

        public Builder description(String description) {
            this.navStep.setDescription(description);
            return this;
        }

        public Builder note(String note) {
            this.navStep.setNote(note);
            return this;
        }

        public Builder startTime(Date startTime) {
            this.navStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.convertDateToMillis(startTime))
                    .build());
            return this;
        }

        public Builder startTime(Date startTime, Integer minutesToAdd) {
            this.navStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.addMinutesToDate(startTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime) {
            this.navStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.convertDateToMillis(finishTime))
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime, Integer minutesToAdd) {
            this.navStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.addMinutesToDate(finishTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder milestoneWhenFinished(String milestoneWhenFinished) {
            this.navStep.setMilestoneWhenFinished(milestoneWhenFinished);
            return this;
        }

        public Builder closeEnoughInFeet(Double closeEnoughInFeet) {
            this.navStep.setCloseEnoughInFeet(closeEnoughInFeet);
            return this;
        }

        public Builder destination(Destination destination) {
            this.navStep.setDestination(destination);
            return this;
        }

        private void validateNavigationStep(ServiceOrderNavigationStep navStep) {
            // required PRESENT (must not be null)
            if (navStep.getGuid() == null) {
                throw new IllegalStateException("GUID is null");
            }

            if (navStep.getTitle() == null) {
                throw new IllegalStateException("title is null");
            }

            if (navStep.getDescription() == null) {
                throw new IllegalStateException("description is null");
            }

            if (navStep.getDestination() == null) {
                throw new IllegalStateException("destination is null");
            }

            if (navStep.getDurationMinutes() == null){
                throw new IllegalStateException("duration minutes is null");
            }

            if (navStep.getMilestoneWhenFinished() == null) {
                throw new IllegalStateException("milestoneWhenFinished is null");
            }

            //required ABSENT (must be null)


            //required SPECIFIC VALUE
            if (navStep.getTaskType() != OrderStepInterface.TaskType.NAVIGATION) {
                throw new IllegalStateException("taskType is not NAVIGATION");
            }

        }

        public  ServiceOrderNavigationStep build(){
            ServiceOrderNavigationStep navStep = new NavigationStepBuilder(this).getNavStep();
            validateNavigationStep(navStep);
            return navStep;
        }

    }
}
