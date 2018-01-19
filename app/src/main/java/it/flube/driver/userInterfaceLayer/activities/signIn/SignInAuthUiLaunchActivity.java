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
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.dialogs.QuitAppAlertDialog;
import it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers.UserInterfaceAuthAlertEventHandler;
import it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers.UserInterfaceAuthChangeEventHandler;
import timber.log.Timber;

/**
 * Created on 12/2/2017
 * Project : Driver
 */

public class SignInAuthUiLaunchActivity extends AppCompatActivity {

    private static final String TAG = "SignInAuthUiLaunchActivity";

    private static final int REQUEST_CODE_SIGN_IN = 1172;

    private SignInAuthUiLaunchController controller;
    private ActivityNavigator navigator;
    private UserInterfaceAuthChangeEventHandler userInterfaceAuthChangeEventHandler;
    private UserInterfaceAuthAlertEventHandler userInterfaceAuthAlertEventHandler;

    private Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in_auth_ui_launch);
        signInButton = (Button) findViewById(R.id.sign_in_auth_ui_sign_in_button);

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onResume(){
        super.onResume();

        controller = new SignInAuthUiLaunchController();
        navigator = new ActivityNavigator();
        userInterfaceAuthChangeEventHandler = new UserInterfaceAuthChangeEventHandler(this, navigator);
        userInterfaceAuthAlertEventHandler = new UserInterfaceAuthAlertEventHandler(this, navigator);

        if (AndroidDevice.getInstance().getUser().isSignedIn()) {
            Timber.tag(TAG).d("...we have a signed in user");
            signInButton.setVisibility(View.INVISIBLE);
        } else {
            Timber.tag(TAG).d("...we don't have a signed in user");
            signInButton.setVisibility(View.VISIBLE);
        }
        Timber.tag(TAG).d("onResume");
    }


    @Override
    public void onPause(){
        super.onPause();
        userInterfaceAuthChangeEventHandler.close();
        userInterfaceAuthAlertEventHandler.close();
        controller.close();
        Timber.tag(TAG).d("onPause");
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("back button pressed");
        new QuitAppAlertDialog().getAlertDialog(this).show();
    }

    public void clickSignInButton(View v) {
        //submit button clicked
        Timber.tag(TAG).d("clicked SignIn");
        signInButton.setVisibility(View.INVISIBLE);

        controller.doSignIn(this, REQUEST_CODE_SIGN_IN);
    }


}
