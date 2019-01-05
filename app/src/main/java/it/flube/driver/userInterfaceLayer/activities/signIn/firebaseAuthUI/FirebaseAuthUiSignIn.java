/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.signIn.firebaseAuthUI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;

import it.flube.driver.dataLayer.AndroidDevice;
import timber.log.Timber;

/**
 * Created on 11/29/2017
 * Project : Driver
 */

public class FirebaseAuthUiSignIn {
    private static final String TAG = "FirebaseAuthUiSignIn";

    public void signInForResult(AppCompatActivity activity, int requestCode){
        Timber.tag(TAG).d("startingActivityForResult for AuthUI signin, requestCode -> " + requestCode);
        activity.startActivityForResult(getSignInIntent(), requestCode);
    }

    public void signIn(Context context){
        Timber.tag(TAG).d("startingActivity for AuthUI signin");
        context.startActivity(getSignInIntent());
    }

    //TODO rework per this url https://firebase.google.com/docs/auth/android/firebaseui

    private Intent getSignInIntent(){
        String termsUrl = AndroidDevice.getInstance().getCloudConfig().getDriverTermsUrl();
        String privacyUrl = AndroidDevice.getInstance().getCloudConfig().getDriverPrivacyUrl();

        return AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build()))
                //.setAvailableProviders(
                //        Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                //                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build()))
                //.setAllowNewEmailAccounts(false)
                .setTosAndPrivacyPolicyUrls(termsUrl,privacyUrl)
                //.setTosUrl(TermsOfServiceUrl)
                //.setPrivacyPolicyUrl(PrivacyPolicyUrl)
                .setIsSmartLockEnabled(true)
                .build();
    }
}
