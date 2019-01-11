/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.signIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.IdpResponse;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.dialogs.AuthUiResponseErrorAlertDialog;
import it.flube.driver.userInterfaceLayer.dialogs.QuitAppAlertDialog;
import it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers.UserInterfaceAuthAlertEventHandler;
import it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers.UserInterfaceAuthChangeEventHandler;
import it.flube.libbatchdata.builders.BuilderUtilities;
import timber.log.Timber;

/**
 * Created on 12/2/2017
 * Project : Driver
 */

public class SignInAuthUiLaunchActivity extends AppCompatActivity
        implements SignInAuthUiLaunchActivityLayoutComponents.Response {

    private static final String TAG = "SignInAuthUiLaunchActivity";

    private static final int REQUEST_CODE_SIGN_IN = 1172;

    private SignInAuthUiLaunchController controller;
    private SignInAuthUiLaunchActivityLayoutComponents layout;
    private UserInterfaceAuthChangeEventHandler userInterfaceAuthChangeEventHandler;
    private UserInterfaceAuthAlertEventHandler userInterfaceAuthAlertEventHandler;


    private String activityGuid;

    //TODO make layout components for this class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in_auth_ui_launch);

        controller = new SignInAuthUiLaunchController();
        layout = new SignInAuthUiLaunchActivityLayoutComponents(this, this);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume(){
        super.onResume();
        Timber.tag(TAG).d("onResume (%s)", activityGuid);

        userInterfaceAuthChangeEventHandler = new UserInterfaceAuthChangeEventHandler(this);
        userInterfaceAuthAlertEventHandler = new UserInterfaceAuthAlertEventHandler(this);

        if (AndroidDevice.getInstance().getCloudAuth().hasDriver()) {
            Timber.tag(TAG).d("...we have a signed in user");
            layout.showSigningInAnimation();
        } else {
            Timber.tag(TAG).d("...we don't have a signed in user");
            layout.showSignInButton();
        }

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
        layout.close();
        super.onDestroy();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Timber.tag(TAG).d("onActivityResult (%s), requestCode -> (%s)", Integer.toString(requestCode), Integer.toString(requestCode));

        layout.showSigningInAnimation();

        // Check which request we're responding to
        if (requestCode == REQUEST_CODE_SIGN_IN) {
            Timber.tag(TAG).d("...this is the response code for our activity");
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                //sign in SUCCESS
                Timber.tag(TAG).d("   ...Sign In SUCCESS!");
            } else {
                //sign in FAILURE
                layout.showSignInButton();
                Timber.tag(TAG).d("   ...Sign In FAILURE!");
                if (response == null) {
                    //user pressed the back button, show message cancelled
                    Timber.tag(TAG).d("      ...user cancelled");

                } else {
                    //some other problem
                    Timber.tag(TAG).d("      ...errorCode -> %s", Integer.toString(response.getError().getErrorCode()));
                    Timber.tag(TAG).d("      ...errorDescription -> %s", response.getError().getLocalizedMessage());

                    new AuthUiResponseErrorAlertDialog().getAlertDialog(this,response.getError().getLocalizedMessage());
                }

            }
        } else {
            Timber.tag(TAG).d("...this isn't the response code for our activity");
        }
    }


    public void signInButtonClicked() {
        //submit button clicked
        Timber.tag(TAG).d("clicked SignIn");
        controller.doSignIn(this, REQUEST_CODE_SIGN_IN);
    }


}
