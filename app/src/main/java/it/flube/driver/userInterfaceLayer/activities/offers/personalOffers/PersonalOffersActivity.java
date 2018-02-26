/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.personalOffers;

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
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListAdapter;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferFailureAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferSuccessAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferTimeoutAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PersonalOfferListUpdatedEvent;
import timber.log.Timber;

/**
 * Created on 10/23/2017
 * Project : Driver
 */

public class PersonalOffersActivity extends AppCompatActivity implements
        OfferClaimAlerts.ClaimOfferSuccessAlertHidden,
        OfferClaimAlerts.ClaimOfferFailureAlertHidden,
        OfferClaimAlerts.ClaimOfferTimeoutAlertHidden {

    private static final String TAG = "PersonalOffersActivity";

    private PersonalOffersController controller;
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
        drawer = new DrawerMenu(this, navigator, R.string.personal_offers_activity_title);
        controller = new PersonalOffersController();

        offersAdapter = new OffersListAdapter(this, controller);

        offersView.setLayoutManager(new LinearLayoutManager(this));
        offersView.setAdapter(offersAdapter);
        offersView.setVisibility(View.INVISIBLE);

        noOffersText.setVisibility(View.VISIBLE);
        noOffersText.setText(R.string.personal_offers_no_offers_available);

        EventBus.getDefault().register(this);

        updatePersonalOfferList();

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

    private void updatePersonalOfferList() {
        ArrayList<Batch> offerList = AndroidDevice.getInstance().getOfferLists().getPersonalOffers();
        Integer offerCount = offerList.size();

        Timber.tag(TAG).d("updatePersonalOfferList : " + Integer.toString(offerCount) + " offers");
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
    public void onEvent(PersonalOfferListUpdatedEvent event) {
        Timber.tag(TAG).d("received PersonalOfferListUpdatedEvent");
        updatePersonalOfferList();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(OfferSelectedResponseHandler.UseCaseOfferSelectedEvent event) {
        Timber.tag(TAG).d("*** Offer was selected event");

        //navigator.gotoActivityOfferClaim(this, event.getBatchDetail().getBatchGuid());
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
