/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.account;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 7/16/2017
 * Project : Driver
 */

public class UseCaseGetAccountDetails implements Runnable {
    private final MobileDeviceInterface device;
    private final UseCaseGetAccountDetails.Response response;

    public UseCaseGetAccountDetails(MobileDeviceInterface device, UseCaseGetAccountDetails.Response response) {
        this.device = device;
        this.response = response;
    }

    public void run(){
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
