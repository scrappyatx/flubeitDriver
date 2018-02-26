/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.interfaces;

import java.util.Map;

import it.flube.libbatchdata.entities.Timestamp;

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

    TaskType getTaskType();

    String getTaskTypeIconText();

    enum WorkTiming {
        ON_TIME,
        LATE,
        VERY_LATE
    }
    void setWorkTiming(WorkTiming workTiming);
    WorkTiming getWorkTiming();

    void setWorkTimingIconTextMap(Map<String, String> workTimingIconTextMap);
    Map<String, String> getWorkTimingIconTextMap();

    enum WorkStatus {
        NORMAL,
        CUSTOMER_SUPPORT
    }

    void setWorkStatus(WorkStatus workStatus);
    WorkStatus getWorkStatus();

    void setWorkStatusIconTextMap(Map<String, String> workStatusIconTextMap);
    Map<String, String> getWorkStatusIconTextMap();

    enum WorkStage {
        NOT_STARTED,
        ACTIVE,
        COMPLETED
    }

    void setWorkStage(WorkStage workStage);
    WorkStage getWorkStage();

    void setWorkStageIconTextMap(Map<String, String> workStageIconTextMap);
    Map<String, String> getWorkStageIconTextMap();

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
