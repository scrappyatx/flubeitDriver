/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.PublicOffersAvailableResponseHandler;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class OffersActivity extends AppCompatActivity {
    private static final String TAG = "OffersActivity";

    private OffersController controller;
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
        controller = new OffersController();

        offersAdapter = new OffersListAdapter(controller);

        offersView.setLayoutManager(new LinearLayoutManager(this));
        offersView.setAdapter(offersAdapter);
        offersView.setVisibility(View.INVISIBLE);

        noOffersText.setVisibility(View.VISIBLE);

        EventBus.getDefault().register(this);

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();
        Timber.tag(TAG).d(TAG, "onPause");

        super.onPause();
    }

    // Events for updating offers list.  Events not removed because we always want most recent
    // offers list whenever this activitity is created, started, or resumed.

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(PublicOffersAvailableResponseHandler.AvailablePublicOffersEvent event) {
        try {
            Timber.tag(TAG).d("received " + Integer.toString(event.getOfferCount()) + " offers");
            if (event.getOfferList().size() > 0) {
                offersAdapter.updateList(event.getOfferList());
                offersView.setVisibility(View.VISIBLE);
                noOffersText.setVisibility(View.INVISIBLE);
            } else {
                offersView.setVisibility(View.INVISIBLE);
                noOffersText.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
            offersView.setVisibility(View.INVISIBLE);
            noOffersText.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(PublicOffersAvailableResponseHandler.NoPublicOffersEvent event) {
        offersView.setVisibility(View.INVISIBLE);
        noOffersText.setVisibility(View.VISIBLE);
    }



    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(OfferSelectedResponseHandler.UseCaseOfferSelectedEvent event) {
        Timber.tag(TAG).d("*** Offer was selected event");
        navigator.gotoActivityOfferClaim(this);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(OfferClaimAlerts.ShowClaimOfferSuccessAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("claim offer SUCCESS!");

        OfferClaimAlerts alert = new OfferClaimAlerts();
        alert.showSuccessAlert(this);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(OfferClaimAlerts.ShowClaimOfferFailureAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("claim offer FAILURE!");

        OfferClaimAlerts alert = new OfferClaimAlerts();
        alert.showFailureAlert(this);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(OfferClaimAlerts.ShowClaimOfferTimeoutAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("claim offer TIMEOUT!");

        OfferClaimAlerts alert = new OfferClaimAlerts();
        alert.showTimeoutAlert(this);
    }


}
