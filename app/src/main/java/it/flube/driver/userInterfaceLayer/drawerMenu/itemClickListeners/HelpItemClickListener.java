/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.drawerMenu.itemClickListeners;

import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHelpActivityEvent;

/**
 * Created on 5/27/2017
 * Project : Driver
 */

public class HelpItemClickListener implements Drawer.OnDrawerItemClickListener {
    private final String TAG = "HelpClickListener";
    public HelpItemClickListener(){

    }
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        // do something with the clicked item :D
        EventBus.getDefault().post(new GotoHelpActivityEvent());
        Log.d(TAG, "clicked on HELP");
        return false;
    }
}
