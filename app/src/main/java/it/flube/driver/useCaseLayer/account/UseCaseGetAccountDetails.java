/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.account;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 7/16/2017
 * Project : Driver
 */

public class UseCaseGetAccountDetails implements Runnable {
    private final static String TAG = "UseCaseGetAccountDetails";

    private final MobileDeviceInterface device;
    private final UseCaseGetAccountDetails.Response response;

    public UseCaseGetAccountDetails(MobileDeviceInterface device, UseCaseGetAccountDetails.Response response) {
        this.device = device;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        if (device.getUser().isSignedIn()) {
            response.accountDetailSuccess(device.getUser().getDriver());
        } else {
            response.accountDetailFailure();
        }
    }

    public interface Response {
        void accountDetailSuccess(Driver driver);

        void accountDetailFailure();
    }
}
