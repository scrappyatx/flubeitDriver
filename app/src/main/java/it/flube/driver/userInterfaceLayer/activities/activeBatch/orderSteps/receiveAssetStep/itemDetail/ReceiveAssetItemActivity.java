/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.itemDetail;

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
 * Created on 8/6/2018
 * Project : Driver
 */
public class ReceiveAssetItemActivity extends AppCompatActivity implements
        ReceiveAssetItemController.GetDriverAndAssetTransferResponse,
        ReceiveAssetItemLayoutComponents.Response,
        ReceiveAssetItemController.UpdateAssetTransferResponse {

    private static final String TAG="ReceiveAssetItemActivity";

    private ReceiveAssetItemController controller;
    private ReceiveAssetItemLayoutComponents layoutComponents;

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.tag(TAG).d("onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_asset_item);



        controller = new ReceiveAssetItemController();
        layoutComponents = new ReceiveAssetItemLayoutComponents(this, this);

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
        DrawerMenu.getInstance().close();
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
        Timber.tag(TAG).d("gotDriverAndAssetTransfer");
        layoutComponents.setValues(batchGuid, serviceOrderGuid, stepGuid, assetTransfer);
        layoutComponents.setVisible();
    }

    public void gotDriverButNoAssetTransfer(Driver driver){
        Timber.tag(TAG).d("gotDriverButNoAssetTransfer");
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }

    public void gotNoDriver(){
        Timber.tag(TAG).d("gotNoDriver");
        ActivityNavigator.getInstance().gotoActivityLogin(this);
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
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

}
