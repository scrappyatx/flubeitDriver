/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.rollbar.android.Rollbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.controllers.OffersController;
import it.flube.driver.dataLayer.interfaces.toBeDeleted.EbEventOfferListDELETE;
import it.flube.driver.dataLayer.messaging.eventBus.driverMessageEvents.ReceivedCurrentOffersMessage;
import it.flube.driver.dataLayer.presenters.OffersListAdapter;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.userInterfaceLayer.drawerMenu.NavigationMenu;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoAccountActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoDemoActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoEarningsActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHelpActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeActiveBatchActivityEventDELETE;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeNoActiveBatchActivityEventDELETE;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoLoginActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoMessagesActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoOffersActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoScheduledBatchesActivityEvent;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class OffersActivity extends AppCompatActivity {
    private static final String TAG = "OffersActivity";

    private OffersController mController;
    private Toolbar mToolbar;
    private Drawer mDrawer;

    private RecyclerView mOfferView;
    private LinearLayoutManager mLinearLayoutManager;
    private OffersListAdapter mAdapter;
    private ArrayList<Offer> mOfferList;

     /* ------------------------------------------------------------------
     Activity Lifecycle Overrides - onCreate

     1.  Instantiate Rollbar (if required)
     2.  Call superclass onCreate()
     3.  Inflate the view associated with this activity
     4.  Create toolbar & navigation menu
     ------------------------------------------------------------------ */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize rollbar
        if (!Rollbar.isInit()) {
            Rollbar.init(this, "6489dbbc16e943beaebf5c0028ee588a", BuildConfig.BUILD_TYPE + "_" + BuildConfig.VERSION_NAME);
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offers);

        //setup toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Offers");
        this.setSupportActionBar(mToolbar);

        mDrawer = new NavigationMenu(this, mToolbar).getDrawer();

        //setup view
        mOfferView = (RecyclerView) findViewById(R.id.offersView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mOfferView.setLayoutManager(mLinearLayoutManager);

        //initialize offer list
        mOfferList = new ArrayList<Offer>();
        mAdapter = new OffersListAdapter(mOfferList);
        mOfferView.setAdapter(mAdapter);

        //turn offerlist visibility off
        mOfferView.setVisibility(View.INVISIBLE);

        Log.d(TAG, "OffersActivity CREATED");
    }


     /* ---------------------------------------------------------------------
     Activity Lifecycle Overrides - onStart & onStop

     1.  Instantiate Controller
     2.  EventBus Registration & Unregistration
     ------------------------------------------------------------------ */


    @Override
    public void onStart() {
        Log.d(TAG, "onStart() START");
        super.onStart();

        //instantiate controller for this activity
        mController = new OffersController();

        //register on eventbus
        EventBus.getDefault().register(this);



        Log.d(TAG, "onStart() END");
    }

    //unsubscribe to EventBus
    @Override
    public void onStop() {
        Log.d(TAG, "onStop() START");

        //unregister on eventbus
        EventBus.getDefault().unregister(this);

        super.onStop();
        Log.d(TAG, "onStop() END");
    }

   /* ---------------------------------------
     Activity Navigation Events
     ----------------------------------------- */

    //event bus events
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoAccountActivityEvent event) {
        event.startActivity(this);
        Log.d(TAG,"starting AccountActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoDemoActivityEvent event) {
        event.startActivity(this);
        Log.d(TAG,"starting DemoActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoEarningsActivityEvent event) {
        event.startActivity(this);
        Log.d(TAG,"starting EarningsActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoHelpActivityEvent event) {
        event.startActivity(this);
        Log.d(TAG,"starting HelpActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoHomeActivityEvent event) {
        event.startActivity(this);
        Log.d(TAG,"starting HomeActiveBatchActivity");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoMessagesActivityEvent event) {
        event.startActivity(this);
        Log.d(TAG,"starting MessagesActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoOffersActivityEvent event) {
        event.startActivity(this);
        Log.d(TAG,"starting OffersActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoScheduledBatchesActivityEvent event) {
        event.startActivity(this);
        Log.d(TAG,"starting ScheduledBatchesActivity");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GotoLoginActivityEvent event) {
        event.startActivity(this);
        Log.d(TAG,"starting LoginActivity");
    }

     /* ---------------------------------------
     Realtime Messaging Events
     ----------------------------------------- */
     

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOfferEvent(ReceivedCurrentOffersMessage event) {

        ArrayList<Offer> offerList = ReceivedCurrentOffersMessage.getCurrentOfferList();

        Log.d(TAG,"*** offers received ***");
        Log.d(TAG," offers available --> " + offerList.size());
        if (offerList.size() > 0) {
            //we have offers
            //update adapter
            mAdapter.updateList(offerList);

            //turn offer list visibility ON
            mOfferView.setVisibility(View.VISIBLE);

        } else {
            //we don't have offers
            //turn offer list visibility OFF
            mOfferView.setVisibility(View.INVISIBLE);
        }
    }



}
