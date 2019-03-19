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
public class TransactionTotalLayoutComponent implements
        View.OnClickListener {
    public static final String TAG="TransactionTotalLayoutComponent";

    private ConstraintLayout border;
    private TextView transactionTotalView;
    private IconTextView transactionTotalStatusView;

    private Response response;

    public TransactionTotalLayoutComponent(AppCompatActivity activity, Response response){
        Timber.tag(TAG).d("TransactionTotalLayoutComponent");
        this.response = response;

        border = (ConstraintLayout) activity.findViewById(R.id.transaction_total_row_item);
        border.setOnClickListener(this);
        border.setClickable(true);

        transactionTotalView = (TextView) activity.findViewById(R.id.total_charged_title);
        transactionTotalStatusView = (IconTextView) activity.findViewById(R.id.total_charged_status);
    }

    public void setValues(AppCompatActivity activity, String transactionTotal, ServiceOrderAuthorizePaymentStep.DataEntryStatus dataEntryStatus, Map<String, String> statusIconText){
        Timber.tag(TAG).d("setValues");
        Timber.tag(TAG).d("   transactionTotal -> %s", transactionTotal);
        Timber.tag(TAG).d("   data entry status -> %s", dataEntryStatus.toString());
        //set the icon text
        transactionTotalStatusView.setText(statusIconText.get(dataEntryStatus.toString()));

        // set the transation id text
        switch (dataEntryStatus){
            case NOT_ATTEMPTED:
                transactionTotalView.setText(activity.getResources().getString(R.string.transaction_total_row_text_not_attempted));
                break;
            case COMPLETE:
                transactionTotalView.setText(String.format(activity.getResources().getString(R.string.transaction_total_row_text_complete), transactionTotal));
                break;
            default:
        }
    }


    public void setVisible(){
        Timber.tag(TAG).d("setVisible");
        border.setVisibility(View.VISIBLE);
        transactionTotalView.setVisibility(View.VISIBLE);
        transactionTotalStatusView.setVisibility(View.VISIBLE);

    }

    public void setInvisible(){
        Timber.tag(TAG).d("setInvisible");
        border.setVisibility(View.INVISIBLE);
        transactionTotalView.setVisibility(View.INVISIBLE);
        transactionTotalStatusView.setVisibility(View.INVISIBLE);
    }

    public void setGone(){
        Timber.tag(TAG).d("setGone");
        border.setVisibility(View.GONE);
        transactionTotalView.setVisibility(View.GONE);
        transactionTotalStatusView.setVisibility(View.GONE);
    }

    public void close(){
        Timber.tag(TAG).d("close");
        transactionTotalView = null;
        transactionTotalStatusView = null;
        border = null;
        response = null;
    }

    ///
    /// View.OnClickInterface
    ///
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        response.transactionTotalRowClicked();
    }

    public interface Response {
        void transactionTotalRowClicked();
    }

}
