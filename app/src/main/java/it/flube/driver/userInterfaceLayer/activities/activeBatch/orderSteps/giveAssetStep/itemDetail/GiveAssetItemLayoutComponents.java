/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep.itemDetail;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;
import timber.log.Timber;

/**
 * Created on 8/16/2018
 * Project : Driver
 */
public class GiveAssetItemLayoutComponents implements
        View.OnClickListener {
    private static final String TAG = "GiveAssetItemLayoutComponents";

    private static final String HAVE_BUTTON_TAG = "assetItemHaveButton";
    private static final String DONT_HAVE_BUTTON_TAG = "assetItemDontHaveButton";
    private static final String FINISHED_BUTTON_TAG = "assetItemFinishedButton";

    private ImageView assetIcon;
    private TextView title;
    private TextView description;
    private TextView identifier;
    private Button haveButton;
    private Button dontHaveButton;
    private TextView haveText;
    private TextView dontHaveText;
    private Button finishedButton;
    private LottieAnimationView waitingAnimation;

    private AssetTransfer assetTransfer;
    private String batchGuid;
    private String serviceOrderGuid;
    private String stepGuid;
    private Response response;

    public GiveAssetItemLayoutComponents(AppCompatActivity activity, Response response) {
        this.response = response;

        assetIcon = (ImageView) activity.findViewById(R.id.asset_item_icon);
        title = (TextView) activity.findViewById(R.id.asset_item_title);
        description = (TextView) activity.findViewById(R.id.asset_item_description);
        identifier = (TextView) activity.findViewById(R.id.asset_item_identifier);
        haveText = (TextView) activity.findViewById(R.id.asset_item_have_it_text);
        dontHaveText = (TextView) activity.findViewById(R.id.asset_item_dont_have_it_text);

        haveButton = (Button) activity.findViewById(R.id.asset_item_have_it_button);
        haveButton.setTag(HAVE_BUTTON_TAG);
        haveButton.setOnClickListener(this);

        dontHaveButton = (Button) activity.findViewById(R.id.asset_item_dont_have_it_button);
        dontHaveButton.setTag(DONT_HAVE_BUTTON_TAG);
        dontHaveButton.setOnClickListener(this);

        finishedButton = (Button) activity.findViewById(R.id.asset_item_finished_button);
        finishedButton.setTag(FINISHED_BUTTON_TAG);
        finishedButton.setOnClickListener(this);

        waitingAnimation = (LottieAnimationView) activity.findViewById(R.id.asset_item_waiting_animation);
        waitingAnimation.useHardwareAcceleration(true);
        waitingAnimation.enableMergePathsForKitKatAndAbove(true);


        setInvisible();
        Timber.tag(TAG).d("created");
    }

    public void showWaitingAnimation() {
        Timber.tag(TAG).d("showWaitingAnimation");

        haveButton.setVisibility(View.INVISIBLE);
        dontHaveButton.setVisibility(View.INVISIBLE);
        haveText.setVisibility(View.INVISIBLE);
        dontHaveText.setVisibility(View.INVISIBLE);

        waitingAnimation.setVisibility(View.VISIBLE);
        waitingAnimation.setProgress(0);
        waitingAnimation.playAnimation();
    }

    public void setValues(String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer) {
        Timber.tag(TAG).d("setValues START...");

        this.assetTransfer = assetTransfer;
        this.batchGuid = batchGuid;
        this.serviceOrderGuid = serviceOrderGuid;
        this.stepGuid = stepGuid;

        Picasso.get()
                .load(assetTransfer.getAsset().getDisplayImageUrl())
                //.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                //.fit()
                //.centerInside()
                .into(assetIcon);

        title.setText(assetTransfer.getAsset().getDisplayTitle());
        description.setText(assetTransfer.getAsset().getDisplayDescription());
        identifier.setText(assetTransfer.getAsset().getDisplayIdentifier());

        Timber.tag(TAG).d("...setValues COMPLETE");
    }

    public void setVisible() {
        Timber.tag(TAG).d("setVisible START..");
        if (assetTransfer != null) {
            assetIcon.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            description.setVisibility(View.VISIBLE);
            identifier.setVisibility(View.VISIBLE);
            finishedButton.setVisibility(View.VISIBLE);
            waitingAnimation.setVisibility(View.GONE);

            switch (assetTransfer.getTransferStatus()) {
                case NOT_ATTEMPTED:
                    showDontHaveItem();
                    break;
                case COMPLETED_FAILED:
                    showDontHaveItem();
                    break;
                case COMPLETED_SUCCESS:
                    showHaveItem();
                    break;
                default:
                    showDontHaveItem();
                    //should never get here
                    Timber.tag(TAG).w("should never get here while trying to set visible");
                    break;
            }
        } else {
            setInvisible();
        }
        Timber.tag(TAG).d("...setVisible COMPLETE");
    }

    private void showHaveItem() {
        //if we have item, show the have elements
        haveButton.setVisibility(View.VISIBLE);
        haveText.setVisibility(View.VISIBLE);

        dontHaveButton.setVisibility(View.INVISIBLE);
        dontHaveText.setVisibility(View.INVISIBLE);
    }

    private void showDontHaveItem() {
        //if we don't have the item, show the dontHave elements
        dontHaveButton.setVisibility(View.VISIBLE);
        dontHaveText.setVisibility(View.VISIBLE);

        haveButton.setVisibility(View.INVISIBLE);
        haveText.setVisibility(View.INVISIBLE);
    }

    public void setInvisible() {
        assetIcon.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        identifier.setVisibility(View.INVISIBLE);
        finishedButton.setVisibility(View.INVISIBLE);
        haveButton.setVisibility(View.INVISIBLE);
        dontHaveButton.setVisibility(View.INVISIBLE);
        haveText.setVisibility(View.INVISIBLE);
        dontHaveText.setVisibility(View.INVISIBLE);
        waitingAnimation.setVisibility(View.GONE);

        Timber.tag(TAG).d("setInvisible");
    }

    public void setGone() {
        assetIcon.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        description.setVisibility(View.GONE);
        identifier.setVisibility(View.GONE);
        finishedButton.setVisibility(View.GONE);
        haveButton.setVisibility(View.GONE);
        dontHaveButton.setVisibility(View.GONE);
        haveText.setVisibility(View.GONE);
        dontHaveText.setVisibility(View.GONE);
        waitingAnimation.setVisibility(View.GONE);
        Timber.tag(TAG).d("setGone");
    }

    public void close() {
        assetIcon = null;
        title = null;
        description = null;
        identifier = null;
        haveButton = null;
        dontHaveButton = null;
        finishedButton = null;
        haveText = null;
        dontHaveText = null;
        waitingAnimation.setImageBitmap(null);
        waitingAnimation = null;

        assetTransfer = null;
        batchGuid = null;
        serviceOrderGuid = null;
        stepGuid = null;
        response = null;
        Timber.tag(TAG).d("close");
    }

    ///
    /// View.OnClickListener interface
    ///
    public void onClick(View v) {
        Timber.tag(TAG).d("onClick, tag -> " + v.getTag());
        switch ((String) v.getTag()) {
            case HAVE_BUTTON_TAG:
                showDontHaveItem();
                assetTransfer.setTransferStatus(AssetTransferInterface.TransferStatus.COMPLETED_FAILED);
                break;
            case DONT_HAVE_BUTTON_TAG:
                showHaveItem();
                assetTransfer.setTransferStatus(AssetTransferInterface.TransferStatus.COMPLETED_SUCCESS);
                break;
            case FINISHED_BUTTON_TAG:
                response.finishedButtonClicked(batchGuid, serviceOrderGuid, stepGuid, assetTransfer);
                break;
            default:
                Timber.tag(TAG).w("should never get here");
                break;
        }
    }

    public interface Response {
        void finishedButtonClicked(String batchGuid, String serviceOrderGuid, String stepGuid, AssetTransfer assetTransfer);
    }
}

