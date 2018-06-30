/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.testOffers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.earnings.productionEarnings.EarningsController;
import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOfferAlerts;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 6/26/2018
 * Project : Driver
 */
public class TestOffersMakeActivity extends AppCompatActivity implements
        TestOfferOptionsListAdapter.Response,
        TestOffersMakeController.CreateTestBatchResponse,
        TestOfferAlerts.TestOfferCreatedAlertHidden {

    private static final String TAG = "EarningsActivity";

    private ActivityNavigator navigator;
    private TestOffersMakeController controller;
    private DrawerMenu drawer;

    private TestOfferMakeLayoutComponents components;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_offers_make);
        components = new TestOfferMakeLayoutComponents(this);

        Timber.tag(TAG).d("onCreate");
    }


    public void onResume() {
        super.onResume();

        //EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator,  R.string.test_offers_activity_title);
        controller = new TestOffersMakeController();

        components.onResume(this, this);
        components.setValues(TestOfferUtilities.getOptionsList());
        components.setVisible();

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {

        //EventBus.getDefault().unregister(this);

        drawer.close();
        components.onPause();

        Timber.tag(TAG).d( "onPause");
        super.onPause();
    }

    ////
    //// TestOfferOptionsListAdapter.Response
    ////
    public void optionSelected(TestOfferOption option) {
        Timber.tag(TAG).d("optionSelected");
        Timber.tag(TAG).d("   option name  -> " + option.getOptionName());
        Timber.tag(TAG).d("   option class -> " + option.getClassName());

        components.showWaitingAnimation();
        controller.createTestBatchRequest(option, this);
    }

    //// CreateTestBatchResponse interface
    public void testBatchCreateSuccess(){
        Timber.tag(TAG).d("testBatchCreateSuccess");
        new TestOfferAlerts().showTestOfferCreatedSuccessAlert(this, this);
    }

    public void testBatchCreateFailure(){
        Timber.tag(TAG).d("testBatchCreateFailure");
        new TestOfferAlerts().showTestOfferCreatedFailureAlert(this, this);
    }

    //// TestOfferCreatedAlertHidden interface
    public void testOfferCreatedAlertHidden(){
        Timber.tag(TAG).d("testOfferCreatedAlertHidden");
        components.setVisible();
    }
}
