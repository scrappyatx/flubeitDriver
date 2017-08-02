/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

/**
 * Created on 7/29/2017
 * Project : Driver
 */

public interface ServiceOrderStepInterface {

    enum TaskType {
        NAVIGATION,
        TAKE_PHOTOS,
        WAIT_FOR_EXTERNAL_TRIGGER,
        WAIT_FOR_USER_TRIGGER
    }

    String getTitle();

    String getDescription();

    TaskType getTaskType();

    void startStep();

    void completeStep();

}
