/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.serviceOrder;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.ChatMessage;
import it.flube.driver.modelLayer.entities.LatLonPosition;
import it.flube.driver.modelLayer.entities.MapPing;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrder {
    private String orderGUID;
    private String orderTitle;
    private String orderDescription;

    private Integer orderStepIndex;
    private ArrayList<ServiceOrderAbstractStep> orderSteps;

    private ArrayList<MapPing> mapPings;
    private ArrayList<ChatMessage> driverChatHistory;
    private ArrayList<ChatMessage> serviceProviderChatHistory;
    private ArrayList<ChatMessage> customerChatHistory;

    public String getOrderGUID() {
        return orderGUID;
    }

    public void setOrderGUID(String orderGUID) {
        this.orderGUID = orderGUID;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public Integer getOrderStepIndex() {
        return orderStepIndex;
    }

    public void setOrderStepIndex(Integer currentStep) {
        this.orderStepIndex = orderStepIndex;
    }

    public ArrayList<ServiceOrderAbstractStep> getOrderSteps() {
        return orderSteps;
    }

    public void setOrderSteps(ArrayList<ServiceOrderAbstractStep> orderSteps) {
        this.orderSteps = orderSteps;
    }

    public ArrayList<MapPing> getMapPings() {
        return mapPings;
    }

    public void setMapPings(ArrayList<MapPing> mapPings) {
        this.mapPings = mapPings;
    }

    public ArrayList<ChatMessage> getDriverChatHistory() {
        return driverChatHistory;
    }

    public void setDriverChatHistory(ArrayList<ChatMessage> driverChatHistory) {
        this.driverChatHistory = driverChatHistory;
    }

    public ArrayList<ChatMessage> getServiceProviderChatHistory() {
        return serviceProviderChatHistory;
    }

    public void setServiceProviderChatHistory(ArrayList<ChatMessage> serviceProviderChatHistory) {
        this.serviceProviderChatHistory = serviceProviderChatHistory;
    }

    public ArrayList<ChatMessage> getCustomerChatHistory() {
        return customerChatHistory;
    }

    public void setCustomerChatHistory(ArrayList<ChatMessage> customerChatHistory) {
        this.customerChatHistory = customerChatHistory;
    }
}
