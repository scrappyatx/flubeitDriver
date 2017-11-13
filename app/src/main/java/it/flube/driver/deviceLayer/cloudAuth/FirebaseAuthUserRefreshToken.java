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
import timber.log.Timber;

/**
 * Created on 11/1/2017
 * Project : Driver
 */

public class FirebaseAuthUserRefreshToken implements
        HttpFirebaseToken.TokenResponse,
        OnCompleteListener<AuthResult> {

    private static final String TAG = "FirebaseAuthUserRefreshToken";

    private FirebaseAuth auth;

    public void refreshToken(FirebaseAuth auth, Driver driver, String tokenUrl){
        Timber.tag(TAG).d("STARTED refreshToken..");
        this.auth = auth;
        if ((auth.getCurrentUser() == null) || (!auth.getCurrentUser().getUid().equals(driver.getClientId()))) {
            // either no current user, OR the current user isn't our clientId
            // need to get a new auth token
            Timber.tag(TAG).d("   ...getting auth token");
            HttpFirebaseToken tokenGetter = new HttpFirebaseToken();
            // get firebase auth token
            tokenGetter.tokenRequest(tokenUrl, driver.getClientId(), this);
        } else {
            // we are already signed in to firebase auth. Check to see if we need to update
            // user details in firebase and return
            Timber.tag(TAG).d("   ...this user already has a firebase auth, don't need to get an auth token");
        }

    }
    public void firebaseTokenSuccess(String token) {
        Timber.tag(TAG).d("   ...got an auth token, now signing in with new token");
        auth.signInWithCustomToken(token).addOnCompleteListener(this);
    }

    public void onComplete(@NonNull Task<AuthResult> task) {
        Timber.tag(TAG).d("   onComplete...");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).d("      ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).d("      ...ERROR");
                Timber.tag(TAG).e(e);
            }
        }
    }

    public void firebaseTokenFailure(String message) {
        // couldn't get a token, no way to continue
        Timber.tag(TAG).w("Couldn't get a firebase token");
    }
}
