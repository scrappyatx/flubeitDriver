/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.HashMap;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ContactPerson {
    public enum ContactRole {
        CUSTOMER,
        SERVICE_PROVIDER,
        FLUBEIT_SUPPORT,
        DRIVER
    }

    private String guid;

    private String displayIconUrl;
    private String displayName;
    private String displayPhoneNumber;
    private String dialPhoneNumber;
    private String email;
    private ContactRole contactRole;

    private Boolean canVoice;
    private Boolean canSMS;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDisplayIconUrl() {
        return displayIconUrl;
    }

    public void setDisplayIconUrl(String displayIconUrl) {
        this.displayIconUrl = displayIconUrl;
    }

    public String getDisplayName(){
        return displayName;
    }

    public void setDisplayName(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayPhoneNumber(){
        return displayPhoneNumber;
    }

    public void setDisplayPhoneNumber(String displayPhoneNumber) {
        this.displayPhoneNumber = displayPhoneNumber;
    }

    public String getDialPhoneNumber() {
        return dialPhoneNumber;
    }

    public void setDialPhoneNumber(String dialPhoneNumber) {
        this.dialPhoneNumber = dialPhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactRole getContactRole(){
        return contactRole;
    }

    public void setContactRole(ContactRole contactRole){
        this.contactRole = contactRole;
    }

    public Boolean getCanVoice() {
        return canVoice;
    }

    public void setCanVoice(Boolean canVoice) {
        this.canVoice = canVoice;
    }

    public Boolean getCanSMS() {
        return canSMS;
    }

    public void setCanSMS(Boolean canSMS) {
        this.canSMS = canSMS;
    }
}
