/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseOfferSelected;
import timber.log.Timber;

/**
 * Created on 7/23/2017
 * Project : Driver
 */

public class OfferSelectedResponseHandler implements UseCaseOfferSelected.Response {
    private final static String TAG = "OfferSelectedResponseHandler";

    public void offerSelectedSuccess(BatchDetail batchDetail) {
        Timber.tag(TAG).d("offer Selected");
        EventBus.getDefault().postSticky(new OfferSelectedResponseHandler.UseCaseOfferSelectedEvent(batchDetail));
    }

    public void offerSelectedFailure(){
        //TODO post an event for an alert to tell user that "no detail is available for this offer
        // should never happen
    }

    public static class UseCaseOfferSelectedEvent {
        private BatchDetail batchDetail;
        public UseCaseOfferSelectedEvent(BatchDetail batchDetail){
            this.batchDetail = batchDetail;
        }

        public BatchDetail getBatchDetail(){
            return batchDetail;
        }
    }
}
