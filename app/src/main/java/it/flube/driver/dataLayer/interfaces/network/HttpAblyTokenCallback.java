/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.interfaces.network;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/10/2017
 * Project : Driver
 */

public interface HttpAblyTokenCallback {
    void requestAblyTokenSuccess(String ablyToken);

    void requestAblyTokenFailure(String responseMessage);
}
