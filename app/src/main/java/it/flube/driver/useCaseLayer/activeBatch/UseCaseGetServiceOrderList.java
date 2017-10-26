/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 10/24/2017
 * Project : Driver
 */

public class UseCaseGetServiceOrderList implements
        Runnable,
        CloudDatabaseInterface.GetServiceOrderListResponse {

    private final String batchGuid;
    private final MobileDeviceInterface device;

    public UseCaseGetServiceOrderList(MobileDeviceInterface device, String batchGuid){
        this.device = device;
        this.batchGuid = batchGuid;
    }
    public void run(){
        device.getCloudDatabase().getServiceOrderListRequest(batchGuid, this);
    }

    public void cloudDatabaseGetServiceOrderListSuccess(ArrayList<ServiceOrder> orderList){
        device.getActiveBatch().setServiceOrderList(orderList);
    }

    public void cloudDatabaseGetServiceOrderListFailure(){
        device.getActiveBatch().setServiceOrderList();
    }

}
