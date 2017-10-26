/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.RouteStop;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;

/**
 * Created on 10/24/2017
 * Project : Driver
 */

public class UseCaseGetOrderStepList implements
        Runnable,
        CloudDatabaseInterface.GetOrderStepListResponse  {


    private final String batchGuid;
    private final String serviceOrderGuid;
    private final MobileDeviceInterface device;

    public UseCaseGetOrderStepList(MobileDeviceInterface device, String batchGuid, String serviceOrderGuid){
        this.device = device;
        this.batchGuid = batchGuid;
        this.serviceOrderGuid = serviceOrderGuid;
    }

    public void run(){
        device.getCloudDatabase().getOrderStepListRequest(batchGuid, serviceOrderGuid, this);
    }

    public void cloudDatabaseGetOrderStepListSuccess(ArrayList<OrderStepInterface> stepList){
        device.getActiveBatch().setOrderStepList(stepList);
    }

    public void cloudDatabaseGetOrderStepListFailure(){
        device.getActiveBatch().setOrderStepList();
    }

}
