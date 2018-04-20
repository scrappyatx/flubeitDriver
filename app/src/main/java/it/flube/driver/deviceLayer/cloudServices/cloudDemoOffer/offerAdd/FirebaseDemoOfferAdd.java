/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offerAdd;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import timber.log.Timber;

/**
 * Created on 3/29/2018
 * Project : Driver
 */
public class FirebaseDemoOfferAdd implements
    FirebaseBatchDataSaveBlob.Response,
    FirebaseDemoOffersAddToOfferList.Response {

    private static final String TAG = "FirebaseDemoOfferAdd";

    private DatabaseReference demoOffersRef;
    private DatabaseReference batchDataRef;
    private String batchGuid;

    private CloudDemoOfferInterface.AddDemoOfferResponse response;

    public void addOffer(DatabaseReference demoOffersRef, DatabaseReference batchDataRef, BatchHolder batchHolder, CloudDemoOfferInterface.AddDemoOfferResponse response){

        this.demoOffersRef = demoOffersRef;
        this.batchDataRef = batchDataRef;
        this.batchGuid = batchHolder.getBatch().getGuid();
        this.response = response;

        Timber.tag(TAG).d("demoOffersRef  = " + demoOffersRef.toString());
        Timber.tag(TAG).d("batchDataRef   = " + batchDataRef.toString());
        Timber.tag(TAG).d("batchGuid      = " + batchGuid);

        // 1 - save all batchData data to batchDataNode
        // 2 - add batchGuid to demo offer list
        new FirebaseBatchDataSaveBlob().saveDemoBatchDataRequest(batchDataRef, batchHolder, this);
    }

    public void saveDemoBatchDataComplete() {
        Timber.tag(TAG).d("   ...saveDemoBatchDataComplete");
        new FirebaseDemoOffersAddToOfferList().addDemoOfferToOfferListRequest(demoOffersRef, batchGuid, this);
    }

    public void demoOfferAddToOfferListComplete(){
        Timber.tag(TAG).d("   ...demoOfferAddToOfferListComplete");
        response.cloudAddDemoOfferComplete();
    }


}
