/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer;

import it.flube.driver.modelLayer.entities.Offer;

/**
 * Created on 7/23/2017
 * Project : Driver
 */

public class UseCaseOfferSelected implements Runnable {
    private final UseCaseOfferSelected.Response response;
    private final Offer offer;

    public UseCaseOfferSelected(Offer offer, UseCaseOfferSelected.Response response) {
        this.response = response;
        this.offer = offer;
    }

    public void run() {
        response.offerSelected(offer);
    }

    public interface Response {
        void offerSelected(Offer offer);
    }
}
