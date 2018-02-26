/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.generateDemoBatch;

import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.libbatchdata.interfaces.DemoBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.libraryTestClass;
import timber.log.Timber;

/**
 * Created on 2/14/2018
 * Project : Driver
 */

public class UseCaseMakeDemoAutoBatchRequest implements
        Runnable,
        CloudDatabaseInterface.AddDemoOfferToOfferListResponse,
        CloudDatabaseInterface.SaveBatchDataResponse {

    private final static String TAG = "UseCaseMakeDemoAutoBatchRequest";

    private final DemoBatchInterface demoMaker;
    private final Response response;

    private final Driver driver;
    private final CloudDatabaseInterface cloudDb;

    private BatchHolder demoBatchHolder;


    public UseCaseMakeDemoAutoBatchRequest(MobileDeviceInterface device, DemoBatchInterface demoMaker, Response response){
        this.demoMaker = demoMaker;
        this.response = response;

        driver = device.getUser().getDriver();
        cloudDb = device.getCloudDatabase();
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        Integer testValue = libraryTestClass.getInteger();
        Timber.tag(TAG).d("using library , value = " + testValue);

        //Step 1 - create a demo batch
        demoBatchHolder = demoMaker.createDemoVehiclePhotoBatch(driver.getClientId());

        Timber.tag(TAG).d("   ...batchGuid -> " + demoBatchHolder.getBatch().getGuid());
        //Step 2 - save the demo batch
        cloudDb.saveBatchDataRequest(demoBatchHolder, this);
    }

    public void cloudDatabaseBatchDataSaveComplete(){
        //Step 3 - add to the demo offer list
        cloudDb.addDemoOfferToOfferListRequest(demoBatchHolder.getBatch().getGuid(), this);
        Timber.tag(TAG).d("   ...batchDataSaved");
    }

    public void cloudDatabaseAddDemoOfferToOfferListComplete(){
        //Step 4 - we are done
        response.makeDemoAutoBatchComplete();
        Timber.tag(TAG).d("   ...added to demo offer list");
    }

    public interface Response {
        void makeDemoAutoBatchComplete();
    }
}
