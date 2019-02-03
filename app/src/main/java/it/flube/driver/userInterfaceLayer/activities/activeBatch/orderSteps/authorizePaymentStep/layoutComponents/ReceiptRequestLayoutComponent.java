/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.layoutComponents;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.squareup.picasso.Picasso;

import java.io.File;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.ReceiptRequest;
import timber.log.Timber;

/**
 * Created on 8/13/2018
 * Project : Driver
 */
public class ReceiptRequestLayoutComponent implements
        View.OnClickListener {
    public static final String TAG="ReceiptRequestLayoutComponent";

    private ConstraintLayout border;
    private TextView title;
    private IconTextView status;
    private ImageView photo;

    private Response response;
    private ReceiptRequest receiptRequest;

    public ReceiptRequestLayoutComponent(AppCompatActivity activity, Response response){
        this.response = response;
        border = (ConstraintLayout) activity.findViewById(R.id.receipt_request_row_item);
        border.setEnabled(true);

        //border.setFocusable(true);
        //border.setFocusableInTouchMode(true);
        border.setOnClickListener(this);
        border.setClickable(true);

        title = (TextView) activity.findViewById(R.id.receipt_request_title);
        title.setText(activity.getResources().getString(R.string.authorize_payment_receipt_request_row_text));

        status = (IconTextView) activity.findViewById(R.id.receipt_request_status);
        photo = (ImageView) activity.findViewById(R.id.receipt_image_thumbnail);

        setInvisible();
        Timber.tag(TAG).d("created");
    }

    public void setValues(ReceiptRequest receiptRequest){
        this.receiptRequest = receiptRequest;

        status.setText(receiptRequest.getStatusIconText().get(receiptRequest.getReceiptStatus().toString()));

        if (receiptRequest.getHasDeviceFile()){
            //Picasso.get()
            //    .load(new File(receiptRequest.getDeviceAbsoluteFileName()))
            //    .into(photo);
        }

        Timber.tag(TAG).d("setValues");
    }

    public void setVisible(){
        Timber.tag(TAG).d("setVisible START...");
        if (receiptRequest != null) {
            Timber.tag(TAG).d("   ...we have a ReceiptRequest");
            border.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);

            if (receiptRequest.getHasDeviceFile()){
                Timber.tag(TAG).d("      ...there is a deviceFile");
                //not showing bitmap on this row
                photo.setVisibility(View.INVISIBLE);
            } else {
                Timber.tag(TAG).d("      ...there is NOT a deviceFile");
                photo.setVisibility(View.INVISIBLE);
            }
        } else {
            Timber.tag(TAG).d("   ...we do NOT have a ReceiptRequest");
            setInvisible();
        }
        Timber.tag(TAG).d("...setVisible COMPLETE");
    }

    public void setInvisible(){
        border.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        status.setVisibility(View.INVISIBLE);
        photo.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("setInvisible");
    }

    public void setGone(){
        border.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        status.setVisibility(View.GONE);
        photo.setVisibility(View.GONE);
        Timber.tag(TAG).d("setGone");
    }

    public void close(){
        border=null;
        title=null;
        status=null;
        photo=null;
        response=null;
        receiptRequest = null;
        Timber.tag(TAG).d("close");
    }


    ///
    /// View.OnClickInterface
    ///
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        response.receiptPhotoRowClicked(receiptRequest);
    }

    public interface Response {
        void receiptPhotoRowClicked(ReceiptRequest receiptRequest);
    }

}
