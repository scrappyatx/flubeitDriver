/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudAuth;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.userChanges.UseCaseThingsToDoWhenUserChangesToAnotherUser;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthAccessDeniedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthNoIdTokenEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthNoProfileEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthNoUserEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthUserChangedEvent;
import timber.log.Timber;

/**
 * Created on 6/25/2017
 * Project : Driver
 */

public class CloudAuthFirebaseWrapper implements
        CloudAuthInterface,
        FirebaseAuthGetUserToken.Response,
        CloudAuthStateChangedResponseHandler.Response,
        FirebaseAuth.AuthStateListener {

    ///
    ///     class variables
    ///
    private static final String TAG = "CloudAuthFirebaseWrapper";

    private FirebaseAuth auth;
    private DriverDeviceStorage storage;

    private Boolean isMonitoring;
    private Driver driver;
    private Boolean gotDriver;

    //private CloudAuthFirebaseWrapper.AuthStateChangedEvent authEvent;

    public CloudAuthFirebaseWrapper(Context appContext) {
        Timber.tag(TAG).d("creating auth instance");

        //load driver from device storage
        storage = new DriverDeviceStorage(appContext);

        gotDriver = storage.isDriverSaved();
        if (gotDriver){
            driver = storage.getDriver();
        } else {
            driver = null;
        }


        //initialize firebase auth
        auth = FirebaseAuth.getInstance();
        auth.removeAuthStateListener(this);

        //we are not monitoring
        isMonitoring = false;
    }

    public Driver getDriver(){
        Timber.tag(TAG).d("getDriver");
        return this.driver;
    }

    public Boolean hasDriver(){
        Timber.tag(TAG).d("hasDriver");
        return this.gotDriver;
    }

    public  void signOutCurrentUserRequest(SignOutCurrentUserResponse response){
        Timber.tag(TAG).d("signOutCurrentUserRequest START...");
        if (isMonitoring){
            Timber.tag(TAG).d("   ...removing auth state listener");
            auth.removeAuthStateListener(this);
        }
        Timber.tag(TAG).d("   ...signing out user");
        auth.signOut();
        Timber.tag(TAG).d("...signOutCurrentUserRequest FINISHED");
        response.cloudAuthSignOutCurrentUserComplete();
    }

    public void startMonitoringAuthStateChanges(StartMonitoringResponse response){
        Timber.tag(TAG).d("startMonitoringAuthStateChanges START...");
        if (!isMonitoring) {
            isMonitoring = true;
            auth.addAuthStateListener(this);
            Timber.tag(TAG).d("   ...started monitoring auth state changes");
        } else {
            Timber.tag(TAG).d("   ...startMonitoring called when already monitoring");
        }
        Timber.tag(TAG).d("...startMonitoringAuthStateChanges FINISHED");
        response.cloudAuthStartMonitoringComplete();
    }

    public void stopMonitoringAuthStateChanges(StopMonitoringResponse response){
        Timber.tag(TAG).d("stopMonitoringAuthStateChanges START...");
        if (isMonitoring) {
            auth.removeAuthStateListener(this);
            isMonitoring = false;
            Timber.tag(TAG).d("   ...stopped monitoring auth state changes");
        } else {
            Timber.tag(TAG).d("   ...stopMonitoring called when not monitoring");
        }
        Timber.tag(TAG).d("...stopMonitoringAuthStateChanges FINISHED");
        response.cloudAuthStopMonitoringComplete();
    }

    ////
    //// Firebase AuthState Listener interface
    ////

    public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
        //This method gets invoked in the UI thread on changes in the authentication state:
        //    - Right after the listener has been registered
        //    - When a user is signed in
        //    - When the current user is signed out
        //    - When the current user changes

        Timber.tag(TAG).d("onAuthStateChanged START...");

        if (auth.getCurrentUser()==null){
            //there is no signed in user
            Timber.tag(TAG).d("   ...there IS NOT a current signed in user");
            new CloudAuthStateChangedResponseHandler(this).doNoUser();
        } else {
            // we have a signed in user
            Timber.tag(TAG).d("   ...there IS a current signed in user, userId -> " + auth.getUid());
            new FirebaseAuthGetUserToken().getUserTokenRequest(auth.getCurrentUser(), this);
        }
        Timber.tag(TAG).d("...onAuthStateChange COMPLETE");
    }

    //// FirebaseAuthGetUserToken interface
    public void idTokenSuccess(String clientId, String email, String idToken){
        new CloudAuthStateChangedResponseHandler(this).doUserChanged(clientId, email, idToken);
    }

    public void idTokenFailure(){
        new CloudAuthStateChangedResponseHandler(this).doNoToken();
    }

    ///
    /// CloudAuthStateChangedResponeHandler interface
    ///
    public void userChangeGotDriver(Driver driver, String idToken){
        Timber.tag(TAG).d("userChangeGotDriver");

        //save this driver
        this.driver = driver;
        gotDriver = true;
        storage.setDriver(driver, idToken);

        EventBus.getDefault().postSticky(new CloudAuthUserChangedEvent(driver));

    }

    public void userChangeNoProfile(){
        Timber.tag(TAG).d("userChangeNoProfile");
        this.driver = null;
        gotDriver = false;
        storage.clearDriver();

        EventBus.getDefault().postSticky(new CloudAuthNoProfileEvent());

    }


    public void userChangeAccessDenied(){
        Timber.tag(TAG).d("userChangeAccessDenied");
        this.driver = null;
        gotDriver = false;
        storage.clearDriver();

        EventBus.getDefault().postSticky(new CloudAuthAccessDeniedEvent());
    }

    public void userChangeNoUser(){
        Timber.tag(TAG).d("userChangeNoUser");
        this.driver = null;
        gotDriver = false;
        storage.clearDriver();

        EventBus.getDefault().postSticky(new CloudAuthNoUserEvent());
    }
}
