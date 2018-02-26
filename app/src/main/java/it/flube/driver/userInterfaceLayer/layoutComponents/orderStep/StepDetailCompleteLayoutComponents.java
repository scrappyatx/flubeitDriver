/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.orderStep;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 1/21/2018
 * Project : Driver
 */

public class StepDetailCompleteLayoutComponents {
    public final static String TAG = "StepDetailCompleteLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     step_detail_complete.xml
    ///
    private Button button;
    private LottieAnimationView animation;

    public StepDetailCompleteLayoutComponents(AppCompatActivity activity, String buttonCaption){
        Timber.tag(TAG).d("creating component, buttonCaption -> " + buttonCaption);
        animation = (LottieAnimationView) activity.findViewById(R.id.step_complete_animation);
        button = (Button) activity.findViewById(R.id.step_complete_button);

        button.setText(buttonCaption);

        setInvisible();
        Timber.tag(TAG).d("...components created");
    }

    public void buttonWasClicked(){
        button.setVisibility(View.INVISIBLE);

        animation.setVisibility(View.VISIBLE);
        animation.setProgress(0);
        animation.playAnimation();
        Timber.tag(TAG).d("...offerClaimed");
    }

    public void setVisible(){
        animation.setVisibility(View.INVISIBLE);
        button.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }
    public void setInvisible(){
        animation.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        animation.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        animation = null;
        button = null;
        Timber.tag(TAG).d("components closed");
    }



}
