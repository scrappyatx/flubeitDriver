/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ContactPerson {
    public enum ContactRole {
        CUSTOMER,
        SERVICE_PROVIDER
    }

    private String displayName;
    private String displayPhoneNumber;
    private ContactRole contactRole;

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

    public ContactRole getContactRole(){
        return contactRole;
    }

    public void setContactRole(ContactRole contactRole){
        this.contactRole = contactRole;
    }

}