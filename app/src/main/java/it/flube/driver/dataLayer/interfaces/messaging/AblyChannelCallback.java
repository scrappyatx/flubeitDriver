/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.interfaces.messaging;

import io.ably.lib.types.ErrorInfo;

/**
 * Created on 5/13/2017
 * Project : Driver
 */

public interface AblyChannelCallback {

    void onChannelCallbackInitialized();

    void onChannelCallbackAttaching();

    void onChannelCallbackAttached(boolean resumed);

    void onChannelCallbackDetaching();

    void onChannelCallbackDetached();

    void onChannelCallbackSuspended();

    void onChannelCallbackFailed(ErrorInfo e);

    //  A channel can exist in any of the following states:

    //  initialized     A Channel object having this state has been initialized but no attach has yet been attempted

    //  attaching       An attach has been initiated by sending a request to Ably. This is a transient state; it will be followed either by a transition to attached, suspended, or failed

    //  attached        Attach has succeeded. In the attached state a client may publish and subscribe to messages, or be present

    //  detaching       A detach has been initiated on the attached Channel by sending a request to Ably. This is a transient state; it will be followed either by a transition to detached or failed

    //  detached        The Channel, having previously been attached, has been detached by the user

    //  suspended       The Channel, having previously been attached, has lost continuity, usually due to the client being disconnected from Ably for more than two minutes. It will automatically attempt to reattach as soon as connectivity is restored

    //  failed          An indefinite failure condition. This state is entered if a Channel error has been received from the Ably service (such as an attempt to attach without the necessary access rights)

}
