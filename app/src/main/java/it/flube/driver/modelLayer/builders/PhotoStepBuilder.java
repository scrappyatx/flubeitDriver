/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.Photo;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrderAbstractStep;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrderPhotoStep;

/**
 * Created on 9/3/2017
 * Project : Driver
 */

public class PhotoStepBuilder {
    private ServiceOrderPhotoStep photoStep;

    private PhotoStepBuilder(@NonNull Builder builder){
        this.photoStep = builder.photoStep;
    }

    private ServiceOrderPhotoStep getPhotoStep(){
        return photoStep;
    }

    public static class Builder {
        private ServiceOrderPhotoStep photoStep;

        public Builder(@NonNull String title, @NonNull String description){
            photoStep = new ServiceOrderPhotoStep();
            photoStep.setGUID(UUID.randomUUID().toString());
            photoStep.setTitle(title);
            photoStep.setDescription(description);
            photoStep.setWorkStage(ServiceOrderAbstractStep.WorkStage.NOT_STARTED);
            photoStep.setWorkTiming(ServiceOrderAbstractStep.WorkTiming.NOT_APPLICABLE);
            photoStep.setWorkStatus(ServiceOrderAbstractStep.WorkStatus.NOT_APPLICABLE);
            photoStep.setPhotoList(new ArrayList<Photo>());
        }

        public PhotoStepBuilder.Builder note(@NonNull String note) {
            this.photoStep.setNote(note);
            return this;
        }

        public PhotoStepBuilder.Builder startScheduledTime(@NonNull Date startScheduledTime) {
            this.photoStep.setStartTimestamp(new TimestampBuilder.Builder(startScheduledTime).build());
            return this;
        }

        public PhotoStepBuilder.Builder finishScheduledTime(@NonNull Date finishScheduledTime) {
            this.photoStep.setFinishTimestamp(new TimestampBuilder.Builder(finishScheduledTime).build());
            return this;
        }

        public PhotoStepBuilder.Builder milestoneWhenFinished(@NonNull String milestoneWhenFinished) {
            this.photoStep.setMilestoneWhenFinished(milestoneWhenFinished);
            return this;
        }

        public PhotoStepBuilder.Builder photo(@NonNull String title, @NonNull String description){
            this.photoStep.getPhotoList().add(new PhotoBuilder.Builder(title, description).build());
            return this;
        }

        private void validate(ServiceOrderPhotoStep photoStep){
            // required PRESENT (must not be null)
            if (photoStep.getGUID() == null) {
                throw new IllegalStateException("GUID is null");
            }

            if (photoStep.getTitle() == null) {
                throw new IllegalStateException("title is null");
            }

            if (photoStep.getDescription() == null) {
                throw new IllegalStateException("description is null");
            }

            if (photoStep.getStartTimestamp() == null) {
                throw new IllegalStateException("startTimestamp is null");
            }

            if (photoStep.getFinishTimestamp() == null) {
                throw new IllegalStateException("finishTimestamp is null");
            }

            if (photoStep.getMilestoneWhenFinished() == null) {
                throw new IllegalStateException("milestoneWhenFinished is null");
            }

            if (photoStep.getPhotoList() == null){
                throw new IllegalStateException("photoList is null");
            } else {
                if (photoStep.getPhotoList().isEmpty()) {
                    throw new IllegalStateException("photoList is empty");
                }
            }

            //required ABSENT (must be null)

            //required SPECIFIC VALUE
            if (photoStep.getTaskType() != ServiceOrderAbstractStep.TaskType.TAKE_PHOTOS) {
                throw new IllegalStateException("taskType is not TAKE_PHOTOS");
            }

            if (photoStep.getWorkStage() != ServiceOrderAbstractStep.WorkStage.NOT_STARTED) {
                throw new IllegalStateException("workStage is not NOT_STARTED");
            }

            if (photoStep.getWorkTiming() != ServiceOrderAbstractStep.WorkTiming.NOT_APPLICABLE) {
                throw new IllegalStateException("workTiming is not NOT_APPLICABLE");
            }

            if (photoStep.getWorkStatus() != ServiceOrderAbstractStep.WorkStatus.NOT_APPLICABLE) {
                throw new IllegalStateException("workStatus is not NOT_APPLICABLE");
            }

        }

        public ServiceOrderPhotoStep build(){
            ServiceOrderPhotoStep photoStep = new PhotoStepBuilder(this).getPhotoStep();
            validate(photoStep);
            return photoStep;
        }
    }
}
