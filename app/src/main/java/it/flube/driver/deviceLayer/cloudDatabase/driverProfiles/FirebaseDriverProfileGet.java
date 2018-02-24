/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.driverProfiles;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 11/30/2017
 * Project : Driver
 */

public class FirebaseDriverProfileGet implements ValueEventListener {
    private static final String TAG = "FirebaseDriverProfileGet";

    private CloudDatabaseInterface.UserProfileResponse response;
    private DatabaseReference clientIdRef;
    private String clientId;
    private String email;

    public void getDriverProfile(DatabaseReference driverProfileNodeRef, String clientId, String email, CloudDatabaseInterface.UserProfileResponse response){
        this.response = response;
        this.clientId = clientId;
        this.email = email;

        Timber.tag(TAG).d("driverProfileNodeRef = " + driverProfileNodeRef.toString());
        Timber.tag(TAG).d("clientId             = " + clientId);
        Timber.tag(TAG).d("email                = " + email);

        clientIdRef = driverProfileNodeRef.child(clientId);
        Timber.tag(TAG).d("clientIdRef          = " + clientIdRef.toString());

        clientIdRef.keepSynced(true);
        clientIdRef.addListenerForSingleValueEvent(this);
    }

    public void onDataChange(DataSnapshot dataSnapshot){
        Timber.tag(TAG).d("onDataChange...");
        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...dataSnapshot exists");
            try {
                Driver driver = dataSnapshot.getValue(Driver.class);
                Timber.tag(TAG).d("      ...got driver data for clientId : " + driver.getClientId());

                if (driver.getAccessEnabled()){
                    Timber.tag(TAG).d("         ...accessEnabled TRUE");
                    response.cloudDatabaseGetUserProfileSuccess(driver);
                } else {
                    Timber.tag(TAG).d("         ...accessEnabled FALSE");
                    response.cloudDatabaseGetUserProfileAccessDenied();
                }

            } catch (Exception e) {
                Timber.tag(TAG).w("      ...ERROR");
                Timber.tag(TAG).e(e);
                response.cloudDatabaseGetUserProfileNotFound();
            }
        } else {
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.cloudDatabaseGetUserProfileNotFound();
        }
        clientIdRef.keepSynced(false);
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).w("onCancelled --> error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.cloudDatabaseGetUserProfileNotFound();
        clientIdRef.keepSynced(false);
    }



}
