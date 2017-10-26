/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.demoOffers;

/**
 * Created on 10/14/2017
 * Project : Driver
 */

public class ClaimDemoOfferSuccessEvent {
    private String offerGUID;

    public ClaimDemoOfferSuccessEvent(String offerGUID){
        this.offerGUID = offerGUID;
    }
    public String getOfferGUID(){ return offerGUID; }
}
