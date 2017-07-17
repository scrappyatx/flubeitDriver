/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.useCaseLayer.UseCaseInitialization;
import timber.log.Timber;

/**
 * Created on 7/13/2017
 * Project : Driver
 */

public class InitializationResponseHandler implements UseCaseInitialization.Response {
    private final static String TAG = "SignOutResponseHandler";

    public void useCaseInitializationComplete(){
        Timber.tag(TAG).d("initialization COMPLETE");
        EventBus.getDefault().postSticky(new InitializationResponseHandler.UseCaseInitializationCompleteEvent());
    }

    public static class UseCaseInitializationCompleteEvent{
        public UseCaseInitializationCompleteEvent(){}
    }

}
