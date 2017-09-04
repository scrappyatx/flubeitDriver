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
        CloudDatabaseInterface.LoadActiveBatchResponse {

    private final MobileDeviceInterface device;
    private final UseCaseThingsToDoAfterSignIn.Response response;

    public UseCaseThingsToDoAfterSignIn(MobileDeviceInterface device, UseCaseThingsToDoAfterSignIn.Response response){
        this.device = device;
        this.response = response;
    }

    public void run(){

        //Step 0 --> set person data for app logging
        device.getAppLogging().setPersonData(device.getUser().getDriver());

        //Step 1 --> sign in the driver on cloud auth
        device.getCloudAuth().signInRequest(device.getUser().getDriver(), device.getAppRemoteConfig(), this);
    }

    public void signInUserCloudAuthComplete(){
        //Step 4 --> save this user to cloud database
        device.getCloudDatabase().saveUserRequest(device.getUser().getDriver(), this);

        //get device data
        device.deviceInfoRequest(this);
    }

    public void cloudDatabaseUserSaveComplete(){
        device.getCloudDatabase().listenForPublicOffers(device.getAppRemoteConfig().getCloudDatabaseBaseNodePublicOffers(), device.getUser().getDriver());

        device.getCloudDatabase().listenForPersonalOffers(device.getAppRemoteConfig().getCloudDatabaseBaseNodePersonalOffers(), device.getUser().getDriver());

        device.getCloudDatabase().listenForDemoOffers(device.getAppRemoteConfig().getCloudDatabaseBaseNodeDemoOffers(), device.getUser().getDriver());

        device.getCloudDatabase().listenForScheduledBatches(device.getAppRemoteConfig().getCloudDatabaseBaseNodeScheduledBatches(), device.getUser().getDriver());

        device.getCloudDatabase().loadActiveBatchRequest(device.getUser().getDriver(), this);

        device.getRealtimeConnection().messageServerConnectRequest(device.getUser().getDriver(), this);

        device.getRealtimeOfferMessages().attach(device.getUser().getDriver().getClientId());

        device.getRealtimeBatchMessages().connect(device.getUser().getDriver().getClientId());

        device.getLocationTelemetry().locationTrackingStartRequest(this, new LocationTrackingPositionChangedHandler());


        response.useCaseThingsToDoAfterSignInComplete();
    }

    public void cloudDatabaseNoActiveBatchAvailable(){
        device.getUser().clearActiveBatch();
    }

    public void cloudDatabaseActiveBatchLoaded(Batch activeBatch) {
        device.getUser().setActiveBatch(activeBatch);
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
        device.getCloudDatabase().saveDeviceInfoRequest(device.getUser().getDriver(), deviceInfo, this);
    }

    public void cloudDatabaseDeviceInfoSaveComplete() {

    }

    public interface Response {
        void useCaseThingsToDoAfterSignInComplete();
    }
}
