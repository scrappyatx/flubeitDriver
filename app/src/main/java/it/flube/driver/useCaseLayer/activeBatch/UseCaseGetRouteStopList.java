/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.RouteStop;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 10/24/2017
 * Project : Driver
 */

public class UseCaseGetRouteStopList implements
        Runnable,
        CloudDatabaseInterface.GetRouteStopListResponse {

    private final String batchGuid;
    private final MobileDeviceInterface device;

    public UseCaseGetRouteStopList(MobileDeviceInterface device, String batchGuid){
        this.device = device;
        this.batchGuid = batchGuid;
        }

    public void run(){
        device.getCloudDatabase().getRouteStopListRequest(batchGuid, this);
    }

    public void cloudDatabaseGetRouteStopListSuccess(ArrayList<RouteStop> stopList){
        device.getActiveBatch().setRouteStopList(stopList);
    }

    public void cloudDatabaseGetRouteStopListFailure(){
        device.getActiveBatch().setRouteStopList();
    }
}
