/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.transactionTotalDetail;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetDriverAndActiveBatchCurrentStep;
import it.flube.driver.useCaseLayer.authorizePaymentStep.UseCaseUpdateServiceProviderTransactionId;
import it.flube.driver.useCaseLayer.authorizePaymentStep.UseCaseUpdateServiceProviderTransactionTotal;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.transactionIdDetail.TransactionIdDetailController;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 3/18/2019
 * Project : Driver
 */
public class TransactionTotalDetailController implements
        UseCaseGetDriverAndActiveBatchCurrentStep.Response,
        UseCaseUpdateServiceProviderTransactionTotal.Response {

    public static final String TAG="TransactionTotalDetailController";

    private GetDriverAndAuthorizePaymentStepResponse response;
    private UpdateTransactionTotalResponse updateResponse;

    public TransactionTotalDetailController(){
        Timber.tag(TAG).d("created");
    }

    public void getDriverAndAuthorizePaymentStep(GetDriverAndAuthorizePaymentStepResponse response){
        Timber.tag(TAG).d("getDriverAndActiveBatchStep");
        this.response = response;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetDriverAndActiveBatchCurrentStep(AndroidDevice.getInstance(), OrderStepInterface.TaskType.AUTHORIZE_PAYMENT, this));

    }

    public void updateTransactionTotalRequest(ServiceOrderAuthorizePaymentStep orderStep, UpdateTransactionTotalResponse updateResponse){
        Timber.tag(TAG).d("updateTransactionId");
        this.updateResponse = updateResponse;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseUpdateServiceProviderTransactionTotal(AndroidDevice.getInstance(), orderStep, this));
    }

    public void close(){
        Timber.tag(TAG).d("close");
        response = null;
        updateResponse = null;
    }

    ///
    public void useCaseUpdateServiceProviderTransactionTotalComplete(){
        Timber.tag(TAG).d("useCaseUpdateServiceProviderTransactionTotalComplete");
        updateResponse.updateTransactionTotalComplete();
    }

    /// UseCaseGetDriverAndActiveBatchCurrentStep response
    public void useCaseGetDriverAndActiveBatchCurrentStepSuccess(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep){
        Timber.tag(TAG).d("close");
        response.getDriverAndAuthorizePaymentStepSuccess(driver, batchDetail, serviceOrder, (ServiceOrderAuthorizePaymentStep) orderStep);
    }

    public void useCaseGetDriverAndActiveBatchCurrentStepFailureDriverOnly(Driver driver){
        Timber.tag(TAG).d("close");
        response.getDriverAndAuthorizePaymentStepFailureDriverOnly(driver);
    }

    public void useCaseGetDriverAndActiveBatchCurrentStepFailureNoDriverNoStep(){
        Timber.tag(TAG).d("close");
        response.getDriverAndAuthorizePaymentStepFailureNoDriverNoStep();
    }

    public void useCaseGetDriverAndActiveBatchCurrentStepFailureStepMismatch(Driver driver, OrderStepInterface.TaskType foundTaskType){
        Timber.tag(TAG).d("close");
        response.getDriverAndAuthorizePaymentStepFailureStepMismatch(driver, foundTaskType);
    }

    public interface GetDriverAndAuthorizePaymentStepResponse {
        void getDriverAndAuthorizePaymentStepSuccess(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder,  ServiceOrderAuthorizePaymentStep orderStep);

        void getDriverAndAuthorizePaymentStepFailureDriverOnly(Driver driver);

        void getDriverAndAuthorizePaymentStepFailureNoDriverNoStep();

        void getDriverAndAuthorizePaymentStepFailureStepMismatch(Driver driver, OrderStepInterface.TaskType foundTaskType);
    }

    public interface UpdateTransactionTotalResponse {
        void updateTransactionTotalComplete();
    }
}

