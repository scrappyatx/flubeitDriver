/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.modelLayer.Driver;
import it.flube.driver.useCaseLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 7/5/2017
 * Project : Driver
 */

public class CloudDatabaseFirebase implements CloudDatabaseInterface {
    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static volatile CloudDatabaseFirebase mInstance = new CloudDatabaseFirebase();
    }

    private CloudDatabaseFirebase() {
        setupFirebaseDatabaseForOfflinePersistence();
    }

    public static CloudDatabaseFirebase getInstance() {
        return CloudDatabaseFirebase.Loader.mInstance;
    }

    private final String TAG = "CloudDatabaseFirebase";

    private DatabaseReference mDatabase;
    private CloudDatabaseInterface.SaveResponse mResponse;

    private void setupFirebaseDatabaseForOfflinePersistence(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Timber.tag(TAG).d("FirebaseDatabase --> setPersistenceEnabled TRUE for OFFLINE persistence");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        Timber.tag(TAG).d("FirebaseDatabase --> got instance");
    }

    public void saveUserRequest(Driver driver, CloudDatabaseInterface.SaveResponse response) {
        mResponse = response;
        mDatabase.child("users").child(driver.getClientId()).setValue(driver).addOnCompleteListener(new SaveUserCompleteListener());
        Timber.tag(TAG).d("saving DRIVER object --> clientId : " + driver.getClientId() + " name : " + driver.getDisplayName());
    }

    private class SaveUserCompleteListener implements OnCompleteListener<Void> {

        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {
                Timber.tag(TAG).d("saveUserRequest --> SUCCESS");
                mResponse.cloudDatabaseUserSaveComplete();
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("saveUserRequest --> FAILURE");
                mResponse.cloudDatabaseUserSaveComplete();
            }
        }
    }
}
