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
        if (device.getCloudAuth().hasDriver()) {
            Driver driver = device.getCloudAuth().getDriver();
            response.useCaseGetAccountDetailSuccess(driver);
        } else {
            response.useCaseGetAccountDetailFailure();
        }
    }

    public interface Response {
        void useCaseGetAccountDetailSuccess(Driver driver);

        void useCaseGetAccountDetailFailure();
    }
}
