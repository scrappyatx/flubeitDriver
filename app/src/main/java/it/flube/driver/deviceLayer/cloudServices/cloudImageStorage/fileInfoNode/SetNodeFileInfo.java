/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FILE_INFO_NODE;

/**
 * Created on 1/21/2019
 * Project : Driver
 */
public class SetNodeFileInfo implements
        OnCompleteListener<Void> {

    public static final String TAG = "SetNodeFileInfo";

    private Response response;

    public void addNodeRequest(DatabaseReference uploadTaskNodeRef, FileToUploadInfo fileToUploadInfo, Response response){
        Timber.tag(TAG).d("addNodeRequest START...");

        this.response = response;

        Timber.tag(TAG).d("   ...uploadTaskNodeRef = " + uploadTaskNodeRef.toString());
        Timber.tag(TAG).d("   ...ownerGuid         = " + fileToUploadInfo.getOwnerGuid());

        uploadTaskNodeRef.child(RTD_FILE_INFO_NODE).child(fileToUploadInfo.getOwnerGuid()).setValue(fileToUploadInfo).addOnCompleteListener(this);
    }

    public void removeNodeRequest(DatabaseReference uploadTaskNodeRef, String ownerGuid, Response response){
        Timber.tag(TAG).d("removeNodeRequest START...");
        this.response = response;

        Timber.tag(TAG).d("   ...uploadTaskNodeRef = " + uploadTaskNodeRef.toString());
        Timber.tag(TAG).d("   ...ownerGuid         = " + ownerGuid);

        uploadTaskNodeRef.child(RTD_FILE_INFO_NODE).child(ownerGuid).setValue(null).addOnCompleteListener(this);
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
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        Timber.tag(TAG).d("COMPLETE");
        response.setNodeFileInfoComplete();
        close();
    }

    private void close(){
        Timber.tag(TAG).d("close");
        response = null;
    }

    public interface Response {
        void setNodeFileInfoComplete();
    }



}
