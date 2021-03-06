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
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.ContactPersonsByServiceOrder;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class MessagesActivity extends AppCompatActivity implements
        CheckCallPermission.Response,
        UseCaseGetContactPersons.Response,
        CommunicationActivityLayoutComponent.Response {
    private static final String TAG = "MessagesActivity";

    private MessagesController controller;

    private CommunicationActivityLayoutComponent layoutComponent;
    private CheckCallPermission checkCallPermission;

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);


        controller = new MessagesController();

        layoutComponent = new CommunicationActivityLayoutComponent(this, this);
        checkCallPermission = new CheckCallPermission();

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume() {
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
        super.onResume();
        DrawerMenu.getInstance().setActivity(this, R.string.communication_activity_title);
        //see if we have permission to make a call
        checkCallPermission.checkCallPermissionRequest(this, this);

    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
        DrawerMenu.getInstance().clearActivity();
        super.onPause();
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }

    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
        super.onStop();
    }

    @Override
    public void onDestroy(){
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
        controller.close();
        layoutComponent.close();
        checkCallPermission.close();
        super.onDestroy();

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
    //// CommunicationActivityLayoutComponent.Response interface
    ////

    public void appInfoButtonClicked(){
        Timber.tag(TAG).d("appInfoButtonClicked");
        checkCallPermission.gotoSettings(this);
    }

    public void supportCallButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("supportCallButtonClicked, dialPhoneNumber -> " + dialPhoneNumber);
        layoutComponent.showWaitingToCall();
        new MakePhoneCall().dialNumberRequest(this, dialPhoneNumber);
    }

    public void supportTextButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("supportTextButtonClicked, dialPhoneNumber -> " + dialPhoneNumber);
        layoutComponent.showWaitingToText();
        new SendTextMessage().sendTextRequest(this, dialPhoneNumber);
    }

    public void customerCallButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("customerCallButtonClicked, dialPhoneNumber -> " + dialPhoneNumber);
        layoutComponent.showWaitingToCall();
        new MakePhoneCall().dialNumberRequest(this, dialPhoneNumber);
    }

    public void customerTextButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("customerTextButtonClicked, dialPhoneNumber -> " + dialPhoneNumber);
        layoutComponent.showWaitingToText();
        new SendTextMessage().sendTextRequest(this, dialPhoneNumber);
    }

    public void serviceProviderCallButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("serviceProviderCallButtonClicked, dialPhoneNumber -> " + dialPhoneNumber);
        layoutComponent.showWaitingToCall();
        new MakePhoneCall().dialNumberRequest(this, dialPhoneNumber);
    }

    public void serviceProviderTextButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("serviceProviderTextButtonClicked, dialPhoneNumber -> " + dialPhoneNumber);
        layoutComponent.showWaitingToText();
        new SendTextMessage().sendTextRequest(this, dialPhoneNumber);
    }

}
