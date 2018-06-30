/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.libbatchdata.entities.ContactPerson;

/**
 * Created on 6/28/2018
 * Project : Driver
 */
public interface CloudVoiceCallingInterface {

    void makeVoiceCallRequest(ContactPerson contactPerson, MakeVoiceCallResponse response);

    interface MakeVoiceCallResponse {
        void makeVoiceCallSuccess();
        void makeVoiceCallFailure();
    }
}
