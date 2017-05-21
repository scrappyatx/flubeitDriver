/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.interfaces.toBeDeleted;

/**
 * Created by Bryan on 4/17/2017.
 */

public class EbEventClientIdDELETE {
    private static String clientId;

    public EbEventClientIdDELETE(String clientId) {
            this.clientId = clientId;
        }

    public static String getEbEventClientId() {
            return clientId;
        }
}

