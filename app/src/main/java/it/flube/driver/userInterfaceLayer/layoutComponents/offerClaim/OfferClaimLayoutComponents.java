/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.offerClaim;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
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

    public OfferClaimLayoutComponents(AppCompatActivity activity){
        claimOfferWaitingAnimation = (LottieAnimationView) activity.findViewById(R.id.claim_offer_animation);
        offerClaimButton = (Button) activity.findViewById(R.id.offer_claim_button);
        setInvisible();
        Timber.tag(TAG).d("...components created");
    }

    public void setValues(BatchDetail batchDetail){
        this.batchDetail = batchDetail;
        offerClaimButton.setVisibility(View.VISIBLE);
        claimOfferWaitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...setValues -> batchGuid = " + batchDetail.getBatchGuid());
    }

    public BatchDetail getBatchDetail(){
        Timber.tag(TAG).d("...getBatchGuid -> " + batchDetail.getBatchGuid());
        return this.batchDetail;
    }

    public void offerClaimStarted(){
        offerClaimButton.setVisibility(View.INVISIBLE);

        claimOfferWaitingAnimation.setVisibility(View.VISIBLE);
        claimOfferWaitingAnimation.setProgress(0);
        claimOfferWaitingAnimation.playAnimation();
        Timber.tag(TAG).d("...offerClaimed");
    }

    public void setVisible(){
        claimOfferWaitingAnimation.setVisibility(View.INVISIBLE);
        offerClaimButton.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }
    public void setInvisible(){
        claimOfferWaitingAnimation.setVisibility(View.INVISIBLE);
        offerClaimButton.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        claimOfferWaitingAnimation.setVisibility(View.GONE);
        offerClaimButton.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        offerClaimButton = null;
        claimOfferWaitingAnimation = null;
        Timber.tag(TAG).d("components closed");
    }

}
