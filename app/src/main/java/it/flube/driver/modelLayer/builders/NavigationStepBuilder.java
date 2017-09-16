/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.Destination;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrderAbstractStep;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrderNavigationStep;

/**
 * Created on 8/24/2017
 * Project : Driver
 */

public class NavigationStepBuilder {
    private ServiceOrderNavigationStep navStep;

    private NavigationStepBuilder(@NonNull Builder builder) {
        this.navStep = builder.navStep;
    }

    private ServiceOrderNavigationStep getNavStep(){
        return navStep;
    }

    public static class Builder {
        private ServiceOrderNavigationStep navStep;

        public Builder(){
            navStep = new ServiceOrderNavigationStep();
            navStep.setGUID(UUID.randomUUID().toString());
            navStep.setAtDestination(false);
            navStep.setWorkStage(ServiceOrderAbstractStep.WorkStage.NOT_STARTED);
            navStep.setWorkTiming(ServiceOrderAbstractStep.WorkTiming.NOT_APPLICABLE);
            navStep.setWorkStatus(ServiceOrderAbstractStep.WorkStatus.NOT_APPLICABLE);
            navStep.setCloseEnoughInFeet(300); //default value is 300 feet
        }

        public Builder guid(@NonNull String guid) {
            this.navStep.setGUID(guid);
            return this;
        }

        public Builder title(@NonNull String title) {
            this.navStep.setTitle(title);
            return this;
        }

        public Builder description(@NonNull String description) {
            this.navStep.setDescription(description);
            return this;
        }

        public Builder note(@NonNull String note) {
            this.navStep.setNote(note);
            return this;
        }

        private Date addMinutesToDate(@NonNull Date initialDate, @NonNull Integer minutesToAdd){
            Calendar cal = Calendar.getInstance();
            cal.setTime(initialDate);
            cal.add(Calendar.MINUTE, minutesToAdd);
            return cal.getTime();
        }

        public Builder startTime(@NonNull Date startTime) {
            this.navStep.setStartTime(new TimestampBuilder.Builder(startTime).build());
            return this;
        }

        public Builder startTime(@NonNull Date startTime, @NonNull Integer minutesToAdd) {
            this.navStep.setStartTime(new TimestampBuilder.Builder(addMinutesToDate(startTime, minutesToAdd)).build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime) {
            this.navStep.setFinishTime(new TimestampBuilder.Builder(finishTime).build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime, @NonNull Integer minutesToAdd) {
            this.navStep.setStartTime(new TimestampBuilder.Builder(addMinutesToDate(finishTime, minutesToAdd)).build());
            return this;
        }

        public Builder milestoneWhenFinished(@NonNull String milestoneWhenFinished) {
            this.navStep.setMilestoneWhenFinished(milestoneWhenFinished);
            return this;
        }

        public Builder closeEnoughInFeet(@NonNull Double closeEnoughInFeet) {
            this.navStep.setCloseEnoughInFeet(closeEnoughInFeet);
            return this;
        }

        public Builder destination(@NonNull Destination destination) {
            this.navStep.setDestination(destination);
            return this;
        }

        private void validateNavigationStep(@NonNull ServiceOrderNavigationStep navStep) {
            // required PRESENT (must not be null)
            if (navStep.getGUID() == null) {
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

            if (navStep.getStartTime() == null) {
                throw new IllegalStateException("startTime is null");
            }

            if (navStep.getFinishTime() == null) {
                throw new IllegalStateException("finishTime is null");
            }

            if (navStep.getMilestoneWhenFinished() == null) {
                throw new IllegalStateException("milestoneWhenFinished is null");
            }

            //required ABSENT (must be null)


            //required SPECIFIC VALUE
            if (navStep.getTaskType() != ServiceOrderAbstractStep.TaskType.NAVIGATION) {
                throw new IllegalStateException("taskType is not NAVIGATION");
            }

            if (navStep.getAtDestination()) {
                throw new IllegalStateException("atDestination is not FALSE");
            }

            if (navStep.getWorkStage() != ServiceOrderAbstractStep.WorkStage.NOT_STARTED) {
                throw new IllegalStateException("workStage is not NOT_STARTED");
            }

            if (navStep.getWorkTiming() != ServiceOrderAbstractStep.WorkTiming.NOT_APPLICABLE) {
                throw new IllegalStateException("workTiming is not NOT_APPLICABLE");
            }

            if (navStep.getWorkStatus() != ServiceOrderAbstractStep.WorkStatus.NOT_APPLICABLE) {
                throw new IllegalStateException("workStatus is not NOT_APPLICABLE");
            }


        }

        public  ServiceOrderNavigationStep build(){
            ServiceOrderNavigationStep navStep = new NavigationStepBuilder(this).getNavStep();
            validateNavigationStep(navStep);
            return navStep;
        }

    }
}
