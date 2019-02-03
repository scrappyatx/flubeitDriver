/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.waitingToFinishBatch;

import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import it.flube.driver.R;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 6/19/2018
 * Project : Driver
 */
public class WaitingToFinishBatchLayoutComponents implements
        Animator.AnimatorListener {
    public final static String TAG = "WaitingToFinishBatchLayoutComponents";

    ///
    ///     wrapper class for the layout file:
    ///     activity_batch_waiting_to_finish.xml
    ///

    private TextView banner;
    private LottieAnimationView waitingAnimation;
    private LottieAnimationView finishedAnimation;
    private FinishedAnimationResponse response;

    public WaitingToFinishBatchLayoutComponents(AppCompatActivity activity){
        Timber.tag(TAG).d("creating component");

        waitingAnimation = (LottieAnimationView) activity.findViewById(R.id.waiting_to_finish_animation);
        waitingAnimation.useHardwareAcceleration(true);
        waitingAnimation.enableMergePathsForKitKatAndAbove(true);

        finishedAnimation = (LottieAnimationView) activity.findViewById(R.id.finished_animation);
        waitingAnimation.useHardwareAcceleration(true);
        waitingAnimation.enableMergePathsForKitKatAndAbove(true);

        banner = (TextView) activity.findViewById(R.id.batch_waiting_to_finish_banner);

        setGone();
        Timber.tag(TAG).d("...components created");
    }

    public void showWaitingAnimation(AppCompatActivity activity){
        banner.setText(activity.getString(R.string.batch_waiting_to_finish_banner));
        banner.setVisibility(View.VISIBLE);

        finishedAnimation.setVisibility(View.GONE);

        waitingAnimation.setVisibility(View.VISIBLE);
        waitingAnimation.setProgress(0);
        waitingAnimation.playAnimation();

        Timber.tag(TAG).d("...showWaitingAnimationAndBanner");
    }

    public void showFinishedAnimation(AppCompatActivity activity, FinishedAnimationResponse response ){
        this.response = response;

        banner.setText(activity.getString(R.string.batch_waiting_to_finish_complete_banner));
        banner.setVisibility(View.VISIBLE);

        waitingAnimation.setVisibility(View.GONE);

        finishedAnimation.setVisibility(View.VISIBLE);
        finishedAnimation.addAnimatorListener(this);
        finishedAnimation.setProgress(0);
        finishedAnimation.setSpeed(0.5f);
        finishedAnimation.loop(false);
        finishedAnimation.playAnimation();

        Timber.tag(TAG).d("...showFinishedAnimation");
    }

    public void stopFinishedAnimation(){
        finishedAnimation.removeAnimatorListener(this);
        finishedAnimation.setProgress(1.0f);
        finishedAnimation.pauseAnimation();
    }

    public void setVisible(){
        waitingAnimation.setVisibility(View.GONE);
        finishedAnimation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        waitingAnimation.setVisibility(View.GONE);
        finishedAnimation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        waitingAnimation.setVisibility(View.GONE);
        finishedAnimation.setVisibility(View.GONE);
        banner.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        waitingAnimation.setImageBitmap(null);
        finishedAnimation.setImageBitmap(null);

        waitingAnimation = null;
        finishedAnimation = null;
        banner = null;
        response = null;
        Timber.tag(TAG).d("components closed");
    }

    public interface FinishedAnimationResponse {
        void finishedAnimationComplete();
    }

    ////
    //// Animator.AnimatorListener interface
    ////
    public void onAnimationStart(Animator animation){
        Timber.tag(TAG).d("...onAnimationStart");
    }

    public void onAnimationEnd(Animator animation){
        Timber.tag(TAG).d("...onAnimationEnd");
        response.finishedAnimationComplete();
    }

    public void onAnimationCancel(Animator animation){
        Timber.tag(TAG).d("...onAnimationCancel");
    }

    public void onAnimationRepeat(Animator animation){
        Timber.tag(TAG).d("...onAnimationRepeat");
    }

}
