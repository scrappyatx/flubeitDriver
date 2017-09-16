/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class FirebasePublicOffersEventListener  implements ValueEventListener {
    private static final String TAG = "FirebasePublicOffersEventListener";

    private CloudDatabaseInterface.PublicOffersUpdated update;

    public FirebasePublicOffersEventListener(CloudDatabaseInterface.PublicOffersUpdated update) {
        this.update = update;
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("public offer data CHANGED!");

        ArrayList<Offer> offerList = new ArrayList<Offer>();

        if (dataSnapshot.exists()) {
            if (dataSnapshot.getChildrenCount() > 0) {
                //dataSnapshot EXISTS & HAS CHILDREN
                // each child node should be an OFFER
                for (DataSnapshot offerSnapshot : dataSnapshot.getChildren()) {
                    try {
                        Offer offer = offerSnapshot.getValue(Offer.class);
                        offerList.add(offer);
                    } catch (Exception e) {
                        Timber.tag(TAG).e(e);
                    }
                    Timber.tag(TAG).d("offer list has " + Integer.toString(offerList.size()) + " offers --> ");
                }

            } else {
                //dataSnapshot has no children
                Timber.tag(TAG).d("no offers in this list");
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("dataSnapshot does not exist");
        }

        update.cloudDatabasePublicOffersUpdated(offerList);
    }


    /*
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
    */

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("firebase database read error in public offers : " + databaseError.getCode() + " --> " + databaseError.getMessage());
    }

}
