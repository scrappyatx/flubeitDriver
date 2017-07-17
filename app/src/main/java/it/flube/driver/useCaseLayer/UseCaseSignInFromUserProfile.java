/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer;

import android.os.Handler;
import android.os.Looper;

import it.flube.driver.modelLayer.Driver;
import it.flube.driver.useCaseLayer.interfaces.CloudAuthInterface;
import it.flube.driver.useCaseLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.useCaseLayer.interfaces.DeviceStorageInterface;
import it.flube.driver.useCaseLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.interfaces.UserProfileInterface;

/**
 * Created on 6/27/2017
 * Project : Driver
 */

public class UseCaseSignInFromUserProfile implements Runnable, UserProfileInterface.Response,
        DeviceStorageInterface.SaveResponse, CloudAuthInterface.SignInResponse, CloudDatabaseInterface.SaveResponse {

    private final MobileDeviceInterface mDevice;
    private final UseCaseSignInFromUserProfile.Response mResponse;
    private final String mUserName;
    private final String mPassword;
    private Driver mDriver;

    public UseCaseSignInFromUserProfile(String userName, String password, MobileDeviceInterface device, Response response) {
        mDevice = device;
        mResponse = response;
        mUserName = userName;
        mPassword = password;
    }

    public void run() {
        // Step 1 -> make driver request
        mDevice.getUser().clear();
        String requestUrl = mDevice.getAppRemoteConfig().getDriverProfileUrl();
        mDevice.getUserProfile().getDriverRequest(requestUrl, mUserName, mPassword, this);
    }

    ///   Callbacks if getDriverRequest SUCCEEDED
    public void getDriverSuccess(Driver driver) {
        //Step 2 --> set the driver in the appUser, set signed-in flag, and save driver to local storage
        mDriver = driver;
        mDevice.getUser().setDriver(mDriver);

        //save the driver to local storage
        mDevice.getDeviceStorage().saveRequest(mDriver, this);
    }

    public void deviceStorageSaveComplete() {
        //Step 3 --> sign in the driver on cloud auth
        mDevice.getCloudAuth().signInRequest(mDriver, mDevice.getAppRemoteConfig(), this);
    }

    public void signInUserCloudAuthComplete(){
        //Step 4 --> save this user to cloud database
        mDevice.getCloudDatabase().saveUserRequest(mDriver, this);
    }

    public void cloudDatabaseUserSaveComplete(){
        mResponse.useCaseSignInFromUserProfileSuccess();
    }

    ////  Callbacks if getDriverRequest FAILED
    public void getDriverFailure(final String responseMessage) {
        mResponse.useCaseSignInFromUserProfileFailure(responseMessage);
    }

    public void getDriverAuthFailure(final String responseMessage) {
        mResponse.useCaseSignInFromUserProfileAuthFailure(responseMessage);
    }

    /// interface to return results to calling program
    public interface Response {
        void useCaseSignInFromUserProfileSuccess();

        void useCaseSignInFromUserProfileFailure(String errorMessage);

        void useCaseSignInFromUserProfileAuthFailure(String errorMessage);
    }
}
