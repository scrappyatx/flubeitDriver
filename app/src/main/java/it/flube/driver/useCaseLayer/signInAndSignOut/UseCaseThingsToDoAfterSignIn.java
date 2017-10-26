/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.signInAndSignOut;

import it.flube.driver.dataLayer.useCaseResponseHandlers.deviceLocation.LocationTrackingPositionChangedHandler;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;

/**
 * Created on 7/28/2017
 * Project : Driver
 */

public class UseCaseThingsToDoAfterSignIn implements
        Runnable,
        CloudAuthInterface.SignInResponse,
        CloudDatabaseInterface.SaveResponse,
        LocationTelemetryInterface.LocationTrackingStartResponse,
        RealtimeMessagingInterface.Connection.ConnectResponse,
        MobileDeviceInterface.DeviceInfoRequestComplete,
        CloudDatabaseInterface.SaveDeviceInfoResponse,
        CloudDatabaseInterface.ConnectResponse {

    private final MobileDeviceInterface device;
    private final UseCaseThingsToDoAfterSignIn.Response response;

    public UseCaseThingsToDoAfterSignIn(MobileDeviceInterface device, UseCaseThingsToDoAfterSignIn.Response response){
        this.device = device;
        this.response = response;
    }

    public void run(){

        //Step 1 --> set person data for app logging
        device.getAppLogging().setPersonData(device.getUser().getDriver());

        //Step 2 --> sign in the driver on cloud auth
        device.getCloudAuth().signInRequest(device.getUser().getDriver(), device.getAppRemoteConfig(), this);
    }

    public void signInUserCloudAuthComplete(){
        //Step 3 --> startup the cloud database
        device.getCloudDatabase().connectRequest(device.getAppRemoteConfig(), device.getUser().getDriver(), this);
    }

    public void cloudDatabaseConnectComplete(){
        //step 4 --> save the user to the cloud database
        device.getCloudDatabase().saveUserRequest(this);

        //get device data
        device.deviceInfoRequest(this);
    }

    public void cloudDatabaseUserSaveComplete(){

        //device.getCloudDatabase().loadActiveBatchRequest(device.getUser().getDriver(), this);

        device.getRealtimeConnection().messageServerConnectRequest(device.getUser().getDriver(), this);

        device.getRealtimeOfferMessages().attach(device.getUser().getDriver().getClientId());

        device.getRealtimeBatchMessages().connect(device.getUser().getDriver().getClientId());

        device.getLocationTelemetry().locationTrackingStartRequest(this, new LocationTrackingPositionChangedHandler());

        device.getCloudDatabase().startMonitoring();

        response.useCaseThingsToDoAfterSignInComplete();
    }


    public void locationTrackingStartSuccess() {

    }

    public void locationTrackingStartFailure() {

    }

    public void messageServerConnectSuccess() {

    }

    public void messageServerConnectFailure() {

    }

    public void deviceInfoFailure(Exception e) {

    }

    public void deviceInfoSuccess(DeviceInfo deviceInfo) {
        device.getCloudDatabase().saveDeviceInfoRequest(deviceInfo, this);
    }

    public void cloudDatabaseDeviceInfoSaveComplete() {

    }

    public interface Response {
        void useCaseThingsToDoAfterSignInComplete();
    }
}
