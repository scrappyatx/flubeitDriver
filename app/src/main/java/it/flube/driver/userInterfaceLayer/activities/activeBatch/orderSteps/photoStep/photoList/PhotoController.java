/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoList;


import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch.UseCaseFinishCurrentStepResponseHandler;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishCurrentStepRequest;
import it.flube.driver.useCaseLayer.photoStep.UseCaseAddAllPhotosToUploadList;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchUtilities;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 10/26/2017
 * Project : Driver
 */

public class PhotoController implements
    UseCaseAddAllPhotosToUploadList.Response {
    private final String TAG = "PhotoController";

    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    private String milestoneEvent;

    public PhotoController() {
        Timber.tag(TAG).d("controller CREATED");
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
    }

    public void getActivityLaunchInfo(AppCompatActivity activity, ActiveBatchUtilities.GetStepResponse response){
        Timber.tag(TAG).d("getActivityLaunchInfo");
        new ActiveBatchUtilities().getOrderStepRequest(activity, device, response);
    }

    public void stepFinished(ArrayList<PhotoRequest> photoList, String milestoneEvent){
        Timber.tag(TAG).d("stepFinished START...");
        this.milestoneEvent = milestoneEvent;
        useCaseExecutor.execute(new UseCaseAddAllPhotosToUploadList(device, photoList, this));
    }

    ////
    ////    UseCaseAddAllPhotosToUploadList response
    ////
    public void addAllPhotosToUploadListComplete(){
        Timber.tag(TAG).d("   ...addAllPhotosToUploadListComplete");
        useCaseExecutor.execute(new UseCaseFinishCurrentStepRequest(device, milestoneEvent, new UseCaseFinishCurrentStepResponseHandler()));
    }

    public void close(){
        useCaseExecutor = null;
        device = null;
    }

}
