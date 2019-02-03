/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsCanceledMonitor;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsFailedMonitor.FirebaseUploadsFailedListener;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers.FirebaseUploadsFailureOrPausedResponseHandler;
import timber.log.Timber;

/**
 * Created on 1/28/2019
 * Project : Driver
 */
public class FirebaseUploadsCanceledMonitor {
    public final String TAG = "FirebaseUploadsCanceledMonitor";

    private DatabaseReference uploadTasksCanceledListeningRef;
    private FirebaseUploadsCanceledListener listener;
    private Boolean isListening;

    public FirebaseUploadsCanceledMonitor(){
        isListening = false;
    }


    public void startListening(FirebaseDatabase driverDb, DatabaseReference uploadTasksCanceledListeningRef, DatabaseReference uploadTasksNodeRef){
        Timber.tag(TAG).d("startListening");
        Timber.tag(TAG).d("   uploadTasksCanceledListeningRef = " + uploadTasksCanceledListeningRef.toString());
        Timber.tag(TAG).d("   uploadTasksNodeRef = " + uploadTasksNodeRef.toString());


        if (isListening){
            /// if we are already listening, we need to remove the current listener from this object
            Timber.tag(TAG).d("   removing existing listner");
            this.uploadTasksCanceledListeningRef.removeEventListener(listener);
        }

        //// now recreate these objects
        this.uploadTasksCanceledListeningRef = uploadTasksCanceledListeningRef;

        /// add the new listener
        listener = new FirebaseUploadsCanceledListener(new FirebaseUploadsFailureOrPausedResponseHandler(driverDb, uploadTasksNodeRef));
        uploadTasksCanceledListeningRef.addChildEventListener(listener);
        isListening = true;
        Timber.tag(TAG).d("STARTED listening");
    }

    public void stopListening(){
        if (isListening) {
            uploadTasksCanceledListeningRef.removeEventListener(listener);
            isListening = false;
            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("...called stopListening when not listening...");
        }

    }

    public interface Update {
        void canceledUploadImageFile(String ownerGuid);
    }

}

