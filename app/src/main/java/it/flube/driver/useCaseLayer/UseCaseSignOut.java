/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer;

import android.os.Handler;
import android.os.Looper;

import it.flube.driver.useCaseLayer.interfaces.CloudAuthInterface;
import it.flube.driver.useCaseLayer.interfaces.DeviceStorageInterface;
import it.flube.driver.useCaseLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 6/28/2017
 * Project : Driver
 */

public class UseCaseSignOut implements Runnable, DeviceStorageInterface.DeleteResponse, CloudAuthInterface.SignOutResponse {

    private final MobileDeviceInterface mDevice;
    private UseCaseSignOut.Response mResponse;

    public UseCaseSignOut(MobileDeviceInterface device, UseCaseSignOut.Response response) {
        mDevice = device;
        mResponse = response;
    }

    public void run(){
        //Step 1 -> clear appUser & signOut from cloud auth
        mDevice.getUser().clear();
        mDevice.getCloudAuth().signOutRequest(this);
    }

    public void signOutUserCloudAuthComplete(){
        //step 2 --> delete driver from local storage
        mDevice.getDeviceStorage().deleteRequest(this);
    }

    public void deviceStorageDeleteComplete(){
        //step 3--> return to calling class on UI thread
        mResponse.useCaseSignOutComplete();
    }

    /// interface to return results to calling program

    public interface Response {
        void useCaseSignOutComplete();
    }
}
