/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.layoutComponents.AuthorizePaymentLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailCompleteButtonComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailSwipeCompleteButtonComponent;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailDueByLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailTitleLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowCompletedServiceOrderAlertEvent;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.PaymentAuthorization;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 4/28/2018
 * Project : Driver
 */
public class AuthorizePaymentActivity extends AppCompatActivity implements
        AuthorizePaymentLayoutComponents.Response,
        AuthorizePaymentController.GetDriverAndActiveBatchStepResponse,
        AuthorizePaymentController.StepFinishedResponse {

    private static final String TAG = "AuthorizePaymentActivity";

    private String activityGuid;

    private AuthorizePaymentController controller;
    private AuthorizePaymentLayoutComponents layoutComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authorize_payment_step);

        controller = new AuthorizePaymentController();
        layoutComponents = new AuthorizePaymentLayoutComponents(this, this);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerMenu.getInstance().setActivityDontMonitorActiveBatch(this, R.string.authorize_payment_step_activity_title);
        controller.getDriverAndActiveBatchStep(this);

        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
        DrawerMenu.getInstance().clearActivity();
        super.onPause();

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
        controller = null;
        layoutComponents = null;
        super.onDestroy();

    }

    ///
    /// AuthorizePaymentLayoutComponents.Response interface
    ///
    public void paymentRowClicked(PaymentAuthorization paymentAuthorization){
        Timber.tag(TAG).d("paymentRowClicked (%s)", activityGuid);
        controller.updatePaymentVerification(paymentAuthorization);
    }

    public void receiptRowClicked(ReceiptRequest receiptRequest){
        Timber.tag(TAG).d("receiptRowClicked (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityReceiptDetail(this);
    }

    public void stepCompleteClicked(ServiceOrderAuthorizePaymentStep orderStep){
        Timber.tag(TAG).d("stepCompleteClicked (%s)", activityGuid);
        layoutComponents.showWaitingAnimationAndBanner(this);
        if (orderStep.getRequireReceipt()) {
            Timber.tag(TAG).d("...has receipt attached (%s)", activityGuid);
            controller.stepFinishedRequest(orderStep, orderStep.getMilestoneWhenFinished(), orderStep.getReceiptRequest(), this);
        } else {
            Timber.tag(TAG).d("...no receipt attached (%s)", activityGuid);
            controller.stepFinishedRequest(orderStep, orderStep.getMilestoneWhenFinished(), this);
        }
    }

    ///
    ///  ReceiveAssetController.GetDriverAndActiveBatchStepResponse interface
    ///
    public void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderAuthorizePaymentStep orderStep){
        Timber.tag(TAG).d("gotDriverAndStep (%s)", activityGuid);
        layoutComponents.setValues(this, orderStep);
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
    //// StepFinsished interface
    ////
    public void stepFinished(){
        Timber.tag(TAG).d("stepFinished");
        //go to the next step
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

}



