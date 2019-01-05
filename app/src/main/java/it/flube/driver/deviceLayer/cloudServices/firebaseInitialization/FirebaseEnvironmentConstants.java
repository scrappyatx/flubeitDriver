/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.firebaseInitialization;

/**
 * Created on 8/28/2018
 * Project : Driver
 */
public class FirebaseEnvironmentConstants {
    //// TARGET ENVIRONMENT -> PRODUCTION
    public static final String DRIVER_DB_PROD = "https://flubeitdriver-prod.firebaseio.com/";
    public static final String USER_PROFILE_DB_PROD = "https://flubeitdriver-prod-userprofiles.firebaseio.com/";

    //// TARGET ENVIRONMENT -> DEMO
    public static final String DRIVER_DB_DEMO = "https://flubeitdriver-demo.firebaseio.com/";
    public static final String USER_PROFILE_DB_DEMO = "https://flubeitdriver-demo-userprofiles.firebaseio.com/";

    //// TARGET ENVIRONMENT -> STAGING
    public static final String DRIVER_DB_STAGING = "https://flubeitdriver-staging.firebaseio.com/";
    public static final String USER_PROFILE_DB_STAGING = "https://flubeitdriver-staging-userprofiles.firebaseio.com/";

    //// TARGET ENVIRONMENT -> DEVELOPMENT
    public static final String DRIVER_DB_DEV = "https://flubeitdriver.firebaseio.com/";
    public static final String USER_PROFILE_DB_DEV = "https://flubeitdriver-userprofiles.firebaseio.com/";
}
