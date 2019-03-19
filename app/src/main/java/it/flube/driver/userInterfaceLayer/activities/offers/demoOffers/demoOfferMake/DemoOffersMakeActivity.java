/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.demoOfferMake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOfferAlerts;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.layoutComponents.demoOffers.DemoOffersMakeLayoutComponents;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import timber.log.Timber;

/**
 * Created on 1/23/2018
 * Project : Driver
 */

public class DemoOffersMakeActivity extends AppCompatActivity
        implements
        DemoOffersMakeController.DemoBatchResponse,
        DemoOfferAlerts.DemoOfferCreatedAlertHidden {

    private static final String TAG = "DemoOffersMakeActivity";

    private DemoOffersMakeController controller;
    private DemoOffersMakeLayoutComponents demoOffersMakeLayoutComponents;

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_offer_make);
        Timber.tag(TAG).d("onCreate");
        demoOffersMakeLayoutComponents = new DemoOffersMakeLayoutComponents(this);
        controller = new DemoOffersMakeController(this);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }


    @Override
    public void onResume() {
        super.onResume();
        demoOffersMakeLayoutComponents.setReadyToMake();
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {
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
        demoOffersMakeLayoutComponents.close();
        super.onDestroy();

    }

    public void onAutoPhotoClicked(View v){
        Timber.tag(TAG).d("...clicked auto photo button");
        demoOffersMakeLayoutComponents.offerMakeStarted();
        controller.doMakeAutoStepOffer();
    }

    public void onTwoStepClicked(View v){
        Timber.tag(TAG).d("...clicked two step button");
        demoOffersMakeLayoutComponents.offerMakeStarted();
        controller.doMakeTwoStepOffer();
    }

    public void onOilChangeClicked(View v){
        Timber.tag(TAG).d("...clicked oil change button");
        demoOffersMakeLayoutComponents.offerMakeStarted();
        controller.doMakeOilChangeOffer();
    }

    // demo batch controller interface
    public void demoBatchCreated(){
        Timber.tag(TAG).d("demoBatchCreated");
        new DemoOfferAlerts().showDemoOfferCreatedAlert(this, this);
    }

    public void demoBatchNotCreated(){
        Timber.tag(TAG).d("demoBatchCreated");
        //TODO put in an alert to the user here
    }

    //// demo batch alert interface
    public void demoOfferCreatedAlertHidden(){
        //we are done, return to calling activity
        Timber.tag(TAG).d("...we are done, go to activity demo offers");
        ActivityNavigator.getInstance().gotoActivityDemoOffers(this);
    }

}
