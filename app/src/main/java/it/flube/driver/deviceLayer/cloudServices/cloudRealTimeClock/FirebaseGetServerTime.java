/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudRealTimeClock;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.CloudRealTimeClockInterface;
import timber.log.Timber;

/**
 * Created on 8/21/2018
 * Project : Driver
 */
public class FirebaseGetServerTime implements
    FirebaseServerTimeWrite.Response,
    FirebaseServerTimeRead.Response {

    private static final String TAG = "FirebaseGetServerTime";

    private DatabaseReference rtcClock;
    private Long timestampStart;
    private Long timestampStop;

    private CloudRealTimeClockInterface.ServerTimeResponse response;

    public void getServerTimeRequest(DatabaseReference rtcClock, CloudRealTimeClockInterface.ServerTimeResponse response){
        Timber.tag(TAG).d("getServerTimeRequest...");
        Timber.tag(TAG).d("   rtcClock = " + rtcClock.toString());
        this.rtcClock = rtcClock;
        this.response = response;

        new FirebaseServerTimeWrite().writeServerTimeRequest(rtcClock, this);
    }

    ///
    /// FirebaseServerTimeWrite.Response interface
    ///
    public void writeServerTimeComplete(){
        Timber.tag(TAG).d("writeServerTimeComplete");
        timestampStart = System.currentTimeMillis();
        new FirebaseServerTimeRead().readServerTimeRequest(rtcClock, this);
    }

    ///
    /// FirebaseServerTimeRead.Response interface
    ///
    public void readServerTimeSuccess(Long serverTimeInMillis){
        timestampStop = System.currentTimeMillis();
        Long deltaCalc = timestampStop - timestampStart;
        Timber.tag(TAG).d("readServerTimeSuccess, roundtrip took " + deltaCalc + " msec");
        Timber.tag(TAG).d("   ...serverTimeInMillis     -> " + serverTimeInMillis.toString());
        Timber.tag(TAG).d("   ...systemTimeInMillis     -> " + timestampStop.toString());

        Long deltaTime = (serverTimeInMillis - timestampStop);
        Timber.tag(TAG).d("   ...skew (server - system) -> " + deltaTime.toString());
        response.getServerTimeSuccess(serverTimeInMillis);
    }

    public void readServerTimeFailure(){
        Timber.tag(TAG).d("readServerTimeFailure");
        response.getServerTimeFailure();
    }

}
