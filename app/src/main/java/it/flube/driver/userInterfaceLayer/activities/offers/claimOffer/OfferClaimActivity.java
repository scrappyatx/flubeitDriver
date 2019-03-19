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
import it.flube.libbatchdata.utilities.BuilderUtilities;
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

    private OfferClaimController controller;

    private BatchDetailTitleLayoutComponents batchTitle;
    private BatchDetailTabLayoutComponents batchTab;
    private OfferClaimLayoutComponents offerClaimButton;

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag(TAG).d("about to setContentView");
        setContentView(R.layout.activity_offer_claim_new);
        Timber.tag(TAG).d("just did setContentView");

        //title block & tab selector
        batchTitle = new BatchDetailTitleLayoutComponents(this);
        batchTab = new BatchDetailTabLayoutComponents(this, savedInstanceState, true);

        // claim offer button
        offerClaimButton = new OfferClaimLayoutComponents(this);

        //set all viewgroups GONE
        batchTitle.setGone();
        batchTab.setGone();
        offerClaimButton.setGone();

        controller = new OfferClaimController();

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onStart(){
        super.onStart();
        batchTab.onStart();
        Timber.tag(TAG).d("onStart (%s)", activityGuid);
    }

    @Override
    public void onResume() {
        super.onResume();

        DrawerMenu.getInstance().setActivity(this, R.string.offer_claim_activity_title);


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
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {
        DrawerMenu.getInstance().clearActivity();
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
        super.onPause();
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
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
                ActivityNavigator.getInstance().gotoActivityDemoOffersAndShowOfferClaimedSuccessAlert(this);
                break;
            case PERSONAL:
                ActivityNavigator.getInstance().gotoActivityPersonalOffersAndShowOfferClaimedSuccessAlert(this);
                break;
            case PUBLIC:
                ActivityNavigator.getInstance().gotoActivityPublicOffersAndShowOfferClaimedSuccessAlert(this);
                break;
            default:
                ActivityNavigator.getInstance().gotoActivityHome(this);
                Timber.tag(TAG).w("...should never get here, offerType -> " + offerType);
                break;
        }
    }

    public void useCaseClaimOfferRequestFailure(String batchGuid, OfferConstants.OfferType offerType){
        Timber.tag(TAG).d("user claim offer FAILURE, offerType -> " + offerType);
        switch (offerType){
            case DEMO:
                ActivityNavigator.getInstance().gotoActivityDemoOffersAndShowOfferClaimedFailureAlert(this);
                break;
            case PERSONAL:
                ActivityNavigator.getInstance().gotoActivityPersonalOffersAndShowOfferClaimedFailureAlert(this);
                break;
            case PUBLIC:
                ActivityNavigator.getInstance().gotoActivityPublicOffersAndShowOfferClaimedFailureAlert(this);
                break;
            default:
                ActivityNavigator.getInstance().gotoActivityHome(this);
                Timber.tag(TAG).w("...should never get here, offerType -> " + offerType);
                break;
        }
    }

    public void useCaseClaimOfferRequestTimeout(String batchGuid, OfferConstants.OfferType offerType){
        Timber.tag(TAG).d("user claim offer TIMEOUT, offerType -> " + offerType);
        switch (offerType){
            case DEMO:
                ActivityNavigator.getInstance().gotoActivityDemoOffersAndShowOfferClaimedTimeoutAlert(this);
                break;
            case PERSONAL:
                ActivityNavigator.getInstance().gotoActivityPersonalOffersAndShowOfferClaimedTimeoutAlert(this);
                break;
            case PUBLIC:
                ActivityNavigator.getInstance().gotoActivityPublicOffersAndShowOfferClaimedTimeoutAlert(this);
                break;
            default:
                ActivityNavigator.getInstance().gotoActivityHome(this);
                Timber.tag(TAG).w("...should never get here, offerType -> " + offerType);
                break;
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        batchTab.onStop();
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
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
        Timber.tag(TAG).d("onLowMemory (%s)", activityGuid);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        controller.close();
        batchTab.onDestroy();
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
    }


}
