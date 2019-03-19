/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.layoutComponents;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.Map;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.ReceiptRequest;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import timber.log.Timber;

/**
 * Created on 3/17/2019
 * Project : Driver
 */
public class TransactionIdLayoutComponent implements
        View.OnClickListener {
    public static final String TAG="TransactionIdLayoutComponent";

    private ConstraintLayout border;
    private TextView transactionIdView;
    private IconTextView transactionIdStatusView;

    private Response response;

    public TransactionIdLayoutComponent(AppCompatActivity activity, Response response){
        Timber.tag(TAG).d("TransactionIdLayoutComponent");
        this.response = response;

        border = (ConstraintLayout) activity.findViewById(R.id.transaction_id_row_item);
        border.setOnClickListener(this);
        border.setClickable(true);

        transactionIdView = (TextView) activity.findViewById(R.id.transaction_id_title);
        transactionIdStatusView = (IconTextView) activity.findViewById(R.id.transaction_id_status);
    }

    public void setValues(AppCompatActivity activity, String transactionId, ServiceOrderAuthorizePaymentStep.DataEntryStatus dataEntryStatus, Map<String, String> statusIconText){
        Timber.tag(TAG).d("setValues");
        Timber.tag(TAG).d("   transactionId -> %s", transactionId);
        Timber.tag(TAG).d("   data entry status -> %s", dataEntryStatus.toString());
        //set the icon text
        transactionIdStatusView.setText(statusIconText.get(dataEntryStatus.toString()));

        // set the transation id text
        switch (dataEntryStatus){
            case NOT_ATTEMPTED:
                transactionIdView.setText(activity.getResources().getString(R.string.transaction_id_row_text_not_attempted));
                break;
            case COMPLETE:
                transactionIdView.setText(String.format(activity.getResources().getString(R.string.transaction_id_row_text_complete), transactionId));
                break;
            default:
        }
    }

    public void setVisible(){
        Timber.tag(TAG).d("setVisible");
        border.setVisibility(View.VISIBLE);
        transactionIdView.setVisibility(View.VISIBLE);
        transactionIdStatusView.setVisibility(View.VISIBLE);

    }

    public void setInvisible(){
        Timber.tag(TAG).d("setInvisible");
        border.setVisibility(View.INVISIBLE);
        transactionIdView.setVisibility(View.INVISIBLE);
        transactionIdStatusView.setVisibility(View.INVISIBLE);
    }

    public void setGone(){
        Timber.tag(TAG).d("setGone");
        border.setVisibility(View.GONE);
        transactionIdView.setVisibility(View.GONE);
        transactionIdStatusView.setVisibility(View.GONE);
    }

    public void close(){
        Timber.tag(TAG).d("close");
        transactionIdView = null;
        transactionIdStatusView = null;
        border = null;
        response = null;
    }

    ///
    /// View.OnClickInterface
    ///
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        response.transactionIdRowClicked();
    }

    public interface Response {
        void transactionIdRowClicked();
    }

}
