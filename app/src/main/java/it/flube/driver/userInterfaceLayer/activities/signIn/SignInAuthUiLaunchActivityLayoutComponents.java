/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.signIn;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.account.AccountActivityLayoutComponents;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 1/7/2019
 * Project : Driver
 */
public class SignInAuthUiLaunchActivityLayoutComponents implements
    View.OnClickListener {

    public final static String TAG = "SignInAuthUiLaunchActivityLayoutComponents";


    private TextView banner;
    private LottieAnimationView animation;
    private Button signInButton;

    private Response response;

    public SignInAuthUiLaunchActivityLayoutComponents(AppCompatActivity activity, Response response){
        Timber.tag(TAG).d("creating");

        signInButton = (Button) activity.findViewById(R.id.sign_in_auth_ui_sign_in_button);
        signInButton.setOnClickListener(this);

        animation = (LottieAnimationView) activity.findViewById(R.id.sign_in_animation);
        animation.useHardwareAcceleration(true);
        animation.enableMergePathsForKitKatAndAbove(true);

        banner = (TextView) activity.findViewById(R.id.signing_in_banner);

        this.response = response;
    }

    public void showSigningInAnimation(){
        Timber.tag(TAG).d("showSigningInAnimation");
        // hide login button
        signInButton.setVisibility(View.GONE);

        // show signing in banner & signing in animation
        banner.setVisibility(View.VISIBLE);

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();
    }

    public void showSignInButton(){
        Timber.tag(TAG).d("showSignInButton");

        //hide banner & signing in animation
        banner.setVisibility(View.INVISIBLE);
        animation.setVisibility(View.INVISIBLE);

        //show signin button
        signInButton.setVisibility(View.VISIBLE);
    }

    public void setVisible(){
        animation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);

        signInButton.setVisibility(View.VISIBLE);

        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        animation.setVisibility(View.GONE);
        signInButton.setVisibility(View.GONE);

        banner.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        animation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);
        signInButton.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        animation.setImageBitmap(null);
        animation = null;
        signInButton = null;
        banner = null;
        response = null;
        Timber.tag(TAG).d("components closed");
    }


    /// button response interface
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        response.signInButtonClicked();
    }

    public interface Response {
        void signInButtonClicked();
    }
}
