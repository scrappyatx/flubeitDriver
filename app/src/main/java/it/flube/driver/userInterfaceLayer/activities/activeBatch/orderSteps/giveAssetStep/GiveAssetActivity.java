/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;


import it.flube.driver.R;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activities.messages.CheckCallPermission;
import it.flube.driver.userInterfaceLayer.activities.messages.MakePhoneCall;
import it.flube.driver.userInterfaceLayer.activities.messages.SendTextMessage;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderGiveAssetStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 4/28/2018
 * Project : Driver
 */
public class GiveAssetActivity extends AppCompatActivity implements
        CheckCallPermission.Response,
        GiveAssetController.GetDriverAndActiveBatchStepResponse,
        GiveAssetLayoutComponents.Response,
        GiveAssetController.StepFinishedResponse {

    private static final String TAG = "GiveAssetActivity";

    private String activityGuid;

    private GiveAssetController controller;
    private GiveAssetLayoutComponents layoutComponents;
    private CheckCallPermission checkCallPermission;

    private Boolean havePermissionToCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /// uses same layout as receive asset step
        setContentView(R.layout.activity_receive_asset_step);

        layoutComponents = new GiveAssetLayoutComponents(this,getResources().getString(R.string.give_asset_step_completed_step_button_caption), this);


        controller = new GiveAssetController();

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

        DrawerMenu.getInstance().setActivityDontMonitorActiveBatch(this, R.string.give_asset_step_activity_title);
        //see if we have permission to make a call
        checkCallPermission.checkCallPermissionRequest(this, this);

        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
        super.onPause();
        DrawerMenu.getInstance().clearActivity();
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }


    @Override
    public void onStop(){
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
        Timber.tag(TAG).d("signatureRowClicked (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityGiveAssetGetSignature(this);
    }

    public void itemsRowClickedWithMultipleItems(){
        Timber.tag(TAG).d("itemsRowClickedWithMultipleItems (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityGiveAssetItemList(this);
    }

    public void itemsRowClickedWithOneItem(String assetGuid){
        Timber.tag(TAG).d("itemsRowClickedWithOneItem (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityGiveAssetItem(this, assetGuid);
    }

    public void contactPersonCallClicked(String displayPhoneNumber){
        Timber.tag(TAG).d("contactPersonCallClicked (%s), displayPhoneNumber -> " + displayPhoneNumber, activityGuid);
        layoutComponents.setCalling();
        new MakePhoneCall().dialNumberRequest(this, displayPhoneNumber);
    }

    public void contactPersonTextClicked(String displayPhoneNumber){
        Timber.tag(TAG).d("contactPersonCallClicked (%s), displayPhoneNumber -> " + displayPhoneNumber, activityGuid);
        new SendTextMessage().sendTextRequest(this, displayPhoneNumber);
    }

    public void appInfoClicked(){
        Timber.tag(TAG).d("appInfoClicked (%s)", activityGuid);
        checkCallPermission.gotoSettings(this);
    }

    public void stepCompleteClicked(ServiceOrderGiveAssetStep orderStep){
        Timber.tag(TAG).d("stepCompleteClicked (%s)", activityGuid);
        layoutComponents.showWaitingAnimationAndBanner(this);
        if (orderStep.getRequireSignature()) {
            Timber.tag(TAG).d("   signature required");
            controller.stepFinishedRequest(orderStep.getMilestoneWhenFinished(), orderStep.getSignatureRequest(), this);
        } else {
            Timber.tag(TAG).d("   signature not required");
            controller.stepFinishedRequest(orderStep.getMilestoneWhenFinished(), this);
        }
    }

    ///
    ///  ReceiveAssetController.GetDriverAndActiveBatchStepResponse interface
    ///
    public void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderGiveAssetStep orderStep){
        Timber.tag(TAG).d("gotDriverAndStep (%s)", activityGuid);
        layoutComponents.setValues(this,orderStep);
        layoutComponents.setVisible(havePermissionToCall);
    }

    public void gotNoDriver(){
        Timber.tag(TAG).d("gotNoDriver (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityLogin(this);
    }

    public void gotDriverButNoStep(Driver driver){
        Timber.tag(TAG).d("gotDriverButNoStep (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }

    public void gotStepMismatch(Driver driver, OrderStepInterface.TaskType taskType){
        Timber.tag(TAG).d("gotStepMismatch (%s), taskType -> " + taskType.toString(), activityGuid);
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

