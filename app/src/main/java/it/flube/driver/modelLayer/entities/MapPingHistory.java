/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

import java.util.ArrayList;

/**
 * Created on 9/17/2017
 * Project : Driver
 */

public class MapPingHistory {
    private String guid;
    private String batchGuid;
    private String batchDetailGuid;
    private String serviceOrderGuid;

    private ArrayList<String> mapPingList;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public ArrayList<String> getMapPingList() {
        return mapPingList;
    }

    public void setMapPingList(ArrayList<String> mapPingList) {
        this.mapPingList = mapPingList;
    }
}
