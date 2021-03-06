/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsFailedMonitor;

import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.annotation.Nonnull;

import timber.log.Timber;

/**
 * Created on 1/24/2019
 * Project : Driver
 */
public class FirebaseUploadsFailedListener implements
        ChildEventListener {
    public static final String TAG = "FirebaseUploadsFailedListener";

    private FirebaseUploadsFailedMonitor.Update update;


    public FirebaseUploadsFailedListener(FirebaseUploadsFailedMonitor.Update update) {
        Timber.tag(TAG).d("FirebaseUploadsNotStartedListener");
        this.update = update;
    }

    ///// methods for child event listener
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName){
        Timber.tag(TAG).d("onChildAdded");
        if (dataSnapshot.exists()) {
            try {
                String ownerGuid = dataSnapshot.getKey();
                Timber.tag(TAG).d("         ...failed upload for ownerGuid -> " + ownerGuid);
                ///now start an upload for this owner guid
                update.failedUploadImageFile(ownerGuid);

            } catch (Exception e) {
                Timber.tag(TAG).w("         ...ERROR");
                Timber.tag(TAG).e(e);
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
        }
    }

    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName){
        Timber.tag(TAG).d("onChildChanged, ownerGuid -> " + previousChildName);
    }

    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String previousChildName){
        Timber.tag(TAG).d("onChildMoved, ownerGuid -> " + previousChildName);
    }

    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot){
        Timber.tag(TAG).d("onChildRemoved");
    }

    public void onCancelled(@NonNull DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error --> " + databaseError.getCode() + " --> " + databaseError.getMessage());
    }
}


