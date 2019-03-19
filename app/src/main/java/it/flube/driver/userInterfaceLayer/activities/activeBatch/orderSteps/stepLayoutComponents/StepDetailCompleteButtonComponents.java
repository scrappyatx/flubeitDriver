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
import timber.log.Timber;

/**
 * Created on 1/21/2018
 * Project : Driver
 */

public class StepDetailCompleteButtonComponents implements
        Button.OnClickListener {
    public final static String TAG = "StepDetailCompleteButtonComponents";
    ///
    ///     wrapper class for the layout file:
    ///     step_detail_complete.xml
    ///
    private Button button;
    private TextView banner;
    private LottieAnimationView animation;

    private Response response;

    public StepDetailCompleteButtonComponents(AppCompatActivity activity, String buttonCaption, Response response){
        Timber.tag(TAG).d("creating component, buttonCaption -> " + buttonCaption);
        animation = (LottieAnimationView) activity.findViewById(R.id.step_complete_animation);
        animation.useHardwareAcceleration(true);
        animation.enableMergePathsForKitKatAndAbove(true);

        banner = (TextView) activity.findViewById(R.id.step_complete_banner);

        button = (Button) activity.findViewById(R.id.step_complete_button);
        button.setOnClickListener(this);

        button.setText(buttonCaption);

        this.response = response;
        setGone();
        Timber.tag(TAG).d("...components created");
    }

    public void showWaitingAnimationAndBanner(String bannerText){
        button.setVisibility(View.GONE);

        banner.setText(bannerText);
        banner.setVisibility(View.VISIBLE);

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();
        Timber.tag(TAG).d("...showWaitingAnimationAndBanner");
    }

    public void showWaitingAnimationWithNoBanner(){
        button.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();
        Timber.tag(TAG).d("...showWaitingAnimationAndBanner");
    }

    public void showButton(){
        animation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);

        button.setVisibility(View.VISIBLE);

        Timber.tag(TAG).d("...show Button");
    }

    public void setVisible(){
        animation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);

        button.setVisibility(View.VISIBLE);

        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        animation.setVisibility(View.GONE);
        button.setVisibility(View.GONE);

        banner.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        animation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        animation.setImageBitmap(null);
        animation = null;
        button = null;
        banner = null;
        response = null;
        Timber.tag(TAG).d("components closed");
    }

    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        response.stepDetailCompleteButtonClicked();
    }

    public interface Response {
        void stepDetailCompleteButtonClicked();
    }

}
