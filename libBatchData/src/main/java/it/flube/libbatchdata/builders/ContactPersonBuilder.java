/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.HashMap;

import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.ContactPerson;

import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_CUSTOMER_ICON_URL_DEMO;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_CUSTOMER_ICON_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_CUSTOMER_ICON_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_CUSTOMER_ICON_URL_STAGING;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_FLUBEIT_SUPPORT_ICON_URL_DEMO;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_FLUBEIT_SUPPORT_ICON_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_FLUBEIT_SUPPORT_ICON_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_FLUBEIT_SUPPORT_ICON_URL_STAGING;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_SERVICE_PROVIDER_ICON_URL_DEMO;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_SERVICE_PROVIDER_ICON_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_SERVICE_PROVIDER_ICON_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_SERVICE_PROVIDER_ICON_URL_STAGING;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_TARGET_ENVIRONMENT;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public class ContactPersonBuilder {
    private static final Boolean DEFAULT_CAN_VOICE = false;
    private static final Boolean DEFAULT_CAN_SMS = false;
    private static final Boolean DEFAULT_HAS_PROXY_PHONE_NUMBER = false;

    private ContactPerson contactPerson;

    private ContactPersonBuilder(Builder builder){
        this.contactPerson = builder.contactPerson;
    }

    private ContactPerson getContactPerson(){
        return this.contactPerson;
    }

    public static class Builder {
        private ContactPerson contactPerson;

        private String defaultCustomerIconUrl;
        private String defaultFlubeitSupportIconUrl;
        private String defaultServiceProviderIconUrl;

        ///public Builder(){
        ///    initializeStuff(DEFAULT_TARGET_ENVIRONMENT);
        ///}

        public Builder(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            initializeStuff(targetEnvironment);
        }

        private void initializeStuff(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            this.contactPerson = new ContactPerson();
            this.contactPerson.setGuid(BuilderUtilities.generateGuid());

            this.contactPerson.setCanVoice(DEFAULT_CAN_VOICE);
            this.contactPerson.setCanSMS(DEFAULT_CAN_SMS);
            this.contactPerson.setHasProxyPhoneNumber(DEFAULT_HAS_PROXY_PHONE_NUMBER);

            switch (targetEnvironment) {
                case PRODUCTION:
                    defaultCustomerIconUrl = DEFAULT_CUSTOMER_ICON_URL_PRODUCTION;
                    defaultFlubeitSupportIconUrl = DEFAULT_FLUBEIT_SUPPORT_ICON_URL_PRODUCTION;
                    defaultServiceProviderIconUrl = DEFAULT_SERVICE_PROVIDER_ICON_URL_PRODUCTION;
                    break;
                case DEMO:
                    defaultCustomerIconUrl = DEFAULT_CUSTOMER_ICON_URL_DEMO;
                    defaultFlubeitSupportIconUrl = DEFAULT_FLUBEIT_SUPPORT_ICON_URL_DEMO;
                    defaultServiceProviderIconUrl = DEFAULT_SERVICE_PROVIDER_ICON_URL_DEMO;
                    break;
                case STAGING:
                    defaultCustomerIconUrl = DEFAULT_CUSTOMER_ICON_URL_STAGING;
                    defaultFlubeitSupportIconUrl = DEFAULT_FLUBEIT_SUPPORT_ICON_URL_STAGING;
                    defaultServiceProviderIconUrl = DEFAULT_SERVICE_PROVIDER_ICON_URL_STAGING;
                    break;
                case DEVELOPMENT:
                    defaultCustomerIconUrl = DEFAULT_CUSTOMER_ICON_URL_DEVELOPMENT;
                    defaultFlubeitSupportIconUrl = DEFAULT_FLUBEIT_SUPPORT_ICON_URL_DEVELOPMENT;
                    defaultServiceProviderIconUrl = DEFAULT_SERVICE_PROVIDER_ICON_URL_DEVELOPMENT;
                    break;
            }
        }

        public Builder guid(String guid){
            this.contactPerson.setGuid(guid);
            return this;
        }

        public Builder displayName(String displayName){
            this.contactPerson.setDisplayName(displayName);
            return this;
        }

        public Builder displayPhoneNumber(String displayPhoneNumber){
            this.contactPerson.setDisplayPhoneNumber(displayPhoneNumber);
            return this;
        }

        public Builder dialPhoneNumber(String dialPhoneNumber){
            this.contactPerson.setDialPhoneNumber(dialPhoneNumber);
            return this;
        }

        public Builder email(String email){
            this.contactPerson.setEmail(email);
            return this;
        }

        public Builder hasProxyPhoneNumber(Boolean hasProxyPhoneNumber){
            this.contactPerson.setHasProxyPhoneNumber(hasProxyPhoneNumber);
            return this;
        }

        public Builder proxyDisplayPhoneNumber(String proxyDisplayPhoneNumber){
            this.contactPerson.setProxyDisplayPhoneNumber(proxyDisplayPhoneNumber);
            return this;
        }

        public Builder proxyDialPhoneNumber(String proxyDialPhoneNumber){
            this.contactPerson.setProxyDialPhoneNumber(proxyDialPhoneNumber);
            return this;
        }

        public Builder contactRole(ContactPerson.ContactRole contactRole){
            this.contactPerson.setContactRole(contactRole);

            // if displayIconUrl is null, then set default based on contact role
            if (this.contactPerson.getDisplayIconUrl() == null) {
                switch (contactRole) {
                    case CUSTOMER:
                        this.contactPerson.setDisplayIconUrl(defaultCustomerIconUrl);
                        break;
                    case FLUBEIT_SUPPORT:
                        this.contactPerson.setDisplayIconUrl(defaultFlubeitSupportIconUrl);
                        break;
                    case SERVICE_PROVIDER:
                        this.contactPerson.setDisplayIconUrl(defaultServiceProviderIconUrl);
                        break;
                }
            }
            return this;
        }

        public Builder displayIconUrl(String displayIconUrl){
            this.contactPerson.setDisplayIconUrl(displayIconUrl);
            return this;
        }

        public Builder canVoice(Boolean canVoice){
            this.contactPerson.setCanVoice(canVoice);
            return this;
        }

        public Builder canSMS(Boolean canSMS){
            this.contactPerson.setCanSMS(canSMS);
            return this;
        }

        private void validate(ContactPerson contactPerson){
            // required PRESENT (must not be null)
            if (contactPerson.getGuid() == null){
                throw new IllegalStateException("guid is null");
            }

            if (contactPerson.getDisplayName() == null){
                throw new IllegalStateException("display name is null");
            }

            ///if (contactPerson.getDisplayPhoneNumber() == null){
            ///    throw new IllegalStateException("display phone number is null");
            ///}

            if (contactPerson.getDialPhoneNumber() == null){
                ///throw new IllegalStateException("dial phone number is null");
            } else {
                if (!BuilderUtilities.isDialPhoneNumberFormattedProperly(contactPerson.getDialPhoneNumber())){
                    throw new IllegalStateException("dial phone number format is invalid");
                }
            }

            if (contactPerson.getContactRole() == null){
                throw new IllegalStateException("contact role is null");
            }

            /// have to have at least one way to contact - either voice or sms
            ///if (!(contactPerson.getCanVoice() || contactPerson.getCanSMS())){
            ///    throw new IllegalStateException("must have at least one way to contact - either voice or sms");
            ///}
        }

        public ContactPerson build(){
            ContactPerson contactPerson = new ContactPersonBuilder(this).getContactPerson();
            validate(contactPerson);
            return contactPerson;
        }
    }
}
