/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.testingUseCases.loadDriverTestInfo;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created by Bryan on 5/2/2017.
 */

public class LoadDriverTestInfo implements Runnable {
    private LoadDriverTestInfoCallback mCallback;
    private String mFirstName;
    private String mLastName;
    private String mClientId;
    private String mEmail;

    public LoadDriverTestInfo(LoadDriverTestInfoCallback callback) {
        mCallback = callback;
        mFirstName = "Fizzi";
        mLastName = "Battlecrank";
        mClientId = "12345";
        mEmail = "test@example.com";
    }

    public void setTestData(String firstName, String lastName, String clientId, String email) {
        mFirstName = firstName;
        mLastName = lastName;
        mClientId = clientId;
        mEmail = email;
    }
    public void run() {
        //load test flube.it.flube.it.driver
        DriverSingleton.getInstance().setFirstName(mFirstName);
        DriverSingleton.getInstance().setLastName(mLastName);
        DriverSingleton.getInstance().setClientId(mClientId);
        DriverSingleton.getInstance().setEmail(mEmail);
        mCallback.loadDriverTestInfoSuccess(DriverSingleton.getInstance());
    }

}
