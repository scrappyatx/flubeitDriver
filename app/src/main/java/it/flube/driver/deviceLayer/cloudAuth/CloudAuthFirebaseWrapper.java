/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudAuth;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import it.flube.driver.dataLayer.useCaseResponseHandlers.cloudAuth.CloudAuthStateChangedResponseHandler;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import timber.log.Timber;

/**
 * Created on 6/25/2017
 * Project : Driver
 */

public class CloudAuthFirebaseWrapper implements
        CloudAuthInterface,
        FirebaseAuth.AuthStateListener {

    ///
    ///  Loader class provides synchronization across threads
    ///  Lazy initialization since Loader class is only called when "getInstance" is called
    ///  volatile keyword guarantees visibility of changes to variables across threads
    ///
    private static class Loader {
        static volatile CloudAuthFirebaseWrapper instance = new CloudAuthFirebaseWrapper();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private CloudAuthFirebaseWrapper() {
        Timber.tag(TAG).d("creating auth instance");
        auth = FirebaseAuth.getInstance();
        auth.removeAuthStateListener(this);

        isMonitoring = false;
        cloudAuthStateChangedResponseHandler = new CloudAuthStateChangedResponseHandler();
    }

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static CloudAuthFirebaseWrapper getInstance() {
        return CloudAuthFirebaseWrapper.Loader.instance;
    }

    ///
    ///     class variables
    ///
    private static final String TAG = "CloudAuthFirebaseWrapper";
    private FirebaseAuth auth;
    private CloudAuthStateChangedResponseHandler cloudAuthStateChangedResponseHandler;

    private Boolean isMonitoring;

    private CloudAuthFirebaseWrapper.AuthStateChangedEvent authEvent;

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
            Timber.tag(TAG).w("   ...startMonitoring called when already monitoring");
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
            Timber.tag(TAG).w("   ...stopMonitoring called when not monitoring");
        }
        Timber.tag(TAG).d("...stopMonitoringAuthStateChanges FINISHED");
        response.cloudAuthStopMonitoringComplete();
    }

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
            cloudAuthStateChangedResponseHandler.cloudAuthStateChangedNoUser();
        } else {
            // we have a signed in user
            Timber.tag(TAG).d("   ...there IS a current signed in user, userId -> " + auth.getUid());
            new FirebaseAuthGetUserToken().getUserTokenRequest(auth.getCurrentUser(), cloudAuthStateChangedResponseHandler);
        }
        Timber.tag(TAG).d("...onAuthStateChange COMPLETE");
    }

}
