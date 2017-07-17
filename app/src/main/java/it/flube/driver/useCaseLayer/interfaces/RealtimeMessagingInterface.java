/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.interfaces;

import java.util.ArrayList;

import it.flube.driver.modelLayer.Batch;
import it.flube.driver.modelLayer.Offer;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public interface RealtimeMessagingInterface {

    interface Notifications {
        void connect();

        void disconnect();

        void receiveMsgCurrentOffers(ArrayList<Offer> offerList);

        void receiveMsgBatchNotification(String batchOid, String batchMessage);
    }

    interface OfferMessages {
        void connect(String clientId);

        void disconnect();

        void sendMsgOnDuty(Boolean dutyStatus);

        void sendMsgRequestCurrentOffers();

        void sendMsgClaimOfferRequest(String offerOID);

        interface Response {

            void receiveMsgClaimOfferResult(String offerOID, String clientId);

            void receiveMsgCurrentOffers(ArrayList<Offer> offerList);
        }
    }

    interface BatchMessages {
        void connect();

        void disconnect();

        void sendMsgRequestAssignedBatches();

        void sendMsgForfeitBatch(String batchOID);

        interface Response {
            void receiveMsgAssignedBatches(ArrayList<Batch> batchList);

            void receiveMsgBatchRemoval(String batchOid, String batchMessage);
        }
    }

    interface ActiveBatchMessages {
        void connect();

        void disconnect();

        void sendMsgBatchStart(String batchOID);

        void sendMsgLocationUpdate(double latitude, double longitude);

        void sendMsgArrivedToPickup(String batchOID);

        void sendMsgDriverTakesVehicleFromCustomer(String batchOID);

        void sendMsgArrivedToService(String batchOID);

        void sendMsgServiceTakesVehicleFromDriver(String batchOID);

        void sendMsgServiceStart(String batchOID);

        void sendMsgServiceComplete(String batchOID);

        void sendMsgDriverTakesVehicleFromService(String batchOID);

        void sendMsgArrivedToDropOff(String batchOID);

        void sendMsgOwnerTakesVehicleFromDriver(String batchOID);
    }

}
