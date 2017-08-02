/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.signInAndSignOut;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.useCaseLayer.signInAndSignOut.UseCaseSignInFromDeviceStorage;
import timber.log.Timber;

/**
 * Created on 7/13/2017
 * Project : Driver
 */

public class SignInFromDeviceStorageResponseHandler implements UseCaseSignInFromDeviceStorage.Response {
    private final static String TAG = "SignInFromDeviceStorageResponseHandler";

    public void useCaseSignInFromDeviceStorageSuccess(){
        Timber.tag(TAG).d("sign in from device storage SUCCESS");
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().postSticky(new UseCaseSignInFromDeviceStorageSuccessEvent());
    }

    public void useCaseSignInFromDeviceStorageFailure(String failureMessage){
        Timber.tag(TAG).d("sign in from device storage FAILURE -> " + failureMessage);
        EventBus.getDefault().postSticky(new UseCaseSignInFromDeviceStorageFailureEvent(failureMessage));
    }

    public static class UseCaseSignInFromDeviceStorageSuccessEvent{
        public UseCaseSignInFromDeviceStorageSuccessEvent(){}
    }


    public static class UseCaseSignInFromDeviceStorageFailureEvent {
        private String failureMessage;

        public UseCaseSignInFromDeviceStorageFailureEvent(String failureMessage) {
            this.failureMessage = failureMessage;
        }

        public String getFailureMessage() {
            return failureMessage;
        }
    }
}
