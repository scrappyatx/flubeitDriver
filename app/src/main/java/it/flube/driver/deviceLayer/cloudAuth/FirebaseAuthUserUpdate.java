/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudAuth;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import it.flube.driver.modelLayer.entities.Driver;
import timber.log.Timber;

/**
 * Created on 10/31/2017
 * Project : Driver
 */

public class FirebaseAuthUserUpdate implements
        OnCompleteListener<Void> {

    private static final String TAG = "FirebaseAuthUserUpdate";

    public void updateUserDetails(FirebaseAuth auth, Driver driver) {
        Timber.tag(TAG).d("checking auth user info to see if it matches with driver supplied info");
        if (auth.getCurrentUser() != null) {
           checkNameAndPhotoUrl(auth.getCurrentUser(), driver);
           checkEmail(auth.getCurrentUser(), driver);
        } else {
            Timber.tag(TAG).d("Auth User is NULL");
        }
    }

    private void checkNameAndPhotoUrl(FirebaseUser user, Driver driver){
        if (!driver.getDisplayName().equals(user.getDisplayName())) {
            Timber.tag(TAG).d("updating firebase auth user profile : driver & firebase user display names don't match");
            Timber.tag(TAG).d("    firebase user displayName = " + user.getDisplayName());
            Timber.tag(TAG).d("    driver displayName = " + driver.getDisplayName());
            Timber.tag(TAG).d("    driver photoUrl = " + driver.getPhotoUrl());

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(driver.getDisplayName())
                    .setPhotoUri(Uri.parse(driver.getPhotoUrl()))
                    .build();
            user.updateProfile(profileUpdates).addOnCompleteListener(this);
        } else {
            Timber.tag(TAG).d("don't need to update firebase user displayName and photoUrl");
        }
    }

    private void checkEmail(FirebaseUser user, Driver driver){
        if (!driver.getEmail().equals(user.getEmail())){
            Timber.tag(TAG).d("updating firebase user email address : driver & firebase user emails don't match");
            Timber.tag(TAG).d("   driver email        = " + driver.getEmail());
            Timber.tag(TAG).d("   firebase user email = " + user.getEmail());
            user.updateEmail(driver.getEmail()).addOnCompleteListener(this);
        } else {
            Timber.tag(TAG).d("don't need to update firebase user email");
        }
    }


    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).w("   ...onComplete");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            } catch (Exception e) {
                Timber.tag(TAG).w("      ...ERROR");
                Timber.tag(TAG).e(e);
            }
        }
    }

}
