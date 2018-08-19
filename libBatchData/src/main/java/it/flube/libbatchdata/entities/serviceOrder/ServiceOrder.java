/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.serviceOrder;

import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.Timestamp;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrder {
    public enum ServiceOrderStatus {
        NOT_STARTED,
        ACTIVE,
        PAUSED,
        COMPLETED
    }

    private String guid;
    private String batchGuid;
    private String batchDetailGuid;

    private String title;
    private String description;

    private Integer sequence;
    private Integer totalSteps;
    private ServiceOrderStatus status;

    private Timestamp startTime;
    private Timestamp finishTime;

    private ContactPerson customerContactPerson;
    private ContactPerson serviceProviderContactPerson;

    public String getBatchGuid() {
        return batchGuid;
    }

    public void setBatchGuid(String batchGuid) {
        this.batchGuid = batchGuid;
    }

    public String getBatchDetailGuid() {
        return batchDetailGuid;
    }

    public void setBatchDetailGuid(String batchDetailGuid) {
        this.batchDetailGuid = batchDetailGuid;
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(Integer totalSteps) {
        this.totalSteps = totalSteps;
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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public ContactPerson getCustomerContactPerson() {
        return customerContactPerson;
    }

    public void setCustomerContactPerson(ContactPerson customerContactPerson) {
        this.customerContactPerson = customerContactPerson;
    }

    public ContactPerson getServiceProviderContactPerson() {
        return serviceProviderContactPerson;
    }

    public void setServiceProviderContactPerson(ContactPerson serviceProviderContactPerson) {
        this.serviceProviderContactPerson = serviceProviderContactPerson;
    }
}
