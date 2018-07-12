/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.HashMap;

import it.flube.libbatchdata.entities.ContactPerson;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public class ContactPersonBuilder {
    private static final String DEFAULT_CUSTOMER_ICON_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/contactPersonImages%2Fcustomer.png?alt=media&token=57dc7e80-9091-44ec-aa58-e4acaaa496f7";
    private static final String DEFAULT_FLUBEIT_SUPPORT_ICON_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/contactPersonImages%2Fflubeit_support.png?alt=media&token=5c4a0bf0-ac4b-486f-9ed5-1ea9f3381623";
    private static final String DEFAULT_SERVICE_PROVIDER_ICON_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/contactPersonImages%2Fservice_provider.png?alt=media&token=4bd7bb42-0e27-4bb1-8e54-5e03836b4ed0";

    private ContactPerson contactPerson;

    private ContactPersonBuilder(Builder builder){
        this.contactPerson = builder.contactPerson;
    }

    private ContactPerson getContactPerson(){
        return this.contactPerson;
    }

    public static class Builder {
        private ContactPerson contactPerson;

        public Builder(){
            this.contactPerson = new ContactPerson();
            this.contactPerson.setGuid(BuilderUtilities.generateGuid());

            this.contactPerson.setCanVoice(false);
            this.contactPerson.setCanSMS(false);
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

            //set the display phone number if the dial phone number is valid
            if (BuilderUtilities.isDialPhoneNumberFormattedProperly(dialPhoneNumber)){
                this.contactPerson.setDisplayPhoneNumber(BuilderUtilities.getFormattedDisplayPhoneNumber(dialPhoneNumber));
            }
            return this;
        }

        public Builder contactRole(ContactPerson.ContactRole contactRole){
            this.contactPerson.setContactRole(contactRole);

            // if displayIconUrl is null, then set default based on contact role
            if (this.contactPerson.getDisplayIconUrl() == null) {
                switch (contactRole) {
                    case CUSTOMER:
                        this.contactPerson.setDisplayIconUrl(DEFAULT_CUSTOMER_ICON_URL);
                        break;
                    case FLUBEIT_SUPPORT:
                        this.contactPerson.setDisplayIconUrl(DEFAULT_FLUBEIT_SUPPORT_ICON_URL);
                        break;
                    case SERVICE_PROVIDER:
                        this.contactPerson.setDisplayIconUrl(DEFAULT_SERVICE_PROVIDER_ICON_URL);
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
