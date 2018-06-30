/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 4/30/2018
 * Project : Driver
 */
public class StepDetailSwipeCompleteButtonComponent {

    public final static String TAG = "StepDetailSwipeCompleteButtonComponent";
    ///
    ///     wrapper class for the layout file:
    ///     step_detail_swipe_complete.xml
    ///
    private SlideView slideButton;
    private TextView banner;
    private LottieAnimationView animation;

    public StepDetailSwipeCompleteButtonComponent(AppCompatActivity activity, String buttonCaption, SlideView.OnSlideCompleteListener listener){
        Timber.tag(TAG).d("creating component, buttonCaption -> " + buttonCaption);

        animation = (LottieAnimationView) activity.findViewById(R.id.step_complete_animation);
        banner = (TextView) activity.findViewById(R.id.step_complete_banner);
        slideButton = (SlideView) activity.findViewById(R.id.step_complete_swipe_button);

        slideButton.setText(buttonCaption);
        slideButton.setOnSlideCompleteListener(listener);

        setGone();
        Timber.tag(TAG).d("...components created");
    }

    public void showWaitingAnimationAndBanner(String bannerText){
        slideButton.setVisibility(View.GONE);

        banner.setText(bannerText);
        banner.setVisibility(View.VISIBLE);

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();
        Timber.tag(TAG).d("...showWaitingAnimationAndBanner");
    }

    public void showWaitingAnimatingWithNoBanner(){
        slideButton.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();
        Timber.tag(TAG).d("...showWaitingAnimationAndBanner");
    }

    public void showButton(){
        animation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);

        slideButton.setVisibility(View.VISIBLE);

        Timber.tag(TAG).d("...show Button");
    }

    public void setVisible(){
        animation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);

        slideButton.setVisibility(View.VISIBLE);

        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        animation.setVisibility(View.GONE);
        slideButton.setVisibility(View.GONE);

        banner.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        animation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);
        slideButton.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        animation = null;
        slideButton = null;
        banner = null;
        Timber.tag(TAG).d("components closed");
    }



}
