/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseOfferSelected;
import timber.log.Timber;

/**
 * Created on 7/23/2017
 * Project : Driver
 */

public class OfferSelectedResponseHandler implements UseCaseOfferSelected.Response {
    private final static String TAG = "OfferSelectedResponseHandler";

    public void offerSelected(Offer offer) {
        Timber.tag(TAG).d("offer Selected");
        EventBus.getDefault().postSticky(new OfferSelectedResponseHandler.UseCaseOfferSelectedEvent(offer));
    }


    public static class UseCaseOfferSelectedEvent {
        private Offer offer;
        public UseCaseOfferSelectedEvent(Offer offer){
            this.offer = offer;
        }

        public Offer getOffer(){
            return offer;
        }
    }
}
