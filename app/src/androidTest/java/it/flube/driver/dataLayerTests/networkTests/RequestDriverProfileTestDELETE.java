/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayerTests.networkTests;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import it.flube.driver.dataLayer.interfaces.network.HttpRequestDriverProfileResultDELETE;
import it.flube.driver.dataLayer.network.HttpDriverProfile;
import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/6/2017
 * Project : Driver
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RequestDriverProfileTestDELETE implements HttpRequestDriverProfileResultDELETE {

    @Test
    public void run() {
        HttpDriverProfile mNetwork = new HttpDriverProfile(this);
        String requestUrl = "https://api.cloudconfidant.com/concierge-oil-service/ably/clientId";
        String username = "test";
        String password = "password";
        DriverSingleton driver = DriverSingleton.getInstance();

        mNetwork.requestDriverProfile(driver, requestUrl, username, password);

    }


    public void getDriverProfileSuccess(DriverSingleton driver) {
        Assert.assertNotNull(driver);
        Assert.assertEquals("Client ID", "XXXXX", driver.getClientId());
    }

    public void getDriverProfileFailure(String errorMessage) {

        Assert.assertNotNull(errorMessage);
        Assert.assertEquals("ErrorMessage","XXXX",errorMessage);
    }

}
