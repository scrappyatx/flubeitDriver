/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.driverProfiles;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.entities.driver.Driver;
import timber.log.Timber;

/**
 * Created on 11/30/2017
 * Project : Driver
 */

public class FirebaseDriverProfileAddOrUpdate implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseDriverProfileAddOrUpdate";

    private AddOrUpdateDriverProfileResponse response;
    private Driver driver;

    public void addOrUpdateDriverProfile(DatabaseReference driverProfileNodeRef, Driver driver, AddOrUpdateDriverProfileResponse response){
        this.response = response;
        this.driver = driver;

        Timber.tag(TAG).d("driverProfileNodeRef = " + driverProfileNodeRef.toString());
        driverProfileNodeRef.child(driver.getClientId()).setValue(driver).addOnCompleteListener(this);
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
        response.addOrUpdateDriverProfileComplete(driver);
    }

    public interface AddOrUpdateDriverProfileResponse {
        void addOrUpdateDriverProfileComplete(Driver driver);
    }


}
