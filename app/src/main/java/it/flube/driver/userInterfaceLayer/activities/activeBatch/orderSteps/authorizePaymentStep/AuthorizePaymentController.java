/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch.UseCaseFinishCurrentStepResponseHandler;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseCompareActivtyLaunchDataToCurrentStep;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishCurrentStepRequest;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetDriverAndActiveBatchCurrentStep;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 4/28/2018
 * Project : Driver
 */
public class AuthorizePaymentController implements
        UseCaseGetDriverAndActiveBatchCurrentStep.Response {

    private final String TAG = "AuthorizePaymentController";

    private GetDriverAndActiveBatchStepResponse response;

    public AuthorizePaymentController() {
        Timber.tag(TAG).d("controller CREATED");

    }

    public void getDriverAndActiveBatchStep(GetDriverAndActiveBatchStepResponse response){
        Timber.tag(TAG).d("getDriverAndActiveBatchStep...");
        this.response = response;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetDriverAndActiveBatchCurrentStep(AndroidDevice.getInstance(), OrderStepInterface.TaskType.AUTHORIZE_PAYMENT, this));
    }

    public void stepFinished(String milestoneEvent){
        Timber.tag(TAG).d("finishing STEP");
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
        response.gotDriverAndStep(driver, batchDetail, serviceOrder, (ServiceOrderAuthorizePaymentStep) orderStep);
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
        void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderAuthorizePaymentStep orderStep);

        void gotNoDriver();

        void gotDriverButNoStep(Driver driver);

        void gotStepMismatch(Driver driver, OrderStepInterface.TaskType taskType);
    }

}
