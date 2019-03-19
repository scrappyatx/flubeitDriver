/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.transactionTotalDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.transactionIdDetail.TransactionIdDetailController;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.transactionIdDetail.TransactionIdDetailLayoutComponents;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import timber.log.Timber;

/**
 * Created on 3/18/2019
 * Project : Driver
 */
public class TransactionTotalDetailActivity extends AppCompatActivity
        implements
        TransactionTotalDetailLayoutComponents.Response,
        TransactionTotalDetailController.GetDriverAndAuthorizePaymentStepResponse,
        TransactionTotalDetailController.UpdateTransactionTotalResponse {

    private static final String TAG="TransactionIdDetailActivity";

    private TransactionTotalDetailController controller;
    private TransactionTotalDetailLayoutComponents layoutComponents;

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_total_detail);

        controller = new TransactionTotalDetailController();
        layoutComponents = new TransactionTotalDetailLayoutComponents(this, this);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume() {
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
        DrawerMenu.getInstance().setActivity(this, R.string.transaction_total_detail_activity_title);
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
        controller = null;
        layoutComponents = null;
        super.onDestroy();

    }

    ////
    //// TransactionIdDetailLayoutComponents response interface
    ////
    public void detectedCloudOcrButtonClicked(ServiceOrderAuthorizePaymentStep orderStep){
        Timber.tag(TAG).d("detectedCloudOcrButtonClicked (%s)", activityGuid);
        controller.updateTransactionTotalRequest(orderStep, this);
    }

    public void detectedDeviceOcrButtonClicked(ServiceOrderAuthorizePaymentStep orderStep){
        Timber.tag(TAG).d("detectedDeviceOcrButtonClicked (%s)", activityGuid);
        controller.updateTransactionTotalRequest(orderStep, this);
    }

    public void manualEntryButtonClicked(ServiceOrderAuthorizePaymentStep orderStep){
        Timber.tag(TAG).d("manualEntryButtonClicked (%s)", activityGuid);
        controller.updateTransactionTotalRequest(orderStep, this);
    }

    ////
    public void updateTransactionTotalComplete(){
        Timber.tag(TAG).d("updateTransactionIdComplete");
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

    /// TransactionTotalDetailController.GetDriverAndAuthorizePaymentStepResponse
    public void getDriverAndAuthorizePaymentStepSuccess(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderAuthorizePaymentStep orderStep){
        layoutComponents.setValues(this, orderStep);
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
