/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mikepenz.materialdrawer.Drawer;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class HomeActiveBatchActivity extends AppCompatActivity {
    private static final String TAG = "HomeActiveBatchActivity";

    private HomeActiveBatchController mController;
    private ActivityNavigator navigator;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_active_batch);

        navigator = new ActivityNavigator();
        mDrawer = new DrawerMenu(this, navigator, R.string.home_activity_title).getDrawer();

        //instantiate controller for this activity
        mController = new HomeActiveBatchController();

        Timber.tag(TAG).d("onCreate");
    }


     /* ---------------------------------------------------------------------
     Activity Lifecycle Overrides - onStart & onStop

     1.  Instantiate Controller
     2.  EventBus Registration & Unregistration
     ------------------------------------------------------------------ */


    @Override
    public void onStart() {
        super.onStart();
        Timber.tag(TAG).d("onStart");
    }

    //unsubscribe to EventBus
    @Override
    public void onStop() {
        super.onStop();
        Timber.tag(TAG).d("onStop");
    }

}
