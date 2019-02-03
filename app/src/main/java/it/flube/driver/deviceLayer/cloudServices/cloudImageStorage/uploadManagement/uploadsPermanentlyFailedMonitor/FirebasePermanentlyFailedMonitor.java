/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsPermanentlyFailedMonitor;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers.FirebaseUploadsPermanentlyFailedResponseHandler;
import timber.log.Timber;

/**
 * Created on 1/24/2019
 * Project : Driver
 */
public class FirebasePermanentlyFailedMonitor {
    public final String TAG = "FirebasePermanentlyFailedMonitor";

    private DatabaseReference uploadTasksPermanentlyFailedListeningRef;
    private FirebaseUploadsPermanentlyFailedListener listener;
    private Boolean isListening;

    public FirebasePermanentlyFailedMonitor(){
        isListening = false;
    }

    public void startListening(DatabaseReference uploadTasksPermanentlyFailedListeningRef, DatabaseReference uploadTasksNodeRef) {

        Timber.tag(TAG).d("startListening");
        Timber.tag(TAG).d("   uploadTasksPermanentlyFailedListeningRef = " + uploadTasksPermanentlyFailedListeningRef.toString());
        Timber.tag(TAG).d("   uploadTasksNodeRef = " + uploadTasksNodeRef.toString());


        if (isListening) {
            /// if we are already listening, we need to remove the current listener from this object
            Timber.tag(TAG).d("   removing existing listener");
            this.uploadTasksPermanentlyFailedListeningRef.removeEventListener(listener);
        }

        //// now recreate these objects
        this.uploadTasksPermanentlyFailedListeningRef = uploadTasksPermanentlyFailedListeningRef;

        /// add the new listener
        listener = new FirebaseUploadsPermanentlyFailedListener(new FirebaseUploadsPermanentlyFailedResponseHandler(uploadTasksNodeRef));
        uploadTasksPermanentlyFailedListeningRef.addChildEventListener(listener);
        isListening = true;
        Timber.tag(TAG).d("STARTED listening");
    }

    public void stopListening() {
        if (isListening) {
            uploadTasksPermanentlyFailedListeningRef.removeEventListener(listener);
            isListening = false;
            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("...called stopListening when not listening...");
        }

    }

    public interface Update {
        void permanentlyFailedUploadImageFile(String ownerGuid);
    }
}

