/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.personalOffers;

/**
 * Created on 10/14/2017
 * Project : Driver
 */

public class ClaimPersonalOfferFailureEvent {
    private String offerGuid;
    public ClaimPersonalOfferFailureEvent(String offerGuid){
        this.offerGuid = offerGuid;
    }
    public String getOfferGuid(){ return offerGuid; }
}
