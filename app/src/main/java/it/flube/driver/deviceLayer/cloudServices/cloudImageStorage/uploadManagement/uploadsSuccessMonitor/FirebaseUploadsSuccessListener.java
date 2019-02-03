/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsSuccessMonitor;

import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import javax.annotation.Nonnull;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsPausedMonitor.FirebaseUploadsPausedMonitor;
import timber.log.Timber;

/**
 * Created on 1/25/2019
 * Project : Driver
 */
public class FirebaseUploadsSuccessListener implements
        ChildEventListener {
    public static final String TAG = "FirebaseUploadsPausedListener";

    private FirebaseUploadsSuccessMonitor.Update update;


    public FirebaseUploadsSuccessListener(FirebaseUploadsSuccessMonitor.Update update){
        Timber.tag(TAG).d("FirebaseUploadsSuccessListener");
        this.update = update;
    }

    ///// methods for child event listener
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName){
        Timber.tag(TAG).d("onChildAdded");
        if (dataSnapshot.exists()) {
            try {
                String ownerGuid = dataSnapshot.getKey();
                Timber.tag(TAG).d("         ...successful upload for ownerGuid -> " + ownerGuid);
                ///now start an upload for this owner guid
                update.successUploadImageFile(ownerGuid);

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
