/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.generateDemoBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 9/6/2017
 * Project : Driver
 */

public class UseCaseMakeDemoBatchRequest implements
        Runnable,
        CloudDemoOfferInterface.AddDemoOfferResponse {

    private final static String TAG = "UseCaseMakeDemoBatchRequest";

    private final DemoBatchInterface demoMaker;
    private final Response response;

    private final Driver driver;
    private final CloudDemoOfferInterface cloudDb;
    private final TargetEnvironmentConstants.TargetEnvironment targetEnvironment;

    private BatchHolder demoBatchHolder;


    public UseCaseMakeDemoBatchRequest(MobileDeviceInterface device, DemoBatchInterface demoMaker, Response response){
        this.demoMaker = demoMaker;
        this.response = response;

        driver = device.getCloudAuth().getDriver();
        cloudDb = device.getCloudDemoOffer();
        targetEnvironment = device.getTargetEnvironment();
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        //Step 1 - create a demo batch
        BatchHolder demoBatchHolder = demoMaker.createDemoBatch(driver.getClientId(), targetEnvironment);

        Timber.tag(TAG).d("   batchGuid -> " + demoBatchHolder.getBatch().getGuid());
        //Step 2 - save the demo batch
        cloudDb.addDemoOfferRequest(driver, demoBatchHolder, this);
    }


    public void cloudAddDemoOfferComplete(){
        //Step 4 - we are done
        response.makeDemoBatchComplete();
        Timber.tag(TAG).d("   ...added to demo offer list");
    }

    public interface Response {
        void makeDemoBatchComplete();
    }
}
