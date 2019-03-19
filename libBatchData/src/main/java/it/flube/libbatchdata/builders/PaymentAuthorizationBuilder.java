/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.entities.PaymentAuthorization;
import it.flube.libbatchdata.utilities.BuilderUtilities;

/**
 * Created on 8/14/2018
 * Project : Driver
 */
public class PaymentAuthorizationBuilder {
    private static final PaymentAuthorization.PaymentType DEFAULT_PAYMENT_TYPE = PaymentAuthorization.PaymentType.CHARGE_TO_ACCOUNT;
    private static final String DEFAULT_DISPLAY_CHARGE_ACCOUNT_ID = "flube.it";
    private static final String DEFAULT_DISPLAY_MASKED_CARD_ON_FILE = "XXXX-XXXX-XXXX-2059";
    private static final PaymentAuthorization.PaymentVerificationStatus DEFAULT_PAYMENT_VERIFICATION_STATUS = PaymentAuthorization.PaymentVerificationStatus.NOT_VERIFIED;
    private static final Boolean DEFAULT_VERIFY_PAYMENT_AMOUNT = false;
    private static final Integer DEFAULT_MAX_PAYMENT_AMOUNT_IN_CENTS = 1000;   //$10.00

    private static final String PAYMENT_VERIFICATION_STATUS_NOT_VERIFIED_STRING = "{fa-ban}";
    private static final String PAYMENT_VERIFICATION_STATUS_PAYMENT_AT_OR_BELOW_LIMIT_STRING = "{fa-check-circle}";
    private static final String PAYMENT_VERIFICATION_STATUS_PAYMENT_EXCEEDS_LIMIT_STRING = "{fa-question-circle}";

    private PaymentAuthorization paymentAuthorization;

    private PaymentAuthorizationBuilder(Builder builder){
        this.paymentAuthorization = builder.paymentAuthorization;
    }

    public PaymentAuthorization getPaymentAuthorization(){
        return this.paymentAuthorization;
    }

    public static class Builder {
        private PaymentAuthorization paymentAuthorization;

        public Builder(){
            this.paymentAuthorization = new PaymentAuthorization();
            this.paymentAuthorization.setGuid(BuilderUtilities.generateGuid());

            this.paymentAuthorization.setPaymentType(DEFAULT_PAYMENT_TYPE);
            this.paymentAuthorization.setDisplayChargeAccountId(DEFAULT_DISPLAY_CHARGE_ACCOUNT_ID);
            this.paymentAuthorization.setDisplayMaskedCardOnFile(DEFAULT_DISPLAY_MASKED_CARD_ON_FILE);
            this.paymentAuthorization.setPaymentType(DEFAULT_PAYMENT_TYPE);
            this.paymentAuthorization.setVerifyPaymentAmount(DEFAULT_VERIFY_PAYMENT_AMOUNT);
            this.paymentAuthorization.setMaxPaymentAmountCents(DEFAULT_MAX_PAYMENT_AMOUNT_IN_CENTS);

            //payment verification status default
            this.paymentAuthorization.setPaymentVerificationStatus(DEFAULT_PAYMENT_VERIFICATION_STATUS);

            HashMap<String, String> verificationStatusIconTextMap = new HashMap<String, String>();
            verificationStatusIconTextMap.put(PaymentAuthorization.PaymentVerificationStatus.NOT_VERIFIED.toString(), PAYMENT_VERIFICATION_STATUS_NOT_VERIFIED_STRING);
            verificationStatusIconTextMap.put(PaymentAuthorization.PaymentVerificationStatus.PAYMENT_AT_OR_BELOW_LIMIT.toString(), PAYMENT_VERIFICATION_STATUS_PAYMENT_AT_OR_BELOW_LIMIT_STRING);
            verificationStatusIconTextMap.put(PaymentAuthorization.PaymentVerificationStatus.PAYMENT_EXCEEDS_LIMIT.toString(), PAYMENT_VERIFICATION_STATUS_PAYMENT_EXCEEDS_LIMIT_STRING);
            this.paymentAuthorization.setVerificationStatusIconText(verificationStatusIconTextMap);

        }

        public Builder guid(String guid){
            this.paymentAuthorization.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String batchGuid){
            this.paymentAuthorization.setBatchGuid(batchGuid);
            return this;
        }

        public Builder batchDetailGuid(String batchDetailGuid){
            this.paymentAuthorization.setBatchDetailGuid(batchDetailGuid);
            return this;
        }

        public Builder serviceOrderGuid(String serviceOrderGuid){
            this.paymentAuthorization.setServiceOrderGuid(serviceOrderGuid);
            return this;
        }

        public Builder stepGuid(String stepGuid){
            this.paymentAuthorization.setStepGuid(stepGuid);
            return this;
        }

        public Builder paymentType(PaymentAuthorization.PaymentType paymentType){
            this.paymentAuthorization.setPaymentType(paymentType);
            return this;
        }

        public Builder displayChargeAccountId(String displayChargeAccountId){
            this.paymentAuthorization.setDisplayChargeAccountId(displayChargeAccountId);
            return this;
        }

        public Builder displayMaskedCardOnFile(String displayMaskedCardOnFile){
            this.paymentAuthorization.setDisplayMaskedCardOnFile(displayMaskedCardOnFile);
            return this;
        }

        public Builder maxPaymentAmountCents(Integer maxPaymentAmountCents){
            this.paymentAuthorization.setMaxPaymentAmountCents(maxPaymentAmountCents);
            return this;
        }

        public Builder verifyPaymentAmount(Boolean verifyPaymentAmount){
            this.paymentAuthorization.setVerifyPaymentAmount(verifyPaymentAmount);
            return this;
        }

        public Builder paymentVerificationStatus(PaymentAuthorization.PaymentVerificationStatus paymentVerificationStatus){
            this.paymentAuthorization.setPaymentVerificationStatus(paymentVerificationStatus);
            return this;
        }

        public Builder verificationStatusIconText(Map<String, String> verificationStatusIconText){
            this.paymentAuthorization.setVerificationStatusIconText(verificationStatusIconText);
            return this;
        }

        private void validate(PaymentAuthorization paymentAuthorization){
            if (paymentAuthorization.getGuid() == null) {
                throw new IllegalStateException("GUID is null");
            }
        }

        public PaymentAuthorization build(){
            PaymentAuthorization paymentAuthorization = new PaymentAuthorizationBuilder(this).getPaymentAuthorization();
            validate(paymentAuthorization);
            return paymentAuthorization;
        }

    }

}
