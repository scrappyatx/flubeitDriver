/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.layoutComponents.ReceiveAssetLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.messages.CheckCallPermission;
import it.flube.driver.userInterfaceLayer.activities.messages.MakePhoneCall;
import it.flube.driver.userInterfaceLayer.activities.messages.SendTextMessage;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 4/27/2018
 * Project : Driver
 */
public class ReceiveAssetActivity extends AppCompatActivity implements
        CheckCallPermission.Response,
        ReceiveAssetController.GetDriverAndActiveBatchStepResponse,
        ReceiveAssetLayoutComponents.Response,
        ReceiveAssetController.StepFinishedResponse {

    private static final String TAG = "ReceiveAssetActivity";

    private ReceiveAssetController controller;
    private ReceiveAssetLayoutComponents layoutComponents;
    private CheckCallPermission checkCallPermission;

    private Boolean havePermissionToCall;

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_receive_asset_step);

        layoutComponents = new ReceiveAssetLayoutComponents(this,getResources().getString(R.string.receive_asset_step_completed_step_button_caption), this);


        controller = new ReceiveAssetController();

        checkCallPermission = new CheckCallPermission();
        havePermissionToCall = false;


        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        Timber.tag(TAG).d("onRequestPermissionsResult (%s), requestCode -> " + requestCode, activityGuid);
        checkCallPermission.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onResume() {
        super.onResume();
        //see if we have permission to make a call
        DrawerMenu.getInstance().setActivityDontMonitorActiveBatch(this, R.string.receive_asset_step_activity_title);
        checkCallPermission.checkCallPermissionRequest(this, this);

        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
        DrawerMenu.getInstance().clearActivity();

        super.onPause();
    }

    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        controller.close();
        layoutComponents.close();
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
    }

    //// CheckCallPermission.Response interface
    public void callPermissionYes(){
        Timber.tag(TAG).d("callPermissionYes (%s)", activityGuid);
        havePermissionToCall=true;
        controller.getDriverAndActiveBatchStep(this);
    }

    public void callPermissionNo(){
        Timber.tag(TAG).d("callPermissionNo (%s)", activityGuid);
        havePermissionToCall=false;
        controller.getDriverAndActiveBatchStep(this);
    }

    ////
    //// ReceiveAssetLayoutComponents interface
    ////
    public void signatureRowClicked(SignatureRequest signatureRequest){
        Timber.tag(TAG).d("signatureRowClicked");
        ActivityNavigator.getInstance().gotoActivityReceiveAssetGetSignature(this);
    }

    public void itemsRowClickedWithMultipleItems(){
        Timber.tag(TAG).d("itemsRowClickedWithMultipleItems");
        ActivityNavigator.getInstance().gotoActivityReceiveAssetItemList(this);
    }

    public void itemsRowClickedWithOneItem(String assetGuid){
        Timber.tag(TAG).d("itemsRowClickedWithOneItem");
        ActivityNavigator.getInstance().gotoActivityReceiveAssetItem(this, assetGuid);
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

    public void stepCompleteClicked(ServiceOrderReceiveAssetStep orderStep){
        Timber.tag(TAG).d("stepCompleteClicked");
        layoutComponents.showWaitingAnimationAndBanner(this);
        if (orderStep.getRequireSignature()){
            Timber.tag(TAG).d("   requires signature");
            controller.stepFinishedRequest(orderStep.getMilestoneWhenFinished(), orderStep.getSignatureRequest(), this);
        } else {
            Timber.tag(TAG).d("   doesn't requires signature");
            controller.stepFinishedRequest(orderStep.getMilestoneWhenFinished(), this);
        }
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
        ActivityNavigator.getInstance().gotoActivityLogin(this);
    }

    public void gotDriverButNoStep(Driver driver){
        Timber.tag(TAG).d("gotDriverButNoStep");
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }

    public void gotStepMismatch(Driver driver, OrderStepInterface.TaskType taskType){
        Timber.tag(TAG).d("gotStepMismatch, taskType -> " + taskType.toString());
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }
    ////
    //// StepFinsished interface
    ////
    public void stepFinished(){
        Timber.tag(TAG).d("stepFinished");
        //go to the next step
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }



}
