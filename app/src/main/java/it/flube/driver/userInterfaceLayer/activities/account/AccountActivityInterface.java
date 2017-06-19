/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.account;

/**
 * Created on 6/18/2017
 * Project : Driver
 */

public interface AccountActivityInterface {
    public void ProfileDetailUpdate(String firstName, String lastName, String email, String role, String clientId);

    public void ProfileDetailNotAvailable(String message);
}
