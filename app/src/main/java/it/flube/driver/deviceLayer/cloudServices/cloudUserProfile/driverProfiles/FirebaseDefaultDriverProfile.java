/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudUserProfile.driverProfiles;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.builders.CloudDatabaseSettingsBuilder;
import it.flube.driver.modelLayer.builders.DriverBuilder;
import it.flube.driver.modelLayer.builders.NameSettingsBuilder;
import it.flube.driver.modelLayer.builders.UserRolesBuilder;
import it.flube.driver.modelLayer.entities.driver.Driver;
import timber.log.Timber;

/**
 * Created on 11/30/2017
 * Project : Driver
 */

public class FirebaseDefaultDriverProfile {
    private static final String TAG = "FirebaseDriverProfileAddOrUpdate";

    private static final String DEFAULT_FIRST_NAME = "Defaulty";
    private static final String DEFAULT_LAST_NAME = "Defaultington";
    private static final String DEFAULT_DISPLAY_NAME = DEFAULT_FIRST_NAME + " " + DEFAULT_LAST_NAME;

    private static final String DEFAULT_PHOTO_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/driverProfileData%2Fdefault_profile_picture.jpg?alt=media&token=e146698b-3986-4764-aad4-7c395c7608fb";

    private static final String DEFAULT_PUBLIC_OFFERS_NODE = "publicOffers";
    private static final String DEFAULT_PERSONAL_OFFERS_NODE = "personalOffers";
    private static final String DEFAULT_DEMO_OFFERS_NODE = "demoOffers";
    private static final String DEFAULT_SCHEDULED_BATCHES_NODE = "assignedBatches";
    private static final String DEFAULT_ACTIVE_BATCH_NODE = "activeBatch";
    private static final String DEFAULT_BATCH_DATA_NODE = "batchData";

    public static Driver getDefaultDriver(@NonNull String clientId, @NonNull String email){

        Timber.tag(TAG).d("getDefaultDriver : clientID -> " + clientId + " email -> " + email);

        return new DriverBuilder.Builder()
                .clientId(clientId).email(email)
                .photoUrl(DEFAULT_PHOTO_URL)
                .nameSettings(new NameSettingsBuilder.Builder()
                        .firstName(DEFAULT_FIRST_NAME)
                        .lastName(DEFAULT_LAST_NAME)
                        .displayName(DEFAULT_DISPLAY_NAME)
                        .build())
               .userRoles(new UserRolesBuilder.Builder()
                       .dev(true)
                       .qa(true)
                       .build())
                .cloudDatabaseSettings(new CloudDatabaseSettingsBuilder.Builder()
                        .publicOffersNode(DEFAULT_PUBLIC_OFFERS_NODE)
                        .personalOffersNode(DEFAULT_PERSONAL_OFFERS_NODE)
                        .demoOffersNode(DEFAULT_DEMO_OFFERS_NODE)
                        .scheduledBatchesNode(DEFAULT_SCHEDULED_BATCHES_NODE)
                        .activeBatchNode(DEFAULT_ACTIVE_BATCH_NODE)
                        .batchDataNode(DEFAULT_BATCH_DATA_NODE)
                        .build())
                .build();
    }
}
