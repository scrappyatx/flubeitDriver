/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.manageBatch;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.DemoInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 8/13/2017
 * Project : Driver
 */

public class UseCaseStartBatchDemoRequest implements
        Runnable,
        CloudDatabaseInterface.SaveActiveBatchResponse {

    private final MobileDeviceInterface device;
    private final DemoInterface demoBatchBuilder;
    private final BatchCloudDB batchToStart;
    private final UseCaseStartBatchDemoRequest.Response response;

    private Batch demoBatch;

    public UseCaseStartBatchDemoRequest(MobileDeviceInterface device, DemoInterface demoBatchBuilder, BatchCloudDB batchToStart, UseCaseStartBatchDemoRequest.Response response){
        this.device = device;
        this.demoBatchBuilder = demoBatchBuilder;
        this.batchToStart = batchToStart;
        this.response = response;
    }

    public void run(){
        // build a sample batch based on current user
        //demoBatch = demoBatchBuilder.getB(device.getUser().getDriver(), batchToStart);

        // save the batch as the active batch
        //device.getCloudDatabase().saveActiveBatchRequest(device.getUser().getDriver(), demoBatch, this);

        //load this batch into the active batch
       // device.getUser().setActiveBatch(demoBatch);
    }

    public void cloudDatabaseActiveBatchSaveComplete(){
        response.startBatchDemoComplete(demoBatch);
    }

    public interface Response {
        void startBatchDemoComplete(Batch activeBatch);
    }

}
