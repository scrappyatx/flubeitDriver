/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.signInAndSignOut;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.useCaseLayer.signInAndSignOut.UseCaseSignInFromUserProfile;
import timber.log.Timber;

/**
 * Created on 7/13/2017
 * Project : Driver
 */

public class SignInFromUserProfileResponseHandler implements UseCaseSignInFromUserProfile.Response {
    private final static String TAG = "SignInFromUserProfileResponseHandler";

    public void useCaseSignInFromUserProfileSuccess(){
        Timber.tag(TAG).d("sign in from device storage SUCCESS");
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().postSticky(new SignInFromUserProfileResponseHandler.UseCaseSignInFromUserProfileSuccessEvent());
    }
    public void useCaseSignInFromUserProfileFailure(String failureMessage){
        Timber.tag(TAG).d("sign in from device storage FAILURE -> " + failureMessage);
        EventBus.getDefault().postSticky(new SignInFromUserProfileResponseHandler.UseCaseSignInFromUserProfileFailureEvent(failureMessage));
    }

    public void useCaseSignInFromUserProfileAuthFailure(String failureMessage) {
        Timber.tag(TAG).d("sign in from device storage AUTH FAILURE -> " + failureMessage);
        EventBus.getDefault().postSticky(new SignInFromUserProfileResponseHandler.UseCaseSignInFromUserProfileAuthFailureEvent(failureMessage));
    }

    public void useCaseSignInFromUserProfileUserNotADriverFailure(){
        Timber.tag(TAG).d("user is not a flube.it DRIVER");
    }

    public static class UseCaseSignInFromUserProfileSuccessEvent{
        public UseCaseSignInFromUserProfileSuccessEvent(){}
    }

    public static class UseCaseSignInFromUserProfileFailureEvent {
        private String failureMessage;

        public UseCaseSignInFromUserProfileFailureEvent(String failureMessage) {
            this.failureMessage = failureMessage;
        }

        public String getFailureMessage() {
            return failureMessage;
        }
    }

    public static class UseCaseSignInFromUserProfileAuthFailureEvent {
        private String failureMessage;

        public UseCaseSignInFromUserProfileAuthFailureEvent(String failureMessage) {
            this.failureMessage = failureMessage;
        }

        public String getFailureMessage() {
            return failureMessage;
        }
    }

    public static class UseCaseSignInFromUserProfileUserNotADriverFailureEvent {
        public UseCaseSignInFromUserProfileUserNotADriverFailureEvent(){

        }
    }
}
