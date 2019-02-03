/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsPermanentlyFailedMonitor;

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
public class FirebaseUploadsPermanentlyFailedListener implements
        ChildEventListener {
    public static final String TAG = "FirebaseUploadsPermanentlyFailedListener";

    private FirebasePermanentlyFailedMonitor.Update update;


    public FirebaseUploadsPermanentlyFailedListener(FirebasePermanentlyFailedMonitor.Update update) {
        Timber.tag(TAG).d("FirebaseUploadsPermanentlyFailedListener");
        this.update = update;
    }


    public void onDataChange(@Nonnull DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");
        if (dataSnapshot.exists()) {
            Long fileCount = dataSnapshot.getChildrenCount();
            Timber.tag(TAG).d("   ...dataSnapshot exists --> there are " + Long.toString(fileCount) + " ownerGuids at this node");

            if (fileCount > 0) {
                Timber.tag(TAG).d("      ...getting ownerGuids");
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    try {
                        String ownerGuid = childSnapshot.getKey();
                        Timber.tag(TAG).d("         ...starting upload for ownerGuid -> " + ownerGuid);
                        ///upload is finished on this owner guid
                        update.permanentlyFailedUploadImageFile(ownerGuid);

                    } catch (Exception e) {
                        Timber.tag(TAG).w("         ...ERROR");
                        Timber.tag(TAG).e(e);
                    }
                }
            } else {
                //dataSnapshot has no children
                Timber.tag(TAG).d("      ...no owner guids");
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
        }
    }

    ///// methods for child event listener
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName){
        Timber.tag(TAG).d("onChildAdded");
        if (dataSnapshot.exists()) {
            try {
                String ownerGuid = dataSnapshot.getKey();
                Timber.tag(TAG).d("         ...permanently failed for ownerGuid -> " + ownerGuid);
                ///this is permanently faild
                update.permanentlyFailedUploadImageFile(ownerGuid);

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

