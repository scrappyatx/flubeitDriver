/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.messaging;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public interface RemoteServerMessagingInterface {
    //connect and disconnect to remote server
    public void connect();

    public void disconnect();

    //send messages to remote server
    public void sendMsgOnDuty(boolean dutyStatus);

    public void sendMsgRequestCurrentOffers();

    public void sendMsgClaimOfferRequest(String offerOID);

    public void sendMsgRequestAssignedBatches();

    public void sendMsgForfeitBatch(String batchOID);

    public void sendMsgBatchStart(String batchOID);

    public void sendMsgLocationUpdate(double latitude, double longitude);

    public void sendMsgArrivedToPickup(String batchOID);

    public void sendMsgDriverTakesVehicleFromCustomer(String batchOID);

    public void sendMsgArrivedToService(String batchOID);

    public void sendMsgServiceTakesVehicleFromDriver(String batchOID);

    public void sendMsgServiceStart(String batchOID);

    public void sendMsgServiceComplete(String batchOID);

    public void sendMsgDriverTakesVehicleFromService(String batchOID);

    public void sendMsgArrivedToDropOff(String batchOID);

    public void sendMsgOwnerTakesVehicleFromDriver(String batchOID);

}
