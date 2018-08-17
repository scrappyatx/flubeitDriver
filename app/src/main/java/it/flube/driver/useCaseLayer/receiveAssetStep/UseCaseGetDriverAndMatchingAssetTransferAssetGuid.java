/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.receiveAssetStep;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 8/7/2018
 * Project : Driver
 */
public class UseCaseGetDriverAndMatchingAssetTransferAssetGuid implements
        Runnable,
        CloudActiveBatchInterface.GetActiveBatchCurrentStepResponse {

    private static final String TAG="UseCaseGetDriverAndMatchingAssetTransferAssetGuid";

    private final MobileDeviceInterface device;
    private final String assetGuid;
    private final Response response;
    private Driver driver;

    public UseCaseGetDriverAndMatchingAssetTransferAssetGuid(MobileDeviceInterface device, String assetGuid, Response response){
        this.device=device;
        this.assetGuid=assetGuid;
        this.response=response;
    }


    public void run(){
        if (device.getUser().isSignedIn()) {
            //// get active batch step
            this.driver = device.getUser().getDriver();
            Timber.tag(TAG).d("...device has signed in user, get current active batch step");
            device.getCloudActiveBatch().getActiveBatchCurrentStepRequest(driver, this);

        } else {
            // no user
            Timber.tag(TAG).d("...there is no signed in user");
            response.useCaseGetDriverAndMatchingAssetTransferAssetGuidFailureNoDriver();
        }
    }

    //// GetActiveBatchCurrentStepRequest response interface
    public void cloudGetActiveBatchCurrentStepSuccess(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep){
        Timber.tag(TAG).d("cloudGetActiveBatchCurrentStepSuccess");
        if ((orderStep.getTaskType() == OrderStepInterface.TaskType.RECEIVE_ASSET) || (orderStep.getTaskType() == OrderStepInterface.TaskType.GIVE_ASSET)) {
            //task type matches what we were expecting
            Timber.tag(TAG).d("...taskType -> " + orderStep.getTaskType().toString());
            // now see if we have a matching asset guid

            switch (orderStep.getTaskType()){
                case GIVE_ASSET:
                    ServiceOrderGiveAssetStep giveStep = (ServiceOrderGiveAssetStep) orderStep;
                    if (giveStep.getAssetList().get(assetGuid) != null){
                        Timber.tag(TAG).d("   ...got a matching asset guid");
                        response.useCaseGetDriverAndMatchingAssetTransferAssetGuidSuccess(driver, batchDetail.getBatchGuid(), serviceOrder.getGuid(), orderStep.getGuid(), giveStep.getAssetList().get(assetGuid));
                    } else {
                        Timber.tag(TAG).d("   ...don't have a matching asset guid");
                        response.useCaseGetDriverAndMatchingAssetTransferAssetGuidFailureNoMatchingAssetTransfer(driver);
                    }
                    break;
                case RECEIVE_ASSET:
                    ServiceOrderReceiveAssetStep receiveStep = (ServiceOrderReceiveAssetStep) orderStep;
                    if (receiveStep.getAssetList().get(assetGuid) != null){
                        Timber.tag(TAG).d("   ...got a matching asset guid");
                        response.useCaseGetDriverAndMatchingAssetTransferAssetGuidSuccess(driver, batchDetail.getBatchGuid(), serviceOrder.getGuid(), orderStep.getGuid(), receiveStep.getAssetList().get(assetGuid));
                    } else {
                        Timber.tag(TAG).d("   ...don't have a matching asset guid");
                        response.useCaseGetDriverAndMatchingAssetTransferAssetGuidFailureNoMatchingAssetTransfer(driver);
                    }
                    break;
            }
        } else {
            //task type doesn't match what we were expecting
            Timber.tag(TAG).d("...taskType (" + orderStep.getTaskType().toString() + "} isn't RECEIVE_ASSET or GIVE_ASSET");
            response.useCaseGetDriverAndMatchingAssetTransferAssetGuidFailureNoMatchingAssetTransfer(driver);
        }
    }

    public void cloudGetActiveBatchCurrentStepFailure(){
        Timber.tag(TAG).d("cloudGetActiveBatchCurrentStepFailure");
        response.useCaseGetDriverAndMatchingAssetTransferAssetGuidFailureDriverOnly(driver);
    }

    public interface Response {
        void useCaseGetDriverAndMatchingAssetTransferAssetGuidFailureNoDriver();

        void useCaseGetDriverAndMatchingAssetTransferAssetGuidSuccess(Driver driver, String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer);

        void useCaseGetDriverAndMatchingAssetTransferAssetGuidFailureDriverOnly(Driver driver);

        void useCaseGetDriverAndMatchingAssetTransferAssetGuidFailureNoMatchingAssetTransfer(Driver driver);

    }




}
