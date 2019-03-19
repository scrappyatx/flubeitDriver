/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep.itemDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import timber.log.Timber;

/**
 * Created on 8/15/2018
 * Project : Driver
 */
public class GiveAssetItemActivity extends AppCompatActivity implements
        GiveAssetItemController.GetDriverAndAssetTransferResponse,
        GiveAssetItemLayoutComponents.Response,
        GiveAssetItemController.UpdateAssetTransferResponse {

    private static final String TAG="GiveAssetItemActivity";

    private String activityGuid;

    private GiveAssetItemController controller;
    private GiveAssetItemLayoutComponents layoutComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.tag(TAG).d("onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_asset_item);


        controller = new GiveAssetItemController();
        layoutComponents = new GiveAssetItemLayoutComponents(this, this);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    protected void onResume(){
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
        super.onResume();
        DrawerMenu.getInstance().setActivity(this, R.string.asset_item_activity_title);
        controller.getDriverAndAssetTransfer(this, this);
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

    @Override
    protected void onPause(){
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
        DrawerMenu.getInstance().close();
        super.onPause();
    }

    @Override
    protected void onStop(){
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
        super.onStop();
    }


    @Override
    public void onDestroy(){
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
        controller.close();
        layoutComponents.close();
        super.onDestroy();

    }

    ///
    /// ReceiveAssetItemController.GetDriverAndAssetTransferResponse
    ///
    public void gotDriverAndAssetTransfer(Driver driver, String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer){
        Timber.tag(TAG).d("gotDriverAndAssetTransfer (%s)", activityGuid);
        layoutComponents.setValues(batchGuid, serviceOrderGuid, stepGuid, assetTransfer);
        layoutComponents.setVisible();
    }

    public void gotDriverButNoAssetTransfer(Driver driver){
        Timber.tag(TAG).d("gotDriverButNoAssetTransfer (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }

    public void gotNoDriver(){
        Timber.tag(TAG).d("gotNoDriver (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityLogin(this);
    }

    ///
    /// ReceiveAssetItemLayoutComponents interface
    ///
    public void finishedButtonClicked(String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer){
        Timber.tag(TAG).d("finishedButtonClicked (%s)", activityGuid);
        layoutComponents.showWaitingAnimation();
        controller.updateAssetTransfer(batchGuid, serviceOrderGuid, stepGuid, assetTransfer, this);
    }

    ///
    /// ReceiveAssetItemController.UpdateAssetTransferResponse
    ///
    public void updateAssetTransferComplete(){
        Timber.tag(TAG).d("updateAssetTransferComplete (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

}

