/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

/**
 * Created on 1/8/2019
 * Project : Driver
 */
public class ServiceOrderNotificationSettings {

    // notifications when order is started
    private Boolean sendTextToServiceProviderWhenOrderStarted;
    private Boolean sendEmailToServiceProviderWhenOrderStarted;
    private Boolean sendVoiceCallToServiceProviderWhenOrderStarted;

    //notifications when driver is navigating to customer
    private Boolean sendTextToCustomerWhenDriverNavigatingToTheirLocation;
    private Boolean sendEmailToCustomerWhenDriverNavigatingToTheirLocation;


    // notifications to send if an order step is late
    private int stepLateMinutes;            // how far behind schedule before a step is considered late, typically 20 minutes
    private int stepVeryLateMinutes;        // how far behind schedule before a step is considered very late, typically 30 minutes

    private Boolean sendAppNotificationToDriverWhenStepIsLate;
    private Boolean sendTextToDriverWhenStepIsLate;
    private Boolean voiceCallDriverWhenStepIsVeryLate;

    private Boolean sendTextToAdminWhenStepIsLate;
    private Boolean sendTextToAdminWhenStepIsVeryLate;


    public Boolean getSendTextToServiceProviderWhenOrderStarted() {
        return sendTextToServiceProviderWhenOrderStarted;
    }

    public void setSendTextToServiceProviderWhenOrderStarted(Boolean sendTextToServiceProviderWhenOrderStarted) {
        this.sendTextToServiceProviderWhenOrderStarted = sendTextToServiceProviderWhenOrderStarted;
    }

    public Boolean getSendEmailToServiceProviderWhenOrderStarted() {
        return sendEmailToServiceProviderWhenOrderStarted;
    }

    public void setSendEmailToServiceProviderWhenOrderStarted(Boolean sendEmailToServiceProviderWhenOrderStarted) {
        this.sendEmailToServiceProviderWhenOrderStarted = sendEmailToServiceProviderWhenOrderStarted;
    }

    public Boolean getSendVoiceCallToServiceProviderWhenOrderStarted() {
        return sendVoiceCallToServiceProviderWhenOrderStarted;
    }

    public void setSendVoiceCallToServiceProviderWhenOrderStarted(Boolean sendVoiceCallToServiceProviderWhenOrderStarted) {
        this.sendVoiceCallToServiceProviderWhenOrderStarted = sendVoiceCallToServiceProviderWhenOrderStarted;
    }

    public Boolean getSendTextToCustomerWhenDriverNavigatingToTheirLocation() {
        return sendTextToCustomerWhenDriverNavigatingToTheirLocation;
    }

    public void setSendTextToCustomerWhenDriverNavigatingToTheirLocation(Boolean sendTextToCustomerWhenDriverNavigatingToTheirLocation) {
        this.sendTextToCustomerWhenDriverNavigatingToTheirLocation = sendTextToCustomerWhenDriverNavigatingToTheirLocation;
    }

    public Boolean getSendEmailToCustomerWhenDriverNavigatingToTheirLocation() {
        return sendEmailToCustomerWhenDriverNavigatingToTheirLocation;
    }

    public void setSendEmailToCustomerWhenDriverNavigatingToTheirLocation(Boolean sendEmailToCustomerWhenDriverNavigatingToTheirLocation) {
        this.sendEmailToCustomerWhenDriverNavigatingToTheirLocation = sendEmailToCustomerWhenDriverNavigatingToTheirLocation;
    }

    public int getStepLateMinutes() {
        return stepLateMinutes;
    }

    public void setStepLateMinutes(int stepLateMinutes) {
        this.stepLateMinutes = stepLateMinutes;
    }

    public int getStepVeryLateMinutes() {
        return stepVeryLateMinutes;
    }

    public void setStepVeryLateMinutes(int stepVeryLateMinutes) {
        this.stepVeryLateMinutes = stepVeryLateMinutes;
    }

    public Boolean getSendAppNotificationToDriverWhenStepIsLate() {
        return sendAppNotificationToDriverWhenStepIsLate;
    }

    public void setSendAppNotificationToDriverWhenStepIsLate(Boolean sendAppNotificationToDriverWhenStepIsLate) {
        this.sendAppNotificationToDriverWhenStepIsLate = sendAppNotificationToDriverWhenStepIsLate;
    }

    public Boolean getSendTextToDriverWhenStepIsLate() {
        return sendTextToDriverWhenStepIsLate;
    }

    public void setSendTextToDriverWhenStepIsLate(Boolean sendTextToDriverWhenStepIsLate) {
        this.sendTextToDriverWhenStepIsLate = sendTextToDriverWhenStepIsLate;
    }

    public Boolean getVoiceCallDriverWhenStepIsVeryLate() {
        return voiceCallDriverWhenStepIsVeryLate;
    }

    public void setVoiceCallDriverWhenStepIsVeryLate(Boolean voiceCallDriverWhenStepIsVeryLate) {
        this.voiceCallDriverWhenStepIsVeryLate = voiceCallDriverWhenStepIsVeryLate;
    }

    public Boolean getSendTextToAdminWhenStepIsLate() {
        return sendTextToAdminWhenStepIsLate;
    }

    public void setSendTextToAdminWhenStepIsLate(Boolean sendTextToAdminWhenStepIsLate) {
        this.sendTextToAdminWhenStepIsLate = sendTextToAdminWhenStepIsLate;
    }

    public Boolean getSendTextToAdminWhenStepIsVeryLate() {
        return sendTextToAdminWhenStepIsVeryLate;
    }

    public void setSendTextToAdminWhenStepIsVeryLate(Boolean sendTextToAdminWhenStepIsVeryLate) {
        this.sendTextToAdminWhenStepIsVeryLate = sendTextToAdminWhenStepIsVeryLate;
    }
}
