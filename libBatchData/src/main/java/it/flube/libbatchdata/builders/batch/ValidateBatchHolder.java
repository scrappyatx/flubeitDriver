/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.batch;

import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.batch.BatchHolder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 7/5/2018
 * Project : Driver
 */
public class ValidateBatchHolder {
    private static final Integer MAX_SERVICE_ORDERS_PER_BATCH = 5;
    private static final Integer MAX_CUSTOMER_CONTACT_PER_SERVICE_ORDER = 1;
    private static final Integer MAX_SERVICE_PROVIDER_CONTACT_PER_SERVICE_ORDER = 1;

    public static Boolean isServiceOrderCountValid(BatchHolder batchHolder) {
        // make sure we have no more than MAX_SERVICE_ORDERS_PER_BATCH
        return (batchHolder.getServiceOrders().size() <= MAX_SERVICE_ORDERS_PER_BATCH);
    }

    public static Boolean isContactPersonCountValid(BatchHolder batchHolder){

        ///make sure we have no more than MAX_CUSTOMER_CONTACT_PER_SERVICE_ORDER customer ContactPerson per service order
        ///make sure we have no more than MAX_SERVICE_PROVIDER_CONTACT_PER_SERVICE_ORDER  service provider ContactPerson per service order
        Integer customerCount;
        Integer serviceProviderCount;
        Boolean valid = true;  //assume the batch is valid, until we prove otherwise

        // iterate through each service order
        for (Map.Entry<String, Map<String, ContactPerson>> thisOrder : batchHolder.getContactPersonsByServiceOrder().entrySet()) {

            //set our ContactPerson counts to zero for this order
            customerCount = 0;
            serviceProviderCount = 0;

            // iterate through all ContactPersons for this order, get a count of each type
            for (Map.Entry<String, ContactPerson> thisContact : thisOrder.getValue().entrySet()) {
                switch (thisContact.getValue().getContactRole()){
                    case SERVICE_PROVIDER:
                        serviceProviderCount = serviceProviderCount + 1;
                        break;
                    case CUSTOMER:
                        customerCount = customerCount + 1;
                        break;
                }
            }

            // check to see that we haven't exceeded the count limits
            if (customerCount > MAX_CUSTOMER_CONTACT_PER_SERVICE_ORDER) {
                valid = false;
            }

            if (serviceProviderCount > MAX_SERVICE_PROVIDER_CONTACT_PER_SERVICE_ORDER) {
                valid = false;
            }
        }

        return valid;
    }
}
