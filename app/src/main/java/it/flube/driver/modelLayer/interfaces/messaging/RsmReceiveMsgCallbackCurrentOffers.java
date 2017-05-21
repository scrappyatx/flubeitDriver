/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.messaging;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Offer;

/**
 * Created on 5/18/2017
 * Project : Driver
 */

public interface RsmReceiveMsgCallbackCurrentOffers {
    void receiveMsgCurrentOffers(ArrayList<Offer> offerList);
}
