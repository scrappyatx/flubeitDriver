/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages.layoutComponents;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 7/6/2018
 * Project : Driver
 */
public class CommunicatingLayoutComponent {
    private static final String TAG = "CommunicatingLayoutComponent";

    ///
    ///     wrapper class for the layout file:
    ///     contact_person_customer.xml
    ///

    private LottieAnimationView waitingAnimation;
    private TextView banner;
    private String bannerCalling;
    private String bannerTexting;

    public CommunicatingLayoutComponent(AppCompatActivity activity){
        waitingAnimation = (LottieAnimationView) activity.findViewById(R.id.communication_animation);
        waitingAnimation.useHardwareAcceleration(true);
        waitingAnimation.enableMergePathsForKitKatAndAbove(true);


        banner = (TextView) activity.findViewById(R.id.communication_banner);
        bannerCalling = activity.getResources().getString(R.string.communication_activity_banner_caption_calling);
        bannerTexting = activity.getResources().getString(R.string.communication_activity_banner_caption_texting);
    }

    public void showWaitingForCall(){
        banner.setText(bannerCalling);
        banner.setVisibility(View.VISIBLE);
        startAnimationLoop();
    }

    public void showWaitingForText(){
        banner.setText(bannerTexting);
        banner.setVisibility(View.VISIBLE);
        startAnimationLoop();
    }

    private void startAnimationLoop(){
        waitingAnimation.setVisibility(View.VISIBLE);
        waitingAnimation.setProgress(0);
        waitingAnimation.playAnimation();
        Timber.tag(TAG).d("...startAnimationLoop");
    }

    public void setVisible(){
        waitingAnimation.setVisibility(View.INVISIBLE);
        banner.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        waitingAnimation.setVisibility(View.INVISIBLE);
        banner.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        waitingAnimation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        waitingAnimation.setImageBitmap(null);
        waitingAnimation = null;
        banner = null;
        bannerCalling = null;
        bannerTexting = null;
        Timber.tag(TAG).d("components closed");
    }

}
