/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.serviceOrder;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.ChatMessage;
import it.flube.driver.modelLayer.entities.MapPing;
import it.flube.driver.modelLayer.entities.Timestamp;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrder {
    public enum ServiceOrderStatus {
        NOT_STARTED,
        ACTIVE,
        COMPLETED_SUCCESS,
        COMPLETED_PROBLEM,
    }

    private String guid;
    private String title;
    private String description;

    private ServiceOrderStatus status;

    private Timestamp startTime;
    private Timestamp finishTime;

    private Integer stepIndex;
    private ArrayList<ServiceOrderAbstractStep> stepList;

    private ArrayList<MapPing> mapPingList;
    private ArrayList<ChatMessage> driverChatHistory;
    private ArrayList<ChatMessage> serviceProviderChatHistory;
    private ArrayList<ChatMessage> customerChatHistory;

    public String getGUID() {
        return guid;
    }

    public void setGUID(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceOrderStatus getStatus(){
        return status;
    }

    public void setStatus(ServiceOrderStatus status) {
        this.status = status;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(Integer stepIndex) {
        this.stepIndex = stepIndex;
    }

    public ArrayList<ServiceOrderAbstractStep> getStepList() {
        return stepList;
    }

    public void setStepList(ArrayList<ServiceOrderAbstractStep> stepList) {
        this.stepList = stepList;
    }

    public ArrayList<MapPing> getMapPingList() {
        return mapPingList;
    }

    public void setMapPingList (ArrayList<MapPing> mapPingList) {
        this.mapPingList = mapPingList;
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
