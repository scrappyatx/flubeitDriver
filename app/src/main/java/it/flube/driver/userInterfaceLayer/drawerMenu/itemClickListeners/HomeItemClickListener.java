/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.drawerMenu.itemClickListeners;

import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeNoActiveBatchActivityEventDELETE;

/**
 * Created on 5/25/2017
 * Project : Driver
 */

public class HomeItemClickListener implements Drawer.OnDrawerItemClickListener {
    private final String TAG = "HomeClickListener";
    public HomeItemClickListener() {

    }

    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        // do something with the clicked item :D
        EventBus.getDefault().post(new GotoHomeActivityEvent());
        Log.d(TAG, "clicked on HOME");
        return false;
    }
}
