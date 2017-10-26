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
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers.DemoOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.dataLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferFailureAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferSuccessAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.batchAlerts.ShowDemoOfferCreatedAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.DemoOfferOfferListUpdatedEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.DemoOffersUpdatedEvent;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.offers.OffersListAdapter;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class DemoOffersActivity extends AppCompatActivity implements
        DemoOfferAlerts.DemoOfferCreatedAlertHidden,
        OfferClaimAlerts.ClaimOfferSuccessAlertHidden,
        OfferClaimAlerts.ClaimOfferFailureAlertHidden,
        OfferClaimAlerts.ClaimOfferTimeoutAlertHidden {

    private static final String TAG = "DemoOffersActivity";

    private DemoOffersController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private RecyclerView offersView;
    private OffersListAdapter offersAdapter;
    private TextView noOffersText;
    private TextView demoInstructions;
    private Button makeDemoOfferButton;

    private Boolean tooManyDemoOffers;
    private Boolean createDemoOfferAlertShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);
        offersView = (RecyclerView) findViewById(R.id.demo_offers_offersView);
        noOffersText = (TextView) findViewById(R.id.demo_offers_noOffers);
        demoInstructions = (TextView) findViewById(R.id.demo_offers_instructions);
        makeDemoOfferButton = (Button) findViewById(R.id.demo_offers_generate_button);

        tooManyDemoOffers = false;
        createDemoOfferAlertShowing = false;

        Timber.tag(TAG).d("onCreate");
    }


    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.demo_offers_title);
        controller = new DemoOffersController();

        offersAdapter = new OffersListAdapter(this, controller);

        offersView.setLayoutManager(new LinearLayoutManager(this));
        offersView.setAdapter(offersAdapter);
        offersView.setVisibility(View.INVISIBLE);

        noOffersText.setVisibility(View.VISIBLE);


        EventBus.getDefault().register(this);
        controller.listenForOffers();
        updateDemoOfferList();

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

    public void clickMakeDemoOfferButton(View v) {
        //make demo offer button clicked
        Timber.tag(TAG).d("clicked MAKE DEMO OFFER");

        makeDemoOfferButton.setVisibility(View.INVISIBLE);
        //tell the controller to login using the user-supplied credentials
        controller.doMakeDemoOffer();
    }

    private void updateDemoOfferButtonVisibility(){
        if ((tooManyDemoOffers) || (createDemoOfferAlertShowing)) {
            Timber.tag(TAG).d("demo offer button is INVISIBLE");
            makeDemoOfferButton.setVisibility(View.INVISIBLE);
        } else {
            Timber.tag(TAG).d("demo offer button is VISIBLE");
            makeDemoOfferButton.setVisibility(View.VISIBLE);
        }
    }

    private void updateDemoOfferList(){

        ArrayList<Batch> offerList = AndroidDevice.getInstance().getOfferLists().getDemoOffers();
        Integer offerCount = offerList.size();

        if (offerCount >= 20) {
            tooManyDemoOffers = true;
        } else {
            tooManyDemoOffers = false;
        }

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

        updateDemoOfferButtonVisibility();
    }

    // Events for updating offers list.  Events not removed because we always want most recent
    // offers list whenever this activitity is created, started, or resumed.

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DemoOfferOfferListUpdatedEvent event) {
        Timber.tag(TAG).d("received PublicOfferListUpdatedEvent");
        updateDemoOfferList();
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(OfferSelectedResponseHandler.UseCaseOfferSelectedEvent event) {
        Timber.tag(TAG).d("*** Offer was selected event");

        navigator.gotoActivityOfferClaim(this);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowDemoOfferCreatedAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("demo offer created!");

        DemoOfferAlerts alert = new DemoOfferAlerts();
        alert.showDemoOfferCreatedAlert(this, this);

        createDemoOfferAlertShowing = true;
        updateDemoOfferButtonVisibility();
    }

    public void demoOfferCreatedAlertHidden(){
        createDemoOfferAlertShowing = false;
        updateDemoOfferButtonVisibility();
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
    public void claimOfferTimeoutAlertHidden() {
        Timber.tag(TAG).d("claim offer timeout alert hidden");
    }



}
