/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoList;


import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch.UseCaseFinishCurrentStepResponseHandler;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishCurrentStepRequest;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetDriverAndActiveBatchCurrentStep;
import it.flube.driver.useCaseLayer.photoStep.UseCaseAddAllPhotosToUploadList;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchUtilities;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/26/2017
 * Project : Driver
 */

public class PhotoController implements
        UseCaseGetDriverAndActiveBatchCurrentStep.Response,
        UseCaseAddAllPhotosToUploadList.Response {

    private final String TAG = "PhotoController";

    private String milestoneEvent;
    private GetDriverAndActiveBatchStepResponse response;

    public PhotoController() {
        Timber.tag(TAG).d("controller CREATED");
    }

    public void getDriverAndActiveBatchStep(GetDriverAndActiveBatchStepResponse response){
        Timber.tag(TAG).d("getDriverAndActiveBatchStep...");
        this.response = response;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetDriverAndActiveBatchCurrentStep(AndroidDevice.getInstance(), OrderStepInterface.TaskType.TAKE_PHOTOS, this));
    }

    public void stepFinished(ArrayList<PhotoRequest> photoList, String milestoneEvent){
        Timber.tag(TAG).d("stepFinished START...");
        this.milestoneEvent = milestoneEvent;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseAddAllPhotosToUploadList(AndroidDevice.getInstance(), photoList, this));
    }

    ////
    ////    UseCaseAddAllPhotosToUploadList response
    ////
    public void addAllPhotosToUploadListComplete(){
        Timber.tag(TAG).d("   ...addAllPhotosToUploadListComplete");
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseFinishCurrentStepRequest(AndroidDevice.getInstance(), milestoneEvent, new UseCaseFinishCurrentStepResponseHandler()));
    }

    public void close(){
        Timber.tag(TAG).d("close");
    }

    ///
    /// UseCaseGetDriverAndActiveBatchCurrentStep response interface
    ///
    public void useCaseGetDriverAndActiveBatchCurrentStepSuccess(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep){
        Timber.tag(TAG).d("useCaseGetDriverAndActiveBatchCurrentStepSuccess");
        response.gotDriverAndStep(driver, batchDetail, serviceOrder, (ServiceOrderPhotoStep) orderStep);
    }

    public void useCaseGetDriverAndActiveBatchCurrentStepFailureDriverOnly(Driver driver){
        Timber.tag(TAG).d("useCaseGetDriverAndActiveBatchCurrentStepFailureDriverOnly");
        response.gotDriverButNoStep(driver);
    }

    public void useCaseGetDriverAndActiveBatchCurrentStepFailureNoDriverNoStep(){
        Timber.tag(TAG).d("useCaseGetDriverAndActiveBatchCurrentStepFailureNoDriverNoStep");
        response.gotNoDriver();
    }

    public void useCaseGetDriverAndActiveBatchCurrentStepFailureStepMismatch(Driver driver, OrderStepInterface.TaskType foundTaskType){
        Timber.tag(TAG).d("useCaseGetDriverAndActiveBatchCurrentStepFailureStepMismatch");
        response.gotStepMismatch(driver, foundTaskType);
    }


    public interface GetDriverAndActiveBatchStepResponse {
        void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderPhotoStep orderStep);

        void gotNoDriver();

        void gotDriverButNoStep(Driver driver);

        void gotStepMismatch(Driver driver, OrderStepInterface.TaskType taskType);
    }

}
