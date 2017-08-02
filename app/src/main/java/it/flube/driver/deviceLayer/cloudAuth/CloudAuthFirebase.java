/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudAuth;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.AppRemoteConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import timber.log.Timber;

/**
 * Created on 6/25/2017
 * Project : Driver
 */

public class CloudAuthFirebase implements CloudAuthInterface, HttpFirebaseToken.TokenResponse {
    ///
    ///  Loader class provides synchronization across threads
    ///  Lazy initialization since Loader class is only called when "getInstance" is called
    ///  volatile keyword guarantees visibility of changes to variables across threads
    ///
    private static class Loader {
        static volatile CloudAuthFirebase mInstance = new CloudAuthFirebase();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private CloudAuthFirebase() {}

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static CloudAuthFirebase getInstance() {
        return CloudAuthFirebase.Loader.mInstance;
    }


    ///
    ///     class variables
    ///
    private static final String TAG = "CloudAuthFirebase";
    private String mTokenUrl;
    private FirebaseAuth mAuth;
    private Driver mDriver;
    private CloudAuthFirebase.SignInResponse mSignInResponse;


    public void signInRequest(Driver driver, AppRemoteConfigInterface appConfig, SignInResponse response) {
        mSignInResponse = response;
        mDriver = driver;
        String tokenUrl = appConfig.getCloudStorageAuthTokenUrl();

        mAuth = FirebaseAuth.getInstance();
        if ((mAuth.getCurrentUser() == null) || (!mAuth.getCurrentUser().getUid().equals(mDriver.getClientId()))) {
            // either no current user, OR the current user isn't our clientId
            // need to get a new auth token
            Timber.tag(TAG).d("current user doesn't match our client id, need to get an auth token");
            HttpFirebaseToken tokenGetter = new HttpFirebaseToken();
            // get firebase auth token
            tokenGetter.tokenRequest(tokenUrl, mDriver.getClientId(), this);
        } else {
            // we are already signed in to firebase auth. Check to see if we need to update
            // user details in firebase and return
            Timber.tag(TAG).d("this user already has a firebase auth");
            updateUserDetails();
            mSignInResponse.signInUserCloudAuthComplete();
        }
    }

    public void signOutRequest(SignOutResponse response) {
        mAuth.signOut();
        Timber.tag(TAG).d("Signed out current user");
        response.signOutUserCloudAuthComplete();
    }

    private void updateUserDetails() {
        FirebaseUser user = mAuth.getCurrentUser();
        String displayName = mDriver.getDisplayName();
        Timber.tag(TAG).d("updating user details DisplayName = " + displayName);

        if ((user!=null) && (!displayName.equals(user.getDisplayName()))) {
            Timber.tag(TAG).d("we need to update firebase user profile");
            Timber.tag(TAG).d("   firebase user displayName = " + user.getDisplayName());
            Timber.tag(TAG).d("   appUser displayName = " + displayName);
            Timber.tag(TAG).d("   appUser photoUrl = " + mDriver.getPhotoUrl());

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(mDriver.getPhotoUrl()))
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new UpdateProfileListener());
        } else {
            Timber.tag(TAG).d("did not need to update firebase user profile");
        }
        // now check to see if we need to update email address for this user
        updateEmail();
    }

    private void updateEmail() {
        FirebaseUser user = mAuth.getCurrentUser();
        String email = mDriver.getEmail();
        Timber.tag(TAG).d("updating user email = " + email);

        if ((user!=null) && (!email.equals(user.getEmail()))) {
            Timber.tag(TAG).d("we need to update firebase user email");
            Timber.tag(TAG).d("    firebase user Email = " + user.getEmail());
            Timber.tag(TAG).d("    appUser email = " + email);
            user.updateEmail(email);
        } else {
            Timber.tag(TAG).d("did not need to update firebase user email");
        }
    }

    ///
    /// Callback -> HttpFirebaseToken.TokenResponse
    ///
    public void firebaseTokenSuccess(String token) {
        mAuth.signInWithCustomToken(token).addOnCompleteListener(new SignInCompleteListener());
    }

    public void firebaseTokenFailure(String message) {
        // couldn't get a token, no way to continue
        Timber.tag(TAG).w("Couldn't get a firebase token");
        mSignInResponse.signInUserCloudAuthComplete();
    }

    ///
    ///  private OnCompleteListener class for signInWithCustomToken
    ///
    private class SignInCompleteListener implements OnCompleteListener<AuthResult> {

        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("signInWithCustomToken --> SUCCESS");
                updateUserDetails();
                mSignInResponse.signInUserCloudAuthComplete();
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("signInWithCustomToken --> FAILURE");
                mSignInResponse.signInUserCloudAuthComplete();
            }
        }
    }

    private class UpdateProfileListener implements OnCompleteListener<Void> {

        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("firebase update user profile --> SUCCESS");
            } else {
                try {
                    throw task.getException();
                } catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("firebase update user profile --> FAILURE");
            }
        }
    }

}
