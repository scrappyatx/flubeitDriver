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
        AuthorizePaymentController.GetDriverAndActiveBatchStepResponse {

    private static final String TAG = "AuthorizePaymentActivity";

    private ActivityNavigator navigator;
    private AuthorizePaymentController controller;
    private DrawerMenu drawer;
    private AuthorizePaymentLayoutComponents layoutComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_authorize_payment_step);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.authorize_payment_step_activity_title);
        controller = new AuthorizePaymentController();
        layoutComponents = new AuthorizePaymentLayoutComponents(this, this);


    }

    @Override
    public void onResume() {
        super.onResume();
        controller.getDriverAndActiveBatchStep(this);

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause");
        super.onPause();

        drawer.close();
        controller.close();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop");

        drawer.close();
        controller.close();
        layoutComponents.close();

        super.onStop();
    }

    ///
    /// AuthorizePaymentLayoutComponents.Response interface
    ///
    public void receiptRowClicked(ReceiptRequest receiptRequest){
        Timber.tag(TAG).d("receiptRowClicked");
    }

    public void stepCompleteClicked(String milestoneWhenFinished){
        Timber.tag(TAG).d("stepCompleteClicked");
        layoutComponents.showWaitingAnimationAndBanner(this);
        controller.stepFinished(milestoneWhenFinished);
    }

    ///
    ///  ReceiveAssetController.GetDriverAndActiveBatchStepResponse interface
    ///
    public void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderAuthorizePaymentStep orderStep){
        Timber.tag(TAG).d("gotDriverAndStep");
        layoutComponents.setValues(this,orderStep);
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

}



