/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.help;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class HelpActivity extends AppCompatActivity {
    private static final String TAG = "HelpActivity";

    private HelpController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Timber.tag(TAG).d("HelpActivity CREATED");
    }


    @Override
    public void onResume() {
        super.onResume();

        //EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.help_activity_title);
        controller = new HelpController();

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
