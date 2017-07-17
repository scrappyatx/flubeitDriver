/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer;

import android.os.Handler;
import android.os.Looper;

import it.flube.driver.modelLayer.Driver;
import it.flube.driver.useCaseLayer.interfaces.CloudAuthInterface;
import it.flube.driver.useCaseLayer.interfaces.DeviceStorageInterface;
import it.flube.driver.useCaseLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 6/6/2017
 * Project : Driver
 */

public class UseCaseSignInFromDeviceStorage implements Runnable, DeviceStorageInterface.LoadResponse, CloudAuthInterface.SignInResponse {
    private MobileDeviceInterface mDevice;
    private Response mResponse;

    public UseCaseSignInFromDeviceStorage(MobileDeviceInterface device, Response response) {
        mDevice = device;
        mResponse = response;
    }

    public void run() {
        ///Step 1 - request driver from local storage
        mDevice.getDeviceStorage().loadRequest(this);
    }

    ///
    /// If load from local storage SUCCEEDS
    ///
    public void deviceStorageLoadSuccess(Driver driver) {
        //step 1 -> set user & sign in to cloud auth
        mDevice.getUser().setDriver(driver);
        mDevice.getCloudAuth().signInRequest(driver, mDevice.getAppRemoteConfig(), this);
    }

    public void signInUserCloudAuthComplete(){
        mResponse.useCaseSignInFromDeviceStorageSuccess();
    }

    ///
    ///  If load from local storage FAILS
    ///
    public void deviceStorageLoadFailure(final String errorMessage) {
        mDevice.getUser().clear();
        mResponse.useCaseSignInFromDeviceStorageFailure(errorMessage);

    }

    /// interface to return results to calling program
    public interface Response {

        void useCaseSignInFromDeviceStorageSuccess();

        void useCaseSignInFromDeviceStorageFailure(String errorMessage);
    }


}
