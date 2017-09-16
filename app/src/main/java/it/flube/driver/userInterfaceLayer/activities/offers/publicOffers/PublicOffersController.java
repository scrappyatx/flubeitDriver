/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.publicOffers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseOfferSelected;
import it.flube.driver.userInterfaceLayer.activities.offers.OffersListAdapter;
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

    public void offerSelected(Offer offer) {
        Timber.tag(TAG).d("offer Selected --> " + offer.getGUID());
        useCaseExecutor.execute(new UseCaseOfferSelected(offer, new OfferSelectedResponseHandler()));
    }

    public void close(){
        useCaseExecutor.shutdown();
    }

}

