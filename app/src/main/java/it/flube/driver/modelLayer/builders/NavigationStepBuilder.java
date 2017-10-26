/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

import it.flube.driver.modelLayer.entities.Destination;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderAbstractStep;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderNavigationStep;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;

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
            navStep.setGuid(BuilderUtilities.generateGuid());
            navStep.setAtDestination(false);
            navStep.setWorkStage(OrderStepInterface.WorkStage.NOT_STARTED);
            navStep.setWorkTiming(OrderStepInterface.WorkTiming.NOT_APPLICABLE);
            navStep.setWorkStatus(OrderStepInterface.WorkStatus.NOT_APPLICABLE);
            navStep.setCloseEnoughInFeet(300); //default value is 300 feet
        }

        public Builder guid(@NonNull String guid) {
            this.navStep.setGuid(guid);
            return this;
        }

        public Builder batchGuid(@NonNull String guid){
            this.navStep.setBatchGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(@NonNull String guid){
            this.navStep.setBatchDetailGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(@NonNull String guid){
            this.navStep.setGuid(guid);
            return this;
        }

        public Builder sequence(@NonNull Integer sequence){
            this.navStep.setSequence(sequence);
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

        public Builder startTime(@NonNull Date startTime) {
            this.navStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(startTime)
                    .build());
            return this;
        }

        public Builder startTime(@NonNull Date startTime, @NonNull Integer minutesToAdd) {
            this.navStep.setStartTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.addMinutesToDate(startTime, minutesToAdd))
                    .build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime) {
            this.navStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(finishTime)
                    .build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime, @NonNull Integer minutesToAdd) {
            this.navStep.setFinishTime(new TimestampBuilder.Builder()
                    .scheduledTime(BuilderUtilities.addMinutesToDate(finishTime, minutesToAdd))
                    .build());
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
            if (navStep.getGuid() == null) {
                throw new IllegalStateException("navigation step GUID is null");
            }

            if (navStep.getTitle() == null) {
                throw new IllegalStateException("navigation step title is null");
            }

            if (navStep.getDescription() == null) {
                throw new IllegalStateException("navigation step description is null");
            }

            if (navStep.getDestination() == null) {
                throw new IllegalStateException("navigation step destination is null");
            }

            if (navStep.getStartTime() == null) {
                throw new IllegalStateException("navigation step startTime is null");
            }

            if (navStep.getFinishTime() == null) {
                throw new IllegalStateException("navigation step finishTime is null");
            }

            if (navStep.getMilestoneWhenFinished() == null) {
                throw new IllegalStateException("navigation step milestoneWhenFinished is null");
            }

            //required ABSENT (must be null)


            //required SPECIFIC VALUE
            if (navStep.getTaskType() != OrderStepInterface.TaskType.NAVIGATION) {
                throw new IllegalStateException("taskType is not NAVIGATION");
            }

            if (navStep.getAtDestination()) {
                throw new IllegalStateException("atDestination is not FALSE");
            }

            if (navStep.getWorkStage() != OrderStepInterface.WorkStage.NOT_STARTED) {
                throw new IllegalStateException("workStage is not NOT_STARTED");
            }

            if (navStep.getWorkTiming() != OrderStepInterface.WorkTiming.NOT_APPLICABLE) {
                throw new IllegalStateException("workTiming is not NOT_APPLICABLE");
            }

            if (navStep.getWorkStatus() != OrderStepInterface.WorkStatus.NOT_APPLICABLE) {
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
