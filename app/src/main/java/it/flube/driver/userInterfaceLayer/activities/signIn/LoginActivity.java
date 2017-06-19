/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.signIn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.rollbar.android.Rollbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.controllers.LoginController;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeNoActiveBatchActivityEventDELETE;
import it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.loginActivity.LoginResultWasUpdatedEvent;
import timber.log.Timber;


public class LoginActivity extends AppCompatActivity implements LoginActivityInterface {
    private static final String TAG = "LoginActivity";
    private TextView mErrorText;
    private Button mSubmitButton;
    private EditText mUsername;
    private EditText mPassword;
    private LottieAnimationView mLoadingAnimation;
    private LoginController mController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set UI element defaults
        mErrorText = (TextView) findViewById(R.id.login_error_text);
        mSubmitButton = (Button) findViewById(R.id.login_submit_button);
        mUsername = (EditText) findViewById(R.id.login_username_text);
        mPassword = (EditText) findViewById(R.id.login_password_text);
        mLoadingAnimation = (LottieAnimationView) findViewById(R.id.login_loading_animation);

        mErrorText.setVisibility(View.INVISIBLE);
        mSubmitButton.setVisibility(View.INVISIBLE);

        mLoadingAnimation.setVisibility(View.INVISIBLE);

        mUsername.setText("");
        mPassword.setText("");
        mUsername.addTextChangedListener(new TextChanged());
        mPassword.addTextChangedListener(new TextChanged());

        //instantiate contoller for this activity
        mController = new LoginController(this, this);

    }

    @Override
    public void onStart() {
        super.onStart();
        //set visibility of UI elements
        mErrorText.setVisibility(View.INVISIBLE);
        mSubmitButton.setVisibility(View.INVISIBLE);
        mLoadingAnimation.setVisibility(View.INVISIBLE);
        mLoadingAnimation.pauseAnimation();

        Timber.tag(TAG).d("onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.tag(TAG).d("onStop");
    }



    public void clickSubmitButton(View v) {
        //submit button clicked
        Timber.tag(TAG).d("clicked SUBMIT");

        //submit button disappears and loading animation starts
        mSubmitButton.setVisibility(View.INVISIBLE);
        mLoadingAnimation.setVisibility(View.VISIBLE);
        mLoadingAnimation.setProgress(0);
        mLoadingAnimation.playAnimation();

        //tell the controller to login using the user-supplied credentials
        mController.signIn(mUsername.getText().toString(), mPassword.getText().toString());
    }

    private class TextChanged implements TextWatcher {
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            mErrorText.setVisibility(View.INVISIBLE);
            if ((mUsername.getText().toString().length() > 0) && (mPassword.getText().toString().length() > 0)) {
                mSubmitButton.setVisibility(View.VISIBLE);
            } else {
                mSubmitButton.setVisibility(View.INVISIBLE);
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after){

        }
        public void afterTextChanged(Editable s) {

        }
    }

    ////
    //// UI update callback --> LoginActivityInterface
    ////
    public void LoginResultUpdate(String message) {
        Timber.tag(TAG).d("*** LoginResultUpdated --> " + message);
        mLoadingAnimation.setVisibility(View.INVISIBLE);
        mErrorText.setText(message);
        mErrorText.setVisibility(View.VISIBLE);

    }

}
