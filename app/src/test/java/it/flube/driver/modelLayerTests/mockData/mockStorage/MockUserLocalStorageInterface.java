/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayerTests.mockData.mockStorage;

import it.flube.driver.modelLayer.interfaces.DeviceStorageInterface;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public class MockUserLocalStorageInterface  {
        private final String TAG = "MockUserLocalStorageInterface";


    private boolean isAvailable() {
        return true;
    }

    public void load(DeviceStorageInterface.LoadResponse callback) {
        //DriverSingleton driver = DriverSingleton.getInstance();

       // Assert.assertNotNull(driver);

        //driver.setFirstName("Fizzi");
       // driver.setLastName("Battlecrank");
        //driver.setClientId("12345");
       // driver.setEmail("test@example.com");
       // driver.setSignedIn(true);

        //callback.success();
    }

    //public void loadRequest(DeviceStorageInterface.LoadResponse callback) {
       // callback.success();
    //}

    //public void saveRequest(DeviceStorageInterface.SaveResponse callback) {
       // callback.success();
    //}

    //public void deleteRequest(DeviceStorageInterface.DeleteReponse callback) {
        //callback.success();
   // }
}
