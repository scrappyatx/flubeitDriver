/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.offerClaimRequest;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 2/24/2018
 * Project : Driver
 */

public class OfferClaimRequest implements
        OnCompleteListener<Void> {

    private static final String TAG = "OfferClaimRequest";

    private static final String BATCH_NODE = "batchGuid";
    private static final String BATCH_TYPE_NODE = "batchType";
    private static final String CLIENT_NODE = "clientId";
    private static final String TIMESTAMP_NODE = "timestamp";

    private CloudDatabaseInterface.ClaimOfferResponse response;

    public void claimOfferRequest(DatabaseReference claimOfferRequestRef, String clientId, String batchGuid, BatchDetail.BatchType batchType,
                                  CloudDatabaseInterface.ClaimOfferResponse response){

        Timber.tag(TAG).d("claimOfferRequest START...");
        this.response = response;

        // claim offer request ref = /userWriteable/claimOfferRequest
        Timber.tag(TAG).d("   ...claimOfferRequestRef = " + claimOfferRequestRef.toString());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(BATCH_NODE, batchGuid);
        data.put(BATCH_TYPE_NODE, batchType.toString());
        data.put(CLIENT_NODE, clientId);
        data.put(TIMESTAMP_NODE, ServerValue.TIMESTAMP);
        Timber.tag(TAG).d("   ...building hash map");

        claimOfferRequestRef.child(batchGuid).setValue(data).addOnCompleteListener(this);
        Timber.tag(TAG).d("   ...setting value");
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
        response.cloudDatabaseClaimOfferRequestComplete();
    }

}
