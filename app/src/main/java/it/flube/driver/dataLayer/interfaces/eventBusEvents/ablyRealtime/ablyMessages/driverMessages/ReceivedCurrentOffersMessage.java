/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.driverMessages;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Offer;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ReceivedCurrentOffersMessage {
    private static ArrayList<Offer> mOfferList;

    public ReceivedCurrentOffersMessage(ArrayList<Offer> offerList){
        mOfferList = offerList;
    }

    public static ArrayList<Offer> getCurrentOfferList() {
        return mOfferList;
    }
}
