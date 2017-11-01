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
 * Created on 11/1/2017
 * Project : Driver
 */

public class FirebaseAuthUserSignIn implements
        OnCompleteListener<AuthResult>,
        HttpFirebaseToken.TokenResponse {

    private static final String TAG = "FirebaseAuthUserSignIn";

    private FirebaseAuth auth;
    private Driver driver;
    private FirebaseAuthUserUpdate firebaseAuthUserUpdate;
    private CloudAuthFirebaseWrapper.SignInResponse response;


    public void signInRequest(FirebaseAuth auth, Driver driver, String tokenUrl, CloudAuthInterface.SignInResponse response) {
        this.auth = auth;
        this.driver = driver;
        this.response = response;
        firebaseAuthUserUpdate = new FirebaseAuthUserUpdate();

        if ((auth.getCurrentUser() == null) || (!auth.getCurrentUser().getUid().equals(driver.getClientId()))) {
            // either no current user, OR the current user isn't our clientId
            // need to get a new auth token
            Timber.tag(TAG).d("current user doesn't match our client id, need to get an auth token");
            HttpFirebaseToken tokenGetter = new HttpFirebaseToken();
            // get firebase auth token
            tokenGetter.tokenRequest(tokenUrl, driver.getClientId(), this);
        } else {
            // we are already signed in to firebase auth. Check to see if we need to update
            // user details in firebase and return
            Timber.tag(TAG).d("this user already has a firebase auth");
            firebaseAuthUserUpdate.updateUserDetails(auth, driver);
            response.signInUserCloudAuthSuccess();
        }
    }

    public void firebaseTokenSuccess(String token) {
        auth.signInWithCustomToken(token).addOnCompleteListener(this);
    }

    public void onComplete(@NonNull Task<AuthResult> task) {
        Timber.tag(TAG).d("onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("   ...SUCCESS");
            firebaseAuthUserUpdate.updateUserDetails(auth, driver);
            response.signInUserCloudAuthSuccess();
        } else {
            Timber.tag(TAG).d("   ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).d("   ...ERROR");
                Timber.tag(TAG).e(e);
            }
            response.signInUserCloudAuthFailure();
        }
    }

    public void firebaseTokenFailure(String message) {
        // couldn't get a token, no way to continue
        Timber.tag(TAG).w("Couldn't get a firebase token");
        response.signInUserCloudAuthFailure();
    }


    public void signOutRequest(FirebaseAuth auth, CloudAuthInterface.SignOutResponse response) {
        Timber.tag(TAG).d("STARTING signOutRequest...");
        if (auth.getCurrentUser()!=null) {
            Timber.tag(TAG).d("   ...signing out current user : " + auth.getCurrentUser().getDisplayName());
            auth.signOut();
        } else {
            Timber.tag(TAG).d("   ...current user is NULL");
        }
        response.signOutUserCloudAuthComplete();
    }

}
