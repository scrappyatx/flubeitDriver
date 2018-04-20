/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch;

import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.useCaseLayer.manageBatch.UseCaseStartBatchRequest;
import it.flube.driver.useCaseLayer.manageBatch.getBatchData.UseCaseGetBatchData;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 7/24/2017
 * Project : Driver
 */

public class BatchManageController {
    private final String TAG = "BatchManageController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;


    public BatchManageController() {
        Timber.tag(TAG).d("controller CREATED");
        useCaseExecutor = Executors.newSingleThreadExecutor();
        device = AndroidDevice.getInstance();
    }

    public void getBatchData(String batchGuid, UseCaseGetBatchData.Response response){
        Timber.tag(TAG).d("getBatchData...");
        useCaseExecutor.execute(new UseCaseGetBatchData(device, batchGuid, response));
    }

    public void confirmForfeit(AppCompatActivity activity, String batchGuid, ForfeitBatchConfirmation.Response response){
        Timber.tag(TAG).d("confirmForfeit...");
        new ForfeitBatchConfirmation().show(activity, device, batchGuid, response);
    }

    public void startBatch(String batchGuid, UseCaseStartBatchRequest.Response response) {
        Timber.tag(TAG).d("startDemoBatch...");
        useCaseExecutor.execute(new UseCaseStartBatchRequest(device, batchGuid, response));
    }


    public void close(){
        useCaseExecutor = null;
        device = null;
        Timber.tag(TAG).d("controller CLOSED");
    }

}
