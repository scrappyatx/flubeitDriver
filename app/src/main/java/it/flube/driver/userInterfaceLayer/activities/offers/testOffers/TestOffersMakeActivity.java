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
import it.flube.libbatchdata.builders.BuilderUtilities;
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

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_offers_make);
        components = new TestOfferMakeLayoutComponents(this);
        controller = new TestOffersMakeController();

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }


    public void onResume() {
        super.onResume();

        DrawerMenu.getInstance().setActivity(this,  R.string.test_offers_activity_title);


        components.onResume(this, this);
        components.setValues(TestOfferUtilities.getOptionsList());
        components.setVisible();

        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {
        DrawerMenu.getInstance().clearActivity();
        components.onPause();

        Timber.tag(TAG).d("onPause (%s)", activityGuid);
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
        components.close();

        controller = null;
        components = null;
        super.onDestroy();

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
