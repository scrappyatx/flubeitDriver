/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.constants;

/**
 * Created on 1/3/2019
 * Project : Driver
 */
public class EnvironmentConstantsProduction {

    //////////////
    ///  Four of the builder classes and one demo batch utility class contain references to files in firebase storage.
    ///  These references are in the form of download URLs, which are specific to a particular firebase instance.
    ///
    ///  Since we are using four different firebase instances, one for each of our target environments, we need
    ///  a unique download url PER TARGET ENVIRONMENT for each resource.
    ///
    ///  This file has all the target environment specific resources for the PRODUCTION environment
    //////////////

    ///
    ///  ContactPersonBuilder CONSTANTS
    ///
    /// **** PRODUCTION
    public static final String DEFAULT_CUSTOMER_ICON_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FcontactPersonImages%2Fcustomer.png?alt=media&token=b382b868-2d4a-4d8e-a978-de622217f369";
    public static final String DEFAULT_FLUBEIT_SUPPORT_ICON_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FcontactPersonImages%2Fflubeit_support.png?alt=media&token=7976a2bf-a7d9-45cb-9aff-b15b21b10771";
    public static final String DEFAULT_SERVICE_PROVIDER_ICON_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FcontactPersonImages%2Fservice_provider.png?alt=media&token=4351ff20-fc9b-4e57-9e06-dcf32392730a";

    ///
    ///  PhotoRequestBuilder CONSTANTS
    ///
    public static final String DEFAULT_NO_ATTEMPT_IMAGE_URL_PRODUCTION= "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2Fno_attempts_photo_image_2.png?alt=media&token=76ba46b8-78c5-4bf2-851b-12a5e466caaa";

    ///
    ///  PhotoRequestListForVehicleBuilder CONSTANTS
    ///
    /// **** PRODUCTION
    public static final String FRONT_CORNER_DRIVER_VIEW_HINT_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Ffront_corner_driver_view.jpg?alt=media&token=3f366e39-d1e0-4469-826b-00a72eddd54f";
    public static final String FRONT_CORNER_PASSENGER_VIEW_HINT_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Ffront_corner_passenger_view.jpg?alt=media&token=3d8c32c7-73cc-4a33-83c2-7250ba3e34ac";
    public static final String FRONT_VIEW_HINT_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Ffront_view.jpg?alt=media&token=aba5b22c-f736-481d-80a7-66be50a8be77";
    public static final String REAR_CORNER_DRIVER_VIEW_HINT_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Frear_corner_driver_view.jpg?alt=media&token=00caed2d-ad66-4bf3-811c-8a5a9cc40fb3";
    public static final String REAR_CORNER_PASSENGER_VIEW_HINT_URL_PRODUCTION="https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Frear_corner_passenger_view.jpg?alt=media&token=04c8b883-2ef6-48ca-9950-9913b551ebaa";
    public static final String REAR_VIEW_HINT_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Frear_view.jpg?alt=media&token=ea21778c-8310-47bc-afae-179b62c9ba56";
    public static final String SIDE_DRIVER_VIEW_HINT_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Fside_driver_view.jpg?alt=media&token=7e53e087-366a-4a2d-a6d9-14c9f5836b7c";
    public static final String SIDE_PASSENGER_VIEW_HINT_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2ForderStepImages%2FphotoStep%2FphotoHints%2FgenericAutoSmall%2Fside_passenger_view.jpg?alt=media&token=03177771-3805-4980-8371-592c058a10b6";

    ///
    ///  VehicleBuilder CONSTANTS
    ///
    public static final String DEFAULT_DISPLAY_IMAGE_URL_PRODUCTION="https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2ForderStepImages%2FassetTransfer%2Fasset_vehicle_icon.png?alt=media&token=693e75b1-7f3e-4918-ba11-5d10aa7efab3";

    ///
    /// ServiceProviderBuilder CONSTANTS
    ///
    public static final String DEFAULT_ICON_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FserviceProviders%2Foil-change-icon.png?alt=media&token=12eed72c-ea33-4cf1-b954-a19b35efbbbf";

    ///
    /// DistanceIndicators
    ///
    public static final String LONG_DISTANCE_ICON_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FdistanceIndicators%2Fdistance_long_ver2.png?alt=media&token=f105bd9e-17c0-4566-a6c1-03fe8cfc7859";
    public static final String MED_DISTANCE_ICON_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FdistanceIndicators%2Fdistance_med_ver2.png?alt=media&token=c66df983-c9d5-41c5-8784-e2e0623f0d51";
    public static final String SHORT_DISTANCE_ICON_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FdistanceIndicators%2Fdistance_short_ver2.png?alt=media&token=36e6d2db-7991-4904-9a05-98f7f05d45fb";
    ///
    /// Demo Oil Change Provider Logos
    ///
    public static final String SERVICE_ONE_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fservice_one_logo.PNG?alt=media&token=300e5b97-87f2-45a7-9a01-bbad984e7a08";
    public static final String KWIKKAR_ICON_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fkwikkar.PNG?alt=media&token=d5f08686-babe-453e-9070-0ba444d8d38f";
    public static final String EXPRESS_ICON_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fexpressoilchange.PNG?alt=media&token=27211f9f-83f4-43c2-91b0-377f842d2de5";
    public static final String JIFFYLUBE_ICON_URL_PRODUCTION = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FserviceProviders%2Fjiffy-lube_ver2.png?alt=media&token=dfc057bf-38cf-4cc9-8136-95fa68e1e071";
    public static final String GENERIC_ICON_URL_PRODUCTON = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver-prod.appspot.com/o/mobileAppDriver%2FserviceProviders%2Foil-change-icon.png?alt=media&token=12eed72c-ea33-4cf1-b954-a19b35efbbbf";

    ///
    ///  BatchNotificationSettings
    ///
    ///
    // notifications when order is placed by customer
    public static final Boolean SEND_EMAIL_TO_CUSTOMER_WITH_ORDER_CONFIRMATION_PRODUCTION = true;

    // notifications to driver when this batch is available as an offer
    public static final Boolean SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PUBLIC_OFFER_PRODUCTION = true;
    public static final Boolean SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PERSONAL_OFFER_PRODUCTION = true;

    // notifications when offer expires without being claimed
    public static final Boolean SEND_TEXT_TO_ADMIN_WHEN_OFFER_EXPIRES_PRODUCTION = true;

    // notifications to driver prior to batch starting
    public static final int UPCOMING_SCHEDULED_BATCH_WARNING_MINUTES_PRODUCTION = 30;
    public static final Boolean SEND_APP_NOTIFICATION_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_PRODUCTION = true;
    public static final Boolean SEND_TEXT_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_PRODUCTION = true;

    // notifications to driver for batch late start
    public static final int LATE_WARNING_TEXT_SCHEDULED_BATCH_SECONDS_PRODUCTION = 120;
    public static final int LATE_WARNING_VOICE_CALL_SCHEDULED_BATCH_SECONDS_PRODUCTION = 240;
    public static final Boolean SEND_TEXT_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_PRODUCTION = true;
    public static final Boolean VOICE_CALL_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_PRODUCTION = true;

    // notifications to send when batch is removed from driver due to NO START
    public static final Boolean SEND_TEXT_TO_ADMIN_WHEN_SCHEDULED_BATCH_REMOVED_DUE_TO_NO_START_PRODUCTION = true;

    ///  ServiceOrderNotificationSettings
    ///
    // notifications when order is started
    public static final Boolean SEND_TEXT_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_PRODUCTION = true;
    public static final Boolean SEND_EMAIL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_PRODUCTION = true;
    public static final Boolean SEND_VOICE_CALL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_PRODUCTION = true;

    //notifications when driver is navigating to customer
    public static final Boolean SEND_TEXT_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_PRODUCTION = true;
    public static final Boolean SEND_EMAIL_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_PRODUCTION = true;


    // notifications to send if an order step is late
    public static final int STEP_LATE_MINUTES_PRODUCTION = 20;
    public static final int STEP_VERY_LATE_MINUTES_PRODUCTION = 30;

    public static final Boolean SEND_APP_NOTIFICATION_TO_DRIVER_WHEN_STEP_IS_LATE_PRODUCTION = true;
    public static final Boolean SEND_TEXT_TO_DRIVER_WHEN_STEP_IS_LATE_PRODUCTION = true;
    public static final Boolean SEND_VOICE_CALL_TO_DRIVER_WHEN_STEP_IS_LATE_PRODUCTION = true;

    public static final Boolean SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_LATE_PRODUCTION = true;
    public static final Boolean SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_VERY_LATE_PRODUCTION = true;


}
