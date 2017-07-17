/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.earnings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mikepenz.materialdrawer.Drawer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.demo.DemoController;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class EarningsActivity extends AppCompatActivity {
    private static final String TAG = "EarningsActivity";

    private ActivityNavigator navigator;
    private EarningsController controller;
    private DrawerMenu drawer;

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
        setContentView(R.layout.activity_earnings);

        Timber.tag(TAG).d("onCreate");
    }


    public void onResume() {
        super.onResume();

        //EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator,  R.string.earnings_activity_title);
        controller = new EarningsController();

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {

        //EventBus.getDefault().unregister(this);

        drawer.close();

        Timber.tag(TAG).d(TAG, "onPause");
        super.onPause();
    }



}
