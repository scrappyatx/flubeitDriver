/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.receiptDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 8/31/2018
 * Project : Driver
 */
public class ReceiptDetailActivity extends AppCompatActivity
    implements
    ReceiptDetailLayoutComponents.Response,
    ReceiptDetailController.GetDriverAndAuthorizePaymentStepResponse {

    private static final String TAG="ReceiptDetailActivity";

    private ActivityNavigator navigator;
    private ReceiptDetailController controller;
    private DrawerMenu drawer;

    private ReceiptDetailLayoutComponents layoutComponents;

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_receipt_detail);

        controller = new ReceiptDetailController();
        layoutComponents = new ReceiptDetailLayoutComponents(this, this);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume() {
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
        DrawerMenu.getInstance().setActivity(this, R.string.receipt_detail_activity_title);
        //get activity data
        controller.getDriverAndAuthorizePaymentStep(this);
        super.onResume();

    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
        DrawerMenu.getInstance().close();
        super.onPause();

    }

    @Override
    public void onBackPressed() {
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

    @Override
    public void onStop() {
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

    //// ReceiptDetailLayoutComponents interface
    public void takePhotoButtonClicked(){
        Timber.tag(TAG).d("takePhotoButtonClicked");
        ActivityNavigator.getInstance().gotoActivityReceiptTakePhoto(this);
    }

    /// ReceiptDetailController.GetDriverAndAuthorizePaymentStepResponse
    public void getDriverAndAuthorizePaymentStepSuccess(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderAuthorizePaymentStep orderStep){
        layoutComponents.setValues(this, orderStep.getReceiptRequest());
        layoutComponents.setVisible();
    }

    public void getDriverAndAuthorizePaymentStepFailureDriverOnly(Driver driver){
        layoutComponents.setInvisible();
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }

    public void getDriverAndAuthorizePaymentStepFailureNoDriverNoStep(){
        layoutComponents.setInvisible();
        ActivityNavigator.getInstance().gotoActivityLogin(this);
    }

    public void getDriverAndAuthorizePaymentStepFailureStepMismatch(Driver driver, OrderStepInterface.TaskType foundTaskType){
        Timber.tag(TAG).d("gotStepMismatch, taskType -> " + foundTaskType.toString());
        layoutComponents.setInvisible();
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

}
