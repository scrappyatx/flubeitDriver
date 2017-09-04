/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class Driver {
    private String firstName;
    private String lastName;
    private String displayName;
    private String clientId;
    private String email;
    private String photoUrl;

    private Boolean isDev;
    private Boolean isQA;
    private String publicOffersNode;
    private String personalOffersNode;
    private String demoOffersNode;
    private String scheduledBatchesNode;
    private String activeBatchNode;

    public Driver() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() { return displayName; }

    public void setDisplayName(String displayName) { this.displayName = displayName;}

    public String getPhotoUrl() { return photoUrl; }

    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl;}

    public void setIsDev(Boolean isDev) { this.isDev = isDev;}

    public Boolean isDev(){return isDev;}

    public void setIsQA(Boolean isQA){ this.isQA = isQA;}

    public Boolean isQA(){ return isQA;}

    public void setPublicOffersNode(String publicOffersNode) { this.publicOffersNode = publicOffersNode;}

    public String getPublicOffersNode() { return publicOffersNode;}

    public void setPersonalOffersNode(String personalOffersNode){ this.personalOffersNode = personalOffersNode;}

    public String getPersonalOffersNode(){ return personalOffersNode; }

    public void setDemoOffersNode(String demoOffersNode){ this.demoOffersNode = demoOffersNode;}

    public String getDemoOffersNode(){ return demoOffersNode;}

    public void setScheduledBatchesNode(String scheduledBatchesNode) { this.scheduledBatchesNode = scheduledBatchesNode;}

    public String getScheduledBatchesNode(){ return scheduledBatchesNode;}

    public void setActiveBatchNode(String activeBatchNode) { this.activeBatchNode = activeBatchNode;}

    public String getActiveBatchNode(){ return activeBatchNode;}

}
