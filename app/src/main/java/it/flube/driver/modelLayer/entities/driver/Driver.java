/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.driver;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class Driver {

    private String clientId;
    private String email;
    private String photoUrl;
    private Boolean accessEnabled;

    private NameSettings nameSettings;
    private UserRoles userRoles;
    private CloudDatabaseSettings cloudDatabaseSettings;

    public Boolean getAccessEnabled() {
        return accessEnabled;
    }

    public void setAccessEnabled(Boolean accessEnabled) {
        this.accessEnabled = accessEnabled;
    }

    public NameSettings getNameSettings() {
        return nameSettings;
    }

    public void setNameSettings(NameSettings nameSettings) {
        this.nameSettings = nameSettings;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public UserRoles getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }

    public CloudDatabaseSettings getCloudDatabaseSettings() {
        return cloudDatabaseSettings;
    }

    public void setCloudDatabaseSettings(CloudDatabaseSettings cloudDatabaseSettings) {
        this.cloudDatabaseSettings = cloudDatabaseSettings;
    }
}
