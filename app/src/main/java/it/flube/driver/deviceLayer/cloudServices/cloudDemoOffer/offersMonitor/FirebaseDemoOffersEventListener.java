/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offersMonitor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.batchDataGet.FirebaseDemoBatchSummaryGet;
import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.libbatchdata.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class FirebaseDemoOffersEventListener implements
        ValueEventListener,
        CloudDemoOfferInterface.GetBatchSummaryResponse {

    private static final String TAG = "FirebaseDemoOffersEventListener";
    private DatabaseReference batchDataRef;
    private CloudDemoOfferInterface.DemoOffersUpdated update;
    private ArrayList<Batch> offerList;
    private ResponseCounter responseCounter;

    public FirebaseDemoOffersEventListener(DatabaseReference batchDataRef, CloudDemoOfferInterface.DemoOffersUpdated update) {
        this.batchDataRef = batchDataRef;
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        this.update = update;
        offerList = new ArrayList<Batch>();
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");
        offerList.clear();

        if (dataSnapshot.exists()) {

            Long offerCount = dataSnapshot.getChildrenCount();
            Timber.tag(TAG).d("   ...dataSnapshot exists --> there are " + offerCount + " batch guids at this node");

            if (offerCount > 0) {

                Timber.tag(TAG).d("      ...getting batch summary data for each batch guid");
                responseCounter = new ResponseCounter(offerCount);
                FirebaseDemoBatchSummaryGet batchSummaryGet = new FirebaseDemoBatchSummaryGet();

                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    try {

                        String batchGuid = offerSnapshot.getKey();
                        Timber.tag(TAG).d("         ...looking for offer data for guid : " + batchGuid);
                        batchSummaryGet.getBatchSummary(batchDataRef, batchGuid, this);

                    } catch (Exception e) {
                        Timber.tag(TAG).w("         ...ERROR");
                        Timber.tag(TAG).e(e);
                    }
                }
            } else {
                //dataSnapshot has no children
                Timber.tag(TAG).d("      ...no batch guids, so no batch summary data to get");
                update.cloudDemoOffersUpdated(offerList);
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
            update.cloudDemoOffersUpdated(offerList);
        }
    }


    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error --> " + databaseError.getCode() + " --> " + databaseError.getMessage());
        update.cloudDemoOffersUpdated(offerList);
    }

    public void cloudGetDemoBatchSummarySuccess(Batch batch) {
        responseCounter.onResponse();
        offerList.add(batch);
        Timber.tag(TAG).d("            ...got batch data (batch node) for batch guid : " + batch.getGuid());
        checkIfAllResponseReceived();
    }

    public void cloudGetDemoBatchSummaryFailure(){
        responseCounter.onResponse();
        Timber.tag(TAG).w("            ...error while trying to get batch data (batch node)");
        checkIfAllResponseReceived();
    }

    private void checkIfAllResponseReceived(){
        if (responseCounter.isFinished()){
            Timber.tag(TAG).d("           ...response counter is finished");
            Timber.tag(TAG).d("offerList has " + offerList.size() + " offers");
            update.cloudDemoOffersUpdated(offerList);
        } else {
            Timber.tag(TAG).d("           ...response counter = " + responseCounter.getCount());
        }
    }

}
