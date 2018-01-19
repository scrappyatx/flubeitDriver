/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.publicOffers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.userInterfaceLayer.layoutComponents.offers.OffersListAdapter;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class PublicOffersController implements OffersListAdapter.Response {
    private final String TAG = "PublicOffersController";
    private ExecutorService useCaseExecutor;

    public PublicOffersController() {
        useCaseExecutor = Executors.newSingleThreadExecutor();
    }

    public void offerSelected(Batch offer) {
        Timber.tag(TAG).d("offer Selected --> " + offer.getGuid());
        //useCaseExecutor.execute(new UseCaseOfferSelected(offer, new OfferSelectedResponseHandler()));
    }

    public void close(){
        useCaseExecutor.shutdown();
    }

}

