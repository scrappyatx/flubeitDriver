/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudRealTimeClock;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.libbatchdata.entities.claimOffer.ClaimOfferResponse;
import timber.log.Timber;

/**
 * Created on 8/21/2018
 * Project : Driver
 */
public class FirebaseServerTimeRead implements
        ValueEventListener {

    public static final String TAG = "FirebaseServerTimeRead";

    private Response response;

    public void readServerTimeRequest(DatabaseReference rtcRef, Response response){
        this.response = response;

        Timber.tag(TAG).d("readServerTimeRequest...");
        Timber.tag(TAG).d("   rtcRef = " + rtcRef.toString());

        rtcRef.addListenerForSingleValueEvent(this);
    }

    public void onDataChange(@NonNull DataSnapshot dataSnapshot){
        Timber.tag(TAG).d("dataSnapshot exists...");

        if (dataSnapshot.exists()){
            Timber.tag(TAG).d("   ...dataSnapshot exists");
            try {
                Long serverTimeInMillis = (Long) dataSnapshot.getValue();
                Timber.tag(TAG).d("   ...serverTimeInMillis -> " + serverTimeInMillis);
                response.readServerTimeSuccess(serverTimeInMillis);
            } catch (Exception e) {
                Timber.tag(TAG).w("         ...ERROR");
                Timber.tag(TAG).e(e);
                response.readServerTimeFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
            response.readServerTimeFailure();
        }
    }

    public void onCancelled(@NonNull DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error --> " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.readServerTimeFailure();
    }

    public interface Response {
        void readServerTimeSuccess(Long serverTimeInMillis);

        void readServerTimeFailure();
    }

}
