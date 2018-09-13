/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudAuth;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthAccessDeniedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthNoProfileEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthNoUserEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthUserChangedEvent;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.userChanges.UseCaseThingsToDoWhenUserChangesToAnotherUser;
import it.flube.driver.useCaseLayer.userChanges.UseCaseThingsToDoWhenUserChangesToNoUser;
import timber.log.Timber;

/**
 * Created on 11/30/2017
 * Project : Driver
 */

public class CloudAuthStateChangedResponseHandler implements
        UseCaseThingsToDoWhenUserChangesToAnotherUser.Response,
        UseCaseThingsToDoWhenUserChangesToNoUser.Response {

    private static final String TAG = "CloudAuthStateChangedResponseHandler";

    String idToken;

    private Response response;

    public CloudAuthStateChangedResponseHandler(Response response){
        this.response = response;
        Timber.tag(TAG).d("created");
    }

    //////////////
    ///     CASE 1 --> If user changes to a new user
    ///
    ///     Do useCase for changing to another user, 3 possible results
    public void doUserChanged(String clientId, String email, String idToken){
        Timber.tag(TAG).d("doUserChanged");

        this.idToken = idToken;
        MobileDeviceInterface device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenUserChangesToAnotherUser(device, clientId, email, idToken, this));
        Timber.tag(TAG).d("case 1 -> user changed to another user : clientId -> " + clientId + " email -> " + email);
    }

    ///     RESULT 1A ---> found user profile
    public void useCaseUserChangedToNewUserSuccess(Driver driver){
        Timber.tag(TAG).d("userChangedToNewUserSuccess");
        response.userChangeGotDriver(driver, idToken);
    }

    ///    RESULT 1B ---> no user profile
    public void useCaseUserChangedToNewUserNoProfile(){
        Timber.tag(TAG).d("userChangedToNewUserNoProfile");
        response.userChangeNoProfile();
        Timber.tag(TAG).d("   ... result 1B -> did not find user profile, posting CloudAuthNoProfileEvent on eventbus");
    }

    ///    RESULT 1C ---> access denied
    public void useCaseUserChangedToNewUserAccessDenied(){
        Timber.tag(TAG).d("userChangedToNewUserAccessDenied");
        response.userChangeAccessDenied();
    }

    /////////////////
    ///     CASE 2 --> If user changes to no user
    ///
    ///     Do uses case for changing to no user, 1 possible result
    public void doNoUser(){
        Timber.tag(TAG).d("doNoUser");
        MobileDeviceInterface device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenUserChangesToNoUser(device, this));
    }

    ///
    ///   CASE 3 --> Could not get idToken from cloudAuth
    ///
    ///     Do uses case for changing to no user, 1 possible result
    public void doNoToken(){
        Timber.tag(TAG).d("doNoToken");
        MobileDeviceInterface device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenUserChangesToNoUser(device, this));
        Timber.tag(TAG).d("case 3 -> could not get idToken for authorized user, treat same as NO USER");
    }


    public void useCaseUserChangedToNoUserComplete(){
        Timber.tag(TAG).d("useCaseUserChangedToNoUserComplete");
        response.userChangeNoUser();
    }

    //// response interface

    interface Response {
        void userChangeGotDriver(Driver driver, String idToken);

        void userChangeNoProfile();

        void userChangeAccessDenied();

        void userChangeNoUser();
    }

}
