/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsNotStartedMonitor;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers.FirebaseUploadsFailureOrPausedResponseHandler;
import timber.log.Timber;

/**
 * Created on 1/20/2019
 * Project : Driver
 */
public class FirebaseUploadsNotStartedMonitor {
    public final String TAG = "FirebaseUploadsNotStartedMonitor";

    private DatabaseReference uploadTasksNotStartedListeningRef;
    private FirebaseUploadsNotStartedListener listener;
    private Boolean isListening;

    public FirebaseUploadsNotStartedMonitor(){
        isListening = false;
    }

    public void startListening(FirebaseDatabase driverDb, DatabaseReference uploadTasksNotStartedListeningRef, DatabaseReference uploadTasksNodeRef){
        Timber.tag(TAG).d("startListening");
        Timber.tag(TAG).d("   uploadTasksNotStartedListeningRef = " + uploadTasksNotStartedListeningRef.toString());
        Timber.tag(TAG).d("   uploadTasksNodeRef = " + uploadTasksNodeRef.toString());


        if (isListening){
            /// if we are already listening, we need to remove the current listener from this object
            Timber.tag(TAG).d("   removing existing listener");
            this.uploadTasksNotStartedListeningRef.removeEventListener(listener);
        }

        //// now recreate these objects
        this.uploadTasksNotStartedListeningRef = uploadTasksNotStartedListeningRef;

        /// add the new listener
        listener = new FirebaseUploadsNotStartedListener(new FirebaseUploadsFailureOrPausedResponseHandler(driverDb, uploadTasksNodeRef));
        uploadTasksNotStartedListeningRef.addChildEventListener(listener);
        isListening = true;
        Timber.tag(TAG).d("STARTED listening");
    }

    public void stopListening(){
        if (isListening) {
            uploadTasksNotStartedListeningRef.removeEventListener(listener);
            isListening = false;
            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("...called stopListening when not listening...");
        }

    }

    public interface Update {
        void uploadImageFile(String ownerGuid);
    }

}
