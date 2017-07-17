/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging;

import java.util.ArrayList;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.Offer;
import it.flube.driver.useCaseLayer.interfaces.RealtimeMessagingInterface;

/**
 * Created on 7/8/2017
 * Project : Driver
 */

public class RealtimeNotifications implements RealtimeMessagingInterface.Notifications {
    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static volatile RealtimeNotifications mInstance = new RealtimeNotifications();
    }

    private RealtimeNotifications() {
        ablyChannel = new AblyChannel(AndroidDevice.getInstance().getAppRemoteConfig().getRealtimeMessagingLookingForOffersChannelName(),
                AndroidDevice.getInstance().getAppRemoteConfig().getRealtimeMessagingAuthTokenUrl());
    }

    public static RealtimeNotifications getInstance() {
        return RealtimeNotifications.Loader.mInstance;
    }

    private static final String TAG = "RealtimeNotifications";
    private final AblyChannel ablyChannel;



    public void connect() {

    }

    public void disconnect() {

    }

    public void receiveMsgCurrentOffers(ArrayList<Offer> offerList) {

    }

    public void receiveMsgBatchNotification(String batchOid, String batchMessage) {

    }

}