/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class UseCaseFinishCurrentStepRequest implements
        Runnable,
        CloudDatabaseInterface.FinishActiveBatchStepResponse {


    private MobileDeviceInterface device;
    private String milestoneEvent;
    private Response response;

    public UseCaseFinishCurrentStepRequest(MobileDeviceInterface device, String milestoneEvent, Response response){
        this.device = device;
        this.milestoneEvent = milestoneEvent;
        this.response = response;
    }

    public void run(){
        device.getCloudDatabase().finishActiveBatchStepRequest(CloudDatabaseInterface.ActorType.MOBILE_USER, this);
        device.getRealtimeActiveBatchMessages().sendMilestoneEvent(milestoneEvent);
    }

    public void cloudDatabaseFinishActiveBatchStepComplete() {
        response.finishCurrentStepComplete();
    }

    public interface Response {
        void finishCurrentStepComplete();
    }
}
