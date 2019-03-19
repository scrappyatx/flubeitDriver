/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.receiptDetail;

import android.support.v7.app.AppCompatActivity;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetDriverAndActiveBatchCurrentStep;
import it.flube.driver.useCaseLayer.authorizePaymentStep.UseCaseReceiptOcr;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 8/31/2018
 * Project : Driver
 */
public class ReceiptDetailController implements
    UseCaseGetDriverAndActiveBatchCurrentStep.Response,
    UseCaseReceiptOcr.Response {
    public static final String TAG="ReceiptDetailController";

    private GetDriverAndAuthorizePaymentStepResponse response;
    private AnalyzePhotoResponse analyzeResponse;

    public ReceiptDetailController(){
        Timber.tag(TAG).d("created");

    }

    public void getDriverAndAuthorizePaymentStep(GetDriverAndAuthorizePaymentStepResponse response){
        Timber.tag(TAG).d("getDriverAndActiveBatchStep");
        this.response = response;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetDriverAndActiveBatchCurrentStep(AndroidDevice.getInstance(), OrderStepInterface.TaskType.AUTHORIZE_PAYMENT, this));

    }

    public void analyzePhoto(ReceiptRequest receiptRequest, AnalyzePhotoResponse analyzeResponse){
        Timber.tag(TAG).d("analyzePhoto");
        this.analyzeResponse = analyzeResponse;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseReceiptOcr(AndroidDevice.getInstance(), receiptRequest, this));
    }

    public void close(){
        Timber.tag(TAG).d("close");
        response = null;
        analyzeResponse = null;

    }

    /// UseCaseReceiptOcr
    public void useCaseReceiptOcrComplete(ReceiptRequest receiptRequest){
        Timber.tag(TAG).d("useCaseReceiptOcrComplete");
        analyzeResponse.analyzePhotoComplete(receiptRequest);
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

    public interface AnalyzePhotoResponse{
        void analyzePhotoComplete(ReceiptRequest receiptRequest);
    }
}
