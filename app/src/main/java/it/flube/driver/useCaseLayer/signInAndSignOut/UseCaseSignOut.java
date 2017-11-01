/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.signInAndSignOut;

import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.DeviceStorageInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;

/**
 * Created on 6/28/2017
 * Project : Driver
 */

public class UseCaseSignOut implements
        Runnable,
        DeviceStorageInterface.DeleteResponse,
        CloudAuthInterface.SignOutResponse,
        RealtimeMessagingInterface.Connection.DisconnectResponse,
        LocationTelemetryInterface.LocationTrackingStopResponse {

    private final MobileDeviceInterface mDevice;
    private final UseCaseSignOut.Response mResponse;

    public UseCaseSignOut(MobileDeviceInterface device, UseCaseSignOut.Response response) {
        mDevice = device;
        mResponse = response;
    }

    public void run(){
        //Step 1 -> clear appUser & signOut from cloud auth
        mDevice.getUser().clear();
        mDevice.getCloudAuth().signOutRequest(this);
        mDevice.getRealtimeConnection().messageServerDisconnectRequest(this);
        mDevice.getRealtimeOfferMessages().detach();
        mDevice.getRealtimeBatchMessages().disconnect();
        mDevice.getLocationTelemetry().locationTrackingStopRequest(this);
    }

    public void signOutUserCloudAuthComplete(){
        //step 2 --> delete driver from local storage
        mDevice.getDeviceStorage().deleteRequest(this);
    }

    public void deviceStorageDeleteComplete(){
        //step 3--> return to calling class on UI thread
        mResponse.useCaseSignOutComplete();
    }

    public void messageServerDisconnectComplete() {

    }

    public void locationTrackingStopComplete(){

    }

    /// interface to return results to calling program

    public interface Response {
        void useCaseSignOutComplete();
    }
}
