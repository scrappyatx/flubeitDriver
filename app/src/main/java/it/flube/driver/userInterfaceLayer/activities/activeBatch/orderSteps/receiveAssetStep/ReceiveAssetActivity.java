/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.layoutComponents.ReceiveAssetLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailCompleteButtonComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailSwipeCompleteButtonComponent;
import it.flube.driver.userInterfaceLayer.activities.messages.CheckCallPermission;
import it.flube.driver.userInterfaceLayer.activities.messages.MakePhoneCall;
import it.flube.driver.userInterfaceLayer.activities.messages.SendTextMessage;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailDueByLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailTitleLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowCompletedServiceOrderAlertEvent;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 4/27/2018
 * Project : Driver
 */
public class ReceiveAssetActivity extends AppCompatActivity implements
        CheckCallPermission.Response,
        ReceiveAssetController.GetDriverAndActiveBatchStepResponse,
        ReceiveAssetLayoutComponents.Response {

    private static final String TAG = "ReceiveAssetActivity";

    private ActivityNavigator navigator;
    private ReceiveAssetController controller;
    private DrawerMenu drawer;

    private ReceiveAssetLayoutComponents layoutComponents;
    private CheckCallPermission checkCallPermission;

    private Boolean havePermissionToCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_receive_asset_step);

        layoutComponents = new ReceiveAssetLayoutComponents(this,getResources().getString(R.string.receive_asset_step_completed_step_button_caption), this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.receive_asset_step_activity_title);
        controller = new ReceiveAssetController();

        checkCallPermission = new CheckCallPermission();
        havePermissionToCall = false;
        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        Timber.tag(TAG).d("onRequestPermissionsResult, requestCode -> " + requestCode);
        checkCallPermission.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
        //see if we have permission to make a call
        checkCallPermission.checkCallPermissionRequest(this, this);

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause");
        super.onPause();

        drawer.close();
        controller.close();

    }

    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop");
        drawer.close();
        controller.close();
        super.onStop();
    }

    //// CheckCallPermission.Response interface
    public void callPermissionYes(){
        Timber.tag(TAG).d("callPermissionYes");
        havePermissionToCall=true;
        controller.getDriverAndActiveBatchStep(this);
    }

    public void callPermissionNo(){
        Timber.tag(TAG).d("callPermissionNo");
        havePermissionToCall=false;
        controller.getDriverAndActiveBatchStep(this);
    }

    ////
    //// ReceiveAssetLayoutComponents interface
    ////
    public void signatureRowClicked(SignatureRequest signatureRequest){
        Timber.tag(TAG).d("signatureRowClicked");
        navigator.gotoActivityReceiveAssetGetSignature(this);
    }

    public void itemsRowClickedWithMultipleItems(){
        Timber.tag(TAG).d("itemsRowClickedWithMultipleItems");
        navigator.gotoActivityReceiveAssetItemList(this);
    }

    public void itemsRowClickedWithOneItem(String assetGuid){
        Timber.tag(TAG).d("itemsRowClickedWithOneItem");
        navigator.gotoActivityReceiveAssetItem(this, assetGuid);
    }

    public void contactPersonCallClicked(String displayPhoneNumber){
        Timber.tag(TAG).d("contactPersonCallClicked, displayPhoneNumber -> " + displayPhoneNumber);
        layoutComponents.setCalling();
        new MakePhoneCall().dialNumberRequest(this, displayPhoneNumber);
    }

    public void contactPersonTextClicked(String displayPhoneNumber){
        Timber.tag(TAG).d("contactPersonTextClicked, displayPhoneNumber -> " + displayPhoneNumber);
        new SendTextMessage().sendTextRequest(this, displayPhoneNumber);
    }

    public void appInfoClicked(){
        Timber.tag(TAG).d("appInfoClicked");
        checkCallPermission.gotoSettings(this);
    }

    public void stepCompleteClicked(String milestoneWhenFinished){
        Timber.tag(TAG).d("stepCompleteClicked");
        layoutComponents.showWaitingAnimationAndBanner(this);
        controller.stepFinished(milestoneWhenFinished);
    }

    ///
    ///  ReceiveAssetController.GetDriverAndActiveBatchStepResponse interface
    ///
    public void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderReceiveAssetStep orderStep){
        Timber.tag(TAG).d("gotDriverAndStep");
        layoutComponents.setValues(this,orderStep);
        layoutComponents.setVisible(havePermissionToCall);
    }

    public void gotNoDriver(){
        Timber.tag(TAG).d("gotNoDriver");
        navigator.gotoActivityLogin(this);
    }

    public void gotDriverButNoStep(Driver driver){
        Timber.tag(TAG).d("gotDriverButNoStep");
        navigator.gotoActivityHome(this);
    }

    public void gotStepMismatch(Driver driver, OrderStepInterface.TaskType taskType){
        Timber.tag(TAG).d("gotStepMismatch, taskType -> " + taskType.toString());
        navigator.gotoActiveBatchStep(this);
    }


}
