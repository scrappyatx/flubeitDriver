/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mikepenz.materialdrawer.Drawer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.help.HelpController;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class MessagesActivity extends AppCompatActivity {
    private static final String TAG = "MessagesActivity";

    private MessagesController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();

        //EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.messages_activity_title);
        controller = new MessagesController();;

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