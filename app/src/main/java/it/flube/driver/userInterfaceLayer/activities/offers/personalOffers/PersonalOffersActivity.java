/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.personalOffers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListLayoutComponent;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListAdapter;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PersonalOfferListUpdatedEvent;
import timber.log.Timber;

/**
 * Created on 10/23/2017
 * Project : Driver
 */

public class PersonalOffersActivity extends AppCompatActivity implements
        OffersListAdapter.Response {

    private static final String TAG = "PersonalOffersActivity";

    private PersonalOffersController controller;
    private OffersListLayoutComponent offersList;

    private String activityGuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offers);
        offersList = new OffersListLayoutComponent(this, getString(R.string.personal_offers_no_offers_available), this);
        controller = new PersonalOffersController();

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }


    @Override
    public void onResume() {
        super.onResume();

        DrawerMenu.getInstance().setActivity(this, R.string.personal_offers_activity_title);
        EventBus.getDefault().register(this);

        offersList.onResume();
        offersList.setValues(this, AndroidDevice.getInstance().getOfferLists().getPersonalOffers());
        offersList.setVisible();

        controller.checkIfClaimAlertNeedsToBeShown(this);

        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        DrawerMenu.getInstance().clearActivity();
        offersList.onPause();

        Timber.tag(TAG).d("onPause (%s)", activityGuid);
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

    ///
    ///   OffersListAdapter.Response interface
    ///
    public void offerSelected(Batch batch){
        Timber.tag(TAG).d("...batchSelected -> " + batch.getGuid());
        ActivityNavigator.getInstance().gotoActivityOfferClaim(this, OfferConstants.OfferType.PERSONAL, batch.getGuid());
    }


    // Events for updating offers list.  Events not removed because we always want most recent
    // offers list whenever this activitity is created, started, or resumed.

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PersonalOfferListUpdatedEvent event) {
        Timber.tag(TAG).d("received PersonalOfferListUpdatedEvent");
        offersList.setValues(this, AndroidDevice.getInstance().getOfferLists().getPersonalOffers());
        offersList.setVisible();
    }

}
