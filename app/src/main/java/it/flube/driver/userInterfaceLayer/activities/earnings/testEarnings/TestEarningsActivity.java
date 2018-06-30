/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.earnings.testEarnings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.earnings.productionEarnings.EarningsController;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 6/26/2018
 * Project : Driver
 */
public class TestEarningsActivity extends AppCompatActivity {
    private static final String TAG = "TestEarningsActivity";

    private ActivityNavigator navigator;
    private TestEarningsController controller;
    private DrawerMenu drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_earnings);

        Timber.tag(TAG).d("onCreate");
    }


    public void onResume() {
        super.onResume();

        //EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator,  R.string.test_earnings_activity_title);
        controller = new TestEarningsController();

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {

        //EventBus.getDefault().unregister(this);

        drawer.close();

        Timber.tag(TAG).d( "onPause");
        super.onPause();
    }

}
