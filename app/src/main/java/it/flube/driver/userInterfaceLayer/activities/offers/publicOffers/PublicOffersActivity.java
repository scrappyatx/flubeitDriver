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
import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListLayoutComponent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PublicOfferListUpdatedEvent;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListAdapter;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class PublicOffersActivity extends AppCompatActivity implements
        OffersListAdapter.Response {

    private static final String TAG = "PublicOffersActivity";

    private PublicOffersController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private OffersListLayoutComponent offersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offers);
        offersList = new OffersListLayoutComponent(this, getString(R.string.public_offers_no_offers_available));

        Timber.tag(TAG).d("onCreate");
    }


    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.public_offers_activity_title);
        controller = new PublicOffersController();

        EventBus.getDefault().register(this);

        offersList.onResume(this, this);
        offersList.setValues(this, AndroidDevice.getInstance().getOfferLists().getPublicOffers());
        offersList.setVisible();

        controller.checkIfClaimAlertNeedsToBeShown(this);

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();
        offersList.onPause();

        Timber.tag(TAG).d( "onPause");
        super.onPause();
    }

    ///
    ///   OffersListAdapter.Response interface
    ///
    public void offerSelected(Batch batch){
        Timber.tag(TAG).d("...batchSelected -> " + batch.getGuid());
        navigator.gotoActivityOfferClaim(this, OfferConstants.OfferType.PUBLIC, batch.getGuid());
    }

    // Events for updating offers list.  Events not removed because we always want most recent
    // offers list whenever this activitity is created, started, or resumed.

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PublicOfferListUpdatedEvent event) {
        Timber.tag(TAG).d("received PublicOfferListUpdatedEvent");
        offersList.setValues(this, AndroidDevice.getInstance().getOfferLists().getPublicOffers());
        offersList.setVisible();
    }

}
