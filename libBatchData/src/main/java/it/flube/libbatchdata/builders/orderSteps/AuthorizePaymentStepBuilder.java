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
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGiveAssetStep;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 4/24/2018
 * Project : Driver
 */
public class AuthorizePaymentStepBuilder {
    private static final OrderStepInterface.TaskType TASK_TYPE = OrderStepInterface.TaskType.AUTHORIZE_PAYMENT;
    private static final Integer DEFAULT_DURATION_MINUTES = 5;
    private static final String DEFAULT_MILESTONE_WHEN_FINISHED = "User has made payment";

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

    private ServiceOrderAuthorizePaymentStep paymentStep;

    private AuthorizePaymentStepBuilder(Builder builder){
        this.paymentStep = builder.paymentStep;
    }

    private ServiceOrderAuthorizePaymentStep getAuthorizePaymentStep(){
        return this.paymentStep;
    }

    public static class Builder {
        private ServiceOrderAuthorizePaymentStep paymentStep;

        public Builder(){
            // create the asset receive step
            this.paymentStep = new ServiceOrderAuthorizePaymentStep();
            this.paymentStep.setTaskType(TASK_TYPE);
            this.paymentStep.setDurationMinutes(DEFAULT_DURATION_MINUTES);
            this.paymentStep.setMilestoneWhenFinished(DEFAULT_MILESTONE_WHEN_FINISHED);

            //generate guid & set work stage, work timing & work status
            this.paymentStep.setGuid(BuilderUtilities.generateGuid());
            this.paymentStep.setWorkStage(OrderStepInterface.WorkStage.NOT_STARTED);
            this.paymentStep.setWorkTiming(OrderStepInterface.WorkTiming.ON_TIME);
            this.paymentStep.setWorkStatus(OrderStepInterface.WorkStatus.NORMAL);

            //default task type icon string
            this.paymentStep.setTaskTypeIconText(TASK_ICON_STRING);

            //build default work timing icon text
            HashMap<String, String> workTimingIconTextMap = new HashMap<String, String>();
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.ON_TIME.toString(), TIMING_ON_TIME_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.LATE.toString(), TIMING_LATE_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.VERY_LATE.toString(), TIMING_VERY_LATE_ICON_STRING);
            this.paymentStep.setWorkTimingIconTextMap(workTimingIconTextMap);

            //build default work status icon strings
            HashMap<String, String> workStatusIconTextMap = new HashMap<String, String>();
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.NORMAL.toString(), STATUS_NORMAL_ICON_STRING);
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.CUSTOMER_SUPPORT.toString(), STATUS_CUSTOMER_SUPPORT_ICON_STRING);
            this.paymentStep.setWorkStatusIconTextMap(workStatusIconTextMap);

            //build default work stage icon strings
            HashMap<String, String> workStageIconTextMap = new HashMap<String, String>();
            workStageIconTextMap.put(OrderStepInterface.WorkStage.NOT_STARTED.toString(), STAGE_NOT_STARTED_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.ACTIVE.toString(), STAGE_ACTIVE_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.COMPLETED.toString(), STAGE_COMPLETED_ICON_STRING);
            this.paymentStep.setWorkStageIconTextMap(workStageIconTextMap);

        }

        public Builder workTimingIconTextMap(Map<String, String> workTimingIconTextMap){
            this.paymentStep.setWorkTimingIconTextMap(workTimingIconTextMap);
            return this;
        }

        public Builder workStatusIconTextMap(Map<String, String> workStatusIconTextMap){
            this.paymentStep.setWorkStatusIconTextMap(workStatusIconTextMap);
            return this;
        }

        public Builder workStageIconTextMap(Map<String, String> workStageIconTextMap){
            this.paymentStep.setWorkStageIconTextMap(workStageIconTextMap);
            return this;
        }

        public Builder guid(String guid){
            this.paymentStep.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.paymentStep.setGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.paymentStep.setBatchDetailGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(String guid){
            this.paymentStep.setGuid(guid);
            return this;
        }

        public Builder sequence(Integer sequence){
            this.paymentStep.setSequence(sequence);
            return this;
        }

        public Builder title(String title){
            this.paymentStep.setTitle(title);
            return this;
        }

        public Builder description(String description) {
            this.paymentStep.setDescription(description);
            return this;
        }

        public Builder note(String note) {
            this.paymentStep.setNote(note);
            return this;
        }

        private Date addMinutesToDate(Date initialDate, Integer minutesToAdd){
            Calendar cal = Calendar.getInstance();
            cal.setTime(initialDate);
            cal.add(Calendar.MINUTE, minutesToAdd);
            return cal.getTime();
        }

        public Builder startTime(Date startTime) {
            this.paymentStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(startTime)
                    .build());
            return this;
        }

        public Builder startTime(Date startTime, Integer minutesToAdd) {
            this.paymentStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.addMinutesToDate(startTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime) {
            this.paymentStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(finishTime)
                    .build());
            return this;
        }

        public Builder finishTime(Date finishTime, Integer minutesToAdd) {
            this.paymentStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(addMinutesToDate(finishTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder milestoneWhenFinished(String milestoneWhenFinished) {
            this.paymentStep.setMilestoneWhenFinished(milestoneWhenFinished);
            return this;
        }

        public Builder maxPaymentAmount(String maxPaymentAmount){
            this.paymentStep.setMaxPaymentAmount(maxPaymentAmount);
            return this;
        }

        private void validate(ServiceOrderAuthorizePaymentStep paymentStep){
            // required PRESENT (must not be null)
            if (paymentStep.getGuid() == null) {
                throw new IllegalStateException("GUID is null");
            }

            if (paymentStep.getTitle() == null) {
                throw new IllegalStateException("title is null");
            }

            if (paymentStep.getDescription() == null) {
                throw new IllegalStateException("description is null");
            }

            if (paymentStep.getDurationMinutes() == null){
                throw new IllegalStateException("duration minutes is null");
            }

            if (paymentStep.getMilestoneWhenFinished() == null) {
                throw new IllegalStateException("milestoneWhenFinished is null");
            }

            if (paymentStep.getMaxPaymentAmount() == null) {
                throw new IllegalStateException("maxPaymentAmount is null");
            }

            //required ABSENT (must be null)

            //required SPECIFIC VALUE
            if (paymentStep.getTaskType() != OrderStepInterface.TaskType.AUTHORIZE_PAYMENT) {
                throw new IllegalStateException("taskType is not AUTHORIZE_PAYMENT");
            }
        }

        public ServiceOrderAuthorizePaymentStep build(){
            ServiceOrderAuthorizePaymentStep paymentStep = new AuthorizePaymentStepBuilder(this).getAuthorizePaymentStep();
            validate(paymentStep);
            return paymentStep;
        }
    }
}
