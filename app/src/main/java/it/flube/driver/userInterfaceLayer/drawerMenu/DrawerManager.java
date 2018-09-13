/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.drawerMenu;

import android.support.v7.app.AppCompatActivity;

import it.flube.driver.R;
import timber.log.Timber;

import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created on 9/12/2018
 * Project : Driver
 */
public class DrawerManager {
    public static final String TAG = "DrawerManager";

    public Toolbar createToolbar(AppCompatActivity activity, int titleId) {
        Timber.tag(TAG).d("createToolbar");

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);


        String toolbarTitle = activity.getResources().getString(titleId);
        Timber.tag(TAG).d("....creating toolbar, title = " + toolbarTitle);
        toolbar.setTitle(toolbarTitle);
        activity.setSupportActionBar(toolbar);
        return toolbar;
    }

    public SwitchCompat getToolbarSwitch(AppCompatActivity activity){
        SwitchCompat toolbarSwitch = (SwitchCompat) activity.findViewById(R.id.switch_looking_for_work);
        toolbarSwitch.setChecked(false);
        toolbarSwitch.setVisibility(View.GONE);
        return toolbarSwitch;
    }

}
