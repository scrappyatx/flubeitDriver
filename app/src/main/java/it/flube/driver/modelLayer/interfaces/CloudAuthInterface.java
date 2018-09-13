/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.driver.Driver;

/**
 * Created on 6/25/2017
 * Project : Driver
 */

public interface CloudAuthInterface {

    /// can be called on main thread
    Boolean hasDriver();

    Driver getDriver();

    /// must be called on background thread
    void startMonitoringAuthStateChanges(StartMonitoringResponse response);

    interface StartMonitoringResponse {
        void cloudAuthStartMonitoringComplete();
    }

    void stopMonitoringAuthStateChanges(StopMonitoringResponse response);

    interface StopMonitoringResponse {
        void cloudAuthStopMonitoringComplete();
    }

    void signOutCurrentUserRequest(SignOutCurrentUserResponse response);

    interface SignOutCurrentUserResponse {
        void cloudAuthSignOutCurrentUserComplete();
    }

}
