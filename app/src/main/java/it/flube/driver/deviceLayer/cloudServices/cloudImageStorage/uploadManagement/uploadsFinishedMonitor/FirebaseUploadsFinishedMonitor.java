/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsFinishedMonitor;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers.FirebaseUploadsFinishedResponseHandler;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 1/23/2019
 * Project : Driver
 */
public class FirebaseUploadsFinishedMonitor {
    public final String TAG = "FirebaseUploadsFinishedMonitor";

    private DatabaseReference uploadTasksFinishedListeningRef;
    private FirebaseUploadsFinishedListener listener;
    private Boolean isListening;

    public FirebaseUploadsFinishedMonitor(){
        isListening = false;
    }

    public void startListening(FirebaseDatabase driverDb, DatabaseReference uploadTasksFinishedListeningRef, DatabaseReference uploadTasksNodeRef,
                               Driver driver, CloudActiveBatchInterface cloudActiveBatch, DeviceImageStorageInterface deviceImageStorage) {

        Timber.tag(TAG).d("startListening");
        Timber.tag(TAG).d("   uploadTasksFinishedListeningRef = " + uploadTasksFinishedListeningRef.toString());
        Timber.tag(TAG).d("   uploadTasksNodeRef = " + uploadTasksNodeRef.toString());


        if (isListening) {
            /// if we are already listening, we need to remove the current listener from this object
            Timber.tag(TAG).d("   removing existing listener");
            this.uploadTasksFinishedListeningRef.removeEventListener(listener);
        }

        //// now recreate these objects
        this.uploadTasksFinishedListeningRef = uploadTasksFinishedListeningRef;

        /// add the new listener
        listener = new FirebaseUploadsFinishedListener(new FirebaseUploadsFinishedResponseHandler(driverDb, uploadTasksNodeRef, driver, cloudActiveBatch, deviceImageStorage));
        uploadTasksFinishedListeningRef.addChildEventListener(listener);
        isListening = true;
        Timber.tag(TAG).d("STARTED listening");
    }

    public void stopListening() {
        if (isListening) {
            uploadTasksFinishedListeningRef.removeEventListener(listener);
            isListening = false;
            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("...called stopListening when not listening...");
        }

    }

    public interface Update {
        void finishedUploadImageFile(String ownerGuid);
    }
}
