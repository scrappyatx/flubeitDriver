/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudRealTimeClock;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import timber.log.Timber;

/**
 * Created on 8/21/2018
 * Project : Driver
 */
public class FirebaseServerTimeWrite implements
        OnCompleteListener<Void> {
    public static final String TAG = "FirebaseServerTimeWrite";

    private Response response;

    public void writeServerTimeRequest(DatabaseReference rtcRef, Response response){
        Timber.tag(TAG).d("writeServerTimeRequest...");
        Timber.tag(TAG).d("   rtcRef = " + rtcRef.toString());

        rtcRef.setValue(ServerValue.TIMESTAMP);
        this.response = response;
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }

        Timber.tag(TAG).d("COMPLETE");
        response.writeServerTimeComplete();
    }

    public interface Response {
        void writeServerTimeComplete();
    }

}
