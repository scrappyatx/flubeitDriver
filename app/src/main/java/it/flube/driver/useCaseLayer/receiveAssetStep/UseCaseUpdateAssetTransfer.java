/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.receiveAssetStep;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import timber.log.Timber;

/**
 * Created on 8/10/2018
 * Project : Driver
 */
public class UseCaseUpdateAssetTransfer implements
        Runnable,
        CloudActiveBatchInterface.UpdateAssetTransferResponse {

    private static final String TAG="UseCaseUpdateAssetTransfer";

    private final MobileDeviceInterface device;
    private final String batchGuid;
    private final String serviceOrderGuid;
    private final String stepGuid;
    private final AssetTransfer assetTransfer;
    private final Response response;
    private Driver driver;

    public UseCaseUpdateAssetTransfer(MobileDeviceInterface device, String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer, Response response ){
        this.response = response;
        this.device=device;
        this.batchGuid=batchGuid;
        this.serviceOrderGuid=serviceOrderGuid;
        this.stepGuid=stepGuid;
        this.assetTransfer=assetTransfer;

    }
    public void run(){
        if (device.getUser().isSignedIn()) {
            //// get active batch step
            this.driver = device.getUser().getDriver();
            Timber.tag(TAG).d("...device has signed in user, continue");
            device.getCloudActiveBatch().updateAssetTransferRequest(driver, batchGuid, serviceOrderGuid, stepGuid, assetTransfer, this);

        } else {
            // no user
            Timber.tag(TAG).d("...there is no signed in user");
            response.useCaseUpdateAssetTransferComplete();
        }
    }

    ///
    /// CloudActiveBatch.UpdateAssetTransferResponse
    ///
    public void cloudActiveBatchUpdateAssetTransferComplete(){
        Timber.tag(TAG).d("cloudActiveBatchUpdateAssetTransferComplete");
        response.useCaseUpdateAssetTransferComplete();
    }


    public interface Response {
        void useCaseUpdateAssetTransferComplete();
    }
}
