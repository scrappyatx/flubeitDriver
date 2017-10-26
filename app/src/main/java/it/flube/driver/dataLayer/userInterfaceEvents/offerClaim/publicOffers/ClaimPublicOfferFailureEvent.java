/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.publicOffers;

/**
 * Created on 10/14/2017
 * Project : Driver
 */

public class ClaimPublicOfferFailureEvent {
    private String offerGuid;
    public ClaimPublicOfferFailureEvent(String offerGuid){
        this.offerGuid = offerGuid;
    }
    public String getOfferGuid(){ return offerGuid; }
}
