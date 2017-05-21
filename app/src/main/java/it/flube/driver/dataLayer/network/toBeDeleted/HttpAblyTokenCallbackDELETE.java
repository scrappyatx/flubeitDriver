/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.network.toBeDeleted;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/10/2017
 * Project : Driver
 */

public interface HttpAblyTokenCallbackDELETE {
    void requestAblyTokenSuccess(String ablyToken);

    void requestAblyTokenFailure(String responseMessage);
}
