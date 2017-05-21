/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.eventBus.messagingEvents.driverMessages;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Offer;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ReceivedCurrentOffersMessage {
    private ArrayList<Offer> mOfferList;

    public ReceivedCurrentOffersMessage(ArrayList<Offer> offerList){
        mOfferList = offerList;
    }

    public ArrayList<Offer> getCurrentOfferList() {
        return mOfferList;
    }
}
