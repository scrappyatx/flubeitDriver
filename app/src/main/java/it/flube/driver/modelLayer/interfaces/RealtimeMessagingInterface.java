/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Batch;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.Offer;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public interface RealtimeMessagingInterface {

    interface Connection {
        void messageServerConnectRequest(Driver driver, ConnectResponse response);

        interface ConnectResponse {
            void messageServerConnectSuccess();

            void messageServerConnectFailure();
        }

        void messageServerDisconnectRequest(DisconnectResponse response);

        interface DisconnectResponse {
            void messageServerDisconnectComplete();
        }

        RealtimeMessagingInterface.OfferChannel getOfferChannel();

        RealtimeMessagingInterface.BatchChannel getBatchChannel();

        RealtimeMessagingInterface.ActiveBatchChannel getActiveBatchChannel();

    }

    interface Notifications {
        void connect();

        void disconnect();

        interface OffersReceived {

            void receiveMsgCurrentOffers(ArrayList<Offer> offerList);
        }

        interface BatchesReceived {

            void receiveMsgBatchNotification(String batchOid, String batchMessage);
        }


    }

    interface OfferChannel {
        void attach(String clientId);

        void detach();

        void sendOnDutyMessage(Boolean dutyStatus);

        void sendClaimOfferRequestMessage(String offerOID, ClaimOfferResponse response);

        interface ClaimOfferResponse {
            void receiveClaimOfferResponseMessage(String offerOID, String batchOID, String clientId);

            void claimOfferRequestTimeoutNoResponse();
        }
    }



    interface BatchChannel {
        void connect(String clientId);

        void disconnect();

        void sendMsgForfeitBatch(String batchOID);

        interface Response {
            void receiveMsgAssignedBatches(ArrayList<Batch> batchList);

            void receiveMsgBatchRemoval(String batchOid, String batchMessage);
        }
    }

    interface ActiveBatchChannel {
        void connect(String batchOID);

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
