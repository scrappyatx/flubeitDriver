/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.signIn;


import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.entities.OfferListSingleton;
import it.flube.driver.modelLayer.interfaces.driverNetwork.DriverNetworkRepository;
import it.flube.driver.modelLayer.interfaces.driverNetwork.DriverNetworkRepositoryCallback;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositorySaveCallback;

/**
 * Created on 6/6/2017
 * Project : Driver
 */

public class UseCaseSignInFromNetwork implements Runnable, DriverNetworkRepositoryCallback, DriverStorageRepositorySaveCallback {
    private DriverNetworkRepository mNetwork;
    private DriverStorageRepository mStorage;
    private String mUrl;
    private String mUsername;
    private String mPassword;
    private UseCaseSignInFromNetworkCallback mCallback;


    public UseCaseSignInFromNetwork(DriverNetworkRepository network, String requestUrl, String username, String password, UseCaseSignInFromNetworkCallback callback, DriverStorageRepository storage){
        mNetwork = network;
        mStorage = storage;
        mUrl = requestUrl;
        mUsername = username;
        mPassword = password;
        mCallback = callback;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void run(){
        DriverSingleton.getInstance().clear();
        OfferListSingleton.getInstance().getOfferList().clear();

        mNetwork.setCallback(this);
        mNetwork.requestDriverProfile(mUrl, mUsername, mPassword);
    }

    /// Callbacks --> DriverNetworkRepositoryCallback
    public void requestDriverProfileSuccess() {
        //we successfully got the driver profile back from the network.
        //now save it in local preferences
        DriverSingleton.getInstance().setSignedIn(true);
        mStorage.save(this);
        mCallback.signInFromNetworkSuccess();
    }

    public void requestDriverProfileFailure(String responseMessage) {
        DriverSingleton.getInstance().setSignedIn(false);
        mCallback.signInFromNetworkFailure(responseMessage);
    }

    public void requestDriverProfileAuthenticationFailure(String responseMessage) {
        DriverSingleton.getInstance().setSignedIn(false);
        mCallback.signInFromNetworkAuthenticationFailure(responseMessage);
    }

    /// Callbacks --> DriverStorageRepositorySaveCallback
    public void saveDriverSuccess() {
        //nothing to do here
        //this is what we expect to happen
    }

    public void saveDriverFailure(String errorMessage) {
         //nothing to do here

    }

}
