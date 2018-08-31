/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudUserProfile;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.BuildConfig;
import it.flube.driver.deviceLayer.cloudServices.cloudUserProfile.driverProfiles.FirebaseDriverProfileGet;
import it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseDbInitialization;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudUserProfileInterface;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudUserProfile.CloudUserProfileConstants.USER_PROFILE_NODE;

/**
 * Created on 3/13/2018
 * Project : Driver
 */

public class UserProfileFirebaseWrapper implements
        CloudUserProfileInterface {

    private static final String TAG = "UserProfileFirebaseWrapper";

    private final String userProfileDb;

    public UserProfileFirebaseWrapper(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        Timber.tag(TAG).d("targetEnvironment -> " + targetEnvironment.toString());
        userProfileDb = FirebaseDbInitialization.getFirebaseUserProfileDb(targetEnvironment);
    }

    public void getUserProfileRequest(String clientId, String email, CloudUserProfileInterface.UserProfileResponse response){
        Timber.tag(TAG).d("getUserProfileRequest : clientId -> " + clientId + " email -> " + email);

        new FirebaseDriverProfileGet().getDriverProfile(FirebaseDatabase.getInstance(userProfileDb).getReference(USER_PROFILE_NODE),
                clientId, email, response);

    }
}
