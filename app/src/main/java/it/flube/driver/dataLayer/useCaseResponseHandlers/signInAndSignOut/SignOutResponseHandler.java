/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.signInAndSignOut;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.useCaseLayer.signInAndSignOut.UseCaseSignOut;
import timber.log.Timber;

/**
 * Created on 7/13/2017
 * Project : Driver
 */

public class SignOutResponseHandler implements UseCaseSignOut.Response {
    private final static String TAG = "SignOutResponseHandler";

    public void useCaseSignOutComplete(){
        Timber.tag(TAG).d("sign out COMPLETE");
        EventBus.getDefault().postSticky(new SignOutResponseHandler.UseCaseSignOutCompleteEvent());
    }

    public static class UseCaseSignOutCompleteEvent{
        public UseCaseSignOutCompleteEvent(){}
    }
}
