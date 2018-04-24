/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.demoOffers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.layoutComponents.demoOffers.DemoOffersMakeLayoutComponents;
import timber.log.Timber;

/**
 * Created on 1/23/2018
 * Project : Driver
 */

public class DemoOffersMakeActivity extends AppCompatActivity
        implements
        DemoOffersMakeController.ActivityDone {

    private static final String TAG = "DemoOffersMakeActivity";

    private ActivityNavigator navigator;
    private DemoOffersMakeController controller;
    private DemoOffersMakeLayoutComponents demoOffersMakeLayoutComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_offer_make);
        Timber.tag(TAG).d("onCreate");

        demoOffersMakeLayoutComponents = new DemoOffersMakeLayoutComponents(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        navigator = new ActivityNavigator();
        controller = new DemoOffersMakeController(this, this);
        demoOffersMakeLayoutComponents.setReadyToMake();
        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d( "onPause");
        controller.close();
        super.onPause();

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

    public void allDone(){
        //we are done, go to demo offers activity
        Timber.tag(TAG).d("...we are done, go to activity demo offers");
        navigator.gotoActivityDemoOffers(this);
    }

}
