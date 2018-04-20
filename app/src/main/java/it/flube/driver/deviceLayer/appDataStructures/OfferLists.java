/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.appDataStructures;

import java.util.ArrayList;

import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.OffersInterface;

/**
 * Created on 10/19/2017
 * Project : Driver
 */

public class OfferLists implements OffersInterface {
    private static final String TAG = "OfferLists";

    private  ArrayList<Batch> publicOffers;
    private  ArrayList<Batch> personalOffers;
    private  ArrayList<Batch> demoOffers;
    private  ArrayList<Batch> scheduledBatches;

    public OfferLists() {
        publicOffers = new ArrayList<Batch>();
        personalOffers = new ArrayList<Batch>();
        demoOffers = new ArrayList<Batch>();
        scheduledBatches = new ArrayList<Batch>();
    }

    public ArrayList<Batch> getPublicOffers(){
        return publicOffers;
    }

    public void setPublicOffers(ArrayList<Batch> offerList){
        publicOffers.clear();
        publicOffers.addAll(offerList);
    }

    public ArrayList<Batch> getPersonalOffers(){
        return personalOffers;
    }

    public void setPersonalOffers(ArrayList<Batch> offerList){
        personalOffers.clear();
        personalOffers.addAll(offerList);
    }

    public ArrayList<Batch> getDemoOffers(){
        return demoOffers;
    }

    public void setDemoOffers(ArrayList<Batch> offerList){
        demoOffers.clear();
        demoOffers.addAll(offerList);
    }

    public ArrayList<Batch> getScheduledBatches(){
        return scheduledBatches;
    }

    public void setScheduledBatches(ArrayList<Batch> batchList){
        scheduledBatches.clear();
        scheduledBatches.addAll(batchList);
    }
}
