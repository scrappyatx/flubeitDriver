/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.getSignature;

import android.graphics.Bitmap;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetDriverAndActiveBatchCurrentStep;
import it.flube.driver.useCaseLayer.receiveAssetStep.UseCaseSaveSignatureToDeviceImageStorage;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.ReceiveAssetController;
import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 6/27/2018
 * Project : Driver
 */
public class ReceiveAssetGetSignatureController implements
        UseCaseGetDriverAndActiveBatchCurrentStep.Response,
        UseCaseSaveSignatureToDeviceImageStorage.Response {

    public static final String TAG="ReceiveAssetGetSignatureController";

    private GetDriverAndActiveBatchStepResponse response;
    private UpdateSignatureRequestWithImageResponse signatureResponse;


    public void getDriverAndActiveBatchStep(GetDriverAndActiveBatchStepResponse response){
        Timber.tag(TAG).d("getDriverAndActiveBatchStep...");
        this.response = response;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetDriverAndActiveBatchCurrentStep(AndroidDevice.getInstance(), OrderStepInterface.TaskType.RECEIVE_ASSET, this));
    }

    public void updateSignatureRequestWithImage(SignatureRequest signatureRequest, Bitmap bitmap, UpdateSignatureRequestWithImageResponse signatureResponse){
        Timber.tag(TAG).d("updateSignatureRequestWithImage");
        this.signatureResponse = signatureResponse;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseSaveSignatureToDeviceImageStorage(AndroidDevice.getInstance(), signatureRequest, bitmap,this));
    }

    public void close(){
        response = null;
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

    /// UseCaseSaveSignatureToDeviceImageStorage interface
    public void signatureSavedSuccess(String imageGuid){
        Timber.tag(TAG).d("signatureSavedSuccess, imageGuid -> " + imageGuid);
        signatureResponse.signatureRequestWithImageUpdateComplete();
    }

    public void signatureSavedFailure(String imageGuid){
        Timber.tag(TAG).d("signatureSavedSuccess, imageGuid -> " + imageGuid);
        signatureResponse.signatureRequestWithImageUpdateComplete();
    }

    //// interfaces for activity

    public interface GetDriverAndActiveBatchStepResponse {
        void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderReceiveAssetStep orderStep);

        void gotNoDriver();

        void gotDriverButNoStep(Driver driver);

        void gotStepMismatch(Driver driver, OrderStepInterface.TaskType taskType);
    }

    public interface UpdateSignatureRequestWithImageResponse {
        void signatureRequestWithImageUpdateComplete();
    }

}
