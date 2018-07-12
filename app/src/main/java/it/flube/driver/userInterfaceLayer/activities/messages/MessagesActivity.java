/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages;

import android.os.Bundle;
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


        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();

        //EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.communication_activity_title);
        controller = new MessagesController();

        layoutComponent = new CommunicationActivityLayoutComponent(this);
        checkCallPermission = new CheckCallPermission();
        checkCallPermission.checkCallPermissionRequest(this, this);


        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        //EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();
        layoutComponent.close();
        checkCallPermission.close();

        Timber.tag(TAG).d( "onPause");
        super.onPause();
    }

    @Override
    public void onStop(){

        super.onStop();

    }

    //// CheckCallPermission.Response interface
    public void callPermissionYes(){
        Timber.tag(TAG).d("callPermissionYes");
        controller.getContactPersonInfo(this);
    }

    public void callPermissionNo(){
        Timber.tag(TAG).d("callPermissionYes");
        //TODO show a dialog that says you have to have permission to use this screen
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

        new MakePhoneCall().dialNumberRequest(this,layoutComponent.getSupportContactPerson().getDialPhoneNumber());
    }

    public void clickSupportTextButton(View v){
        Timber.tag(TAG).d("clickSupportTextButton");
        Timber.tag(TAG).d("   ROLE              -> " +layoutComponent.getSupportContactPerson().getContactRole());
        Timber.tag(TAG).d("   display name      -> " +layoutComponent.getSupportContactPerson().getDisplayName());
        Timber.tag(TAG).d("   dial phone number -> " +layoutComponent.getSupportContactPerson().getDialPhoneNumber());

        new SendTextMessage().sendTextRequest(this,layoutComponent.getSupportContactPerson().getDialPhoneNumber());
    }

    public void clickCustomerCallButton(View v){
        Timber.tag(TAG).d("clickCustomerCallButton");
        Timber.tag(TAG).d("   ROLE              -> " +layoutComponent.getCustomerContactPerson().getContactRole());
        Timber.tag(TAG).d("   display name      -> " +layoutComponent.getCustomerContactPerson().getDisplayName());
        Timber.tag(TAG).d("   dial phone number -> " +layoutComponent.getCustomerContactPerson().getDialPhoneNumber());

        new MakePhoneCall().dialNumberRequest(this,layoutComponent.getSupportContactPerson().getDialPhoneNumber());
    }

    public void clickCustomerTextButton(View v){
        Timber.tag(TAG).d("clickCustomerTextButton");
        Timber.tag(TAG).d("   ROLE              -> " +layoutComponent.getCustomerContactPerson().getContactRole());
        Timber.tag(TAG).d("   display name      -> " +layoutComponent.getCustomerContactPerson().getDisplayName());
        Timber.tag(TAG).d("   dial phone number -> " +layoutComponent.getCustomerContactPerson().getDialPhoneNumber());

        new SendTextMessage().sendTextRequest(this, layoutComponent.getCustomerContactPerson().getDialPhoneNumber());
    }

    public void clickServiceProviderCallButton(View v){
        Timber.tag(TAG).d("clickServiceProviderCallButton");
        Timber.tag(TAG).d("   ROLE              -> " +layoutComponent.getServiceProviderContactPerson().getContactRole());
        Timber.tag(TAG).d("   display name      -> " +layoutComponent.getServiceProviderContactPerson().getDisplayName());
        Timber.tag(TAG).d("   dial phone number -> " +layoutComponent.getServiceProviderContactPerson().getDialPhoneNumber());

        new MakePhoneCall().dialNumberRequest(this,layoutComponent.getSupportContactPerson().getDialPhoneNumber());
    }

    public void clickServiceProviderTextButton(View v){
        Timber.tag(TAG).d("clickServiceProviderTextButton");
        Timber.tag(TAG).d("   ROLE              -> " +layoutComponent.getServiceProviderContactPerson().getContactRole());
        Timber.tag(TAG).d("   display name      -> " +layoutComponent.getServiceProviderContactPerson().getDisplayName());
        Timber.tag(TAG).d("   dial phone number -> " +layoutComponent.getServiceProviderContactPerson().getDialPhoneNumber());

        new SendTextMessage().sendTextRequest(this,layoutComponent.getServiceProviderContactPerson().getDialPhoneNumber());
    }

}
