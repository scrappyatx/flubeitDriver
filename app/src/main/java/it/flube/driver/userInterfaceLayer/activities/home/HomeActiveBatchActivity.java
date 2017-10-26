/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class HomeActiveBatchActivity extends AppCompatActivity {
    private static final String TAG = "HomeActiveBatchActivity";

    private HomeActiveBatchController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_active_batch);

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onResume(){
        super.onResume();
        Timber.tag(TAG).d("onResume");

        //EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.home_active_batch_activity_title);
        controller = new HomeActiveBatchController();

    }

    @Override
    public void onPause(){
        super.onPause();
        Timber.tag(TAG).d("onPause");

        //EventBus.getDefault().unregister(this);

        drawer.close();
    }


}
