/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.deviceAndUser;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class FirebaseUser {
    private static final String TAG = "FirebaseUser";

    private DatabaseReference userRef;

    public FirebaseUser(FirebaseDatabase database, String baseNode){
        userRef = database.getReference(baseNode);
    }

    public void saveUserRequest(Driver driver, CloudDatabaseInterface.SaveResponse response) {
        userRef.child("driverInfo").child(driver.getClientId()).setValue(driver).addOnCompleteListener(new FirebaseUser.SaveUserCompleteListener(response));
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
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("saveUserRequest --> FAILURE");
            }
            response.cloudDatabaseUserSaveComplete();
        }
    }

    public void saveUserLastLogin(Driver driver) {
        Map<String, Object> value = new HashMap<String, Object>();
        value.put("clientId", driver.getClientId());
        value.put("lastLogin", ServerValue.TIMESTAMP);
        userRef.child("lastLogin").child(driver.getClientId()).setValue(value);
        Timber.tag(TAG).d("saving DRIVER lastLogin --> clientId : " + driver.getClientId() + " name : " + driver.getDisplayName());
    }

}
