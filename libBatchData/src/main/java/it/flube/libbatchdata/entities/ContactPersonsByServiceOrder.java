/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 7/5/2018
 * Project : Driver
 */
public class ContactPersonsByServiceOrder {
    private HashMap<String, HashMap<String, ContactPerson>> contactPersonsByServiceOrder;

    public HashMap<String, HashMap<String, ContactPerson>> getContactPersonsByServiceOrder() {
        return contactPersonsByServiceOrder;
    }

    public void setContactPersonsByServiceOrder(HashMap<String, HashMap<String, ContactPerson>> contactPersonsByServiceOrder) {
        this.contactPersonsByServiceOrder = contactPersonsByServiceOrder;
    }
}
