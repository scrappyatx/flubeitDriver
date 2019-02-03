/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsSuccessMonitor;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsPermanentlyFailedMonitor.FirebaseUploadsPermanentlyFailedListener;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers.FirebaseUploadsPermanentlyFailedResponseHandler;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers.FirebaseUploadsSuccessResponseHandler;
import timber.log.Timber;

/**
 * Created on 1/25/2019
 * Project : Driver
 */
public class FirebaseUploadsSuccessMonitor {
    public final String TAG = "FirebaseUploadsSuccessMonitor";

    private DatabaseReference uploadTasksSuccessListeningRef;
    private FirebaseUploadsSuccessListener listener;
    private Boolean isListening;

    public FirebaseUploadsSuccessMonitor(){
        isListening = false;
    }

    public void startListening(DatabaseReference uploadTasksSuccessListeningRef, DatabaseReference uploadTasksNodeRef) {

        Timber.tag(TAG).d("startListening");
        Timber.tag(TAG).d("   uploadTasksSuccessListeningRef = " + uploadTasksSuccessListeningRef.toString());
        Timber.tag(TAG).d("   uploadTasksNodeRef = " + uploadTasksNodeRef.toString());


        if (isListening) {
            /// if we are already listening, we need to remove the current listener from this object
            Timber.tag(TAG).d("   removing existing listener");
            this.uploadTasksSuccessListeningRef.removeEventListener(listener);
        }

        //// now recreate these objects
        this.uploadTasksSuccessListeningRef = uploadTasksSuccessListeningRef;

        /// add the new listener
        listener = new FirebaseUploadsSuccessListener(new FirebaseUploadsSuccessResponseHandler(uploadTasksNodeRef));
        uploadTasksSuccessListeningRef.addChildEventListener(listener);
        isListening = true;
        Timber.tag(TAG).d("STARTED listening");
    }

    public void stopListening() {
        if (isListening) {
            uploadTasksSuccessListeningRef.removeEventListener(listener);
            isListening = false;
            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("...called stopListening when not listening...");
        }

    }

    public interface Update {
        void successUploadImageFile(String ownerGuid);
    }
}

