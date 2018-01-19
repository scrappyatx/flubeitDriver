/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.driverProfiles;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import timber.log.Timber;

/**
 * Created on 11/30/2017
 * Project : Driver
 */

public class FirebaseDriverProfileDelete implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseDriverProfileDelete";

    private DeleteDriverProfileResponse response;

    public void deleteDriverProfile(DatabaseReference driverProfileNodeRef, String clientId, DeleteDriverProfileResponse response){
        this.response = response;
        Timber.tag(TAG).d("driverProfileNodeRef = " + driverProfileNodeRef.toString());
        driverProfileNodeRef.child(clientId).setValue(null).addOnCompleteListener(this);
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("onComplete...");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("   ...SUCCESS");
        } else {
            Timber.tag(TAG).w("   ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).w("   ...ERROR");
                Timber.tag(TAG).e(e);
            }

        }
        response.deleteDriverProfileComplete();
    }

    public interface DeleteDriverProfileResponse {
        void deleteDriverProfileComplete();
    }
}
