/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.demoOffers;

import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.generateDemoBatch.UseCaseMakeDemoBatchRequest;
import it.flube.libbatchdata.demoBatchCreation.DemoBatchOilChange;
import it.flube.libbatchdata.demoBatchCreation.DemoBatchSimpleTwoStep;
import it.flube.libbatchdata.demoBatchCreation.DemoBatchTwoStepWithVehiclePhotos;
import timber.log.Timber;

/**
 * Created on 1/23/2018
 * Project : Driver
 */

public class DemoOffersMakeController implements
        UseCaseMakeDemoBatchRequest.Response,
        DemoOfferAlerts.DemoOfferCreatedAlertHidden {

    private final String TAG = "DemoOffersMakeController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;
    private AppCompatActivity activity;
    private ActivityDone activityDone;

    public DemoOffersMakeController(AppCompatActivity activity, ActivityDone activityDone) {
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
        this.activity = activity;
        this.activityDone = activityDone;

        Timber.tag(TAG).d("created");
    }

    public void doMakeTwoStepOffer(){
        useCaseExecutor.execute(new UseCaseMakeDemoBatchRequest(device, new DemoBatchSimpleTwoStep(), this));
        Timber.tag(TAG).d("make demo two step offer REQUEST...");
    }

    public void doMakeAutoStepOffer(){
        useCaseExecutor.execute(new UseCaseMakeDemoBatchRequest(device, new DemoBatchTwoStepWithVehiclePhotos(), this));
        Timber.tag(TAG).d("make demo auto batch offer REQUEST...");
    }

    public void doMakeOilChangeOffer(){
        useCaseExecutor.execute(new UseCaseMakeDemoBatchRequest(device, new DemoBatchOilChange(), this));
        Timber.tag(TAG).d("make oil change offer REQUEST...");
    }

    public void makeDemoBatchComplete(){
        Timber.tag(TAG).d("...make demo two step batch COMPLETE!");
        //EventBus.getDefault().postSticky(new ShowDemoOfferCreatedAlertEvent());
        new DemoOfferAlerts().showDemoOfferCreatedAlert(activity, this);
    }

    public void demoOfferCreatedAlertHidden(){
        //we are done, return to calling activity
        activityDone.allDone();
    }

    public void close(){
        activity = null;
        activityDone = null;
        device = null;
        useCaseExecutor = null;
    }

    public interface ActivityDone {
        void allDone();
    }

}
