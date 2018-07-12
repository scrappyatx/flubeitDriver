/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudConfig;

/**
 * Created on 7/2/2018
 * Project : Driver
 */
public class FirebaseRemoteConfigConstants {

    /// keys
    public static final String FLUBE_IT_SUPPORT_DISPLAY_NAME_KEY = "flube_it_support_display_name";
    public static final String FLUBE_IT_SUPPORT_CONTACT_NUMBER_KEY = "flube_it_support_contact_number";
    public static final String FLUBE_IT_SUPPORT_CAN_SMS_KEY = "flube_it_support_can_sms";
    public static final String FLUBE_IT_SUPPORT_CAN_VOICE_KEY = "flube_it_support_can_voice";
    public static final String FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_KEY = "flube_it_support_display_icon_url";

    public static final String LOGGLY_DEBUG_ACTIVE_KEY = "loggly_debug_active";
    public static final String LOGGLY_RELEASE_ACTIVE_KEY = "loggly_release_active";
    public static final String ROLLBAR_DEBUG_ACTIVE_KEY = "rollbar_debug_active";
    public static final String ROLLBAR_RELEASE_ACTIVE_KEY = "rollbar_release_active";

    public static final String FIREBASE_DATABASE_BASE_NODE_PUBLIC_OFFERS = "firebase_database_base_node_public_offers";
    public static final String FIREBASE_DATABASE_BASE_NODE_PERSONAL_OFFERS = "firebase_database_base_node_personal_offers";
    public static final String FIREBASE_DATABASE_BASE_NODE_DEMO_OFFERS = "firebase_database_base_node_demo_offers";
    public static final String FIREBASE_DATABASE_BASE_NODE_SCHEDULED_BATCHES = "firebase_database_base_node_scheduled_batches";
    public static final String FIREBASE_DATABASE_BASE_NODE_ACTIVE_BATCH = "firebase_database_base_node_active_batch";
    public static final String FIREBASE_DATABASE_BASE_NODE_BATCH_DATA = "firebase_database_base_node_batch_data";
    public static final String FIREBASE_DATABASE_BASE_NODE_USER_DATA = "firebase_database_base_node_user_data";
    public static final String FIREBASE_DATABASE_BASE_NODE_DEVICE_DATA = "firebase_database_base_node_device_data";


    //values
    public static final String FLUBE_IT_SUPPORT_DISPLAY_NAME_DEFAULT = "flube.it support";
    public static final String FLUBE_IT_SUPPORT_CONTACT_NUMBER_DEFAULT = "5125732942";
    public static final Boolean FLUBE_IT_SUPPORT_CAN_SMS_DEFAULT = true;
    public static final Boolean FLUBE_IT_SUPPORT_CAN_VOICE_DEFAULT = false;
    public static final String FLUBE_IT_SUPPORT_DISPLAY_ICON_URL_DEFAULT = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/contactPersonImages%2Fflubeit_support.png?alt=media&token=5c4a0bf0-ac4b-486f-9ed5-1ea9f3381623";

    public static final Boolean LOGGLY_DEBUG_ACTIVE_DEFAULT = true;
    public static final Boolean LOGGLY_RELEASE_ACTIVE_DEFAULT = false;
    public static final Boolean ROLLBAR_DEBUG_ACTIVE_DEFAULT = true;
    public static final Boolean ROLLBAR_RELEASE_ACTIVE_DEFAULT = false;

    public static final String FIREBASE_DATABASE_BASE_NODE_PUBLIC_OFFERS_DEFAULT = "mobileData/userReadable";
    public static final String FIREBASE_DATABASE_BASE_NODE_PERSONAL_OFFERS_DEFAULT = "mobileData/userReadable/users";
    public static final String FIREBASE_DATABASE_BASE_NODE_DEMO_OFFERS_DEFAULT = "mobileData/userOwned/users";
    public static final String FIREBASE_DATABASE_BASE_NODE_SCHEDULED_BATCHES_DEFAULT = "mobileData/userOwned/users";
    public static final String FIREBASE_DATABASE_BASE_NODE_ACTIVE_BATCH_DEFAULT = "mobileData/userOwned/users";
    public static final String FIREBASE_DATABASE_BASE_NODE_BATCH_DATA_DEFAULT = "mobileData/userOwned/users";
    public static final String FIREBASE_DATABASE_BASE_NODE_USER_DATA_DEFAULT = "mobileData/userWriteable/users";
    public static final String FIREBASE_DATABASE_BASE_NODE_DEVICE_DATA_DEFAULT = "mobileData/userWriteable/users";


}
