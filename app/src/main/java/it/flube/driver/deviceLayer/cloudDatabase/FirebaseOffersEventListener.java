/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class FirebaseOffersEventListener implements ValueEventListener {
    private static final String TAG = "FirebaseOffersEventListener";

    private CloudDatabaseInterface.OffersUpdated update;
    private String offersType;

    public FirebaseOffersEventListener(String offersType, CloudDatabaseInterface.OffersUpdated update) {
        this.offersType = offersType;
        this.update = update;
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("firebase database offer data CHANGED! --> " + offersType);
        try {
            GenericTypeIndicator<ArrayList<Offer>> t = new GenericTypeIndicator<ArrayList<Offer>>() {};
            ArrayList<Offer> offerList = dataSnapshot.getValue(t);

            if (offerList == null) {
                Timber.tag(TAG).d("no offers in this list --> " + offersType);
                update.cloudDatabaseNoAvailableOffers();
            } else {
                Timber.tag(TAG).d("offer list has " + Integer.toString(offerList.size()) + " offers --> " + offersType);
                update.cloudDatabaseAvailableOffersUpdated(offerList);
            }
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e(offersType + " --> firebase database read error in offers : " + databaseError.getCode() + " --> " + databaseError.getMessage());
    }


}
