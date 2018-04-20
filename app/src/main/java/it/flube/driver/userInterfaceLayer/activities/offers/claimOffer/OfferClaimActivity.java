/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.claimOffer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimOfferRequest;
import it.flube.driver.useCaseLayer.claimOffer.getOfferData.UseCaseGetOfferData;
import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.BatchDetailTabLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.BatchDetailTitleLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.offerClaim.OfferClaimLayoutComponents;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.BATCH_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.OFFER_TYPE_KEY;

/**
 * Created on 7/22/2017
 * Project : Driver
 */

public class OfferClaimActivity extends AppCompatActivity
    implements
        UseCaseGetOfferData.Response,
        UseCaseClaimOfferRequest.Response {

    private static final String TAG = "OfferClaimActivity";

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

            if (getIntent().hasExtra(OFFER_TYPE_KEY)) {
                String offerTypeString = getIntent().getStringExtra(OFFER_TYPE_KEY);
                OfferConstants.OfferType offerType = OfferConstants.OfferType.valueOf(offerTypeString);
                Timber.tag(TAG).d("offerType -> " + offerType);
                controller.getOfferData(batchGuid, offerType, this);

            } else {
                Timber.tag(TAG).w("no offerType in the intent extras.  should never happen");
            }

        } else {
            Timber.tag(TAG).w("no batch guid in the intent extras.  should never happen");
        }
        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        drawer.close();
        controller.close();
        Timber.tag(TAG).d( "onPause");
        super.onPause();
    }

    public void clickClaimButton(View v) {
        Timber.tag(TAG).d("clicked claim button");
        batchTab.setGone();
        offerClaimButton.offerClaimStarted();

        controller.claimOfferRequest(offerClaimButton.getBatchDetail().getBatchGuid(),
                offerClaimButton.getOfferType(),  offerClaimButton.getBatchDetail().getBatchType(), this);
    }

    ///
    ///  UseCaseGetOfferData
    ///
    public void getOfferDataSuccess(OfferConstants.OfferType offerType, BatchDetail batchDetail, ArrayList<ServiceOrder> orderList, ArrayList<RouteStop> routeList){
        Timber.tag(TAG).d("got batch data");

        //set the values on display components
        batchTitle.setValues(this, batchDetail);
        batchTab.setValues(this, batchDetail, orderList, routeList);
        offerClaimButton.setValues(offerType, batchDetail);

        //make them visible();
        batchTitle.setVisible();
        batchTab.setVisible();
        offerClaimButton.setVisible();
    }

    public void getOfferDataFailure(){
        Timber.tag(TAG).d("could not get batch data");
    }

    ///
    ///  UseCaseClaimOfferRequest RESPONSE
    ///
    public void useCaseClaimOfferRequestSuccess(String batchGuid, OfferConstants.OfferType offerType){
        Timber.tag(TAG).d("user claim offer SUCCESS, offerType -> " + offerType);
        switch (offerType){
            case DEMO:
                navigator.gotoActivityDemoOffersAndShowOfferClaimedSuccessAlert(this);
                break;
            case PERSONAL:
                navigator.gotoActivityPersonalOffersAndShowOfferClaimedSuccessAlert(this);
                break;
            case PUBLIC:
                navigator.gotoActivityPublicOffersAndShowOfferClaimedSuccessAlert(this);
                break;
            default:
                navigator.gotoActivityHome(this);
                Timber.tag(TAG).w("...should never get here, offerType -> " + offerType);
                break;
        }
    }

    public void useCaseClaimOfferRequestFailure(String batchGuid, OfferConstants.OfferType offerType){
        Timber.tag(TAG).d("user claim offer FAILURE, offerType -> " + offerType);
        switch (offerType){
            case DEMO:
                navigator.gotoActivityDemoOffersAndShowOfferClaimedFailureAlert(this);
                break;
            case PERSONAL:
                navigator.gotoActivityPersonalOffersAndShowOfferClaimedFailureAlert(this);
                break;
            case PUBLIC:
                navigator.gotoActivityPublicOffersAndShowOfferClaimedFailureAlert(this);
                break;
            default:
                navigator.gotoActivityHome(this);
                Timber.tag(TAG).w("...should never get here, offerType -> " + offerType);
                break;
        }
    }

    public void useCaseClaimOfferRequestTimeout(String batchGuid, OfferConstants.OfferType offerType){
        Timber.tag(TAG).d("user claim offer TIMEOUT, offerType -> " + offerType);
        switch (offerType){
            case DEMO:
                navigator.gotoActivityDemoOffersAndShowOfferClaimedTimeoutAlert(this);
                break;
            case PERSONAL:
                navigator.gotoActivityPersonalOffersAndShowOfferClaimedTimeoutAlert(this);
                break;
            case PUBLIC:
                navigator.gotoActivityPublicOffersAndShowOfferClaimedTimeoutAlert(this);
                break;
            default:
                navigator.gotoActivityHome(this);
                Timber.tag(TAG).w("...should never get here, offerType -> " + offerType);
                break;
        }
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
