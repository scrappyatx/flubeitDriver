/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class FirebaseUser {
    private static final String TAG = "FirebaseUser";

    public void saveUserRequest(DatabaseReference reference, Driver driver, CloudDatabaseInterface.SaveResponse response) {
        reference.child(driver.getClientId()).setValue(driver).addOnCompleteListener(new FirebaseUser.SaveUserCompleteListener(response));
        Timber.tag(TAG).d("saving DRIVER object --> clientId : " + driver.getClientId() + " name : " + driver.getDisplayName());
    }

    private class SaveUserCompleteListener implements OnCompleteListener<Void> {
        private CloudDatabaseInterface.SaveResponse response;

        public SaveUserCompleteListener(@NonNull CloudDatabaseInterface.SaveResponse response) {
            this.response = response;
        }

        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("saveUserRequest --> SUCCESS");
                response.cloudDatabaseUserSaveComplete();
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("saveUserRequest --> FAILURE");
                response.cloudDatabaseUserSaveComplete();
            }
        }
    }
}
