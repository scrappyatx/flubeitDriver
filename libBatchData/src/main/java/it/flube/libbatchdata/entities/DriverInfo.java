/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.HashMap;

/**
 * Created on 8/29/2018
 * Project : Driver
 */
public class DriverInfo {
    private String clientId;
    private String photoUrl;
    private String displayName;
    private String proxyDialNumber;
    private String proxyDisplayNumber;
    private String proxyParticipantSid;

    private HashMap<String, String> proxySessionIdMap;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProxyDialNumber() {
        return proxyDialNumber;
    }

    public void setProxyDialNumber(String proxyDialNumber) {
        this.proxyDialNumber = proxyDialNumber;
    }

    public String getProxyDisplayNumber() {
        return proxyDisplayNumber;
    }

    public void setProxyDisplayNumber(String proxyDisplayNumber) {
        this.proxyDisplayNumber = proxyDisplayNumber;
    }

    public HashMap<String, String> getProxySessionIdMap() {
        return proxySessionIdMap;
    }

    public void setProxySessionIdMap(HashMap<String, String> proxySessionIdMap) {
        this.proxySessionIdMap = proxySessionIdMap;
    }

    public String getProxyParticipantSid() {
        return proxyParticipantSid;
    }

    public void setProxyParticipantSid(String proxyParticipantSid) {
        this.proxyParticipantSid = proxyParticipantSid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
