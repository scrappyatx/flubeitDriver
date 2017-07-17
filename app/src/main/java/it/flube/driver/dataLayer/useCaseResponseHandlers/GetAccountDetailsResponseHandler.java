/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.modelLayer.Driver;
import it.flube.driver.useCaseLayer.UseCaseGetAccountDetails;
import timber.log.Timber;

/**
 * Created on 7/16/2017
 * Project : Driver
 */

public class GetAccountDetailsResponseHandler implements UseCaseGetAccountDetails.Response {
    private final static String TAG = "GetAccountDetailsResponseHandler";

    public void accountDetailSuccess(Driver driver) {
        Timber.tag(TAG).d("get account detail SUCCESS");
        EventBus.getDefault().postSticky(new UseCaseGetAccountDetailsSuccessEvent(driver));
    }

    public void accountDetailFailure(){
        Timber.tag(TAG).d("get account detail FAILURE");
        EventBus.getDefault().postSticky(new UseCaseGetAccountDetailsFailureEvent());
    }

    public static class UseCaseGetAccountDetailsSuccessEvent {
        private Driver driver;
        public UseCaseGetAccountDetailsSuccessEvent(Driver driver){
            this.driver = driver;
        }

        public Driver getDriver(){
            return driver;
        }
    }

    public static class UseCaseGetAccountDetailsFailureEvent {
        public UseCaseGetAccountDetailsFailureEvent(){}
    }

}
