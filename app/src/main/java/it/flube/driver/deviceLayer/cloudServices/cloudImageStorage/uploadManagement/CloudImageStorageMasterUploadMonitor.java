/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsCanceledMonitor.FirebaseUploadsCanceledMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsFailedMonitor.FirebaseUploadsFailedMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsFinishedMonitor.FirebaseUploadsFinishedMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsNotStartedMonitor.FirebaseUploadsNotStartedMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsPausedMonitor.FirebaseUploadsPausedMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsPermanentlyFailedMonitor.FirebasePermanentlyFailedMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsSuccessMonitor.FirebaseUploadsSuccessMonitor;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_UPLOAD_TASKS_CANCELED_LISTENER_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_UPLOAD_TASKS_FAILED_LISTENER_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_UPLOAD_TASKS_FINISHED_LISTENER_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_UPLOAD_TASKS_NOT_STARTED_LISTENER_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_UPLOAD_TASKS_PATH;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_UPLOAD_TASKS_PAUSED_LISTENER_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_UPLOAD_TASKS_PERMANENTLY_FAILED_LISTENER_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_UPLOAD_TASKS_SUCCESS_LISTENER_NODE;

/**
 * Created on 1/23/2019
 * Project : Driver
 */
public class CloudImageStorageMasterUploadMonitor {
    public static final String TAG = "CloudImageStorageMasterUploadMonitor";

    private FirebaseUploadsNotStartedMonitor notStartedMonitor;
    private FirebaseUploadsPausedMonitor pausedMonitor;
    private FirebaseUploadsFailedMonitor failedMonitor;
    private FirebaseUploadsSuccessMonitor successMonitor;
    private FirebasePermanentlyFailedMonitor permanentlyFailedMonitor;
    private FirebaseUploadsFinishedMonitor finishedMonitor;
    private FirebaseUploadsCanceledMonitor canceledMonitor;

    public CloudImageStorageMasterUploadMonitor(){
        notStartedMonitor = new FirebaseUploadsNotStartedMonitor();
        pausedMonitor = new FirebaseUploadsPausedMonitor();
        failedMonitor = new FirebaseUploadsFailedMonitor();
        finishedMonitor = new FirebaseUploadsFinishedMonitor();
        permanentlyFailedMonitor = new FirebasePermanentlyFailedMonitor();
        successMonitor = new FirebaseUploadsSuccessMonitor();
        canceledMonitor = new FirebaseUploadsCanceledMonitor();
    }

    public void startListening(FirebaseDatabase driverDb, Driver driver, String clientId, String deviceGuid, String batchGuid,
                               CloudActiveBatchInterface cloudActiveBatch, DeviceImageStorageInterface deviceImageStorage){
        Timber.tag(TAG).d("startListening");
        Timber.tag(TAG).d("   clientId      -> " + clientId);
        Timber.tag(TAG).d("   deviceGuid    -> " + deviceGuid);
        Timber.tag(TAG).d("   batchGuid     -> " + batchGuid);

        /// get upload tasks node
        String uploadTasksNode = String.format(RTD_UPLOAD_TASKS_PATH, clientId, deviceGuid, batchGuid);
        Timber.tag(TAG).d("   uploadTasksNode                    -> " + uploadTasksNode);

        /// start listening on the "not started" node
        Timber.tag(TAG).d("   ...start listening on 'not started' node");
        String uploadTasksNotStartedListeningNode = String.format(RTD_UPLOAD_TASKS_NOT_STARTED_LISTENER_NODE, clientId, deviceGuid, batchGuid);
        Timber.tag(TAG).d("          uploadTasksNotStartedListeningNode -> " + uploadTasksNotStartedListeningNode);
        notStartedMonitor.startListening(driverDb, driverDb.getReference(uploadTasksNotStartedListeningNode), driverDb.getReference(uploadTasksNode));

        // start listening on the "paused"
        Timber.tag(TAG).d("   ...start listening on 'paused' node");
        String uploadTasksPausedListeningNode = String.format(RTD_UPLOAD_TASKS_PAUSED_LISTENER_NODE, clientId, deviceGuid, batchGuid);
        Timber.tag(TAG).d("          uploadTasksPausedListeningNode -> " + uploadTasksPausedListeningNode);
        pausedMonitor.startListening(driverDb, driverDb.getReference(uploadTasksPausedListeningNode), driverDb.getReference(uploadTasksNode));

        // start listening on the "failed" node
        Timber.tag(TAG).d("   ...start listening on 'failed' node");
        String uploadTasksFailedListeningNode = String.format(RTD_UPLOAD_TASKS_FAILED_LISTENER_NODE, clientId, deviceGuid, batchGuid);
        Timber.tag(TAG).d("          uploadTasksFailedListeningNode -> " + uploadTasksFailedListeningNode);
        failedMonitor.startListening(driverDb, driverDb.getReference(uploadTasksFailedListeningNode), driverDb.getReference(uploadTasksNode));

        // start listening on the "finished" node
        Timber.tag(TAG).d("   ...start listening on 'finished' node");
        String uploadTasksFinishedListeningNode = String.format(RTD_UPLOAD_TASKS_FINISHED_LISTENER_NODE, clientId, deviceGuid, batchGuid);
        Timber.tag(TAG).d("          uploadTasksFinishedListeningNode -> " + uploadTasksFinishedListeningNode);
        finishedMonitor.startListening(driverDb, driverDb.getReference(uploadTasksFinishedListeningNode), driverDb.getReference(uploadTasksNode), driver, cloudActiveBatch, deviceImageStorage);

        // start listening on the "permanently failed" node
        Timber.tag(TAG).d("   ...start listening on 'permanently failed' node");
        String uploadTasksPermanentlyFailedListeningNode = String.format(RTD_UPLOAD_TASKS_PERMANENTLY_FAILED_LISTENER_NODE, clientId, deviceGuid, batchGuid);
        Timber.tag(TAG).d("          uploadTasksPermanentlyFailedListeningNode -> " + uploadTasksPermanentlyFailedListeningNode);
        permanentlyFailedMonitor.startListening(driverDb.getReference(uploadTasksPermanentlyFailedListeningNode), driverDb.getReference(uploadTasksNode));

        //start listening on "success" node
        Timber.tag(TAG).d("   ...start listening on 'succes' node");
        String uploadTasksSuccessListeningNode = String.format(RTD_UPLOAD_TASKS_SUCCESS_LISTENER_NODE, clientId, deviceGuid, batchGuid);
        Timber.tag(TAG).d("          uploadTasksSuccessListeningNode -> " + uploadTasksSuccessListeningNode);
        successMonitor.startListening(driverDb.getReference(uploadTasksSuccessListeningNode), driverDb.getReference(uploadTasksNode));

        //start listening on "canceled" node
        Timber.tag(TAG).d("   ...start listening on 'canceled' node");
        String uploadTasksCanceledNode = String.format(RTD_UPLOAD_TASKS_CANCELED_LISTENER_NODE, clientId, deviceGuid, batchGuid);
        Timber.tag(TAG).d("          uploadTasksCanceledNode -> " + uploadTasksCanceledNode);
        canceledMonitor.startListening(driverDb, driverDb.getReference(uploadTasksCanceledNode), driverDb.getReference(uploadTasksNode));
    }

    public void stopListening(){
        Timber.tag(TAG).d("stopListening");

        /// stop listening on the "not started node
        notStartedMonitor.stopListening();
        pausedMonitor.stopListening();
        failedMonitor.stopListening();
        finishedMonitor.stopListening();
        permanentlyFailedMonitor.stopListening();
    }

}
