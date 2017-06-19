/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

import java.util.ArrayList;

/**
 * Created on 6/13/2017
 * Project : Driver
 */

public class OfferListSingleton {

    ///
    ///  Loader class provides synchronization across threads
    ///  Lazy initialization since Loader class is only called when "getInstance" is called
    ///  volatile keyword guarantees visibility of changes to variables across threads
    ///
    private static class Loader {
        static volatile OfferListSingleton mInstance = new OfferListSingleton();
        static volatile ArrayList<Offer> mOfferList = new ArrayList<Offer>();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private OfferListSingleton(){}

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static OfferListSingleton getInstance() {
        return Loader.mInstance;
    }

    ///
    ///  all class variables are static
    ///


    public ArrayList<Offer> getOfferList() { return Loader.mOfferList; }

}
