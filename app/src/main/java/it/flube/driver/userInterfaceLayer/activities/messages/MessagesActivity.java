/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.useCaseLayer.messages.UseCaseGetContactPersons;
import it.flube.driver.userInterfaceLayer.activities.messages.layoutComponents.CommunicationActivityLayoutComponent;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.ContactPersonsByServiceOrder;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class MessagesActivity extends AppCompatActivity implements
        CheckCallPermission.Response,
        UseCaseGetContactPersons.Response {
    private static final String TAG = "MessagesActivity";

    private MessagesController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private CommunicationActivityLayoutComponent layoutComponent;
    private CheckCallPermission checkCallPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.communication_activity_title);
        controller = new MessagesController();

        layoutComponent = new CommunicationActivityLayoutComponent(this);
        checkCallPermission = new CheckCallPermission();

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onResume() {
        Timber.tag(TAG).d("onResume");
        super.onResume();

        //see if we have permission to make a call
        checkCallPermission.checkCallPermissionRequest(this, this);

    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d( "onPause");
        super.onPause();
    }

    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop");

        drawer.close();
        controller.close();
        layoutComponent.close();
        checkCallPermission.close();

        super.onStop();

    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        Timber.tag(TAG).d("onRequestPermissionsResult, requestCode -> " + requestCode);
        checkCallPermission.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

    //// CheckCallPermission.Response interface
    public void callPermissionYes(){
        Timber.tag(TAG).d("callPermissionYes");
        controller.getContactPersonInfo(this);
    }

    public void callPermissionNo(){
        Timber.tag(TAG).d("callPermissionNo");
        layoutComponent.showNoPermission();
    }

    /// UseCaseGetContactPersons interface
    public void getContactPersonsSuccess(ContactPerson supportContact){
        Timber.tag(TAG).d("getContactPersonsSuccess -> supportContact only");
        layoutComponent.setValues(supportContact);
        layoutComponent.setVisible();
    }

    public void getContactPersonsSuccess(ContactPerson supportContact, ArrayList<String> serviceOrderList, ContactPersonsByServiceOrder contactMap){
        Timber.tag(TAG).d("getContactPersonsSuccess -> supportContact, serviceOrderList, contactMap");
        layoutComponent.setValues(supportContact, serviceOrderList, contactMap);
        layoutComponent.setVisible();
    }

    ////
    //// button click handlers
    ////
    public void clickSupportCallButton(View v){
        Timber.tag(TAG).d("clickSupportCallButton");
        Timber.tag(TAG).d("   ROLE              -> " +layoutComponent.getSupportContactPerson().getContactRole());
        Timber.tag(TAG).d("   display name      -> " +layoutComponent.getSupportContactPerson().getDisplayName());
        Timber.tag(TAG).d("   dial phone number -> " +layoutComponent.getSupportContactPerson().getDialPhoneNumber());

        layoutComponent.showWaitingToCall();
        new MakePhoneCall().dialNumberRequest(this,layoutComponent.getSupportContactPerson().getDialPhoneNumber());
    }

    public void clickSupportTextButton(View v){
        Timber.tag(TAG).d("clickSupportTextButton");
        Timber.tag(TAG).d("   ROLE              -> " +layoutComponent.getSupportContactPerson().getContactRole());
        Timber.tag(TAG).d("   display name      -> " +layoutComponent.getSupportContactPerson().getDisplayName());
        Timber.tag(TAG).d("   dial phone number -> " +layoutComponent.getSupportContactPerson().getDialPhoneNumber());

        layoutComponent.showWaitingToText();
        new SendTextMessage().sendTextRequest(this,layoutComponent.getSupportContactPerson().getDialPhoneNumber());
    }

    public void clickCustomerCallButton(View v){
        Timber.tag(TAG).d("clickCustomerCallButton");
        Timber.tag(TAG).d("   ROLE              -> " +layoutComponent.getCustomerContactPerson().getContactRole());
        Timber.tag(TAG).d("   display name      -> " +layoutComponent.getCustomerContactPerson().getDisplayName());
        Timber.tag(TAG).d("   dial phone number -> " +layoutComponent.getCustomerContactPerson().getDialPhoneNumber());

        layoutComponent.showWaitingToCall();
        new MakePhoneCall().dialNumberRequest(this,layoutComponent.getCustomerContactPerson().getDialPhoneNumber());
    }

    public void clickCustomerTextButton(View v){
        Timber.tag(TAG).d("clickCustomerTextButton");
        Timber.tag(TAG).d("   ROLE              -> " +layoutComponent.getCustomerContactPerson().getContactRole());
        Timber.tag(TAG).d("   display name      -> " +layoutComponent.getCustomerContactPerson().getDisplayName());
        Timber.tag(TAG).d("   dial phone number -> " +layoutComponent.getCustomerContactPerson().getDialPhoneNumber());

        layoutComponent.showWaitingToText();
        new SendTextMessage().sendTextRequest(this, layoutComponent.getCustomerContactPerson().getDialPhoneNumber());
    }

    public void clickServiceProviderCallButton(View v){
        Timber.tag(TAG).d("clickServiceProviderCallButton");
        Timber.tag(TAG).d("   ROLE              -> " +layoutComponent.getServiceProviderContactPerson().getContactRole());
        Timber.tag(TAG).d("   display name      -> " +layoutComponent.getServiceProviderContactPerson().getDisplayName());
        Timber.tag(TAG).d("   dial phone number -> " +layoutComponent.getServiceProviderContactPerson().getDialPhoneNumber());

        layoutComponent.showWaitingToCall();
        new MakePhoneCall().dialNumberRequest(this,layoutComponent.getServiceProviderContactPerson().getDialPhoneNumber());
    }

    public void clickServiceProviderTextButton(View v){
        Timber.tag(TAG).d("clickServiceProviderTextButton");
        Timber.tag(TAG).d("   ROLE              -> " +layoutComponent.getServiceProviderContactPerson().getContactRole());
        Timber.tag(TAG).d("   display name      -> " +layoutComponent.getServiceProviderContactPerson().getDisplayName());
        Timber.tag(TAG).d("   dial phone number -> " +layoutComponent.getServiceProviderContactPerson().getDialPhoneNumber());

        layoutComponent.showWaitingToText();
        new SendTextMessage().sendTextRequest(this,layoutComponent.getServiceProviderContactPerson().getDialPhoneNumber());
    }

    public void clickSettings(View v){
        Timber.tag(TAG).d("clickServiceProviderTextButton");
        checkCallPermission.gotoSettings(this);
    }

}
