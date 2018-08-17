/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.layoutComponents;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.HashMap;
import java.util.Map;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;
import timber.log.Timber;

import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferStatus.COMPLETED_FAILED;
import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferStatus.COMPLETED_SUCCESS;
import static it.flube.libbatchdata.interfaces.AssetTransferInterface.TransferStatus.NOT_ATTEMPTED;

/**
 * Created on 7/25/2018
 * Project : Driver
 */
public class AssetTransferItemsRowLayoutComponents implements
    View.OnClickListener {
    private static final String TAG = "AssetTransferItemsRowLayoutComponents";

    private TextView title;
    private IconTextView overallStatus;
    private ConstraintLayout itemRow;

    private Response response;
    private Boolean allTransferComplete;

    public AssetTransferItemsRowLayoutComponents(AppCompatActivity activity, Response response){
        this.response = response;
        title = (TextView) activity.findViewById(R.id.asset_transfer_row_title);
        overallStatus = (IconTextView) activity.findViewById(R.id.asset_transfer_status);

        allTransferComplete = false;

        itemRow = (ConstraintLayout) activity.findViewById(R.id.asset_transfer_row_item);
        itemRow.setClickable(true);
        itemRow.setFocusable(true);
        itemRow.setFocusableInTouchMode(true);
        itemRow.setOnClickListener(this);

        Timber.tag(TAG).d("...created");
    }

    public void setValues(AppCompatActivity activity, HashMap<String, AssetTransfer> assetTransferHashMap){
        Timber.tag(TAG).d("...setValues START");
        Timber.tag(TAG).d("   ...assetTransferHashMap.size() -> " + assetTransferHashMap.size());
        ///
        ///  Set the title to the format '%s Items' based on number of items in the asset transfer hashmap
        title.setText(activity.getResources().getQuantityString(R.plurals.asset_transfer_items_available, assetTransferHashMap.size(), assetTransferHashMap.size()));
        Timber.tag(TAG).d("   ...title -> " + title.getText());

        //set the overall status
        overallStatus.setText(getOverallStatusString(assetTransferHashMap));
    }

    private String getOverallStatusString(HashMap<String, AssetTransfer> assetTransferHashMap){
        ///  Set the transfer status based on iterating over all asset transfers in the hashmap.  each one is either:
        ///       COMPLETED_SUCCESS,
        //        COMPLETED_FAILED,
        //        NOT_ATTEMPTED
        ///
        ///   If any are NOT_ATTEMPTED, then set transferStatus = NOT_ATTEMPTED
        ///   otherwise, set to COMPLETED_SUCCESS
        ///
        Timber.tag(TAG).d("getOverallStatusString START...");
        /// assume it is COMPLETED_SUCCESS
        Boolean anyNotAttempted = false;
        Boolean anyCompletedSuccess = false;
        String overallStatusIconText = COMPLETED_SUCCESS.toString();

        //assume all items are done
        allTransferComplete = true;

        // check each asset transfer's transfer status
        for (Map.Entry<String, AssetTransfer> thisAssetTransfer : assetTransferHashMap.entrySet()){
            Timber.tag(TAG).d("      asset transfer key -> " + thisAssetTransfer.getKey() + " asset transfer status => " + thisAssetTransfer.getValue().getTransferStatus().toString());

            switch (thisAssetTransfer.getValue().getTransferStatus()){
                case NOT_ATTEMPTED:
                    anyNotAttempted = true;
                    /// if we have a NOT_ATTEMPTED, this will always be the overall icon
                    overallStatusIconText = thisAssetTransfer.getValue().getStatusIconText().get(NOT_ATTEMPTED.toString());
                    Timber.tag(TAG).d("      ...setting overallTranserText -> NOT_ATTEMPTED");

                    // if any are not attempted, then all transfer complete = false
                    allTransferComplete = false;

                    break;
                case COMPLETED_FAILED:
                    /// this will only be the overall icon if no NOT_ATTEMPTED and no COMPLETED_SUCCESS have been seen
                    if ((!anyCompletedSuccess) && (!anyNotAttempted)) {
                        overallStatusIconText = thisAssetTransfer.getValue().getStatusIconText().get(COMPLETED_FAILED.toString());
                        Timber.tag(TAG).d("      ...setting overallTranserText -> COMPLETED_FAILED");
                    }
                    break;
                case COMPLETED_SUCCESS:
                    anyCompletedSuccess = true;
                    /// this will only be the overall icon if no NOT_ATTEMPTED has been seen
                    if (!anyNotAttempted){
                        overallStatusIconText = thisAssetTransfer.getValue().getStatusIconText().get(COMPLETED_SUCCESS.toString());
                        Timber.tag(TAG).d("      ...setting overallTranserText -> COMPLETED_SUCCESS");
                    }
                    break;
                default:
                    //should never see this, do nothing except log it
                    Timber.tag(TAG).w("  should never get here");
                    break;
            }
        }
        Timber.tag(TAG).d("   ...returning overallStatusIconText = " + overallStatusIconText);
        return overallStatusIconText;
    }

    public Boolean allTransfersAreComplete(){
        Timber.tag(TAG).d("allTransfersAreComplete = " + allTransferComplete);
        return allTransferComplete;
    }

    public void setVisible(){
        itemRow.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        overallStatus.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        itemRow.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        overallStatus.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone() {
        itemRow.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        overallStatus.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        title=null;
        overallStatus=null;
        response=null;
        itemRow=null;
    }

    ///
    /// View.OnClickInterface
    ///
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        response.itemRowClicked();
    }

    public interface Response {
        void itemRowClicked();
    }

}
