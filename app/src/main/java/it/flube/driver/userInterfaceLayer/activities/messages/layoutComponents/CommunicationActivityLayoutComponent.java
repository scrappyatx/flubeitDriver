/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages.layoutComponents;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.ContactPersonsByServiceOrder;
import timber.log.Timber;

/**
 * Created on 7/6/2018
 * Project : Driver
 */
public class CommunicationActivityLayoutComponent {
    private static final String TAG = "CommunicationActivityLayoutComponent";

    private ContactPersonSupportLayoutComponent support;
    private ServiceOrderTabLayoutComponent orderTab;
    private CommunicatingLayoutComponent waitingAnimation;

    private Boolean hasSupportContact;
    private Boolean hasOrderContacts;

    public CommunicationActivityLayoutComponent(AppCompatActivity activity){
        support = new ContactPersonSupportLayoutComponent(activity);
        orderTab = new ServiceOrderTabLayoutComponent(activity);
        waitingAnimation = new CommunicatingLayoutComponent(activity);

        hasSupportContact= false;
        hasOrderContacts = false;

        support.setGone();
        orderTab.setGone();
        waitingAnimation.setGone();

        Timber.tag(TAG).d("...created");
    }

    public void setValues(ContactPerson supportContact){
        support.setValues(supportContact);
        hasSupportContact = true;
        Timber.tag(TAG).d("...setValues supportContact only");
    }

    public void setValues(ContactPerson supportContact, ArrayList<String> serviceOrderList, ContactPersonsByServiceOrder contactMap){
        support.setValues(supportContact);
        orderTab.setValues(serviceOrderList, contactMap);
        hasSupportContact = true;
        hasOrderContacts = true;
        Timber.tag(TAG).d("...setValues supportContact, serviceOrderList, contactMap");
    }

    public void showWaitingToCall(){
        support.setGone();
        orderTab.setGone();
        waitingAnimation.showWaitingForCall();
        Timber.tag(TAG).d("...showWaitingToCall");
    }

    public void showWaitingToText(){
        support.setGone();
        orderTab.setGone();
        waitingAnimation.showWaitingForText();
        Timber.tag(TAG).d("...showWaitingToText");
    }

    public void setVisible(){
        if (hasSupportContact) {
            support.setVisible();
        }
        if (hasOrderContacts) {
            orderTab.setVisible();
        }

        waitingAnimation.setGone();
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        support.setGone();
        orderTab.setGone();
        waitingAnimation.setGone();
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        support.setGone();
        orderTab.setGone();
        waitingAnimation.setGone();
        Timber.tag(TAG).d("...set GONE");
    }

    public ContactPerson getSupportContactPerson(){
        return support.getContactPerson();
    }

    public ContactPerson getCustomerContactPerson(){
        return orderTab.getCustomerContactPerson();
    }

    public ContactPerson getServiceProviderContactPerson(){
        return orderTab.getServiceProviderContactPerson();
    }

    public void close(){
        support = null;
        orderTab = null;
        waitingAnimation = null;

        hasSupportContact = null;
        hasOrderContacts = null;
    }

}
