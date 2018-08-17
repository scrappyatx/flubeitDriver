/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.getSignature;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
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
public class ReceiveAssetGetSignatureActivity extends AppCompatActivity implements
        ReceiveAssetGetSignatureLayoutComponents.Response,
        ReceiveAssetGetSignatureController.GetDriverAndActiveBatchStepResponse,
        ReceiveAssetGetSignatureController.UpdateSignatureRequestWithImageResponse {

    private static final String TAG="ReceiveAssetGetSignatureActivity";

    private ActivityNavigator navigator;
    private ReceiveAssetGetSignatureController controller;
    private DrawerMenu drawer;

    private ReceiveAssetGetSignatureLayoutComponents layoutComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.tag(TAG).d("...down the rabbit hole");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_signature);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.get_signature_activity_title);
        controller = new ReceiveAssetGetSignatureController();

        layoutComponents = new ReceiveAssetGetSignatureLayoutComponents(this, this);
        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();

        controller.getDriverAndActiveBatchStep(this);
        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.tag(TAG).d("onPause");
    }

    @Override
    public void onStop() {
        drawer.close();
        controller.close();

        super.onStop();
        Timber.tag(TAG).d("onStop");
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed");
        navigator.gotoActiveBatchStep(this);
    }

    ///
    /// ReceiveAssetGetSignatureLayoutComponents.Response interface
    ///

    public void gotSignature(SignatureRequest signatureRequest, Bitmap signatureBitmap){
        Timber.tag(TAG).d("gotSignature");
        layoutComponents.showSavingAnimation();
        controller.updateSignatureRequestWithImage(signatureRequest, signatureBitmap, this);
    }

    ///
    ///  ReceiveAssetGetSignatureController.GetDriverAndActiveBatchStepResponse interface
    ///
    public void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderReceiveAssetStep orderStep){
        Timber.tag(TAG).d("gotDriverAndStep");
        layoutComponents.setValues(orderStep);
        layoutComponents.setVisible();
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

    ////
    ////
    ////
    public void signatureRequestWithImageUpdateComplete(){
        Timber.tag(TAG).d("signatureRequestWithImageUpdateComplete");
        navigator.gotoActiveBatchStep(this);
    }
}
