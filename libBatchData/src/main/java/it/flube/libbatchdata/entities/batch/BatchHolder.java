/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.batch;

import java.util.Map;


import it.flube.libbatchdata.entities.ChatHistory;
import it.flube.libbatchdata.entities.ChatMessage;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.FileUpload;
import it.flube.libbatchdata.entities.MapPing;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.orderStep.StepId;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 9/19/2017
 * Project : Driver
 */

public class BatchHolder {
    private Batch batch;
    private BatchDetail batchDetail;

    private Map<String, RouteStop> routeStops;
    private Map<String, ServiceOrder> serviceOrders;

    private Map<String, ContactPerson> contactPersons;
    private Map<String, Map<String, ContactPerson>> contactPersonsByServiceOrder;

    private Map<String, StepId> stepIds;
    private Map<String, OrderStepInterface> steps;

    private Map<String, MapPing> mapPings;

    private Map<String, ChatHistory> driverChatHistories;
    private Map<String, ChatHistory> serviceProviderChatHistories;
    private Map<String, ChatHistory> customerChatHistories;
    private Map<String, ChatMessage> chatMessages;

    private Map<String, FileUpload> fileAttachments;


    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public BatchDetail getBatchDetail() {
        return batchDetail;
    }

    public void setBatchDetail(BatchDetail batchDetail) {
        this.batchDetail = batchDetail;
    }

    public Map<String, RouteStop> getRouteStops() {
        return routeStops;
    }

    public void setRouteStops(Map<String, RouteStop> routeStops) {
        this.routeStops = routeStops;
    }

    public Map<String, ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }

    public void setServiceOrders(Map<String, ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }

    public Map<String, ContactPerson> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(Map<String, ContactPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public Map<String, Map<String, ContactPerson>> getContactPersonsByServiceOrder() {
        return contactPersonsByServiceOrder;
    }

    public void setContactPersonsByServiceOrder(Map<String, Map<String, ContactPerson>> contactPersonsByServiceOrder) {
        this.contactPersonsByServiceOrder = contactPersonsByServiceOrder;
    }

    public Map<String, StepId> getStepIds() {
        return stepIds;
    }

    public void setStepIds(Map<String, StepId> stepIds) {
        this.stepIds = stepIds;
    }

    public Map<String, OrderStepInterface> getSteps() {
        return steps;
    }

    public void setSteps(Map<String, OrderStepInterface> steps) {
        this.steps = steps;
    }

    public Map<String, MapPing> getMapPings() {
        return mapPings;
    }

    public void setMapPings(Map<String, MapPing> mapPings) {
        this.mapPings = mapPings;
    }

    public Map<String, ChatHistory> getDriverChatHistories() {
        return driverChatHistories;
    }

    public void setDriverChatHistories(Map<String, ChatHistory> driverChatHistories) {
        this.driverChatHistories = driverChatHistories;
    }

    public Map<String, ChatHistory> getServiceProviderChatHistories() {
        return serviceProviderChatHistories;
    }

    public void setServiceProviderChatHistories(Map<String, ChatHistory> serviceProviderChatHistories) {
        this.serviceProviderChatHistories = serviceProviderChatHistories;
    }

    public Map<String, ChatHistory> getCustomerChatHistories() {
        return customerChatHistories;
    }

    public void setCustomerChatHistories(Map<String, ChatHistory> customerChatHistories) {
        this.customerChatHistories = customerChatHistories;
    }

    public Map<String, ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(Map<String, ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public Map<String, FileUpload> getFileAttachments() {
        return fileAttachments;
    }

    public void setFileAttachments(Map<String, FileUpload> fileAttachments) {
        this.fileAttachments = fileAttachments;
    }
}
