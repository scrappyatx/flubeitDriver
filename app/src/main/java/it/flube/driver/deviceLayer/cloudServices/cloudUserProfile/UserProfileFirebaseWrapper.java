/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudUserProfile;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.cloudUserProfile.driverProfiles.FirebaseDriverProfileGet;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudUserProfileInterface;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudUserProfile.CloudUserProfileConstants.USER_PROFILE_NODE;

/**
 * Created on 3/13/2018
 * Project : Driver
 */

public class UserProfileFirebaseWrapper implements
        CloudUserProfileInterface {

    private static final String TAG = "UserProfileFirebaseWrapper";

    public void getUserProfileRequest(String clientId, String email, CloudUserProfileInterface.UserProfileResponse response){
        String driverProfileNode = USER_PROFILE_NODE;

        new FirebaseDriverProfileGet().getDriverProfile(FirebaseDatabase.getInstance().getReference(driverProfileNode),
                clientId, email, response);

        Timber.tag(TAG).d("getUserProfileRequest : clientId -> " + clientId + " email -> " + email);
    }
}
