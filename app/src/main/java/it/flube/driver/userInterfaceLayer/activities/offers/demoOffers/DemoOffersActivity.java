/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.demoOffers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.driver.userInterfaceLayer.activityNavigator.OfferClaimNavigator;
import it.flube.driver.userInterfaceLayer.layoutComponents.demoOffers.DemoOffersLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListLayoutComponent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.DemoOfferOfferListUpdatedEvent;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListAdapter;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants.MAX_OFFERS;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class DemoOffersActivity extends AppCompatActivity implements
        OffersListAdapter.Response,
        DemoOfferAlerts.DemoOfferCreatedAlertHidden {

    private static final String TAG = "DemoOffersActivity";

    private DemoOffersController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private DemoOffersLayoutComponents offersLayout;
    private OffersListLayoutComponent offersList;

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_offers);

        offersLayout = new DemoOffersLayoutComponents(this);
        offersList = new OffersListLayoutComponent(this, getString(R.string.demo_offers_no_offers_available), this);
        controller = new DemoOffersController();

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }


    @Override
    public void onResume() {
        super.onResume();

        DrawerMenu.getInstance().setActivity(this, R.string.demo_offers_title);


        EventBus.getDefault().register(this);

        offersLayout.setVisible();

        offersList.onResume();
        offersList.setValues(this, AndroidDevice.getInstance().getOfferLists().getDemoOffers());
        offersList.setVisible();

        checkIfTooManyOffers();
        controller.checkIfClaimAlertNeedsToBeShown(this);
        controller.checkIfMakeAlertNeedsToBeShown(this, this);

        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        DrawerMenu.getInstance().close();
        controller.close();
        offersList.onPause();

        Timber.tag(TAG).d( "onPause");
        super.onPause();

    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }

    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
        super.onStop();
    }

    @Override
    public void onDestroy(){
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);

        controller.close();
        offersList.close();
        super.onDestroy();

    }

    public void clickMakeDemoOfferButton(View v) {
        //make demo offer button clicked
        Timber.tag(TAG).d("clicked MAKE DEMO OFFER");
        ActivityNavigator.getInstance().gotoActivityDemoOffersMake(this);
        //offersLayout.offerMakeStarted();

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


    ///
    ///   OffersListAdapter.Response interface
    ///
    public void offerSelected(Batch batch){
        Timber.tag(TAG).d("...batchSelected -> " + batch.getGuid());
        ActivityNavigator.getInstance().gotoActivityOfferClaim(this, OfferConstants.OfferType.DEMO, batch.getGuid());
    }

    // Events for updating offers list.  Events not removed because we always want most recent
    // offers list whenever this activitity is created, started, or resumed.

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DemoOfferOfferListUpdatedEvent event) {
        Timber.tag(TAG).d("received DemoOfferListUpdatedEvent");
        offersList.setValues(this, AndroidDevice.getInstance().getOfferLists().getDemoOffers());
        offersList.setVisible();
        checkIfTooManyOffers();
    }

    /// make offer alert interfaces
    public void demoOfferCreatedAlertHidden(){
        offersList.setValues(this, AndroidDevice.getInstance().getOfferLists().getDemoOffers());
        checkIfTooManyOffers();
    }





}
