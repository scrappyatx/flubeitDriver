/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.personalOffers;

/**
 * Created on 10/14/2017
 * Project : Driver
 */

public class ClaimPersonalOfferSuccessEvent {
    private String offerGuid;
    public ClaimPersonalOfferSuccessEvent(String offerGuid){
        this.offerGuid = offerGuid;
    }
    public String getOfferGuid(){ return offerGuid; }
}
