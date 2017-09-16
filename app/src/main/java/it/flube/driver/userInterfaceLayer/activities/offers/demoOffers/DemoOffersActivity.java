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

import it.flube.driver.R;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers.DemoOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.offers.OffersListAdapter;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class DemoOffersActivity extends AppCompatActivity {
    private static final String TAG = "DemoOffersActivity";

    private DemoOffersController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private RecyclerView offersView;
    private OffersListAdapter offersAdapter;
    private TextView noOffersText;
    private TextView demoInstructions;
    private Button makeDemoOfferButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);
        offersView = (RecyclerView) findViewById(R.id.demo_offers_offersView);
        noOffersText = (TextView) findViewById(R.id.demo_offers_noOffers);
        demoInstructions = (TextView) findViewById(R.id.demo_offers_instructions);
        makeDemoOfferButton = (Button) findViewById(R.id.demo_offers_generate_button);

        Timber.tag(TAG).d("onCreate");
    }


    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.demo_offers_title);
        controller = new DemoOffersController();

        offersAdapter = new OffersListAdapter(controller);

        offersView.setLayoutManager(new LinearLayoutManager(this));
        offersView.setAdapter(offersAdapter);
        offersView.setVisibility(View.INVISIBLE);

        noOffersText.setVisibility(View.VISIBLE);
        makeDemoOfferButton.setVisibility(View.VISIBLE);

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

    public void clickMakeDemoOfferButton(View v) {
        //make demo offer button clicked
        Timber.tag(TAG).d("clicked MAKE DEMO OFFER");

        makeDemoOfferButton.setVisibility(View.INVISIBLE);
        //tell the controller to login using the user-supplied credentials
        controller.doMakeDemoOffer();
    }

    // Events for updating offers list.  Events not removed because we always want most recent
    // offers list whenever this activitity is created, started, or resumed.

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(DemoOffersAvailableResponseHandler.AvailableDemoOffersEvent event) {
        try {
            Timber.tag(TAG).d("received " + Integer.toString(event.getOfferCount()) + " offers");
            if (event.getOfferList().size() > 0) {
                Timber.tag(TAG).d("updating list!");
                offersAdapter.updateList(event.getOfferList());
                offersView.setVisibility(View.VISIBLE);
                noOffersText.setVisibility(View.INVISIBLE);
            } else {
                Timber.tag(TAG).d("making list invisible!");
                offersView.setVisibility(View.INVISIBLE);
                noOffersText.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
            offersView.setVisibility(View.INVISIBLE);
            noOffersText.setVisibility(View.VISIBLE);
        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(OfferSelectedResponseHandler.UseCaseOfferSelectedEvent event) {
        Timber.tag(TAG).d("*** Offer was selected event");

        navigator.gotoActivityOfferClaim(this);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(DemoOfferAlerts.ShowDemoOfferCreatedAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("demo offer created!");

        DemoOfferAlerts alert = new DemoOfferAlerts();
        alert.showDemoOfferCreatedAlert(this);

        makeDemoOfferButton.setVisibility(View.VISIBLE);
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


}
