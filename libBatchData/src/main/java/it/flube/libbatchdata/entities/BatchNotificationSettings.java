/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

/**
 * Created on 1/8/2019
 * Project : Driver
 */
public class BatchNotificationSettings {
    // notifications when order is placed by customer
    private Boolean sendEmailToCustomerWithOrderConfirmation;

    // notifications to driver when this batch is available as an offer
    private Boolean sendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer;
    private Boolean sendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer;

    // notifications when offer expires without being claimed
    private Boolean sendTextToAdminWhenOfferExpires;

    // notifications to driver prior to batch starting
    private int upcomingScheduledBatchWarningMinutes;                                   // normally 30 minutes prior to scheduled batch start time
    private Boolean sendAppNotificationToDriverUpcomingScheduledBatch;                  // send early warning by in-app notification
    private Boolean sendTextToDriverUpcomingScheduledBatch;                             // send early warning by SMS text message

    // notifications to driver for batch late start
    private int lateWarningTextScheduledBatchSeconds;                                   // normally send 120 seconds after batch scheduled start time
    private int lateWarningVoiceCallScheduledBatchSeconds;                              // normally call 180 seconds after batch scheduled start time
    private Boolean sendTextToDriverLateStartWarningScheduledBatch;
    private Boolean voiceCallToDriverLateStartWarningScheduledBatch;

    // notifications to send when batch is removed from driver due to NO START
    private Boolean sendTextToAdminWhenScheduledBatchRemovedDueToNoStart;

    public Boolean getSendEmailToCustomerWithOrderConfirmation() {
        return sendEmailToCustomerWithOrderConfirmation;
    }

    public void setSendEmailToCustomerWithOrderConfirmation(Boolean sendEmailToCustomerWithOrderConfirmation) {
        this.sendEmailToCustomerWithOrderConfirmation = sendEmailToCustomerWithOrderConfirmation;
    }

    public Boolean getSendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer() {
        return sendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer;
    }

    public void setSendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer(Boolean sendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer) {
        this.sendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer = sendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer;
    }

    public Boolean getSendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer() {
        return sendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer;
    }

    public void setSendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer(Boolean sendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer) {
        this.sendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer = sendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer;
    }

    public Boolean getSendTextToAdminWhenOfferExpires() {
        return sendTextToAdminWhenOfferExpires;
    }

    public void setSendTextToAdminWhenOfferExpires(Boolean sendTextToAdminWhenOfferExpires) {
        this.sendTextToAdminWhenOfferExpires = sendTextToAdminWhenOfferExpires;
    }

    public int getUpcomingScheduledBatchWarningMinutes() {
        return upcomingScheduledBatchWarningMinutes;
    }

    public void setUpcomingScheduledBatchWarningMinutes(int upcomingScheduledBatchWarningMinutes) {
        this.upcomingScheduledBatchWarningMinutes = upcomingScheduledBatchWarningMinutes;
    }

    public Boolean getSendAppNotificationToDriverUpcomingScheduledBatch() {
        return sendAppNotificationToDriverUpcomingScheduledBatch;
    }

    public void setSendAppNotificationToDriverUpcomingScheduledBatch(Boolean sendAppNotificationToDriverEarlyWarningUpcomingScheduledBatch) {
        this.sendAppNotificationToDriverUpcomingScheduledBatch = sendAppNotificationToDriverEarlyWarningUpcomingScheduledBatch;
    }

    public Boolean getSendTextToDriverUpcomingScheduledBatch() {
        return sendTextToDriverUpcomingScheduledBatch;
    }

    public void setSendTextToDriverUpcomingScheduledBatch(Boolean sendTextToDriverEarlyWarningUpcomingScheduledBatch) {
        this.sendTextToDriverUpcomingScheduledBatch = sendTextToDriverEarlyWarningUpcomingScheduledBatch;
    }

    public int getLateWarningTextScheduledBatchSeconds() {
        return lateWarningTextScheduledBatchSeconds;
    }

    public void setLateWarningTextScheduledBatchSeconds(int lateWarningTextScheduledBatchSeconds) {
        this.lateWarningTextScheduledBatchSeconds = lateWarningTextScheduledBatchSeconds;
    }

    public int getLateWarningVoiceCallScheduledBatchSeconds() {
        return lateWarningVoiceCallScheduledBatchSeconds;
    }

    public void setLateWarningVoiceCallScheduledBatchSeconds(int lateWarningVoiceCallScheduledBatchSeconds) {
        this.lateWarningVoiceCallScheduledBatchSeconds = lateWarningVoiceCallScheduledBatchSeconds;
    }

    public Boolean getSendTextToDriverLateStartWarningScheduledBatch() {
        return sendTextToDriverLateStartWarningScheduledBatch;
    }

    public void setSendTextToDriverLateStartWarningScheduledBatch(Boolean sendTextToDriverLateStartWarningScheduledBatch) {
        this.sendTextToDriverLateStartWarningScheduledBatch = sendTextToDriverLateStartWarningScheduledBatch;
    }

    public Boolean getVoiceCallToDriverLateStartWarningScheduledBatch() {
        return voiceCallToDriverLateStartWarningScheduledBatch;
    }

    public void setVoiceCallToDriverLateStartWarningScheduledBatch(Boolean voiceCallToDriverLateStartWarningScheduledBatch) {
        this.voiceCallToDriverLateStartWarningScheduledBatch = voiceCallToDriverLateStartWarningScheduledBatch;
    }

    public Boolean getSendTextToAdminWhenScheduledBatchRemovedDueToNoStart() {
        return sendTextToAdminWhenScheduledBatchRemovedDueToNoStart;
    }

    public void setSendTextToAdminWhenScheduledBatchRemovedDueToNoStart(Boolean sendTextToAdminWhenScheduledBatchRemovedDueToNoStart) {
        this.sendTextToAdminWhenScheduledBatchRemovedDueToNoStart = sendTextToAdminWhenScheduledBatchRemovedDueToNoStart;
    }
}
