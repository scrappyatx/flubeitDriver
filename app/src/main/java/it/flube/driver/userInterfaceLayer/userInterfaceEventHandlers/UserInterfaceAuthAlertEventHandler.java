/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.dialogs.AuthAccessDeniedAlertDialog;
import it.flube.driver.userInterfaceLayer.dialogs.AuthNoProfileAlertDialog;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuthAlerts.ShowAuthAccessDeniedAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuthAlerts.ShowAuthNoProfileAlertEvent;
import timber.log.Timber;

/**
 * Created on 12/20/2017
 * Project : Driver
 */

public class UserInterfaceAuthAlertEventHandler {
    public final static String TAG = "UserInterfaceAuthAlertEventHandler";

    private AppCompatActivity activity;
    private ActivityNavigator navigator;

    public UserInterfaceAuthAlertEventHandler(@NonNull AppCompatActivity activity, @NonNull ActivityNavigator navigator ){
        this.activity = activity;
        this.navigator = navigator;
        EventBus.getDefault().register(this);
    }

    public void close(){
        EventBus.getDefault().unregister(this);
        navigator = null;
        activity = null;
    }

    ///
    ///  Show AUTH NO PROFILE alert dialog
    ///
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowAuthNoProfileAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(ShowAuthNoProfileAlertEvent.class);

        Timber.tag(TAG).d("show alert for AuthNoProfile event received!");
        new AuthNoProfileAlertDialog().getAlertDialog(activity).show();
    }

    ///
    ///  Show AUTH ACCESS DENIED
    ///
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowAuthAccessDeniedAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(ShowAuthAccessDeniedAlertEvent.class);

        Timber.tag(TAG).d("show alert for AuthAccessDenied event received!");
        new AuthAccessDeniedAlertDialog().getAlertDialog(activity).show();
    }

}
