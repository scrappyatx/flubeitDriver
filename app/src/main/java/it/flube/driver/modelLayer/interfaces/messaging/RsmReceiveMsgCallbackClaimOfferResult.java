/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.messaging;

/**
 * Created on 5/18/2017
 * Project : Driver
 */

public interface RsmReceiveMsgCallbackClaimOfferResult {
    void receiveMsgClaimOfferResult(String offerOID, String clientId);
}
