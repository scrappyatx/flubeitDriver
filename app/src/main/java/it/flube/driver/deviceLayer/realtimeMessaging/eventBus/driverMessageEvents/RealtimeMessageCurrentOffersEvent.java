/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.eventBus.driverMessageEvents;

import java.util.ArrayList;

import it.flube.driver.modelLayer.Offer;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class RealtimeMessageCurrentOffersEvent {
    private ArrayList<Offer> offerList;
    private int offerCount;

    public RealtimeMessageCurrentOffersEvent(ArrayList<Offer> offerList){
        this.offerList = offerList;
        this.offerCount = offerList.size();
    }

    public ArrayList<Offer> getCurrentOfferList() {
        return offerList;
    }

    public int getOfferCount() {
        return offerCount;
    }

}
