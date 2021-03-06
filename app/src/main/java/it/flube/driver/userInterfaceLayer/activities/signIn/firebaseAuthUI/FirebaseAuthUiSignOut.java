/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.signIn.firebaseAuthUI;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import timber.log.Timber;

/**
 * Created on 11/30/2017
 * Project : Driver
 */

public class FirebaseAuthUiSignOut implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseAuthUiSignOut";

    private AuthUiSignOutResponse response;

    public void signOutRequest(AppCompatActivity activity, AuthUiSignOutResponse response){
        this.response = response;

        AuthUI.getInstance()
                .signOut(activity)
                .addOnCompleteListener(this);
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("onComplete...");
        if (task.isSuccessful()) {
            // Deletion succeeded
            Timber.tag(TAG).d("onComplete...");
        } else {
            // Deletion failed
            Timber.tag(TAG).w("   ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).w("   ...ERROR");
                Timber.tag(TAG).e(e);
            }
        }
        response.authUiSignOutComplete();
    }

    public interface AuthUiSignOutResponse {
        void authUiSignOutComplete();
    }
}
