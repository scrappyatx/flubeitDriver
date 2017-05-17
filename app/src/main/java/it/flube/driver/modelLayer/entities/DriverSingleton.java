/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

/**
 * Created by Bryan on 5/2/2017.
 */

public class DriverSingleton {
    private static final DriverSingleton mInstance = new DriverSingleton();
    private static String mFirstName;
    private static String mLastName;
    private static String mClientId;
    private static String mEmail;
    private static boolean mOnDuty;
    private static boolean mIsLoaded;

    public static DriverSingleton getInstance() {
        return mInstance;
    }

    private DriverSingleton() {
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String clientId) {
        mClientId = clientId;
    }

    public String getEmail() { return mEmail;}

    public void setEmail(String email) { mEmail = email; }

    public boolean isOnDuty() {
        return mOnDuty;
    }

    public void setOnDuty(boolean onDuty) {
        mOnDuty = onDuty;
    }

    public boolean isLoaded() {
        return mIsLoaded;
    }

    public void setLoaded(boolean loaded) {
        mIsLoaded = loaded;
    }


}
