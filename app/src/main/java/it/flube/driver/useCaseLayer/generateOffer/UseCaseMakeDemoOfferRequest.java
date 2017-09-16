/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.generateOffer;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.DemoInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 9/6/2017
 * Project : Driver
 */

public class UseCaseMakeDemoOfferRequest implements
    Runnable,
    CloudDatabaseInterface.DeleteAllDemoOfferResponse,
    CloudDatabaseInterface.SaveDemoOfferResponse {

    private final DemoInterface demoMaker;
    private final Response response;

    private final String demoOfferNode;
    private final Driver driver;
    private final CloudDatabaseInterface cloudDb;


    public UseCaseMakeDemoOfferRequest(MobileDeviceInterface device, DemoInterface demoMaker, Response response){
        this.demoMaker = demoMaker;
        this.response = response;

        driver = device.getUser().getDriver();
        demoOfferNode = device.getAppRemoteConfig().getCloudDatabaseBaseNodeDemoOffers();
        cloudDb = device.getCloudDatabase();
    }

    public void run(){
        /// delete all existing demo offers
        cloudDb.deleteAllDemoOffersRequest(demoOfferNode, driver, this);
    }

    public void cloudDatabaseDemoOfferDeleteAllComplete(){
        /// create and save a demo offer
        Offer demoOffer = demoMaker.createDemoOffer(driver);
        cloudDb.saveDemoOfferRequest(demoOfferNode, driver, demoOffer, this);
    }

    public void cloudDatabaseDemoOfferSaveComplete(){
        response.makeDemoOfferComplete();
    }

    public interface Response {
        void makeDemoOfferComplete();
    }
}
