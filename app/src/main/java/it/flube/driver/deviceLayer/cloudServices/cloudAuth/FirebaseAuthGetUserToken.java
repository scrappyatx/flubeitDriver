/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudAuth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import timber.log.Timber;

/**
 * Created on 12/19/2017
 * Project : Driver
 */

public class FirebaseAuthGetUserToken implements
        OnCompleteListener<GetTokenResult> {

    private static final String TAG = "FirebaseAuthGetUserToken";

    private CloudAuthInterface.AuthStateChangedEvent response;
    private FirebaseUser currentUser;

    public void getUserTokenRequest(FirebaseUser currentUser, CloudAuthInterface.AuthStateChangedEvent response){
        this.response = response;
        this.currentUser = currentUser;

        currentUser.getIdToken(true).addOnCompleteListener(this);
        Timber.tag(TAG).d("getUserTokenRequest START...");
        Timber.tag(TAG).d("   ...clientId = " + currentUser.getUid());
        Timber.tag(TAG).d("   ...email    = " + currentUser.getEmail());
    }

    public void onComplete(@NonNull Task<GetTokenResult> task) {
        Timber.tag(TAG).d("   onComplete...");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");

            try {
                String idToken = task.getResult().getToken();
                Timber.tag(TAG).d("      ...idToken = " + idToken);
                response.cloudAuthStateChangedUserChanged(currentUser.getUid(), currentUser.getEmail(), idToken);
            } catch (Exception e) {
                Timber.tag(TAG).d("      ...ERROR retrieving token string");
                Timber.tag(TAG).e(e);
                response.cloudAuthStateChangedNoIdToken();
            }

        } else {
            Timber.tag(TAG).d("      ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).d("      ...ERROR");
                Timber.tag(TAG).e(e);
            }
            response.cloudAuthStateChangedNoIdToken();
        }
    }
}
