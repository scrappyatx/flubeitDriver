/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging;

import it.flube.driver.useCaseLayer.interfaces.RealtimeMessagingInterface;

/**
 * Created on 7/8/2017
 * Project : Driver
 */

public class RealtimeActiveBatchMessages implements RealtimeMessagingInterface.ActiveBatchMessages {

    public void connect() {

    }

    public void disconnect() {

    }

    public void sendMsgBatchStart(String batchOID) {

    }

    public void sendMsgLocationUpdate(double latitude, double longitude) {

    }

    public void sendMsgArrivedToPickup(String batchOID) {

    }

    public void sendMsgDriverTakesVehicleFromCustomer(String batchOID) {

    }

    public void sendMsgArrivedToService(String batchOID) {

    }

    public void sendMsgServiceTakesVehicleFromDriver(String batchOID) {

    }

    public void sendMsgServiceStart(String batchOID) {

    }

    public void sendMsgServiceComplete(String batchOID) {

    }

    public void sendMsgDriverTakesVehicleFromService(String batchOID) {

    }

    public void sendMsgArrivedToDropOff(String batchOID) {

    }

    public void sendMsgOwnerTakesVehicleFromDriver(String batchOID) {

    }


}
