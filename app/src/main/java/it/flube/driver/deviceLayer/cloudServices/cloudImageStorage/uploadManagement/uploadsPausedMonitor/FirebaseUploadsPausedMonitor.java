/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsPausedMonitor;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers.FirebaseUploadsFailureOrPausedResponseHandler;
import timber.log.Timber;

/**
 * Created on 1/23/2019
 * Project : Driver
 */
public class FirebaseUploadsPausedMonitor {
    public final String TAG = "FirebaseUploadsPausedMonitor";

    private DatabaseReference uploadTasksPausedListeningRef;
    private FirebaseUploadsPausedListener listener;
    private Boolean isListening;

    public FirebaseUploadsPausedMonitor(){
        isListening = false;
    }

    public void startListening(FirebaseDatabase driverDb, DatabaseReference uploadTasksPausedListeningRef, DatabaseReference uploadTasksNodeRef){
        Timber.tag(TAG).d("startListening");
        Timber.tag(TAG).d("   uploadTasksPausedListeningRef = " + uploadTasksPausedListeningRef.toString());
        Timber.tag(TAG).d("   uploadTasksNodeRef = " + uploadTasksNodeRef.toString());


        if (isListening){
            /// if we are already listening, we need to remove the current listener from this object
            Timber.tag(TAG).d("   removing existing listner");
            this.uploadTasksPausedListeningRef.removeEventListener(listener);
        }

        //// now recreate these objects
        this.uploadTasksPausedListeningRef = uploadTasksPausedListeningRef;

        /// add the new listener
        listener = new FirebaseUploadsPausedListener(new FirebaseUploadsFailureOrPausedResponseHandler(driverDb, uploadTasksNodeRef));
        uploadTasksPausedListeningRef.addChildEventListener(listener);
        isListening = true;
        Timber.tag(TAG).d("STARTED listening");
    }

    public void stopListening(){
        if (isListening) {
            uploadTasksPausedListeningRef.removeEventListener(listener);
            isListening = false;
            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("...called stopListening when not listening...");
        }

    }

    public interface Update {
        void resumeUploadImageFile(String ownerGuid);
    }

}
