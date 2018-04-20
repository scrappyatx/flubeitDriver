/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class UseCaseFinishCurrentStepRequest implements
        Runnable,
        CloudActiveBatchInterface.FinishActiveBatchStepResponse {


    private MobileDeviceInterface device;
    private Driver driver;
    private String milestoneEvent;
    private Response response;

    public UseCaseFinishCurrentStepRequest(MobileDeviceInterface device, String milestoneEvent, Response response){
        this.device = device;
        this.driver = device.getUser().getDriver();
        this.milestoneEvent = milestoneEvent;
        this.response = response;
    }

    public void run(){
        device.getCloudActiveBatch().finishActiveBatchStepRequest(driver, ActiveBatchManageInterface.ActorType.MOBILE_USER, this);
        //device.getRealtimeActiveBatchMessages().sendMilestoneEvent(milestoneEvent);
    }

    public void cloudActiveBatchFinishStepComplete() {
        response.finishCurrentStepComplete();
    }

    public interface Response {
        void finishCurrentStepComplete();
    }
}
