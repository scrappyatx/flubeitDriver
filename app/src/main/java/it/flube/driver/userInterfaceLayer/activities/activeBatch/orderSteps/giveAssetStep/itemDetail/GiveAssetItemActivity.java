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

    private ActivityNavigator navigator;
    private DrawerMenu drawer;
    private GiveAssetItemController controller;
    private GiveAssetItemLayoutComponents layoutComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.tag(TAG).d("onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_asset_item);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.asset_item_activity_title);
        controller = new GiveAssetItemController();
        layoutComponents = new GiveAssetItemLayoutComponents(this, this);

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    protected void onResume(){
        Timber.tag(TAG).d("onResume");
        super.onResume();

        controller.getDriverAndAssetTransfer(this, this);
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed");
        navigator.gotoActiveBatchStep(this);
    }

    @Override
    protected void onPause(){
        Timber.tag(TAG).d("onPause");
        super.onPause();
    }

    @Override
    protected void onStop(){
        Timber.tag(TAG).d("onStop");

        drawer.close();
        layoutComponents.close();
        controller.close();

        super.onStop();
    }

    ///
    /// ReceiveAssetItemController.GetDriverAndAssetTransferResponse
    ///
    public void gotDriverAndAssetTransfer(Driver driver, String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer){
        Timber.tag(TAG).d("gotDriverAndAssetTransfer");
        layoutComponents.setValues(batchGuid, serviceOrderGuid, stepGuid, assetTransfer);
        layoutComponents.setVisible();
    }

    public void gotDriverButNoAssetTransfer(Driver driver){
        Timber.tag(TAG).d("gotDriverButNoAssetTransfer");
        navigator.gotoActivityHome(this);
    }

    public void gotNoDriver(){
        Timber.tag(TAG).d("gotNoDriver");
        navigator.gotoActivityLogin(this);
    }

    ///
    /// ReceiveAssetItemLayoutComponents interface
    ///
    public void finishedButtonClicked(String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer){
        Timber.tag(TAG).d("finishedButtonClicked");
        layoutComponents.showWaitingAnimation();
        controller.updateAssetTransfer(batchGuid, serviceOrderGuid, stepGuid, assetTransfer, this);
    }

    ///
    /// ReceiveAssetItemController.UpdateAssetTransferResponse
    ///
    public void updateAssetTransferComplete(){
        Timber.tag(TAG).d("updateAssetTransferComplete");
        navigator.gotoActiveBatchStep(this);
    }

}

