/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.signInAndSignOut;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.DeviceStorageInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.UserProfileInterface;

/**
 * Created on 6/27/2017
 * Project : Driver
 */

public class UseCaseSignInFromUserProfile implements Runnable,
        UserProfileInterface.Response,
        DeviceStorageInterface.SaveResponse,
        UseCaseThingsToDoAfterSignIn.Response {

    private final MobileDeviceInterface device;
    private final UseCaseSignInFromUserProfile.Response response;
    private final String userName;
    private final String password;
    private Driver driver;

    public UseCaseSignInFromUserProfile(String userName, String password, MobileDeviceInterface device, Response response) {
        this.device = device;
        this.response = response;
        this.userName = userName;
        this.password = password;
    }

    public void run() {
        // Step 1 -> make driver request
        device.getUser().clear();
        String requestUrl = device.getAppRemoteConfig().getDriverProfileUrl();
        device.getUserProfile().getDriverRequest(requestUrl, userName, password, this);
    }

    ///   Callbacks if getDriverRequest SUCCEEDED
    public void getDriverSuccess(Driver driver) {
        //Step 2 --> set the driver in the appUser, set signed-in flag, and save driver to local storage
        device.getUser().setDriver(driver);
        device.getDeviceStorage().saveRequest(driver, this);
    }

    public void deviceStorageSaveComplete() {
        UseCaseThingsToDoAfterSignIn useCaseThingsToDoAfterSignIn = new UseCaseThingsToDoAfterSignIn(device, this);
        useCaseThingsToDoAfterSignIn.run();
    }

    public void useCaseThingsToDoAfterSignInComplete(){
        response.useCaseSignInFromUserProfileSuccess();
    }


    ////  Callbacks if getDriverRequest FAILED
    public void getDriverFailure(final String responseMessage) {
        response.useCaseSignInFromUserProfileFailure(responseMessage);
    }

    public void getDriverAuthFailure(final String responseMessage) {
        response.useCaseSignInFromUserProfileAuthFailure(responseMessage);
    }

    public void getDriverUserNotADriverFailure() {
        response.useCaseSignInFromUserProfileUserNotADriverFailure();
    }

    /// interface to return results to calling program
    public interface Response {
        void useCaseSignInFromUserProfileSuccess();

        void useCaseSignInFromUserProfileFailure(String errorMessage);

        void useCaseSignInFromUserProfileAuthFailure(String errorMessage);

        void useCaseSignInFromUserProfileUserNotADriverFailure();
    }
}
