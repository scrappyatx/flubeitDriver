/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.messaging.receivedMessageCallbacks;

/**
 * Created on 5/18/2017
 * Project : Driver
 */

public interface RsmReceiveMsgCallbackBatchNotification {
    void receiveMsgBatchNotification(String batchOid, String batchMessage);
}
