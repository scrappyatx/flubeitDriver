/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.ContactPerson;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public class ContactPersonBuilder {
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

        }

        public Builder displayName(String displayName){
            this.contactPerson.setDisplayName(displayName);
            return this;
        }

        public Builder displayPhoneNumber(String displayPhoneNumber){
            this.contactPerson.setDisplayPhoneNumber(displayPhoneNumber);
            return this;
        }

        public Builder contactRole(ContactPerson.ContactRole contactRole){
            this.contactPerson.setContactRole(contactRole);
            return this;
        }

        private void validate(ContactPerson contactPerson){
            // required PRESENT (must not be null)
            if (contactPerson.getDisplayName() == null){
                throw new IllegalStateException("display name is null");
            }

            if (contactPerson.getDisplayPhoneNumber() == null){
                throw new IllegalStateException("display phone number is null");
            }

            if (contactPerson.getContactRole() == null){
                throw new IllegalStateException("contact role is null");
            }
        }

        public ContactPerson build(){
            ContactPerson contactPerson = new ContactPersonBuilder(this).getContactPerson();
            validate(contactPerson);
            return contactPerson;
        }
    }
}
