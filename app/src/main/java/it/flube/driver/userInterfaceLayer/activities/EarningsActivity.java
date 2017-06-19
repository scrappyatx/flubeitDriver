/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.mikepenz.materialdrawer.Drawer;
import com.rollbar.android.Rollbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.controllers.EarningsController;
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

public class EarningsActivity extends AppCompatActivity {
    private static final String TAG = "EarningsActivity";

    private EarningsController mController;
    private Toolbar mToolbar;
    private Drawer mDrawer;

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

        setContentView(R.layout.activity_earnings);

        //setup toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Earnings");
        this.setSupportActionBar(mToolbar);

        mDrawer = new NavigationMenu(this, mToolbar).getDrawer();

        Log.d(TAG, "EarningsActivity CREATED");
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
        mController = new EarningsController();

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


}
