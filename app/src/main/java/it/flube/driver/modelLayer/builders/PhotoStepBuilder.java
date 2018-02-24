/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.flube.driver.modelLayer.entities.PhotoRequest;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderAbstractStep;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;

/**
 * Created on 9/3/2017
 * Project : Driver
 */

public class PhotoStepBuilder {
    /// icon strings use fontawesome.io icon strings
    private static final String TASK_ICON_STRING = "{fa-camera}";

    private static final String TIMING_ON_TIME_ICON_STRING = "{fa-clock-o}";
    private static final String TIMING_LATE_ICON_STRING = "{fa-clock-o}";
    private static final String TIMING_VERY_LATE_ICON_STRING = "{fa-clock-o}";

    private static final String STATUS_NORMAL_ICON_STRING = "";
    private static final String STATUS_CUSTOMER_SUPPORT_ICON_STRING = "{fa-exclamation-circle}";

    private static final String STAGE_NOT_STARTED_ICON_STRING = "{fa-camera}";
    private static final String STAGE_ACTIVE_ICON_STRING = "{fa-camera}";
    private static final String STAGE_COMPLETED_ICON_STRING = "{fa-check-circle}";


    private ServiceOrderPhotoStep photoStep;

    private PhotoStepBuilder(@NonNull Builder builder){
        this.photoStep = builder.photoStep;
    }

    private ServiceOrderPhotoStep getPhotoStep(){
        return photoStep;
    }

    public static class Builder {
        private ServiceOrderPhotoStep photoStep;

        public Builder(){
            photoStep = new ServiceOrderPhotoStep();
            photoStep.setGuid(BuilderUtilities.generateGuid());
            photoStep.setWorkStage(OrderStepInterface.WorkStage.NOT_STARTED);
            photoStep.setWorkTiming(OrderStepInterface.WorkTiming.ON_TIME);
            photoStep.setWorkStatus(OrderStepInterface.WorkStatus.NORMAL);
            photoStep.setPhotoRequestList(new ArrayList<PhotoRequest>());

            //default task type icon string
            photoStep.setTaskTypeIconText(TASK_ICON_STRING);

            //build default work timing icon text
            HashMap<String, String> workTimingIconTextMap = new HashMap<String, String>();
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.ON_TIME.toString(), TIMING_ON_TIME_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.LATE.toString(), TIMING_LATE_ICON_STRING);
            workTimingIconTextMap.put(OrderStepInterface.WorkTiming.VERY_LATE.toString(), TIMING_VERY_LATE_ICON_STRING);
            photoStep.setWorkTimingIconTextMap(workTimingIconTextMap);

            //build default work status icon strings
            HashMap<String, String> workStatusIconTextMap = new HashMap<String, String>();
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.NORMAL.toString(), STATUS_NORMAL_ICON_STRING);
            workStatusIconTextMap.put(OrderStepInterface.WorkStatus.CUSTOMER_SUPPORT.toString(), STATUS_CUSTOMER_SUPPORT_ICON_STRING);
            photoStep.setWorkStatusIconTextMap(workStatusIconTextMap);

            //build default work stage icon strings
            HashMap<String, String> workStageIconTextMap = new HashMap<String, String>();
            workStageIconTextMap.put(OrderStepInterface.WorkStage.NOT_STARTED.toString(), STAGE_NOT_STARTED_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.ACTIVE.toString(), STAGE_ACTIVE_ICON_STRING);
            workStageIconTextMap.put(OrderStepInterface.WorkStage.COMPLETED.toString(), STAGE_COMPLETED_ICON_STRING);
            photoStep.setWorkStageIconTextMap(workStageIconTextMap);
        }

        public Builder workTimingIconTextMap(Map<String, String> workTimingIconTextMap){
            photoStep.setWorkTimingIconTextMap(workTimingIconTextMap);
            return this;
        }

        public Builder workStatusIconTextMap(Map<String, String> workStatusIconTextMap){
            photoStep.setWorkStatusIconTextMap(workStatusIconTextMap);
            return this;
        }

        public Builder workStageIconTextMap(Map<String, String> workStageIconTextMap){
            photoStep.setWorkStageIconTextMap(workStageIconTextMap);
            return this;
        }

        public Builder guid(@NonNull String guid){
            this.photoStep.setGuid(guid);
            return this;
        }

        public Builder batchGuid(@NonNull String guid){
            this.photoStep.setGuid(guid);
            return this;
        }

        public Builder batchDetaiGuid(@NonNull String guid){
            this.photoStep.setBatchDetailGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(@NonNull String guid){
            this.photoStep.setGuid(guid);
            return this;
        }

        public Builder sequence(@NonNull Integer sequence){
            this.photoStep.setSequence(sequence);
            return this;
        }

        public Builder title(@NonNull String title){
            this.photoStep.setTitle(title);
            return this;
        }

        public Builder description(@NonNull String description) {
            this.photoStep.setDescription(description);
            return this;
        }

        public Builder note(@NonNull String note) {
            this.photoStep.setNote(note);
            return this;
        }

        private Date addMinutesToDate(@NonNull Date initialDate, @NonNull Integer minutesToAdd){
            Calendar cal = Calendar.getInstance();
            cal.setTime(initialDate);
            cal.add(Calendar.MINUTE, minutesToAdd);
            return cal.getTime();
        }

        public Builder startTime(@NonNull Date startTime) {
            this.photoStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(startTime)
                    .build());
            return this;
        }

        public Builder startTime(@NonNull Date startTime, @NonNull Integer minutesToAdd) {
            this.photoStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.addMinutesToDate(startTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime) {
            this.photoStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(finishTime)
                    .build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime, @NonNull Integer minutesToAdd) {
            this.photoStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(addMinutesToDate(finishTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder milestoneWhenFinished(@NonNull String milestoneWhenFinished) {
            this.photoStep.setMilestoneWhenFinished(milestoneWhenFinished);
            return this;
        }

        public Builder addPhotoRequest(@NonNull PhotoRequest photoRequest){
            this.photoStep.getPhotoRequestList().add(photoRequest);
            return this;
        }

        public Builder addVehiclePhotoRequests(@NonNull ArrayList<PhotoRequest> photoList){
            this.photoStep.getPhotoRequestList().addAll(photoList);
            return this;
        }

        private void validate(ServiceOrderPhotoStep photoStep){
            // required PRESENT (must not be null)
            if (photoStep.getGuid() == null) {
                throw new IllegalStateException("photoStep GUID is null");
            }

            if (photoStep.getTitle() == null) {
                throw new IllegalStateException("photoStep title is null");
            }

            if (photoStep.getDescription() == null) {
                throw new IllegalStateException("photoStep description is null");
            }

            if (photoStep.getStartTime() == null) {
                throw new IllegalStateException("photoStep startTime is null");
            }

            if (photoStep.getFinishTime() == null) {
                throw new IllegalStateException("photoStep finishTime is null");
            }

            if (photoStep.getMilestoneWhenFinished() == null) {
                throw new IllegalStateException("photoStep milestoneWhenFinished is null");
            }

            if (photoStep.getPhotoRequestList() == null){
                throw new IllegalStateException("photoList is null");
            } else {
                if (photoStep.getPhotoRequestList().isEmpty()) {
                    throw new IllegalStateException("photoList is empty");
                }
            }

            //required ABSENT (must be null)

            //required SPECIFIC VALUE
            if (photoStep.getTaskType() != OrderStepInterface.TaskType.TAKE_PHOTOS) {
                throw new IllegalStateException("taskType is not TAKE_PHOTOS");
            }

        }

        public ServiceOrderPhotoStep build(){
            ServiceOrderPhotoStep photoStep = new PhotoStepBuilder(this).getPhotoStep();
            validate(photoStep);
            return photoStep;
        }
    }
}
