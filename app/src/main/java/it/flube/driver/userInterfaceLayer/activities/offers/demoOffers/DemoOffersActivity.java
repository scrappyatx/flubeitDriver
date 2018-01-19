/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.demoOffers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.useCaseLayer.generateDemoBatch.UseCaseMakeDemoBatchRequest;
import it.flube.driver.userInterfaceLayer.layoutComponents.demoOffers.DemoOffersLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListLayoutComponent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferFailureAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferSuccessAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowDemoOfferCreatedAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.DemoOfferOfferListUpdatedEvent;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListAdapter;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class DemoOffersActivity extends AppCompatActivity implements
        OffersListAdapter.Response,
        UseCaseMakeDemoBatchRequest.Response,
        DemoOfferAlerts.DemoOfferCreatedAlertHidden,
        OfferClaimAlerts.ClaimOfferSuccessAlertHidden,
        OfferClaimAlerts.ClaimOfferFailureAlertHidden,
        OfferClaimAlerts.ClaimOfferTimeoutAlertHidden {

    private static final String TAG = "DemoOffersActivity";
    private static final Integer MAX_OFFERS = 20;
    private static final String CLAIM_OFFER_RESULT_KEY = "claimOfferResult";
    private static final String CLAIM_OFFER_SUCCESS_VALUE = "success";
    private static final String CLAIM_OFFER_FAILURE_VALUE = "failure";
    private static final String CLAIM_OFFER_TIMEOUT_VALUE = "timeout";

    private DemoOffersController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private DemoOffersLayoutComponents offersLayout;
    private OffersListLayoutComponent offersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_offers);

        offersLayout = new DemoOffersLayoutComponents(this);
        offersList = new OffersListLayoutComponent(this, getString(R.string.demo_offers_no_offers_available));

        Timber.tag(TAG).d("onCreate");
    }


    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.demo_offers_title);
        controller = new DemoOffersController();

        EventBus.getDefault().register(this);

        offersLayout.setVisible();

        offersList.onResume(this, this);
        offersList.setValues(AndroidDevice.getInstance().getOfferLists().getDemoOffers());
        offersList.setVisible();

        checkIfTooManyOffers();
        checkIfAlertNeedsToBeShown();
        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();
        offersList.onPause();

        Timber.tag(TAG).d(TAG, "onPause");
        super.onPause();

    }

    public void clickMakeDemoOfferButton(View v) {
        //make demo offer button clicked
        Timber.tag(TAG).d("clicked MAKE DEMO OFFER");

        offersLayout.offerMakeStarted();
        controller.doMakeDemoOffer();
    }


    private void checkIfTooManyOffers(){
        if (AndroidDevice.getInstance().getOfferLists().getDemoOffers().size() >= MAX_OFFERS) {
            //too many offers
            Timber.tag(TAG).d("...too many offers");
            offersLayout.setTooManyOffers();
        } else {
            //can still make offers
            Timber.tag(TAG).d("...can still make offers");
            offersLayout.setReadyToMake();
        }
    }

    private void checkIfAlertNeedsToBeShown(){
        /// get the batch data for the batchGuid that was used to launch this activity
        if (getIntent().hasExtra(CLAIM_OFFER_RESULT_KEY)){
            //get the result
            String resultKey = getIntent().getStringExtra(CLAIM_OFFER_RESULT_KEY);
            Timber.tag(TAG).d(CLAIM_OFFER_RESULT_KEY + " --> " + resultKey);
            switch (resultKey){
                case CLAIM_OFFER_SUCCESS_VALUE:
                    Timber.tag(TAG).d("claim offer SUCCESS!");
                    new OfferClaimAlerts().showSuccessAlert(this, this);
                    break;
                case CLAIM_OFFER_FAILURE_VALUE:
                    Timber.tag(TAG).d("claim offer FAILURE!");
                    new OfferClaimAlerts().showFailureAlert(this, this);
                    break;
                case CLAIM_OFFER_TIMEOUT_VALUE:
                    Timber.tag(TAG).d("claim offer TIMEOUT");
                    break;
                default:
                    Timber.tag(TAG).w("unknown resultKey, this should never happen");
                    break;
            }
            // now remove the key, only want to show an alert once
            getIntent().removeExtra(CLAIM_OFFER_RESULT_KEY);
        } else {
            Timber.tag(TAG).w("activity started with no claim_offer_result");
        }
    }

    ///
    ///   OffersListAdapter.Response interface
    ///
    public void offerSelected(Batch batch){
        Timber.tag(TAG).d("...batchSelected -> " + batch.getGuid());
        navigator.gotoActivityOfferClaim(this, batch.getGuid());
    }

    ///
    ///   UseCaseMakeDemoBatchRequest.Response interface
    ///
    public void makeDemoBatchComplete(){
        Timber.tag(TAG).d("...make demo batch COMPLETE!");
        new DemoOfferAlerts().showDemoOfferCreatedAlert(this, this);
    }


    // Events for updating offers list.  Events not removed because we always want most recent
    // offers list whenever this activitity is created, started, or resumed.

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DemoOfferOfferListUpdatedEvent event) {
        Timber.tag(TAG).d("received DemoOfferListUpdatedEvent");
        offersList.setValues(AndroidDevice.getInstance().getOfferLists().getDemoOffers());
        offersList.setVisible();
        checkIfTooManyOffers();
    }

    ///
    ///   Interfaces for Alerts that can be shown in this activity
    ///
    public void demoOfferCreatedAlertHidden(){
        offersList.setValues(AndroidDevice.getInstance().getOfferLists().getDemoOffers());
        checkIfTooManyOffers();
    }

    public void claimOfferSuccessAlertHidden() {
        Timber.tag(TAG).d("claim offer success alert hidden");
    }

    public void claimOfferFailureAlertHidden() {
        Timber.tag(TAG).d("claim offer failure alert hidden");
    }
    public void claimOfferTimeoutAlertHidden() {
        Timber.tag(TAG).d("claim offer timeout alert hidden");
    }



}
