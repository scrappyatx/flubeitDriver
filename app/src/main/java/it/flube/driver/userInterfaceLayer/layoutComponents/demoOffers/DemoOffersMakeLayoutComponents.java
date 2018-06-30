/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.demoOffers;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 1/30/2018
 * Project : Driver
 */

public class DemoOffersMakeLayoutComponents {
    public final static String TAG = "DemoOffersMakeLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     activity_demo_offers_make.xml
    ///

    private TextView instructions;
    private Button twoStepButton;
    private Button autoPhotoButton;
    private Button oilChangeButton;
    private LottieAnimationView makeOfferWaitingAnimation;

    public DemoOffersMakeLayoutComponents(AppCompatActivity activity){
        instructions = (TextView) activity.findViewById(R.id.instructionText);
        makeOfferWaitingAnimation = (LottieAnimationView) activity.findViewById(R.id.demo_offer_make_animation);

        twoStepButton = (Button) activity.findViewById(R.id.button_two_step);
        autoPhotoButton = (Button) activity.findViewById(R.id.button_auto_photo);
        oilChangeButton = (Button) activity.findViewById(R.id.button_oil_change);

        setInvisible();
        Timber.tag(TAG).d("...components created");
    }

    public void setReadyToMake(){
        instructions.setVisibility(View.VISIBLE);
        twoStepButton.setVisibility(View.GONE);
        autoPhotoButton.setVisibility(View.GONE);
        oilChangeButton.setVisibility(View.VISIBLE);
        makeOfferWaitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...setReadyToMake");
    }

    public void setTooManyOffers(){
        instructions.setVisibility(View.VISIBLE);
        twoStepButton.setVisibility(View.GONE);
        autoPhotoButton.setVisibility(View.GONE);
        oilChangeButton.setVisibility(View.INVISIBLE);
        makeOfferWaitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...setTooManyOffers");
    }

    public void offerMakeStarted(){
        twoStepButton.setVisibility(View.GONE);
        autoPhotoButton.setVisibility(View.GONE);
        oilChangeButton.setVisibility(View.INVISIBLE);

        makeOfferWaitingAnimation.setVisibility(View.VISIBLE);
        makeOfferWaitingAnimation.setProgress(0);
        makeOfferWaitingAnimation.playAnimation();
        Timber.tag(TAG).d("...offerMakeStarted");
    }

    public void setVisible(){
        instructions.setVisibility(View.VISIBLE);
        twoStepButton.setVisibility(View.GONE);
        autoPhotoButton.setVisibility(View.GONE);
        oilChangeButton.setVisibility(View.VISIBLE);

        Timber.tag(TAG).d("...set VISIBLE");
    }
    public void setInvisible(){
        instructions.setVisibility(View.INVISIBLE);
        makeOfferWaitingAnimation.setVisibility(View.INVISIBLE);
        twoStepButton.setVisibility(View.GONE);
        autoPhotoButton.setVisibility(View.GONE);
        oilChangeButton.setVisibility(View.INVISIBLE);

        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        instructions.setVisibility(View.GONE);
        twoStepButton.setVisibility(View.GONE);
        autoPhotoButton.setVisibility(View.GONE);
        oilChangeButton.setVisibility(View.GONE);

        makeOfferWaitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        instructions =null;
        autoPhotoButton = null;
        twoStepButton = null;
        oilChangeButton = null;
        makeOfferWaitingAnimation = null;
        Timber.tag(TAG).d("components closed");
    }

}
