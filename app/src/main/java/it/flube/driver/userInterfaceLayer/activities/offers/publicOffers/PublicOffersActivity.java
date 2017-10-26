/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.publicOffers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.publicOffers.PublicOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferFailureAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferSuccessAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferTimeoutAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.PublicOfferListUpdatedEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.PublicOffersUpdatedEvent;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOfferAlerts;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.offers.OffersListAdapter;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class PublicOffersActivity extends AppCompatActivity implements
        OfferClaimAlerts.ClaimOfferSuccessAlertHidden,
        OfferClaimAlerts.ClaimOfferFailureAlertHidden,
        OfferClaimAlerts.ClaimOfferTimeoutAlertHidden {

    private static final String TAG = "PublicOffersActivity";

    private PublicOffersController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private RecyclerView offersView;
    private OffersListAdapter offersAdapter;
    private TextView noOffersText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offers);
        offersView = (RecyclerView) findViewById(R.id.offersView);
        noOffersText = (TextView) findViewById(R.id.noOffers);

        Timber.tag(TAG).d("onCreate");
    }


    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.offers_activity_title);
        controller = new PublicOffersController();

        offersAdapter = new OffersListAdapter(this, controller);

        offersView.setLayoutManager(new LinearLayoutManager(this));
        offersView.setAdapter(offersAdapter);
        offersView.setVisibility(View.INVISIBLE);

        noOffersText.setVisibility(View.VISIBLE);

        EventBus.getDefault().register(this);

        updatePublicOfferList();

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();
        offersAdapter.close();
        Timber.tag(TAG).d(TAG, "onPause");

        super.onPause();
    }

    private void updatePublicOfferList() {
        ArrayList<Batch> offerList = AndroidDevice.getInstance().getOfferLists().getPublicOffers();
        Integer offerCount = offerList.size();

        Timber.tag(TAG).d("updatePublicOfferList : " + Integer.toString(offerCount) + " offers");
        if (offerCount > 0) {
            Timber.tag(TAG).d("updating list!");
            offersAdapter.updateList(offerList);
            offersView.setVisibility(View.VISIBLE);
            noOffersText.setVisibility(View.INVISIBLE);
        } else {
            Timber.tag(TAG).d("making list invisible!");
            offersView.setVisibility(View.INVISIBLE);
            noOffersText.setVisibility(View.VISIBLE);
        }
    }

    // Events for updating offers list.  Events not removed because we always want most recent
    // offers list whenever this activitity is created, started, or resumed.

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PublicOfferListUpdatedEvent event) {
        Timber.tag(TAG).d("received PublicOfferListUpdatedEvent");
        updatePublicOfferList();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(OfferSelectedResponseHandler.UseCaseOfferSelectedEvent event) {
        Timber.tag(TAG).d("*** Offer was selected event");

        navigator.gotoActivityOfferClaim(this);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowClaimOfferSuccessAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("claim offer SUCCESS!");

        OfferClaimAlerts alert = new OfferClaimAlerts();
        alert.showSuccessAlert(this, this);
    }

    public void claimOfferSuccessAlertHidden() {
        Timber.tag(TAG).d("claim offer success alert hidden");
    }


    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowClaimOfferFailureAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("claim offer FAILURE!");

        OfferClaimAlerts alert = new OfferClaimAlerts();
        alert.showFailureAlert(this, this);
    }

    public void claimOfferFailureAlertHidden() {
        Timber.tag(TAG).d("claim offer failure alert hidden");
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowClaimOfferTimeoutAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("claim offer TIMEOUT!");

        OfferClaimAlerts alert = new OfferClaimAlerts();
        alert.showTimeoutAlert(this, this);
    }


    public void claimOfferTimeoutAlertHidden() {
        Timber.tag(TAG).d("claim offer timeout alert hidden");
    }
}
