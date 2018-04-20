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
        CloudAuthInterface.AuthStateChangedEvent,
        UseCaseThingsToDoWhenUserChangesToAnotherUser.Response,
        UseCaseThingsToDoWhenUserChangesToNoUser.Response {

    private static final String TAG = "CloudAuthStateChangedResponseHandler";

    ///
    ///     CASE 1 --> If user changes to a new user
    ///
    public void cloudAuthStateChangedUserChanged(String clientId, String email, String idToken){
        MobileDeviceInterface device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenUserChangesToAnotherUser(device, clientId, email, idToken, this));
        Timber.tag(TAG).d("case 1 -> user changed to another user : clientId -> " + clientId + " email -> " + email);
    }

    ///
    ///     RESULT 1A ---> found user profile
    ///
    public void userChangedToNewUserSuccess(Driver driver){
        EventBus.getDefault().postSticky(new CloudAuthUserChangedEvent(driver));
        Timber.tag(TAG).d("    ... result 1A -> found user profile, posting CloudAuthUserChangedEvent on eventbus");
    }

    ///
    ///    RESULT 1B ---> no user profile
    ///
    public void userChangedToNewUserNoProfile(){
        EventBus.getDefault().postSticky(new CloudAuthNoProfileEvent());
        Timber.tag(TAG).d("   ... result 1B -> did not find user profile, posting CloudAuthNoProfileEvent on eventbus");
    }



    ///
    ///    RESULT 1C ---> access denied
    ///
    public void userChangedToNewUserAccessDenied(){
        EventBus.getDefault().postSticky(new CloudAuthAccessDeniedEvent());
        Timber.tag(TAG).d("   ...result 1C -> access denied, posting CloudAuthAccessDeniedEvent on eventbus");
    }

    ///
    ///     CASE 2 --> If user changes to no user
    ///
    public void cloudAuthStateChangedNoUser(){
        MobileDeviceInterface device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenUserChangesToNoUser(device, this));
        Timber.tag(TAG).d("case 2 -> user changed to no user");
    }

    public void userChangedToNoUserComplete(){
        EventBus.getDefault().postSticky(new CloudAuthNoUserEvent());
        Timber.tag(TAG).d("   ...result 2 -> posting CloudAuthNoUserEvent on eventbus");
    }

    ///
    ///   CASE 3 --> Could not get idToken from cloudAuth
    ///
    public void cloudAuthStateChangedNoIdToken(){
        MobileDeviceInterface device = AndroidDevice.getInstance();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenUserChangesToNoUser(device, this));
        Timber.tag(TAG).d("case 3 -> could not get idToken for authorized user, treat same as NO USER");
    }

}
