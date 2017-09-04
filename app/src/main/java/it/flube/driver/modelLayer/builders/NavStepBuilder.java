/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.Destination;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrderAbstractStep;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrderNavigationStep;

/**
 * Created on 8/24/2017
 * Project : Driver
 */

public class NavStepBuilder {
    private ServiceOrderNavigationStep navStep;

    private NavStepBuilder(@NonNull Builder builder) {
        this.navStep = builder.navStep;
    }

    private ServiceOrderNavigationStep getNavStep(){
        return navStep;
    }

    public static class Builder {
        private ServiceOrderNavigationStep navStep;

        public Builder(@NonNull String title, @NonNull String description){
            navStep = new ServiceOrderNavigationStep();
            navStep.setGUID(UUID.randomUUID().toString());
            navStep.setTitle(title);
            navStep.setDescription(description);
            navStep.setAtDestination(false);
            navStep.setWorkStage(ServiceOrderAbstractStep.WorkStage.NOT_STARTED);
            navStep.setWorkTiming(ServiceOrderAbstractStep.WorkTiming.NOT_APPLICABLE);
            navStep.setWorkStatus(ServiceOrderAbstractStep.WorkStatus.NOT_APPLICABLE);
            navStep.setCloseEnoughInFeet(300); //default value is 300 feet
        }

        public Builder note(@NonNull String note) {
            this.navStep.setNote(note);
            return this;
        }

        public Builder startScheduledTime(@NonNull Date startScheduledTime) {
            this.navStep.setStartTimestamp(new TimestampBuilder.Builder(startScheduledTime).build());
            return this;
        }

        public Builder finishScheduledTime(@NonNull Date finishScheduledTime) {
            this.navStep.setFinishTimestamp(new TimestampBuilder.Builder(finishScheduledTime).build());
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

            if (navStep.getStartTimestamp() == null) {
                throw new IllegalStateException("startTimestamp is null");
            }

            if (navStep.getFinishTimestamp() == null) {
                throw new IllegalStateException("finishTimestamp is null");
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
            ServiceOrderNavigationStep navStep = new NavStepBuilder(this).getNavStep();
            validateNavigationStep(navStep);
            return navStep;
        }

    }
}
