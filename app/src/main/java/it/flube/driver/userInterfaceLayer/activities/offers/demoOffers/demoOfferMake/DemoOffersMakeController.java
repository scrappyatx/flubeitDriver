/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.demoOfferMake;

import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.generateDemoBatch.UseCaseMakeDemoBatchRequest;
import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOfferAlerts;
import it.flube.libbatchdata.demoBatchCreation.DemoBatchOilChange;
import it.flube.libbatchdata.demoBatchCreation.DemoBatchSimpleTwoStep;
import it.flube.libbatchdata.demoBatchCreation.DemoBatchTwoStepWithVehiclePhotos;
import timber.log.Timber;

/**
 * Created on 1/23/2018
 * Project : Driver
 */

public class DemoOffersMakeController implements
        UseCaseMakeDemoBatchRequest.Response {

    private final String TAG = "DemoOffersMakeController";

    private DemoBatchResponse response;

    public DemoOffersMakeController(DemoBatchResponse response) {
        Timber.tag(TAG).d("created");
        this.response = response;
    }

    public void doMakeTwoStepOffer(){
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseMakeDemoBatchRequest(AndroidDevice.getInstance(), new DemoBatchSimpleTwoStep(), this));
        Timber.tag(TAG).d("make demo two step offer REQUEST...");
    }

    public void doMakeAutoStepOffer(){
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseMakeDemoBatchRequest(AndroidDevice.getInstance(), new DemoBatchTwoStepWithVehiclePhotos(), this));
        Timber.tag(TAG).d("make demo auto batch offer REQUEST...");
    }

    public void doMakeOilChangeOffer(){
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseMakeDemoBatchRequest(AndroidDevice.getInstance(), new DemoBatchOilChange(), this));
        Timber.tag(TAG).d("make oil change offer REQUEST...");
    }

    public void makeDemoBatchSuccess(){
        Timber.tag(TAG).d("...make demo two step batch COMPLETE!");
        response.demoBatchCreated();
    }

    public void makeDemoBatchFailure(){
        Timber.tag(TAG).d("makeDemoBatchSuccess");
        response.demoBatchNotCreated();
    }

    public void close(){
        response = null;
    }

    public interface DemoBatchResponse {
        void demoBatchCreated();

        void demoBatchNotCreated();
    }

}
