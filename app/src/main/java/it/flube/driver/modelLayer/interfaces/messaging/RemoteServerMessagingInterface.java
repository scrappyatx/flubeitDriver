/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.messaging;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public interface RemoteServerMessagingInterface {

    public void sendMsgOnDuty(boolean dutyStatus);

    public void sendMsgRequestCurrentOffers();

    public void sendMsgClaimOfferRequest(String offerOID);

    public void sendMsgRequestAssignedBatches();

    public void sendMsgForfeitBatch(String batchOID);

    public void sendMsgBatchStart(String batchOID);

    public void sendMsgLocationUpdate();

    public void sendMsgArrivedToPickup();

    public void sendMsgDriverTakesVehicle();

    public void sendMsgArrivedToService();

    public void sendMsgServiceTakesVehicle();

    public void sendMsgServiceStart();

    public void sendMsgServiceComplete();

    public void sendMsgDriverTakesCarFromService();

    public void sendMsgArrivedToDropOff();

    public void sendMsgOwnerTakesVehicle();

}
