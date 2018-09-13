/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.signIn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.dialogs.QuitAppAlertDialog;
import it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers.UserInterfaceAuthAlertEventHandler;
import it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers.UserInterfaceAuthChangeEventHandler;
import it.flube.libbatchdata.builders.BuilderUtilities;
import timber.log.Timber;

/**
 * Created on 12/2/2017
 * Project : Driver
 */

public class SignInAuthUiLaunchActivity extends AppCompatActivity {

    private static final String TAG = "SignInAuthUiLaunchActivity";

    private static final int REQUEST_CODE_SIGN_IN = 1172;

    private SignInAuthUiLaunchController controller;
    private UserInterfaceAuthChangeEventHandler userInterfaceAuthChangeEventHandler;
    private UserInterfaceAuthAlertEventHandler userInterfaceAuthAlertEventHandler;

    private Button signInButton;

    private String activityGuid;

    //TODO make layout components for this class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in_auth_ui_launch);
        signInButton = (Button) findViewById(R.id.sign_in_auth_ui_sign_in_button);

        controller = new SignInAuthUiLaunchController();

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume(){
        super.onResume();



        userInterfaceAuthChangeEventHandler = new UserInterfaceAuthChangeEventHandler(this);
        userInterfaceAuthAlertEventHandler = new UserInterfaceAuthAlertEventHandler(this);

        if (AndroidDevice.getInstance().getCloudAuth().hasDriver()) {
            Timber.tag(TAG).d("...we have a signed in user");
            signInButton.setVisibility(View.INVISIBLE);
        } else {
            Timber.tag(TAG).d("...we don't have a signed in user");
            signInButton.setVisibility(View.VISIBLE);
        }
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }


    @Override
    public void onPause(){
        super.onPause();
        userInterfaceAuthChangeEventHandler.close();
        userInterfaceAuthAlertEventHandler.close();

        Timber.tag(TAG).d("onPause (%s)", activityGuid);
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        new QuitAppAlertDialog().getAlertDialog(this).show();
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
        super.onDestroy();

    }


    public void clickSignInButton(View v) {
        //submit button clicked
        Timber.tag(TAG).d("clicked SignIn");
        signInButton.setVisibility(View.INVISIBLE);

        controller.doSignIn(this, REQUEST_CODE_SIGN_IN);
    }


}
