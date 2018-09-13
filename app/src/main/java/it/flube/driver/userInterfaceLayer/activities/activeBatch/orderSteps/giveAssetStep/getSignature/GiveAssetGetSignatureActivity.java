/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep.getSignature;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.driver.Driver;
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
 * Created on 8/15/2018
 * Project : Driver
 */
public class GiveAssetGetSignatureActivity extends AppCompatActivity implements
        GiveAssetGetSignatureLayoutComponents.Response,
        GiveAssetGetSignatureController.GetDriverAndActiveBatchStepResponse,
        GiveAssetGetSignatureController.UpdateSignatureRequestWithImageResponse {

    private static final String TAG="ReceiveAssetGetSignatureActivity";

    private String activityGuid;

    private GiveAssetGetSignatureController controller;
    private GiveAssetGetSignatureLayoutComponents layoutComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_signature);


        controller = new GiveAssetGetSignatureController();
        layoutComponents = new GiveAssetGetSignatureLayoutComponents(this, this);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerMenu.getInstance().setActivity(this, R.string.get_signature_activity_title);
        controller.getDriverAndActiveBatchStep(this);
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {
        super.onPause();
        DrawerMenu.getInstance().close();
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
    }

    @Override
    public void onDestroy(){
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
        controller.close();
        layoutComponents.close();
        super.onDestroy();

    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

    ///
    /// ReceiveAssetGetSignatureLayoutComponents.Response interface
    ///

    public void gotSignature(SignatureRequest signatureRequest, Bitmap signatureBitmap){
        Timber.tag(TAG).d("gotSignature (%s)", activityGuid);
        layoutComponents.showSavingAnimation();
        controller.updateSignatureRequestWithImage(signatureRequest, signatureBitmap, this);
    }

    ///
    ///  GiveAssetGetSignatureController.GetDriverAndActiveBatchStepResponse interface
    ///
    public void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderGiveAssetStep orderStep){
        Timber.tag(TAG).d("gotDriverAndStep (%s)", activityGuid);
        layoutComponents.setValues(orderStep);
        layoutComponents.setVisible();
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
    ////
    ////
    public void signatureRequestWithImageUpdateComplete(){
        Timber.tag(TAG).d("signatureRequestWithImageUpdateComplete (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }
}

