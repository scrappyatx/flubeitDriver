/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FILE_INFO_NODE;

/**
 * Created on 1/22/2019
 * Project : Driver
 */
public class GetNodeFileInfo implements
        ValueEventListener {

    public static final String TAG = "GetNodeFileInfo";

    private Response response;

    public void getNodeFileInfo(DatabaseReference uploadTaskNodeRef, String ownerGuid, Response response){
        Timber.tag(TAG).d("getNodeFileInfo");
        Timber.tag(TAG).d("   uploadTaskNodeRef --> " + uploadTaskNodeRef.toString());
        Timber.tag(TAG).d("   ownerGuid         --> " + ownerGuid);
        this.response = response;
        uploadTaskNodeRef.child(RTD_FILE_INFO_NODE).child(ownerGuid).addListenerForSingleValueEvent(this);
    }

    public void onDataChange(@NonNull DataSnapshot dataSnapshot){
        Timber.tag(TAG).d("onDataChange...");
        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...dataSnapshot exists");
            try {
                FileToUploadInfo fileToUploadInfo = dataSnapshot.getValue(FileToUploadInfo.class);
                Timber.tag(TAG).d("      ...got FileToUploadInfo for owner guid : " + fileToUploadInfo.getOwnerGuid());
                response.getNodeFileInfoSuccess(fileToUploadInfo);
            } catch (Exception e) {
                Timber.tag(TAG).w("      ...ERROR");
                Timber.tag(TAG).e(e);
                response.getNodeFileInfoFailure();
            }
        } else {
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.getNodeFileInfoFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).w("onCancelled --> error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.getNodeFileInfoFailure();
    }

    public interface Response {
        void getNodeFileInfoSuccess(FileToUploadInfo fileToUploadInfo);

        void getNodeFileInfoFailure();
    }



}
