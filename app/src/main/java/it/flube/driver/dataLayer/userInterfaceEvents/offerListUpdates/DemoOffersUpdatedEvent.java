/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.batch.Batch;

/**
 * Created on 10/14/2017
 * Project : Driver
 */

public class DemoOffersUpdatedEvent {
    private ArrayList<Batch> offerList;
    private int offerCount;

    public DemoOffersUpdatedEvent(ArrayList<Batch> offerList){
        this.offerList = new ArrayList<Batch>();
        this.offerList.addAll(offerList);
        this.offerCount = offerList.size();
    }

    public ArrayList<Batch> getOfferList() {
        return offerList;
    }

    public int getOfferCount() {
            return offerCount;
        }
}
