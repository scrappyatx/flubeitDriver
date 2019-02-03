/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 1/26/2019
 * Project : Driver
 */
public class FirebaseStorageWaitForAllUploadsToComplete implements
        OnCompleteListener<List<Task<?>>> {
    private static final String TAG = "FirebaseStorageWaitForAllUploadsToComplete";

    private CloudImageStorageInterface.WaitForAllPendingUploadsToFinishResponse response;

    public void waitForAllUploadsToComplete(CloudImageStorageInterface.WaitForAllPendingUploadsToFinishResponse response){
        Timber.tag(TAG).d("waitForAllUploadsToComplete");

        this.response = response;
        List<UploadTask> taskList = FirebaseStorage.getInstance().getReference().getActiveUploadTasks();
        Timber.tag(TAG).d("   active upload tasks -> " + taskList.size());
        Tasks.whenAllComplete(taskList).addOnCompleteListener(this);

    }


    /// interface for when all uploads complete
    public void onComplete(@NonNull Task<List<Task<?>>> tasks) {
        Timber.tag(TAG).d("onComplete");
        response.cloudImageStorageAllUploadsComplete();
    }

}
