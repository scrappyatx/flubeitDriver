/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.layoutComponents;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.SignatureRequest;
import timber.log.Timber;

/**
 * Created on 7/25/2018
 * Project : Driver
 */
public class SignatureRequestRowLayoutComponents implements
    View.OnClickListener {

    private static final String TAG = "SignatureRequestRowLayoutComponents";

    private TextView title;
    private IconTextView status;

    private ConstraintLayout signatureRow;
    private Boolean signatureComplete;
    private SignatureRequest signatureRequest;

    private Response response;

    public SignatureRequestRowLayoutComponents(AppCompatActivity activity, Response response){
        this.response = response;

        title = (TextView) activity.findViewById(R.id.signature_request_row_title);
        status = (IconTextView) activity.findViewById(R.id.signature_request_status);

        //assume signatureComplete is true
        signatureComplete = true;

        //set click handler for signature row
        signatureRow = (ConstraintLayout) activity.findViewById(R.id.signature_request_row_item);
        signatureRow.setClickable(true);
        signatureRow.setFocusable(true);
        signatureRow.setFocusableInTouchMode(true);
        signatureRow.setOnClickListener(this);

        Timber.tag(TAG).d("...created");
    }

    public void setValues(SignatureRequest signatureRequest){
        this.signatureRequest = signatureRequest;
        status.setText(signatureRequest.getStatusIconText().get(signatureRequest.getSignatureStatus().toString()));
        Timber.tag(TAG).d("...setValues");

        switch (signatureRequest.getSignatureStatus()){
            case COMPLETED_SUCCESS:
                signatureComplete = true;
                break;
            case COMPLETED_FAILED:
                signatureComplete = true;
                break;
            case NOT_ATTEMPTED:
                signatureComplete = false;
                break;
            default:
                signatureComplete = false;
                break;
        }
    }

    public Boolean signatureIsComplete(){
        Timber.tag(TAG).d("signatureIsComplete -> " + signatureComplete);
        return signatureComplete;
    }

    public void setVisible(){
        if (signatureRequest != null) {
            signatureRow.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
        } else {
            setGone();
        }
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        signatureRow.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        status.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone() {
        signatureRow.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        status.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        title=null;
        status=null;
        signatureRequest=null;
        signatureRow = null;
        response = null;
    }

    ///
    /// OnClickListener interface
    ///
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        response.signatureRowClicked(signatureRequest);
    }

    public interface Response {
        void signatureRowClicked(SignatureRequest signatureRequest);
    }

}
