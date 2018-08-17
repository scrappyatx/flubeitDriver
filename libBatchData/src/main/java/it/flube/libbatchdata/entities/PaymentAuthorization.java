/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.Map;

/**
 * Created on 8/14/2018
 * Project : Driver
 */
public class PaymentAuthorization {

    public enum PaymentType {
        CHARGE_TO_ACCOUNT,
        CHARGE_TO_CARD_ON_FILE
    }

    public enum PaymentVerificationStatus {
        PAYMENT_AT_OR_BELOW_LIMIT,
        PAYMENT_EXCEEDS_LIMIT,
        NOT_VERIFIED
    }

    private String guid;
    private String batchGuid;
    private String batchDetailGuid;
    private String serviceOrderGuid;
    private String stepGuid;

    private PaymentType paymentType;
    private String displayChargeAccountId;
    private String displayMaskedCardOnFile;

    private Integer maxPaymentAmountCents;

    private Boolean verifyPaymentAmount;
    private PaymentVerificationStatus paymentVerificationStatus;
    private Map<String, String> verificationStatusIconText;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

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

    public String getServiceOrderGuid() {
        return serviceOrderGuid;
    }

    public void setServiceOrderGuid(String serviceOrderGuid) {
        this.serviceOrderGuid = serviceOrderGuid;
    }

    public String getStepGuid() {
        return stepGuid;
    }

    public void setStepGuid(String stepGuid) {
        this.stepGuid = stepGuid;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getMaxPaymentAmountCents() {
        return maxPaymentAmountCents;
    }

    public void setMaxPaymentAmountCents(Integer maxPaymentAmountCents) {
        this.maxPaymentAmountCents = maxPaymentAmountCents;
    }

    public String getDisplayChargeAccountId() {
        return displayChargeAccountId;
    }

    public void setDisplayChargeAccountId(String displayChargeAccountId) {
        this.displayChargeAccountId = displayChargeAccountId;
    }

    public String getDisplayMaskedCardOnFile() {
        return displayMaskedCardOnFile;
    }

    public void setDisplayMaskedCardOnFile(String displayMaskedCardOnFile) {
        this.displayMaskedCardOnFile = displayMaskedCardOnFile;
    }

    public Boolean getVerifyPaymentAmount() {
        return verifyPaymentAmount;
    }

    public void setVerifyPaymentAmount(Boolean verifyPaymentAmount) {
        this.verifyPaymentAmount = verifyPaymentAmount;
    }

    public PaymentVerificationStatus getPaymentVerificationStatus() {
        return paymentVerificationStatus;
    }

    public void setPaymentVerificationStatus(PaymentVerificationStatus paymentVerificationStatus) {
        this.paymentVerificationStatus = paymentVerificationStatus;
    }

    public Map<String, String> getVerificationStatusIconText() {
        return verificationStatusIconText;
    }

    public void setVerificationStatusIconText(Map<String, String> verificationStatusIconText) {
        this.verificationStatusIconText = verificationStatusIconText;
    }
}
