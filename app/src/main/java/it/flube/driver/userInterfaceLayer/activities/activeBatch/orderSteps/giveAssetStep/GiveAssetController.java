/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch.UseCaseFinishCurrentStepResponseHandler;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseCompareActivtyLaunchDataToCurrentStep;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishCurrentStepRequest;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 4/28/2018
 * Project : Driver
 */
public class GiveAssetController {
    private final String TAG = "GiveAssetController";

    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public GiveAssetController() {
        Timber.tag(TAG).d("controller CREATED");
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
    }

    public void compareActivityLaunchDataToCurrentStepData(String batchGuid, String serviceOrderGuid, String orderStepGuid, OrderStepInterface.TaskType taskType, UseCaseCompareActivtyLaunchDataToCurrentStep.Response response){
        Timber.tag(TAG).d("compareActivityLaunchDataToCurrentStepData...");
        useCaseExecutor.execute(new UseCaseCompareActivtyLaunchDataToCurrentStep(device,batchGuid, serviceOrderGuid, orderStepGuid, taskType, response));
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
