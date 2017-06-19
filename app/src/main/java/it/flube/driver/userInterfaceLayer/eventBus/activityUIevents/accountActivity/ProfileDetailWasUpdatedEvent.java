/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.accountActivity;

/**
 * Created on 6/4/2017
 * Project : Driver
 */

public class ProfileDetailWasUpdatedEvent {
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mRole;
    private String mClientId;

    public ProfileDetailWasUpdatedEvent(String firstName, String lastName, String email, String role, String clientId) {
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mClientId = clientId;
        mRole = role;
    }

    public String getFirstName() {
        return mFirstName;
    }
    public String getLastName() {
        return mLastName;
    }
    public String getEmail() {
        return mEmail;
    }
    public String getRole() { return mRole; }
    public String getClientId(){
        return mClientId;
    }
}
