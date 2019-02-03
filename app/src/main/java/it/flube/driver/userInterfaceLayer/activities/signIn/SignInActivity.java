/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.signIn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import timber.log.Timber;


public class SignInActivity extends AppCompatActivity {
    private static final String TAG = "SignInActivity";
    private TextView mErrorText;
    private Button mSubmitButton;
    private EditText mUsername;
    private EditText mPassword;
    private LottieAnimationView mLoadingAnimation;
    private SignInController mController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUiElements();
        Timber.tag(TAG).d("onCreate");
    }

    private void setupUiElements() {
        setContentView(R.layout.activity_login);
        mErrorText = (TextView) findViewById(R.id.login_error_text);
        mSubmitButton = (Button) findViewById(R.id.login_submit_button);
        mUsername = (EditText) findViewById(R.id.login_username_text);
        mPassword = (EditText) findViewById(R.id.login_password_text);

        mLoadingAnimation = (LottieAnimationView) findViewById(R.id.login_loading_animation);
        mLoadingAnimation.useHardwareAcceleration(true);
        mLoadingAnimation.enableMergePathsForKitKatAndAbove(true);

        mErrorText.setVisibility(View.INVISIBLE);
        mSubmitButton.setVisibility(View.INVISIBLE);

        mLoadingAnimation.setVisibility(View.INVISIBLE);

        mUsername.setText("");
        mPassword.setText("");
        mUsername.addTextChangedListener(new TextChanged());
        mPassword.addTextChangedListener(new TextChanged());
        Timber.tag(TAG).d("setupUiElements complete");
    }

    @Override
    public void onStart() {
        super.onStart();

        Timber.tag(TAG).d("onStart");
    }

    private void updateUiElementVisibility(){
        mErrorText.setVisibility(View.INVISIBLE);
        mSubmitButton.setVisibility(View.INVISIBLE);
        mLoadingAnimation.setVisibility(View.INVISIBLE);
        mLoadingAnimation.pauseAnimation();
        Timber.tag(TAG).d("updateUiElementVisibility complete");
    }

    @Override
    public void onResume(){
        super.onResume();

        EventBus.getDefault().register(this);

        mController = new SignInController();

        updateUiElementVisibility();

        Timber.tag(TAG).d("onResume");
    }


    @Override
    public void onPause(){
        EventBus.getDefault().unregister(this);

        Timber.tag(TAG).d("onPause");

        super.onPause();
    }

    @Override
    public void onDestroy(){
        Timber.tag(TAG).d("onDestroy");
        mLoadingAnimation.setImageBitmap(null);
        super.onDestroy();
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
        mController.doSignIn(mUsername.getText().toString(), mPassword.getText().toString());
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




}
