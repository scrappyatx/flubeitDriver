/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep.itemDetail;

import android.support.v7.app.AppCompatActivity;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.useCaseLayer.receiveAssetStep.UseCaseGetDriverAndMatchingAssetTransferAssetGuid;
import it.flube.driver.useCaseLayer.receiveAssetStep.UseCaseUpdateAssetTransfer;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.itemDetail.ReceiveAssetItemController;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchConstants.ASSET_TRANSFER_ASSET_GUID_KEY;

/**
 * Created on 8/15/2018
 * Project : Driver
 */
public class GiveAssetItemController implements
        UseCaseGetDriverAndMatchingAssetTransferAssetGuid.Response,
        UseCaseUpdateAssetTransfer.Response {

    private static final String TAG="GiveAssetItemController";

    private GetDriverAndAssetTransferResponse getLaunchResponse;
    private UpdateAssetTransferResponse updateAssetTransferResponse;

    public GiveAssetItemController(){
        Timber.tag(TAG).d("created");
    }

    public void getDriverAndAssetTransfer(AppCompatActivity activity, GetDriverAndAssetTransferResponse response){
        Timber.tag(TAG).d("getDriverAndAssetTransfer START...");
        getLaunchResponse = response;

        //// get launch info from activity, should be an asset guid
        //// then get current active batch step, and see if it has an asset transfer with matching guid in it
        if (activity.getIntent().hasExtra(ASSET_TRANSFER_ASSET_GUID_KEY)){
            Timber.tag(TAG).d("   ...activity intent has asset guid key ->  " + activity.getIntent().getStringExtra(ASSET_TRANSFER_ASSET_GUID_KEY));
            String assetGuid = activity.getIntent().getStringExtra(ASSET_TRANSFER_ASSET_GUID_KEY);
            AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetDriverAndMatchingAssetTransferAssetGuid(AndroidDevice.getInstance(),assetGuid, this));
        } else {
            Timber.tag(TAG).w("   ...activity intent has no extra data, should never happen");
            getLaunchResponse.gotNoDriver();
        }
        Timber.tag(TAG).d("...getDriverAndAssetTransfer COMPLETE");
    }

    public void updateAssetTransfer(String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer, UpdateAssetTransferResponse response){
        this.updateAssetTransferResponse = response;

        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseUpdateAssetTransfer(AndroidDevice.getInstance(), batchGuid, serviceOrderGuid, stepGuid, assetTransfer, this));
    }

    public void close(){
        Timber.tag(TAG).d("closed");
    }

    ////
    //// UseCaseGetDriverAndMatchingAssetTransferAssetGuid.Response interface
    ////
    public void useCaseGetDriverAndMatchingAssetTransferAssetGuidFailureNoDriver(){
        getLaunchResponse.gotNoDriver();
    }

    public void useCaseGetDriverAndMatchingAssetTransferAssetGuidSuccess(Driver driver, String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer){
        getLaunchResponse.gotDriverAndAssetTransfer(driver, batchGuid, serviceOrderGuid, stepGuid, assetTransfer);
    }

    public void useCaseGetDriverAndMatchingAssetTransferAssetGuidFailureDriverOnly(Driver driver){
        getLaunchResponse.gotDriverButNoAssetTransfer(driver);
    }

    public void useCaseGetDriverAndMatchingAssetTransferAssetGuidFailureNoMatchingAssetTransfer(Driver driver){
        getLaunchResponse.gotDriverButNoAssetTransfer(driver);
    }

    ///
    /// UseCaseUpdateAssetTransfer.Response interface
    ///
    public void useCaseUpdateAssetTransferComplete(){
        updateAssetTransferResponse.updateAssetTransferComplete();
    }


    public interface GetDriverAndAssetTransferResponse {
        void gotDriverAndAssetTransfer(Driver driver, String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer);

        void gotDriverButNoAssetTransfer(Driver driver);

        void gotNoDriver();
    }

    public interface UpdateAssetTransferResponse {
        void updateAssetTransferComplete();
    }

}

