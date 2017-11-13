/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.generateDemoBatch;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.batch.BatchHolder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.DemoBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 9/6/2017
 * Project : Driver
 */

public class UseCaseMakeDemoBatchRequest implements
    Runnable,
    CloudDatabaseInterface.AddDemoOfferToOfferListResponse,
    CloudDatabaseInterface.SaveBatchDataResponse {

    private final DemoBatchInterface demoMaker;
    private final Response response;

    private final Driver driver;
    private final CloudDatabaseInterface cloudDb;

    private BatchHolder demoBatchHolder;


    public UseCaseMakeDemoBatchRequest(MobileDeviceInterface device, DemoBatchInterface demoMaker, Response response){
        this.demoMaker = demoMaker;
        this.response = response;

        driver = device.getUser().getDriver();
        cloudDb = device.getCloudDatabase();
    }

    public void run(){
        //Step 1 - create a demo batch
        demoBatchHolder = demoMaker.createDemoBatch(driver);

        //Step 2 - save the demo batch
        cloudDb.saveBatchDataRequest(demoBatchHolder, this);
    }

    public void cloudDatabaseBatchDataSaveComplete(){
        //Step 3 - add to the demo offer list
        cloudDb.addDemoOfferToOfferListRequest(demoBatchHolder.getBatch().getGuid(), this);
    }

    public void cloudDatabaseAddDemoOfferToOfferListComplete(){
        //Step 4 - we are done
        response.makeDemoBatchComplete();
    }

    public interface Response {
        void makeDemoBatchComplete();
    }
}
