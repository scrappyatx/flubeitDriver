/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.signInAndSignOut;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.DeviceStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 6/6/2017
 * Project : Driver
 */

public class UseCaseSignInFromDeviceStorage implements
        Runnable,
        DeviceStorageInterface.LoadResponse,
        UseCaseThingsToDoAfterSignIn.Response {


    private final MobileDeviceInterface device;
    private final UseCaseSignInFromDeviceStorage.Response response;

    public UseCaseSignInFromDeviceStorage(MobileDeviceInterface device, UseCaseSignInFromDeviceStorage.Response response) {
        this.device = device;
        this.response = response;
    }

    public void run() {
        device.getDeviceStorage().loadRequest(this);
    }

    ///
    /// If load from local storage SUCCEEDS
    ///
    public void deviceStorageLoadSuccess(Driver driver) {
        device.getUser().setDriver(driver);
        UseCaseThingsToDoAfterSignIn useCaseThingsToDoAfterSignIn = new UseCaseThingsToDoAfterSignIn(device, this);
        useCaseThingsToDoAfterSignIn.run();
    }

    public void useCaseThingsToDoAfterSignInComplete(){
        response.useCaseSignInFromDeviceStorageSuccess();
    }

    ///
    ///  If load from local storage FAILS
    ///
    public void deviceStorageLoadFailure(final String errorMessage) {
        device.getUser().clear();
        response.useCaseSignInFromDeviceStorageFailure(errorMessage);
    }

    /// interface to return results to calling program
    public interface Response {

        void useCaseSignInFromDeviceStorageSuccess();

        void useCaseSignInFromDeviceStorageFailure(String errorMessage);
    }


}
