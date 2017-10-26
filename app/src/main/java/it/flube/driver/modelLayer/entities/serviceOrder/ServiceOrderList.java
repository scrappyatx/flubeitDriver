/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.serviceOrder;

import java.util.ArrayList;

/**
 * Created on 9/19/2017
 * Project : Driver
 */

public class ServiceOrderList {
    private String guid;
    private String batchGuid;
    private String batchDetailGuid;

    private Integer currentServiceOrder;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
    public String getBatchGuid() {
        return batchGuid;
    }

    public void setBatchGuid(String batchGuid) {
        this.batchGuid = batchGuid;
    }

    public String getBatchDetailGuid() {
        return batchDetailGuid;
    }

    public void setBatchDetailGuid(String batchDetailGuid) {
        this.batchDetailGuid = batchDetailGuid;
    }

    public Integer getCurrentServiceOrder() {
        return currentServiceOrder;
    }

    public void setCurrentServiceOrder(Integer currentServiceOrder) {
        this.currentServiceOrder = currentServiceOrder;
    }

}
