/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.OffersInterface;

/**
 * Created on 10/19/2017
 * Project : Driver
 */

public class OfferLists implements OffersInterface {
    private static final String TAG = "OfferLists";

    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom

    private static class Loader {
        static volatile OfferLists instance = new OfferLists();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private OfferLists() {
        publicOffers = new ArrayList<Batch>();
        personalOffers = new ArrayList<Batch>();
        demoOffers = new ArrayList<Batch>();
        scheduledBatches = new ArrayList<Batch>();
    }

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static OfferLists getInstance() {
        return OfferLists.Loader.instance;
    }

    private ArrayList<Batch> publicOffers;
    private ArrayList<Batch> personalOffers;
    private ArrayList<Batch> demoOffers;
    private ArrayList<Batch> scheduledBatches;



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
