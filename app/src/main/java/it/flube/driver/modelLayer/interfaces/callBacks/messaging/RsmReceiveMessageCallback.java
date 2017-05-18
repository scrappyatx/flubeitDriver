/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.callBacks.messaging;

import java.util.ArrayList;

import io.ably.lib.types.Message;
import it.flube.driver.modelLayer.entities.Offer;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public interface RsmReceiveMessageCallback {

    //callback to receive messages from remote server
    void receiveMsgCurrentOffers(ArrayList<Offer> offerList);

    void receiveMsgClaimOfferResult(String offerOID, String clientId);

    void receiveMsgBatchNotification(String batchOid, String batchMessage);

    void receiveMsgBatchRemoval(String batchOid, String batchMessage);

}
