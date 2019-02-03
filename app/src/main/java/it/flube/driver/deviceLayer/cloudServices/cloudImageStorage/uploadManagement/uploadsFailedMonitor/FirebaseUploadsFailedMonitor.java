/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsFailedMonitor;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers.FirebaseUploadsFailureOrPausedResponseHandler;
import timber.log.Timber;

/**
 * Created on 1/24/2019
 * Project : Driver
 */
public class FirebaseUploadsFailedMonitor {
    public final String TAG = "FirebaseUploadsFailedMonitor";

    private DatabaseReference uploadTasksFailedListeningRef;
    private FirebaseUploadsFailedListener listener;
    private Boolean isListening;

    public FirebaseUploadsFailedMonitor(){
        isListening = false;
    }


    public void startListening(FirebaseDatabase driverDb, DatabaseReference uploadTasksFailedListeningRef, DatabaseReference uploadTasksNodeRef){
        Timber.tag(TAG).d("startListening");
        Timber.tag(TAG).d("   uploadTasksFailedListeningRef = " + uploadTasksFailedListeningRef.toString());
        Timber.tag(TAG).d("   uploadTasksNodeRef = " + uploadTasksNodeRef.toString());


        if (isListening){
            /// if we are already listening, we need to remove the current listener from this object
            Timber.tag(TAG).d("   removing existing listner");
            this.uploadTasksFailedListeningRef.removeEventListener(listener);
        }

        //// now recreate these objects
        this.uploadTasksFailedListeningRef = uploadTasksFailedListeningRef;

        /// add the new listener
        listener = new FirebaseUploadsFailedListener(new FirebaseUploadsFailureOrPausedResponseHandler(driverDb, uploadTasksNodeRef));
        uploadTasksFailedListeningRef.addChildEventListener(listener);
        isListening = true;
        Timber.tag(TAG).d("STARTED listening");
    }

    public void stopListening(){
        if (isListening) {
            uploadTasksFailedListeningRef.removeEventListener(listener);
            isListening = false;
            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("...called stopListening when not listening...");
        }

    }

    public interface Update {
        void failedUploadImageFile(String ownerGuid);
    }

}

