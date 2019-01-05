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
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthAccessDeniedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthNoProfileEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthNoUserEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthUserChangedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuthAlerts.ShowAuthAccessDeniedAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuthAlerts.ShowAuthNoProfileAlertEvent;
import timber.log.Timber;

/**
 * Created on 12/17/2017
 * Project : Driver
 */

public class UserInterfaceAuthChangeEventHandler {
    public final static String TAG = "UserInterfaceAuthChangeEventHandler";

    private AppCompatActivity activity;


    public UserInterfaceAuthChangeEventHandler(@NonNull AppCompatActivity activity){
        this.activity = activity;
        EventBus.getDefault().register(this);
        Timber.tag(TAG).d("created");
    }

    public void close(){
        EventBus.getDefault().unregister(this);
        activity = null;
        Timber.tag(TAG).d("close");
    }

    ////
    //// AUTH CHANGE EVENTS
    ////

    ///
    ///  This event gets posted when a user signs in or the user changes
    ///
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(CloudAuthUserChangedEvent event) {
        EventBus.getDefault().removeStickyEvent(CloudAuthUserChangedEvent.class);

        Timber.tag(TAG).d("cloud auth user changed event received!");
        Timber.tag(TAG).d("    clientId    -> " + event.getDriver().getClientId());
        Timber.tag(TAG).d("    displayName -> " + event.getDriver().getNameSettings().getDisplayName());
        ActivityNavigator.getInstance().gotoActivityHome(activity);
    }

    ///
    ///  This event gets posted when a user signs out
    ///

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(CloudAuthNoUserEvent event) {
        EventBus.getDefault().removeStickyEvent(CloudAuthNoUserEvent.class);

        Timber.tag(TAG).d("cloud auth NO USER event received!");
        ActivityNavigator.getInstance().gotoActivityAuthUiSignIn(activity);
    }

    ///
    ///  This event gets posted when we can't get profile data for the user
    ///
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(CloudAuthNoProfileEvent event) {
        EventBus.getDefault().removeStickyEvent(CloudAuthNoProfileEvent.class);

        Timber.tag(TAG).d("cloud auth NO PROFILE event received!");
        ActivityNavigator.getInstance().gotoActivityAuthUiSignIn(activity);

        EventBus.getDefault().postSticky(new ShowAuthNoProfileAlertEvent());
        Timber.tag(TAG).d("posting ShowAuthNoProfileAlertEvent");
    }

    ///
    ///  This event gets posted when a user has been denied access in their profile data
    ///
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(CloudAuthAccessDeniedEvent event) {
        EventBus.getDefault().removeStickyEvent(CloudAuthAccessDeniedEvent.class);

        Timber.tag(TAG).d("cloud auth ACCESS DENIED event received!");
        ActivityNavigator.getInstance().gotoActivityAuthUiSignIn(activity);

        EventBus.getDefault().postSticky(new ShowAuthAccessDeniedAlertEvent());
        Timber.tag(TAG).d("posting ShowAuthAccessDeniedAlertEvent");
    }

}
