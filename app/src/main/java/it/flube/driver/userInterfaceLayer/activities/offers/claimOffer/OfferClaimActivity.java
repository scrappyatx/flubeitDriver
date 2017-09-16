/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.claimOffer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 7/22/2017
 * Project : Driver
 */

public class OfferClaimActivity extends AppCompatActivity {
    private static final String TAG = "OfferClaimActivity";

    private ActivityNavigator navigator;
    private OfferClaimController controller;
    private DrawerMenu drawer;

    private Offer offer;

    private TextView offerDescription;
    private TextView offerTime;
    private TextView offerDuration;
    private TextView offerBaseEarnings;
    private TextView offerExtraEarnings;
    private Button offerClaimButton;
    private LottieAnimationView claimOfferWaitingAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_claim);
        offerDescription = (TextView) findViewById(R.id.item_description);
        offerTime = (TextView) findViewById(R.id.item_time);
        offerDuration = (TextView) findViewById(R.id.item_duration);
        offerBaseEarnings = (TextView) findViewById(R.id.item_earnings);
        offerExtraEarnings = (TextView) findViewById(R.id.item_earnings_extra);

        claimOfferWaitingAnimation = (LottieAnimationView) findViewById(R.id.claim_offer_animation);
        claimOfferWaitingAnimation.setVisibility(View.INVISIBLE);

        offerClaimButton = (Button) findViewById(R.id.offer_claim_button);
        offerClaimButton.setVisibility(View.INVISIBLE);

        Timber.tag(TAG).d("onCreate");
    }
    @Override
    public void onResume() {
        super.onResume();


        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.offers_claim_activity_title);
        controller = new OfferClaimController();

        EventBus.getDefault().register(this);

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();
        Timber.tag(TAG).d(TAG, "onPause");

        super.onPause();
    }

    public void clickClaimButton(View v) {
        Timber.tag(TAG).d("clicked claim button");
        offerClaimButton.setVisibility(View.INVISIBLE);

        claimOfferWaitingAnimation.setVisibility(View.VISIBLE);
        claimOfferWaitingAnimation.setProgress(0);
        claimOfferWaitingAnimation.playAnimation();


        controller.claimOfferRequest(offer);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(OfferSelectedResponseHandler.UseCaseOfferSelectedEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("*** Offer was selected event");
        offer = event.getOffer();

        offerDescription.setText(offer.getServiceDescription());
        offerTime.setText(offer.getOfferDate());
        offerDuration.setText(offer.getOfferDuration());
        offerBaseEarnings.setText(offer.getEstimatedEarnings());
        offerExtraEarnings.setText(offer.getEstimatedEarningsExtra());

        offerClaimButton.setVisibility(View.VISIBLE);
    }

}
