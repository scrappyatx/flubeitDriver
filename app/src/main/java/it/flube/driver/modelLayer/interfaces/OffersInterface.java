/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.batch.Batch;

/**
 * Created on 10/19/2017
 * Project : Driver
 */

public interface OffersInterface {

    ArrayList<Batch> getPublicOffers();

    void setPublicOffers(ArrayList<Batch> offerList);

    ArrayList<Batch> getPersonalOffers();

    void setPersonalOffers(ArrayList<Batch> offerList);

    ArrayList<Batch> getDemoOffers();

    void setDemoOffers(ArrayList<Batch> offerList);

    ArrayList<Batch> getScheduledBatches();

    void setScheduledBatches(ArrayList<Batch> batchList);
}
