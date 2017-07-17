/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.holder.StringHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.dataLayer.presenters.OffersListAdapter;
import it.flube.driver.deviceLayer.realtimeMessaging.receiveMessageHandlers.CurrentOffersMessageHandler;
import it.flube.driver.modelLayer.Offer;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.demo.DemoController;
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

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_offers);


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


    @Override
    public void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.offers_activity_title);
        controller = new OffersController();

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        drawer.close();

        Timber.tag(TAG).d(TAG, "onPause");

        super.onPause();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(){

    }



    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(CurrentOffersMessageHandler.CurrentOffersEvent event) {
        try {
            Timber.tag(TAG).d("received " + Integer.toString(event.getOfferCount()) + " offers");
            if (event.getOfferList().size() > 0) {
                mAdapter.updateList(event.getOfferList());
                mOfferView.setVisibility(View.VISIBLE);
            } else {
                mOfferView.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
            mOfferView.setVisibility(View.INVISIBLE);
        }
    }

}
