/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.drawerMenu.itemClickListeners;

import android.util.Log;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoDemoActivityEvent;


/**
 * Created on 5/27/2017
 * Project : Driver
 */

public class DemoItemClickListener implements Drawer.OnDrawerItemClickListener {
    private final String TAG = "DemoClickListener";
    public DemoItemClickListener() {

    }
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        // do something with the clicked item :D
        EventBus.getDefault().post(new GotoDemoActivityEvent());
        Log.d(TAG, "clicked on DEMO");
        return false;
    }

}
