/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.batchDetail;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.libbatchdata.entities.FileUpload;
import timber.log.Timber;

/**
 * Created on 1/29/2019
 * Project : Driver
 */
public class FirebaseBatchDetailSetFileUpload implements
        OnCompleteListener<Void> {
    private static final String TAG = "FirebaseBatchDetailSetFileUpload";

    private static final String BATCH_DATA_BATCH_DETAIL_NODE = "batchDetail";
    private static final String BATCH_DATA_FILE_UPLOADS_NODE = "fileUploads";

    private Response response;

    public void fileUploadSetRequest(DatabaseReference batchDataRef, FileUpload fileUpload, Response response){
        Timber.tag(TAG).d("fileUploadSetRequest");
        Timber.tag(TAG).d("   batchDataRef   -> " + batchDataRef.toString());
        Timber.tag(TAG).d("   batchGuid      -> " + fileUpload.getBatchGuid());
        Timber.tag(TAG).d("   fileUploadGuid -> " + fileUpload.getGuid());

        this.response = response;

        batchDataRef.child(fileUpload.getBatchGuid()).child(BATCH_DATA_BATCH_DETAIL_NODE).child(BATCH_DATA_FILE_UPLOADS_NODE).child(fileUpload.getGuid()).setValue(fileUpload).addOnCompleteListener(this);

    }

    //// OnCompleteListener interface
    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        Timber.tag(TAG).d("COMPLETE");
        response.fileUploadSetComplete();
    }

    public interface Response {
        void  fileUploadSetComplete();
    }

}
