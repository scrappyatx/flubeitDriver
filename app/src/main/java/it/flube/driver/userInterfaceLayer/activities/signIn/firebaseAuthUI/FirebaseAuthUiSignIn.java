/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.signIn.firebaseAuthUI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;

import timber.log.Timber;

/**
 * Created on 11/29/2017
 * Project : Driver
 */

public class FirebaseAuthUiSignIn {
    private static final String TAG = "FirebaseAuthUiSignIn";

    private String TermsOfServiceUrl = "https://docs.google.com/document/d/e/2PACX-1vRCZQiR9RYPpxvCnkWbHJeQJB2_6dNXfMVJTc0_NVrQU_VfnpKf9KDcACFz-EsvhEoRxVMiSYbJGYGA/pub";
    private String PrivacyPolicyUrl = "https://docs.google.com/document/d/e/2PACX-1vSQ6o176q6gvvGHk5aj4S8U1yvDflZKjir1hAAoa2sitkNERW1KHvOuEWfUYW_Cy1Fnu8pC9Xi6XCXP/pub";

    public void signInForResult(AppCompatActivity activity, int requestCode){
        Timber.tag(TAG).d("startingActivityForResult for AuthUI signin, requestCode -> " + requestCode);
        activity.startActivityForResult(getSignInIntent(), requestCode);
    }

    public void signIn(Context context){
        Timber.tag(TAG).d("startingActivity for AuthUI signin");
        context.startActivity(getSignInIntent());
    }

    private Intent getSignInIntent(){
        return AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                //.setAvailableProviders(
                //        Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                //                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()))
                .setAllowNewEmailAccounts(false)
                .setTosUrl(TermsOfServiceUrl)
                .setPrivacyPolicyUrl(PrivacyPolicyUrl)
                .setIsSmartLockEnabled(true)
                .build();
    }
}
