/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.content.Context;

/**
 * Created on 11/7/2017
 * Project : Driver
 */

public interface ActiveBatchForegroundServiceInterface {


    void startActiveBatchForegroundService(String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType);

    void updateActiveBatchForegroundService(String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType);

    void stopActiveBatchForegroundService();
}
