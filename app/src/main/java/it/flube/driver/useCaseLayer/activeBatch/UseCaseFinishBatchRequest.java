/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;

/**
 * Created on 6/22/2018
 * Project : Driver
 */
public class UseCaseFinishBatchRequest implements
        Runnable,
        CloudActiveBatchInterface.FinishActiveBatchResponse {

    private MobileDeviceInterface device;
    private Driver driver;
    private String batchGuid;
    private UseCaseFinishBatchRequest.Response response;

    public UseCaseFinishBatchRequest(MobileDeviceInterface device, String batchGuid, UseCaseFinishBatchRequest.Response response){
        this.device = device;
        this.driver = device.getUser().getDriver();
        this.batchGuid = batchGuid;
        this.response = response;
    }

    public void run(){
        device.getCloudActiveBatch().finishActiveBatchRequest(driver, ActiveBatchManageInterface.ActorType.MOBILE_USER, batchGuid, this);
    }

    public void cloudActiveBatchFinished(){
        response.finishBatchComplete();
    }

    public interface Response {
        void finishBatchComplete();
    }
}
