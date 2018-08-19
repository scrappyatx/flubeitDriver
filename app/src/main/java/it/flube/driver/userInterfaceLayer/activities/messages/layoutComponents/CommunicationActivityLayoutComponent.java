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
public class CommunicationActivityLayoutComponent implements
    ContactPersonSupportLayoutComponent.Response,
    ServiceOrderTabLayoutComponent.Response,
    PhoneCallPermissionComponent.Response {

    private static final String TAG = "CommunicationActivityLayoutComponent";

    private ContactPersonSupportLayoutComponent support;
    private ServiceOrderTabLayoutComponent orderTab;
    private CommunicatingLayoutComponent waitingAnimation;
    private PhoneCallPermissionComponent permission;

    private Boolean hasSupportContact;
    private Boolean hasOrderContacts;

    private Response response;

    public CommunicationActivityLayoutComponent(AppCompatActivity activity, Response response){
        this.response = response;

        support = new ContactPersonSupportLayoutComponent(activity, this);
        orderTab = new ServiceOrderTabLayoutComponent(activity, this);
        waitingAnimation = new CommunicatingLayoutComponent(activity);
        permission = new PhoneCallPermissionComponent(activity, this);

        hasSupportContact= false;
        hasOrderContacts = false;

        support.setGone();
        orderTab.setGone();
        permission.setGone();
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

    public void showNoPermission(){
        support.setGone();
        orderTab.setGone();
        waitingAnimation.setGone();

        permission.setVisible();
        Timber.tag(TAG).d("...showNoPermission");
    }

    public void setVisible(){
        if (hasSupportContact) {
            support.setVisible();
        }
        if (hasOrderContacts) {
            orderTab.setVisible();
        }

        waitingAnimation.setGone();
        permission.setGone();
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        support.setGone();
        orderTab.setGone();
        waitingAnimation.setGone();
        permission.setGone();
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        support.setGone();
        orderTab.setGone();
        waitingAnimation.setGone();
        permission.setGone();
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
        permission = null;

        hasSupportContact = null;
        hasOrderContacts = null;

        response = null;
    }

    /// PhoneCallPermissionComponent.Response interface
    public void appInfoButtonClicked(){
        Timber.tag(TAG).d("appInfoButtonClicked");
        response.appInfoButtonClicked();
    }

    /// ContactPersonSupportLayoutComponent.Response interface
    public void supportCallButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("supportCallButtonClicked");
        response.supportCallButtonClicked(dialPhoneNumber);
    }

    public void supportTextButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("supportTextButtonClicked");
        response.supportTextButtonClicked(dialPhoneNumber);
    }

    /// ServiceOrderTabLayoutComponent.Response interface
    public void customerCallButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("customerCallButtonClicked");
        response.customerCallButtonClicked(dialPhoneNumber);
    }

    public void customerTextButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("customerTextButtonClicked");
        response.customerTextButtonClicked(dialPhoneNumber);
    }

    public void serviceProviderCallButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("serviceProviderCallButtonClicked");
        response.serviceProviderCallButtonClicked(dialPhoneNumber);
    }

    public void serviceProviderTextButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("serviceProviderTextButtonClicked");
        response.serviceProviderTextButtonClicked(dialPhoneNumber);
    }

    public interface Response {
        void appInfoButtonClicked();

        void supportCallButtonClicked(String dialPhoneNumber);

        void supportTextButtonClicked(String dialPhoneNumber);

        void customerCallButtonClicked(String dialPhoneNumber);

        void customerTextButtonClicked(String dialPhoneNumber);

        void serviceProviderCallButtonClicked(String dialPhoneNumber);

        void serviceProviderTextButtonClicked(String dialPhoneNumber);
    }

}
