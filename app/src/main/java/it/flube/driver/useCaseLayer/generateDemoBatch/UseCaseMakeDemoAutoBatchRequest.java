/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.generateDemoBatch;

import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 2/14/2018
 * Project : Driver
 */

public class UseCaseMakeDemoAutoBatchRequest implements
        Runnable,
        CloudDemoOfferInterface.AddDemoOfferResponse {

    private final static String TAG = "UseCaseMakeDemoAutoBatchRequest";

    private final DemoBatchInterface demoMaker;
    private final Response response;

    private final Driver driver;
    private final CloudDemoOfferInterface cloudDb;

    public UseCaseMakeDemoAutoBatchRequest(MobileDeviceInterface device, DemoBatchInterface demoMaker, Response response){
        this.demoMaker = demoMaker;
        this.response = response;

        driver = device.getUser().getDriver();
        cloudDb = device.getCloudDemoOffer();
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        //Step 1 - create a demo batch
        BatchHolder demoBatchHolder = demoMaker.createDemoVehiclePhotoBatch(driver.getClientId());

        Timber.tag(TAG).d("   ...batchGuid -> " + demoBatchHolder.getBatch().getGuid());
        //Step 2 - save the demo batch
        cloudDb.addDemoOfferRequest(driver, demoBatchHolder, this);
    }


    public void cloudAddDemoOfferComplete(){
        //Step 4 - we are done
        response.makeDemoAutoBatchComplete();
        Timber.tag(TAG).d("   ...added to demo offer list");
    }

    public interface Response {
        void makeDemoAutoBatchComplete();
    }
}
