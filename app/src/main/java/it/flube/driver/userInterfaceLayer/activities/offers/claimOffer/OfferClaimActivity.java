/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.claimOffer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.modelLayer.entities.RouteStop;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.useCaseLayer.batchDetail.UseCaseGetBatchData;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimDemoOfferRequest;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.BatchDetailTabLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabDetails.TabDetailLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.BatchDetailTitleLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabLocations.TabLocationsLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.offerClaim.OfferClaimLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.demoOffers.ClaimDemoOfferFailureEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.demoOffers.ClaimDemoOfferSuccessEvent;
import timber.log.Timber;

/**
 * Created on 7/22/2017
 * Project : Driver
 */

public class OfferClaimActivity extends AppCompatActivity
    implements
        UseCaseGetBatchData.Response,
        UseCaseClaimDemoOfferRequest.Response {

    private static final String TAG = "OfferClaimActivity";

    private static final String BATCH_GUID_KEY = "batchGuid";

    private ActivityNavigator navigator;
    private OfferClaimController controller;
    private DrawerMenu drawer;

    private BatchDetailTitleLayoutComponents batchTitle;
    private BatchDetailTabLayoutComponents batchTab;
    private OfferClaimLayoutComponents offerClaimButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_claim_new);

        //title block & tab selector
        batchTitle = new BatchDetailTitleLayoutComponents(this);
        batchTab = new BatchDetailTabLayoutComponents(this, savedInstanceState, true);

        // claim offer button
        offerClaimButton = new OfferClaimLayoutComponents(this);

        //set all viewgroups GONE
        batchTitle.setGone();
        batchTab.setGone();
        offerClaimButton.setGone();

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onStart(){
        super.onStart();
        batchTab.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.offer_claim_activity_title);
        controller = new OfferClaimController();

        /// get the batch data for the batchGuid that was used to launch this activity
        if (getIntent().hasExtra(BATCH_GUID_KEY)){
            //get the batchGuid
            String batchGuid = getIntent().getStringExtra(BATCH_GUID_KEY);
            Timber.tag(TAG).d("batchGuid -> " + batchGuid);

            controller.getOfferData(batchGuid, this);
        } else {
            Timber.tag(TAG).w("no batch guid in the intent extras.  should never happen");
        }
        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        drawer.close();
        controller.close();
        Timber.tag(TAG).d(TAG, "onPause");
        super.onPause();
    }

    public void clickClaimButton(View v) {
        Timber.tag(TAG).d("clicked claim button");
        offerClaimButton.offerClaimStarted();
        controller.claimOfferRequest(offerClaimButton.getBatchDetail(), this);
    }

    ///
    ///  UseCaseGetBatchData
    ///
    public void getBatchDataSuccess(BatchDetail batchDetail, ArrayList<ServiceOrder> orderList, ArrayList<RouteStop> routeList){
        Timber.tag(TAG).d("got batch data");

        //set the values on display components
        batchTitle.setValues(this, batchDetail);
        batchTab.setValues(this, batchDetail, orderList, routeList);
        offerClaimButton.setValues(batchDetail);

        //make them visible();
        batchTitle.setVisible();
        batchTab.setVisible();
        offerClaimButton.setVisible();
    }

    public void getBatchDataFailure(){
        Timber.tag(TAG).d("could not get batch data");
    }

    ///
    ///  UseCaseClaimDemoOfferRequest
    ///
    public void useCaseClaimDemoOfferRequestSuccess(String offerGUID) {
        Timber.tag(TAG).d("user claim demo offer SUCCESS");
        navigator.gotoActivityDemoOffersAndShowOfferClaimedSuccessAlert(this);
    }

    // FAILURE - Someone else got offer
    public void useCaseClaimDemoOfferRequestFailure(String offerGUID) {
        Timber.tag(TAG).d("user claim demo offer FAILURE");
        navigator.gotoActivityDemoOffersAndShowOfferClaimedFailureAlert(this);
    }


    @Override
    public void onStop(){
        super.onStop();
        batchTab.onStop();
        Timber.tag(TAG).d("onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        batchTab.onSaveInstanceState(outState);
        Timber.tag(TAG).d("onSaveInstanceState");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        batchTab.onLowMemory();
        Timber.tag(TAG).d("onLowMemory");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        batchTab.onDestroy();
        Timber.tag(TAG).d("onDestroy");
    }


    //@Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    //public void onEvent(OfferSelectedResponseHandler.UseCaseOfferSelectedEvent event) {
    //    EventBus.getDefault().removeStickyEvent(event);
//
    //    Timber.tag(TAG).d("*** Offer was selected event");
    //    offerDetail = event.getBatchDetail();
//
    //    batchTitle.setValues(this, offerDetail);
     //   batchTitle.setVisible();

    //    batchTab.setValues(this, offerDetail);
     //   batchDetailTab.setVisible();
//
     //   offerClaimButton.setValuesAndShow();
    //}

}
