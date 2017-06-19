/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

/**
 * Created by Bryan on 5/2/2017.
 */

public class DriverSingleton {

    ///
    ///  Loader class provides synchronization across threads
    ///  Lazy initialization since Loader class is only called when "getInstance" is called
    ///  volatile keyword guarantees visibility of changes to variables across threads
    ///
    private static class Loader {
        static volatile DriverSingleton mInstance = new DriverSingleton();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private DriverSingleton() {}

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static DriverSingleton getInstance() {
        return Loader.mInstance;
    }


    ///
    ///     all class variables are static so there is only one across all instances (and there will only be one instance)
    ///
    private static String mFirstName;
    private static String mLastName;
    private static String mClientId;
    private static String mEmail;
    private static String mRole;
    private static String mActiveBatchOID;
    private static boolean mSignedIn;
    private static boolean mHasActiveBatch;


    ///
    ///   getters & setters for the class variables
    ///

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

    public String getRole() { return mRole; }

    public void setRole(String role) { mRole = role; }

    public void setSignedIn(boolean status) {
        mSignedIn = status;
    }

    public boolean isSignedIn() {
        return mSignedIn;
    }

    public void setHasActiveBatch(boolean status) {
        mHasActiveBatch = status;
    }

    public void setActiveBatchOID(String oid) {
        mActiveBatchOID = oid;
    }

    public String getActiveBatchOID() {
        return mActiveBatchOID;
    }

    ///
    ///  convenience method to clear all internal variables
    ///
    public void clear() {
        mFirstName = null;
        mLastName = null;
        mClientId = null;
        mEmail = null;
        mRole = null;
        mSignedIn = false;
        mHasActiveBatch = false;
        mActiveBatchOID = null;
    }
}
