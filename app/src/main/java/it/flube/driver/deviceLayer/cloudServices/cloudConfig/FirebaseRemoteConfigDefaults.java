/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudConfig;

import java.util.HashMap;
import java.util.Map;

import it.flube.libbatchdata.constants.TargetEnvironmentConstants;

import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_PRIVACY_URL_DEFAULT_DEMO;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_PRIVACY_URL_DEFAULT_DEV;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_PRIVACY_URL_DEFAULT_PROD;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_PRIVACY_URL_DEFAULT_STAGING;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_PRIVACY_URL_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_TERMS_URL_DEFAULT_DEMO;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_TERMS_URL_DEFAULT_DEV;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_TERMS_URL_DEFAULT_PROD;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_TERMS_URL_DEFAULT_STAGING;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.DRIVER_TERMS_URL_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_ACTIVE_BATCH;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_ACTIVE_BATCH_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_BATCH_DATA;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_BATCH_DATA_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_DEMO_OFFERS;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_DEMO_OFFERS_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_DEVICE_DATA;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_DEVICE_DATA_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_PERSONAL_OFFERS;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_PERSONAL_OFFERS_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_PUBLIC_OFFERS;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_PUBLIC_OFFERS_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_SCHEDULED_BATCHES;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_SCHEDULED_BATCHES_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_USER_DATA;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FIREBASE_DATABASE_BASE_NODE_USER_DATA_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_CAN_SMS_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_CAN_SMS_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_CAN_VOICE_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_CAN_VOICE_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_CONTACT_NUMBER_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_CONTACT_NUMBER_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_DEFAULT_DEMO;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_DEFAULT_DEV;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_DEFAULT_PROD;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_DEFAULT_STAGING;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_DISPLAY_NAME_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.FLUBE_IT_SUPPORT_DISPLAY_NAME_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.LOGGLY_DEBUG_ACTIVE_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.LOGGLY_DEBUG_ACTIVE_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.LOGGLY_RELEASE_ACTIVE_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.LOGGLY_RELEASE_ACTIVE_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.ROLLBAR_DEBUG_ACTIVE_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.ROLLBAR_DEBUG_ACTIVE_KEY;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.ROLLBAR_RELEASE_ACTIVE_DEFAULT;
import static it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigConstants.ROLLBAR_RELEASE_ACTIVE_KEY;

/**
 * Created on 7/9/2018
 * Project : Driver
 */
public class FirebaseRemoteConfigDefaults {
    private static final String TAG = "FirebaseRemoteConfigDefaults";

    public static Map<String, Object> getDefaults(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        HashMap<String, Object> defaults = new HashMap<String, Object>();


        //defaults for driver privacy & terms, flube.it support
        switch (targetEnvironment){
            case PRODUCTION:
                defaults.put(DRIVER_PRIVACY_URL_KEY, DRIVER_PRIVACY_URL_DEFAULT_PROD);
                defaults.put(DRIVER_TERMS_URL_KEY, DRIVER_TERMS_URL_DEFAULT_PROD);
                defaults.put(FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_KEY, FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_DEFAULT_PROD);
                break;
            case DEMO:
                defaults.put(DRIVER_PRIVACY_URL_KEY, DRIVER_PRIVACY_URL_DEFAULT_DEMO);
                defaults.put(DRIVER_TERMS_URL_KEY, DRIVER_TERMS_URL_DEFAULT_DEMO);
                defaults.put(FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_KEY, FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_DEFAULT_DEMO);
                break;
            case STAGING:
                defaults.put(DRIVER_PRIVACY_URL_KEY, DRIVER_PRIVACY_URL_DEFAULT_STAGING);
                defaults.put(DRIVER_TERMS_URL_KEY, DRIVER_TERMS_URL_DEFAULT_STAGING);
                defaults.put(FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_KEY, FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_DEFAULT_STAGING);
                break;
            case DEVELOPMENT:
                defaults.put(DRIVER_PRIVACY_URL_KEY, DRIVER_PRIVACY_URL_DEFAULT_DEV);
                defaults.put(DRIVER_TERMS_URL_KEY, DRIVER_TERMS_URL_DEFAULT_DEV);
                defaults.put(FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_KEY, FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_DEFAULT_DEV);
                break;
            default:
                defaults.put(DRIVER_PRIVACY_URL_KEY, DRIVER_PRIVACY_URL_DEFAULT_DEV);
                defaults.put(DRIVER_TERMS_URL_KEY, DRIVER_TERMS_URL_DEFAULT_DEV);
                defaults.put(FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_KEY, FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_DEFAULT_DEV);
                break;
        }
        defaults.put(FLUBE_IT_SUPPORT_DISPLAY_NAME_KEY, FLUBE_IT_SUPPORT_DISPLAY_NAME_DEFAULT);
        defaults.put(FLUBE_IT_SUPPORT_CONTACT_NUMBER_KEY, FLUBE_IT_SUPPORT_CONTACT_NUMBER_DEFAULT);
        defaults.put(FLUBE_IT_SUPPORT_CAN_SMS_KEY, FLUBE_IT_SUPPORT_CAN_SMS_DEFAULT);
        defaults.put(FLUBE_IT_SUPPORT_CAN_VOICE_KEY, FLUBE_IT_SUPPORT_CAN_VOICE_DEFAULT);

        //defaults for logging
        defaults.put(LOGGLY_DEBUG_ACTIVE_KEY, LOGGLY_DEBUG_ACTIVE_DEFAULT);
        defaults.put(LOGGLY_RELEASE_ACTIVE_KEY, LOGGLY_RELEASE_ACTIVE_DEFAULT);
        defaults.put(ROLLBAR_DEBUG_ACTIVE_KEY, ROLLBAR_DEBUG_ACTIVE_DEFAULT);
        defaults.put(ROLLBAR_RELEASE_ACTIVE_KEY, ROLLBAR_RELEASE_ACTIVE_DEFAULT);

        //cloud database defaults
        defaults.put(FIREBASE_DATABASE_BASE_NODE_ACTIVE_BATCH, FIREBASE_DATABASE_BASE_NODE_ACTIVE_BATCH_DEFAULT);
        defaults.put(FIREBASE_DATABASE_BASE_NODE_BATCH_DATA, FIREBASE_DATABASE_BASE_NODE_BATCH_DATA_DEFAULT);
        defaults.put(FIREBASE_DATABASE_BASE_NODE_DEMO_OFFERS, FIREBASE_DATABASE_BASE_NODE_DEMO_OFFERS_DEFAULT);
        defaults.put(FIREBASE_DATABASE_BASE_NODE_DEVICE_DATA, FIREBASE_DATABASE_BASE_NODE_DEVICE_DATA_DEFAULT);
        defaults.put(FIREBASE_DATABASE_BASE_NODE_PERSONAL_OFFERS, FIREBASE_DATABASE_BASE_NODE_PERSONAL_OFFERS_DEFAULT);
        defaults.put(FIREBASE_DATABASE_BASE_NODE_PUBLIC_OFFERS, FIREBASE_DATABASE_BASE_NODE_PUBLIC_OFFERS_DEFAULT);
        defaults.put(FIREBASE_DATABASE_BASE_NODE_SCHEDULED_BATCHES, FIREBASE_DATABASE_BASE_NODE_SCHEDULED_BATCHES_DEFAULT);
        defaults.put(FIREBASE_DATABASE_BASE_NODE_USER_DATA, FIREBASE_DATABASE_BASE_NODE_USER_DATA_DEFAULT);

        return defaults;
    }

}
