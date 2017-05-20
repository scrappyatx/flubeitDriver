/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces;

/**
 * Created on 5/11/2017
 * Project : Driver
 */

public interface AblyConnectionCallback {
    void onConnectionCallbackException(Exception ex);

    void onConnectionCallbackInitialized();

    void onConnectionCallbackConnecting();

    void onConnectionCallbackConnected();

    void onConnectionCallbackDisconnected();

    void onConnectionCallbackSuspended();

    void onConnectionCallbackClosing();

    void onConnectionCallbackClosed();

    void onConnectionCallbackFailed();

    // VALID CONNECTION STATES

    //initialized       Connection object having this state has been initialized but no connection has yet been attempted.

    //connecting        Connection attempt has been initiated. The connecting state is entered as soon as the library has completed initialization, and is reentered each time connection is re-attempted following disconnection

    //connected         connection exists and is active.

    //disconnected      temporary failure condition. No current connection exists because there is no network connectivity or no host is available.
    //
    //                  The disconnected state is entered if an established connection is dropped, or if a connection attempt was unsuccessful. In the disconnected state the library will periodically attempt to open a new connection (approximately every 15 seconds), anticipating that the connection will be re-established soon and thus connection and channel continuity will be possible.
    //
    //                  In this state, developers can continue to publish messages as they are automatically placed in a local queue, and sent as soon as a connection is established.
    //                  Messages published by other clients to the Ably service, whilst this client is disconnected, are queued and delivered to this client upon reconnection, so long as the connection is resumed within 2 minutes.
    //                  If connection state recovery is not possible following a new connection being established, all channels are detached, thus ensuring the developer is made aware that continuity on the channel was not possible. The developer is then responsible for re-attaching channels and recovering state, if required, using the history API.

    //suspended         long term failure condition. No current connection exists because there is no network connectivity or no host is available.
    //
    //                  The suspended state is entered after a failed connection attempt if there has then been no connection for a period of two minutes. In the suspended state, the library will periodically attempt to open a new connection every 30 seconds. Developers are unable to publish messages in this state. A new connection attempt can also be triggered by an explicit call to connect on the Connection object.

    //closing           An explicit request by the developer to close the connection has been sent to the Ably service. If a reply is not received from Ably within a short period of time, the connection will be forcibly terminated and the connection state will become closed.

    //closed            The connection has been explicitly closed by the client.
    //
    //                  In the closed state, no reconnection attempts are made automatically by the library, and clients may not publish messages. No connection state is preserved by the service or by the library. A new connection attempt can be triggered by an explicit call to connect on the Connection object, which will result in a new connection.

    //failed            An indefinite failure condition. This state is entered if a connection error has been received from the Ably service (such as an attempt to connect with invalid credentials). A failed state may also be triggered by the client library directly as a result of some local permanent error.
    //
    //                  In the failed state, no reconnection attempts are made automatically by the library, and clients may not publish messages. A new connection attempt can be triggered by an explicit call to connect on the Connection object.

    // TYPICAL CONNECTION STATE SEQUENCES

    //      The library is initialized and initiates a successful connection.
    //
    //          initialized → connecting → connected


    //      An existing connection is dropped and reestablished on the first attempt.
    //
    //          connected → disconnected → connecting → connected


    //      An existing connection is dropped, and reestablished after several attempts but within a two minute interval.
    //      Connection retry is automatic and occurs approximately every 15 seconds
    //
    //          connected → disconnected → connecting → disconnected → … → connecting → connected


    //      An existing connection is dropped, and reestablished after several attempts but longer than 2 minutes.
    //      Connection retry is automatic and occurs approximately every 15 seconds
    //
    //          connected → disconnected → connecting → disconnected → … → connecting → suspended → connecting → connected


    //      There is no connection established after initializing the library.
    //
    //          initialized → connecting → disconnected → connecting → … → suspended


    //      After a period of being offline a connection is reestablished.
    //      Connection retry is automatic and occurs approximately every 30 seconds
    //
    //          suspended → connecting → suspended → … → connecting → connected


}
