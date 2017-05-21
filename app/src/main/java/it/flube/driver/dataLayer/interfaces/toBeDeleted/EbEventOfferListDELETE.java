/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.interfaces.toBeDeleted;

import it.flube.driver.modelLayer.entities.Offer;

import java.util.ArrayList;

/**
 * Created by Bryan on 4/11/2017.
 */

public class EbEventOfferListDELETE {
    private static ArrayList<Offer> offerList = new ArrayList<Offer>();

    public EbEventOfferListDELETE(ArrayList<Offer> offerList) {
        this.offerList = offerList;
    }

    public static ArrayList<Offer> getOfferList() {
        return offerList;
    }
}
