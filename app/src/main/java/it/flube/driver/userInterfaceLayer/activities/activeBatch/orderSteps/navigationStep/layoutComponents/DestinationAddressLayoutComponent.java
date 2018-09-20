/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.layoutComponents;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.AddressLocation;
import timber.log.Timber;

/**
 * Created on 9/19/2018
 * Project : Driver
 */
public class DestinationAddressLayoutComponent {
    private static final String TAG="DestinationAddressLayoutComponent";

    ///
    /// wrapper class for navigation_detail_address
    ///
    private TextView stepDestinationCaption;
    private TextView stepDestinationAddress;
    private AddressLocation addressLocation;

    public DestinationAddressLayoutComponent(AppCompatActivity activity){
        stepDestinationCaption = (TextView) activity.findViewById(R.id.nav_detail_destination_caption);
        stepDestinationAddress= (TextView) activity.findViewById(R.id.nav_detail_destination_address);
    }

    public void setValues(AddressLocation addressLocation){
        Timber.tag(TAG).d("setValues...");
        this.addressLocation = addressLocation;

        String addressText;
        if (addressLocation.getStreet2() == null) {
            addressText = addressLocation.getStreet1() + System.getProperty("line.separator")
                    + addressLocation.getCity() + ", " + addressLocation.getState() + " " + addressLocation.getZip();
        } else {
            addressText = addressLocation.getStreet1() + System.getProperty("line.separator")
                    + addressLocation.getStreet2() + System.getProperty("line.separator")
                    + addressLocation.getCity() + ", " + addressLocation.getState() + " " + addressLocation.getZip();
        }

        stepDestinationAddress.setText(addressText);
    }

    public void setVisible(){
        if (addressLocation != null){
            stepDestinationCaption.setVisibility(View.VISIBLE);
            stepDestinationAddress.setVisibility(View.VISIBLE);
        } else {
            setInvisible();
        }
    }

    public void setInvisible(){
        stepDestinationCaption.setVisibility(View.INVISIBLE);
        stepDestinationAddress.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("setInvisible");
    }

    public void setGone(){
        stepDestinationCaption.setVisibility(View.GONE);
        stepDestinationAddress.setVisibility(View.GONE);
        Timber.tag(TAG).d("setGone");
    }

    public void close(){
        addressLocation=null;
        stepDestinationAddress=null;
        stepDestinationCaption=null;
        Timber.tag(TAG).d("close");
    }

}
