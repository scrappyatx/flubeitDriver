/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep;


import java.util.concurrent.ExecutorService;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch.UseCaseFinishCurrentStepResponseHandler;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishCurrentStepRequest;
import timber.log.Timber;

/**
 * Created on 10/26/2017
 * Project : Driver
 */

public class PhotoController {
    private final String TAG = "PhotoController";

    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public PhotoController() {
        Timber.tag(TAG).d("controller CREATED");
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
    }

    public void stepFinished(String milestoneEvent){
        Timber.tag(TAG).d("finishing STEP");
        useCaseExecutor.execute(new UseCaseFinishCurrentStepRequest(device, milestoneEvent, new UseCaseFinishCurrentStepResponseHandler()));
    }

    public void close(){
        useCaseExecutor = null;
        device = null;
    }

}
