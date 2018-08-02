/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.layoutComponents;

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
public class SignatureRequestRowLayoutComponents {
    private static final String TAG = "SignatureRequestRowLayoutComponents";

    private TextView title;
    private IconTextView status;

    private Boolean signatureComplete;
    private SignatureRequest signatureRequest;

    public SignatureRequestRowLayoutComponents(AppCompatActivity activity){
        title = (TextView) activity.findViewById(R.id.signature_request_row_title);
        status = (IconTextView) activity.findViewById(R.id.signature_request_status);

        //assume signatureComplete is true
        signatureComplete = true;
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
            title.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.GONE);
            status.setVisibility(View.GONE);
        }
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        title.setVisibility(View.INVISIBLE);
        status.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone() {
        title.setVisibility(View.GONE);
        status.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        title=null;
        status=null;
        signatureRequest=null;
    }

}
