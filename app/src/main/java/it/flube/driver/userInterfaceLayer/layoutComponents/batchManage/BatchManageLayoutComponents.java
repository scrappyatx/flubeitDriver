/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.batchManage;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch.BatchManageActivity;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 1/13/2018
 * Project : Driver
 */

public class BatchManageLayoutComponents {
    public final static String TAG = "BatchManageLayoutComponents";
    ///
    ///     wrapper class for the layout file:
    ///     activity_batch_manage_new.xml
    ///

    private LottieAnimationView batchStartWaitingAnimation;
    private TextView batchForfeit;
    private TextView batchForfeitBanner;
    private TextView batchStartBanner;
    private SlideView batchStart;
    private String batchGuid;



    public BatchManageLayoutComponents(AppCompatActivity activity, SlideView.OnSlideCompleteListener listener){

        batchForfeit = (TextView) activity.findViewById(R.id.batch_manage_forfeit);
        batchForfeitBanner = (TextView) activity.findViewById(R.id.batch_manage_forfeit_banner);
        batchStartBanner = (TextView) activity.findViewById(R.id.batch_manage_start_banner);

        // swipe to start button
        batchStart = (SlideView) activity.findViewById(R.id.batch_start_button);
        batchStart.setOnSlideCompleteListener(listener);

        //animation
        batchStartWaitingAnimation = (LottieAnimationView) activity.findViewById(R.id.claim_offer_animation);
        batchStartWaitingAnimation.useHardwareAcceleration(true);
        batchStartWaitingAnimation.enableMergePathsForKitKatAndAbove(true);

        Timber.tag(TAG).d("...components created");
    }

    public void setValuesAndShow(String batchGuid){
        this.batchGuid = batchGuid;

        batchForfeit.setVisibility(View.VISIBLE);
        batchForfeitBanner.setVisibility(View.INVISIBLE);
        batchStartBanner.setVisibility(View.INVISIBLE);

        batchStart.setVisibility(View.VISIBLE);
        batchStartWaitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...setValuesAndShow");
    }

    public String getBatchGuid(){
        return batchGuid;
    }

    public void batchStarted(){
        batchForfeit.setVisibility(View.INVISIBLE);
        batchForfeitBanner.setVisibility(View.INVISIBLE);
        batchStartBanner.setVisibility(View.VISIBLE);

        batchStart.setVisibility(View.INVISIBLE);

        batchStartWaitingAnimation.setVisibility(View.VISIBLE);
        batchStartWaitingAnimation.setProgress(0);
        batchStartWaitingAnimation.playAnimation();
        Timber.tag(TAG).d("...batchStarted");
    }

    public void forfeitRequestAsk(){
        batchForfeit.setVisibility(View.VISIBLE);
        batchForfeitBanner.setVisibility(View.INVISIBLE);
        batchStartBanner.setVisibility(View.INVISIBLE);

        batchStart.setVisibility(View.VISIBLE);
        batchStartWaitingAnimation.setVisibility(View.INVISIBLE);

        Timber.tag(TAG).d("...forfeitRequestAsk");
    }

    public void forfeitRequestStart(){
        batchForfeit.setVisibility(View.INVISIBLE);
        batchForfeitBanner.setVisibility(View.VISIBLE);
        batchStartBanner.setVisibility(View.INVISIBLE);

        batchStart.setVisibility(View.INVISIBLE);

        batchStartWaitingAnimation.setVisibility(View.VISIBLE);
        batchStartWaitingAnimation.setProgress(0);
        batchStartWaitingAnimation.playAnimation();
        Timber.tag(TAG).d("...forfeitRequestStart");
    }

    public void setVisible(){
        batchForfeit.setVisibility(View.VISIBLE);
        batchForfeitBanner.setVisibility(View.INVISIBLE);
        batchStartBanner.setVisibility(View.INVISIBLE);

        batchStart.setVisibility(View.VISIBLE);
        batchStartWaitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        batchForfeit.setVisibility(View.INVISIBLE);
        batchForfeitBanner.setVisibility(View.INVISIBLE);
        batchStartBanner.setVisibility(View.INVISIBLE);

        batchStart.setVisibility(View.INVISIBLE);
        batchStartWaitingAnimation.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        batchForfeit.setVisibility(View.GONE);
        batchForfeitBanner.setVisibility(View.GONE);
        batchStartBanner.setVisibility(View.GONE);

        batchStart.setVisibility(View.GONE);
        batchStartWaitingAnimation.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        batchForfeit = null;
        batchForfeitBanner = null;
        batchStartBanner = null;

        batchStart = null;

        batchStartWaitingAnimation.setImageBitmap(null);
        batchStartWaitingAnimation = null;
        Timber.tag(TAG).d("components closed");
    }
}
