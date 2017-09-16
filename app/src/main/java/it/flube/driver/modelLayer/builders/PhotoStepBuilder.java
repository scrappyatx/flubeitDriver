/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
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

        public Builder(){
            photoStep = new ServiceOrderPhotoStep();
            photoStep.setGUID(UUID.randomUUID().toString());
            photoStep.setWorkStage(ServiceOrderAbstractStep.WorkStage.NOT_STARTED);
            photoStep.setWorkTiming(ServiceOrderAbstractStep.WorkTiming.NOT_APPLICABLE);
            photoStep.setWorkStatus(ServiceOrderAbstractStep.WorkStatus.NOT_APPLICABLE);
            photoStep.setPhotoList(new ArrayList<Photo>());
        }

        public Builder guid(String guid){
            this.photoStep.setGUID(guid);
            return this;
        }

        public Builder title(String title){
            this.photoStep.setTitle(title);
            return this;
        }

        public Builder description(String description) {
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
            this.photoStep.setStartTime(new TimestampBuilder.Builder(startTime).build());
            return this;
        }

        public Builder startTime(@NonNull Date startTime, @NonNull Integer minutesToAdd) {
            this.photoStep.setStartTime(new TimestampBuilder.Builder(addMinutesToDate(startTime, minutesToAdd)).build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime) {
            this.photoStep.setFinishTime(new TimestampBuilder.Builder(finishTime).build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime, @NonNull Integer minutesToAdd) {
            this.photoStep.setStartTime(new TimestampBuilder.Builder(addMinutesToDate(finishTime, minutesToAdd)).build());
            return this;
        }

        public Builder milestoneWhenFinished(@NonNull String milestoneWhenFinished) {
            this.photoStep.setMilestoneWhenFinished(milestoneWhenFinished);
            return this;
        }

        public Builder addPhoto(@NonNull Photo photo){
            this.photoStep.getPhotoList().add(photo);
            return this;
        }

        public Builder addPhoto(@NonNull Integer index, @NonNull Photo photo){
            this.photoStep.getPhotoList().add(index, photo);
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

            if (photoStep.getStartTime() == null) {
                throw new IllegalStateException("startTime is null");
            }

            if (photoStep.getFinishTime() == null) {
                throw new IllegalStateException("finishTime is null");
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
