/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.PermissionsCheckActivity;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class HomeNoActiveBatchActivity extends PermissionsCheckActivity {
    private static final String TAG = "HomeNabActivity";

    private HomeNoActiveBatchController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_no_active_batch);
        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onPermissionResume(){
        super.onPermissionResume();
        Timber.tag(TAG).d("onPermissionResume");
        //EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.home_no_active_batch_activity_title);
        controller = new HomeNoActiveBatchController();

        controller.sendSomeTestMessages();
    }


    @Override
    public void onPermissionPause(){
        super.onPermissionPause();
        Timber.tag(TAG).d("onPermissionPause");
        //EventBus.getDefault().unregister(this);

        drawer.close();
    }

}
