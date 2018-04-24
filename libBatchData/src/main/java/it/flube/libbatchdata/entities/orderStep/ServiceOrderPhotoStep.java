/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.orderStep;

import java.util.ArrayList;
import java.util.Map;


import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.Timestamp;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrderPhotoStep extends AbstractStep implements OrderStepInterface {
    private static final OrderStepInterface.TaskType TASK_TYPE = TaskType.TAKE_PHOTOS;
    private static final Integer DEFAULT_DURATION_MINUTES = 10;

    private ArrayList<PhotoRequest> photoRequestList;

    /// return the constants for this step type
    public TaskType getTaskType() {
        return TASK_TYPE;
    }

    public Integer getDefaultDurationMinutes() { return DEFAULT_DURATION_MINUTES; }

    /// implement the getters/setters for the unique data in this step type
    public ArrayList<PhotoRequest> getPhotoRequestList() {
        return photoRequestList;
    }

    public void setPhotoRequestList(ArrayList<PhotoRequest> photoRequestList) {
        this.photoRequestList = photoRequestList;
    }
}
