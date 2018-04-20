/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers;

/**
 * Created on 4/10/2018
 * Project : Driver
 */
public class OfferConstants {

    public enum OfferType {
        PERSONAL,
        PUBLIC,
        DEMO
    }

    public static final Integer MAX_OFFERS = 20;

    public static final String CLAIM_OFFER_RESULT_KEY = "claimOfferResult";
    public static final String CLAIM_OFFER_SUCCESS_VALUE = "success";
    public static final String CLAIM_OFFER_FAILURE_VALUE = "failure";
    public static final String CLAIM_OFFER_TIMEOUT_VALUE = "timeout";

    public static final String MAKE_OFFER_RESULT_KEY = "makeOfferResult";
    public static final String MAKE_OFFER_SUCCESS_VALUE = "success";
    public static final String MAKE_OFFER_FAILURE_VALUE = "failure";

    public static final String OFFER_TYPE_KEY = "offerType";
    public static final String BATCH_GUID_KEY = "batchGuid";
    public static final String BATCH_TYPE_KEY = "batchType";

}
