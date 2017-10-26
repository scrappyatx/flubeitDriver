/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.Timestamp;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderAbstractStep;

/**
 * Created on 9/25/2017
 * Project : Driver
 */

public interface OrderStepInterface {

    enum TaskType {
        NAVIGATION,
        TAKE_PHOTOS,
        WAIT_FOR_EXTERNAL_TRIGGER,
        WAIT_FOR_USER_TRIGGER,
        AUTHORIZE_PAYMENT,
        GIVE_ASSET,
        RECEIVE_ASSET,
        WAIT_FOR_SERVICE_ON_ASSET
    }

    enum WorkTiming {
        ON_TIME,
        LATE,
        VERY_LATE,
        NOT_APPLICABLE
    }

    enum WorkStatus {
        NORMAL,
        CUSTOMER_SUPPORT,
        NOT_APPLICABLE
    }

    enum WorkStage {
        NOT_STARTED,
        BEING_WORKED,
        COMPLETED,
        NOT_APPLICABLE
    }


    TaskType getTaskType();

    String getStepClassName();

    void setGuid(String guid);
    String getGuid();

    void setServiceOrderGuid(String guid);
    String getServiceOrderGuid();

    void setBatchGuid(String guid);
    String getBatchGuid();

    void setBatchDetailGuid(String guid);
    String getBatchDetailGuid();

    void setSequence(Integer sequence);
    Integer getSequence();

    void setWorkTiming(WorkTiming workTiming);
    WorkTiming getWorkTiming();

    void setWorkStatus(WorkStatus workStatus);
    WorkStatus getWorkStatus();

    void setWorkStage(WorkStage workStage);
    WorkStage getWorkStage();

    void setTitle(String title);
    String getTitle();

    void setDescription(String description);
    String getDescription();

    void setNote(String note);
    String getNote();

    void setMilestoneWhenFinished(String milestoneWhenFinished);
    String getMilestoneWhenFinished();

    void setStartTime(Timestamp startTime);
    Timestamp getStartTime();

    void setFinishTime(Timestamp finishTime);
    Timestamp getFinishTime();

}
