/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.signIn;

import android.support.v7.app.AppCompatActivity;

import it.flube.driver.userInterfaceLayer.activities.signIn.firebaseAuthUI.FirebaseAuthUiSignIn;
import timber.log.Timber;

/**
 * Created on 12/2/2017
 * Project : Driver
 */

public class SignInAuthUiLaunchController {
    private static final String TAG = "SignInAuthUiLaunchController";


    public void doSignIn(AppCompatActivity activity, int requestCode){
        Timber.tag(TAG).d("clicked sign in button");
        new FirebaseAuthUiSignIn().signInForResult(activity, requestCode);
        //new FirebaseAuthUiSignIn().signIn(activity);
    }
    public void close(){

    }
}
