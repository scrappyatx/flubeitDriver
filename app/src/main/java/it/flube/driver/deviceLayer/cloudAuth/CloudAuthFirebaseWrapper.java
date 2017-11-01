/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudAuth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.AppRemoteConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import timber.log.Timber;

/**
 * Created on 6/25/2017
 * Project : Driver
 */

public class CloudAuthFirebaseWrapper implements
        CloudAuthInterface,
        FirebaseAuth.AuthStateListener,
        FirebaseAuth.IdTokenListener {

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
        signOutCurrentUser();

        shouldBeSignedIn = false;
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
    private Driver driver;

    private String tokenUrl;
    private Boolean shouldBeSignedIn;

    private CloudAuthFirebaseWrapper.SignInResponse mSignInResponse;


    public void connectRequest(AppRemoteConfigInterface appConfig, CloudAuthInterface.ConnectResponse response){
        Timber.tag(TAG).d("STARTING connectRequest...");

        tokenUrl = appConfig.getCloudStorageAuthTokenUrl();
        Timber.tag(TAG).d("   ...tokenUrl = " + tokenUrl);

        signOutCurrentUser();

        auth.addAuthStateListener(this);
        auth.addIdTokenListener(this);

        Timber.tag(TAG).d("...connect COMPLETE");
        response.cloudAuthConnectComplete();
    }

    private void signOutCurrentUser(){
        Timber.tag(TAG).d("signOutCurrentUser...");
        if (auth.getCurrentUser()!=null) {
            Timber.tag(TAG).d("   ...current user : " + auth.getCurrentUser().getDisplayName());
            Timber.tag(TAG).d("   ...signing out current user");
            auth.signOut();
        } else {
            Timber.tag(TAG).d("   ...no current user");
        }
        shouldBeSignedIn = false;
    }

    public void disconnect(){
        Timber.tag(TAG).d("STARTING disconnect...");

        auth.removeAuthStateListener(this);
        auth.removeIdTokenListener(this);
        signOutCurrentUser();

        Timber.tag(TAG).d("...disconnect COMPLETE");
    }

    public void signInRequest(Driver driver, SignInResponse response){
        this.driver = driver;
        shouldBeSignedIn = true;

        new FirebaseAuthUserSignIn().signInRequest(auth, driver, tokenUrl, response);
        Timber.tag(TAG).d("received signInRequest");
    }

    public void signOutRequest(SignOutResponse response){
        shouldBeSignedIn = false;

        new FirebaseAuthUserSignIn().signOutRequest(auth, response);
        Timber.tag(TAG).d("received signOutRequest");
    }

    public void onAuthStateChanged(@NonNull FirebaseAuth auth) {
        Timber.tag(TAG).d("onAuthStateChanged...");

        if (shouldBeSignedIn){
            Timber.tag(TAG).d("   ...user should be signed in");
            if (auth.getCurrentUser()==null){
                Timber.tag(TAG).d("      ...need to refresh user's token");
                new FirebaseAuthUserRefreshToken().refreshToken(auth, driver, tokenUrl);
            } else {
                Timber.tag(TAG).d("   ...user is signed in");
            }
        } else {
            Timber.tag(TAG).d("   ...user should not be signed in");
            if (auth.getCurrentUser()!=null){
                Timber.tag(TAG).d("      ...signing user out");
                signOutCurrentUser();
            } else {
                Timber.tag(TAG).d("   ...user is signed out");
            }
        }
    }


    public void onIdTokenChanged(@NonNull FirebaseAuth auth){
        Timber.tag(TAG).d("onIdTokenChanged...");
    }

}
