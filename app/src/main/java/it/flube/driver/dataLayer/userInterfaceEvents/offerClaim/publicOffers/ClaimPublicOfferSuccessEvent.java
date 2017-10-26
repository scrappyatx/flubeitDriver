/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.publicOffers;

/**
 * Created on 10/14/2017
 * Project : Driver
 */

public class ClaimPublicOfferSuccessEvent {
    private String offerGuid;
    public ClaimPublicOfferSuccessEvent(String offerGuid){
        this.offerGuid = offerGuid;
    }
    public String getOfferGuid(){ return offerGuid; }
}
