/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.offerClaim;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 1/13/2018
 * Project : Driver
 */

public class OfferClaimLayoutComponents {
    public final static String TAG = "OfferClaimLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     activity_offer_claim_new.xml
    ///

    private Button offerClaimButton;
    private LottieAnimationView claimOfferWaitingAnimation;
    private BatchDetail batchDetail;
    private TextView offerClaimBanner;
    private OfferConstants.OfferType offerType;

    public OfferClaimLayoutComponents(AppCompatActivity activity){
        claimOfferWaitingAnimation = (LottieAnimationView) activity.findViewById(R.id.claim_offer_animation);
        offerClaimButton = (Button) activity.findViewById(R.id.offer_claim_button);
        offerClaimBanner = (TextView) activity.findViewById(R.id.offer_claim_request_banner);
        setInvisible();
        Timber.tag(TAG).d("...components created");
    }

    public void setValues(OfferConstants.OfferType offerType, BatchDetail batchDetail){
        this.offerType = offerType;
        this.batchDetail = batchDetail;
        offerClaimButton.setVisibility(View.VISIBLE);
        offerClaimBanner.setVisibility(View.GONE);
        claimOfferWaitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...setValues -> batchGuid = " + batchDetail.getBatchGuid());
        Timber.tag(TAG).d("...setValues -> offerType = " + offerType);
    }

    public BatchDetail getBatchDetail(){
        Timber.tag(TAG).d("...getBatchGuid -> " + batchDetail.getBatchGuid());
        return this.batchDetail;
    }

    public OfferConstants.OfferType getOfferType(){
        Timber.tag(TAG).d("...getOfferType -> " + offerType);
        return this.offerType;
    }

    public void offerClaimStarted(){
        offerClaimButton.setVisibility(View.INVISIBLE);
        offerClaimBanner.setVisibility(View.VISIBLE);

        claimOfferWaitingAnimation.setVisibility(View.VISIBLE);
        claimOfferWaitingAnimation.setProgress(0);
        claimOfferWaitingAnimation.playAnimation();
        Timber.tag(TAG).d("...offerClaimed");
    }

    public void setVisible(){
        claimOfferWaitingAnimation.setVisibility(View.INVISIBLE);
        offerClaimBanner.setVisibility(View.INVISIBLE);
        offerClaimButton.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }
    public void setInvisible(){
        offerClaimBanner.setVisibility(View.INVISIBLE);
        claimOfferWaitingAnimation.setVisibility(View.INVISIBLE);
        offerClaimButton.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        offerClaimBanner.setVisibility(View.GONE);
        claimOfferWaitingAnimation.setVisibility(View.GONE);
        offerClaimButton.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        offerClaimButton = null;
        claimOfferWaitingAnimation = null;
        offerClaimBanner = null;
        Timber.tag(TAG).d("components closed");
    }

}
