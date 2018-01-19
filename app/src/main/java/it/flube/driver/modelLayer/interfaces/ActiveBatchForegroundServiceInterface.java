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


    void startActiveBatchForegroundServiceRequest(String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType,
                                           StartActiveBatchForegroundServiceResponse response);

    interface StartActiveBatchForegroundServiceResponse {
        void activeBatchForegroundServiceStarted();
    }

    void updateActiveBatchForegroundServiceRequest(String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType,
                                            UpdateActiveBatchForegroundServiceResponse response);

    interface UpdateActiveBatchForegroundServiceResponse {
        void activeBatchForegroundServiceUpdated();
    }

    void stopActiveBatchForegroundServiceRequest(StopActiveBatchForegroundServiceResponse response);

    interface StopActiveBatchForegroundServiceResponse {
        void activeBatchForegroundServiceStopped();
    }
}
