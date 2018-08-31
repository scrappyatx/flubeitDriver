/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudRealTimeClock;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseDbInitialization;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudRealTimeClockInterface;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudRealTimeClock.FirebaseRealTimeClockConstants.REAL_TIME_CLOCK_NODE;

/**
 * Created on 8/21/2018
 * Project : Driver
 */
public class FirebaseRealTimeClockWrapper implements
    CloudRealTimeClockInterface {

    private static final String TAG="FirebaseRealTimeClockWrapper";

    private final String driverDb;
    private String realtimeClockNode;

    public FirebaseRealTimeClockWrapper(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
       driverDb = FirebaseDbInitialization.getFirebaseDriverDb(targetEnvironment);
    }

    private void getNode(Driver driver){
        realtimeClockNode = String.format(REAL_TIME_CLOCK_NODE, driver.getClientId());
        Timber.tag(TAG).d("realtimeClockNode = " + realtimeClockNode);
    }


    public void getServerTimeRequest(Driver driver, CloudRealTimeClockInterface.ServerTimeResponse response){
        Timber.tag(TAG).d("getServerTimeRequest");
        getNode(driver);
        new FirebaseGetServerTime().getServerTimeRequest(FirebaseDatabase.getInstance(driverDb).getReference(realtimeClockNode), response);
    }
}
