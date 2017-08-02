/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

/**
 * Created on 7/25/2017
 * Project : Driver
 */

public class BatchCloudDB {
    private String mBatchState;
    private String mBatchStatus;
    private Customer mCustomer;
    private Driver mDriver;
    private String offerOID;
    private String orderOID;
    private Location pickupLocation;
    private Location returnLocation;


    public BatchCloudDB(){}

    public String getBatchState(){ return mBatchState; }
    public String getBatchStatus(){ return mBatchStatus; }
    public Driver getDriver() { return mDriver; }
    public Customer getCustomer() { return mCustomer; }
    public String getOfferOID() { return offerOID; }
    public String getOrderOID(){ return orderOID; }
    public Location getPickupLocation() { return pickupLocation; }
    public Location getReturnLocation() { return returnLocation; }

}
