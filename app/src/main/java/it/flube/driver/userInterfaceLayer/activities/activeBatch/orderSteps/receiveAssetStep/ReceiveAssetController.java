/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseCompareActivtyLaunchDataToCurrentStep;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishCurrentStepRequest;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetDriverAndActiveBatchCurrentStep;
import it.flube.driver.useCaseLayer.receiveAssetStep.UseCaseAddSignatureRequestToUploadList;
import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 4/27/2018
 * Project : Driver
 */
public class ReceiveAssetController implements
    UseCaseGetDriverAndActiveBatchCurrentStep.Response,
    UseCaseFinishCurrentStepRequest.Response,
    UseCaseAddSignatureRequestToUploadList.Response {

    private final String TAG = "ReceiveAssetController";

    private GetDriverAndActiveBatchStepResponse response;
    private StepFinishedResponse stepResponse;
    private String milestoneEvent;


    public ReceiveAssetController() {
        Timber.tag(TAG).d("controller CREATED");
    }

    public void getDriverAndActiveBatchStep(GetDriverAndActiveBatchStepResponse response){
        Timber.tag(TAG).d("getDriverAndActiveBatchStep...");
        this.response = response;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetDriverAndActiveBatchCurrentStep(AndroidDevice.getInstance(), OrderStepInterface.TaskType.RECEIVE_ASSET, this));

    }

    public void compareActivityLaunchDataToCurrentStepData(String batchGuid, String serviceOrderGuid, String orderStepGuid, OrderStepInterface.TaskType taskType, UseCaseCompareActivtyLaunchDataToCurrentStep.Response response){
        Timber.tag(TAG).d("compareActivityLaunchDataToCurrentStepData...");
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseCompareActivtyLaunchDataToCurrentStep(AndroidDevice.getInstance(),batchGuid, serviceOrderGuid, orderStepGuid, taskType, response));
    }

    public void stepFinishedRequest(String milestoneEvent, StepFinishedResponse stepResponse){
        Timber.tag(TAG).d("finishing STEP, no signature request");
        this.stepResponse = stepResponse;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseFinishCurrentStepRequest(AndroidDevice.getInstance(), milestoneEvent, this));
    }

    public void stepFinishedRequest(String milestoneEvent, SignatureRequest signatureRequest, StepFinishedResponse stepResponse){
        Timber.tag(TAG).d("finishing STEP, WITH signature request");
        this.stepResponse = stepResponse;
        this.milestoneEvent = milestoneEvent;
        //DO THIS ON UPLOAD EXECUTOR USES LOWER PRIORITY THREADS
        AndroidDevice.getInstance().getUseCaseEngine().getUploadExecutor().execute(new UseCaseAddSignatureRequestToUploadList(AndroidDevice.getInstance(), signatureRequest, this));
    }

    public void addSignatureRequestToUploadListComplete(){
        Timber.tag(TAG).d("addSignatureRequestToUploadListComplete, now finishing step...");
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseFinishCurrentStepRequest(AndroidDevice.getInstance(), milestoneEvent, this));
    }

    public void close(){
        Timber.tag(TAG).d("close");
    }

    ///
    /// UseCaseGetDriverAndActiveBatchCurrentStep response interface
    ///
    public void useCaseGetDriverAndActiveBatchCurrentStepSuccess(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep){
       Timber.tag(TAG).d("useCaseGetDriverAndActiveBatchCurrentStepSuccess");
        response.gotDriverAndStep(driver, batchDetail, serviceOrder, (ServiceOrderReceiveAssetStep) orderStep);
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

    /// UseCaseFinishStepRequest.Response
    public void finishCurrentStepComplete(){
        Timber.tag(TAG).d("finishCurrentStepComplete");
        stepResponse.stepFinished();
    }

    public interface GetDriverAndActiveBatchStepResponse {
        void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderReceiveAssetStep orderStep);

        void gotNoDriver();

        void gotDriverButNoStep(Driver driver);

        void gotStepMismatch(Driver driver, OrderStepInterface.TaskType taskType);
    }

    public interface StepFinishedResponse {
        void stepFinished();
    }

}
