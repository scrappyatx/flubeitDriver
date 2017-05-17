/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging;

import it.flube.driver.dataLayer.interfaces.messaging.AblyChannelCallback;
import it.flube.driver.dataLayer.interfaces.messaging.AblyConnectionCallback;
import it.flube.driver.modelLayer.interfaces.messaging.RemoteServerMessagingInterface;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class RemoteServerMessaging implements RemoteServerMessagingInterface {
    private final String TAG = "RemoteServerMessaging";

    private AblyRealtimeSingleton mAblyRealtime;
    private AblyChannel mLookingForOffers;
    private AblyChannel mBatchActivity;

    public RemoteServerMessaging() {
        mAblyRealtime = AblyRealtimeSingleton.getInstance();

    }


    // messages that can be sent

    public void sendMsgOnDuty(boolean dutyStatus) {

    }

    public void sendMsgRequestCurrentOffers() {

    }

    public void sendMsgClaimOfferRequest(String offerOID) {

    }

    public void sendMsgRequestAssignedBatches() {

    }

    public void sendMsgForfeitBatch(String batchOID) {

    }

    public void sendMsgBatchStart(String batchOID) {

    }

    public void sendMsgLocationUpdate() {

    }

    public void sendMsgArrivedToPickup() {

    }

    public void sendMsgDriverTakesVehicle() {

    }

    public void sendMsgArrivedToService() {

    }

    public void sendMsgServiceTakesVehicle() {

    }

    public void sendMsgServiceStart() {

    }

    public void sendMsgServiceComplete() {

    }

    public void sendMsgDriverTakesCarFromService() {

    }

    public void sendMsgArrivedToDropOff() {

    }

    public void sendMsgOwnerTakesVehicle() {

    }


}
