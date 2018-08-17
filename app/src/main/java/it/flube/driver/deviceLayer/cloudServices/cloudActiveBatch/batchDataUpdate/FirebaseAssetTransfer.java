/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.ASSET_TRANSFER_STATUS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_ASSET_LIST_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_STEPS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.SIGNATURE_REQUEST_HAS_DEVICE_FILE_NODE;

/**
 * Created on 8/10/2018
 * Project : Driver
 */
public class FirebaseAssetTransfer implements OnCompleteListener<Void> {
    private static final String TAG="FirebaseAssetTransfer";

    private CloudActiveBatchInterface.UpdateAssetTransferResponse response;

    public void updateAssetTransferRequest(DatabaseReference batchDataNode, String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer, CloudActiveBatchInterface.UpdateAssetTransferResponse response){
        Timber.tag(TAG).d("updateAssetTransferRequest START...");

        this.response = response;
        Timber.tag(TAG).d("   batchDataNode    = " + batchDataNode.toString());
        Timber.tag(TAG).d("   batchGuid        = " + batchGuid);
        Timber.tag(TAG).d("   serviceOrderGuid = " + serviceOrderGuid);
        Timber.tag(TAG).d("   stepGuid         = " + stepGuid);
        Timber.tag(TAG).d("   assetGuid        = " + assetTransfer.getAsset().getGuid());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(ASSET_TRANSFER_STATUS_NODE, assetTransfer.getTransferStatus());

        batchDataNode.child(batchGuid).child(BATCH_DATA_STEPS_NODE).child(stepGuid).child(BATCH_DATA_ASSET_LIST_NODE).child(assetTransfer.getAsset().getGuid()).updateChildren(data).addOnCompleteListener(this);
    }

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
        response.cloudActiveBatchUpdateAssetTransferComplete();
    }
}
