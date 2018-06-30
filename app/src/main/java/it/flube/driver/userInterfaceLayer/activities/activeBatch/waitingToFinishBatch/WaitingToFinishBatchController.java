/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.waitingToFinishBatch;

import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseDoAllTheThingsBeforeBatchCanBeFinished;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishBatchRequest;
import timber.log.Timber;

/**
 * Created on 6/19/2018
 * Project : Driver
 */
public class WaitingToFinishBatchController {

    public static final String TAG = "WaitingToFinishBatchController";

    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;


    public WaitingToFinishBatchController(){
        Timber.tag(TAG).d("controller CREATED");
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
    }

    public void getActivityLaunchData(AppCompatActivity activity, WaitingToFinishUtilities.Response response){
        new WaitingToFinishUtilities().getActivityLaunchInfo(activity, response);
    }

    public void doAllTheThingsBeforeBatchCanBeFinished(String batchGuid, UseCaseDoAllTheThingsBeforeBatchCanBeFinished.Response response){
        Timber.tag(TAG).d("waitForBatchToFinish, batchGuid -> " + batchGuid);
        useCaseExecutor.execute(new UseCaseDoAllTheThingsBeforeBatchCanBeFinished(device, batchGuid, response));
    }

    public void finishBatchRequest(String batchGuid, UseCaseFinishBatchRequest.Response response){
        useCaseExecutor.execute(new UseCaseFinishBatchRequest(device, batchGuid, response));
    }

    public void close(){
        useCaseExecutor = null;
        device = null;
    }

}
