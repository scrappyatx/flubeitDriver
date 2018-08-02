/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.layoutComponents;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconButton;
import com.squareup.picasso.Picasso;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.ContactPerson;
import timber.log.Timber;

/**
 * Created on 7/25/2018
 * Project : Driver
 */
public class ContactPersonLayoutComponent {
    private final String TAG="ContactPersonLayoutComponent";

    ///
    ///     wrapper class for the layout file:
    ///     contact_person_transfer_asset.xml
    ///

    private ImageView displayIcon;
    private TextView displayName;
    private TextView displayPhoneNumber;
    private IconButton callButton;
    private IconButton textButton;
    private TextView permissionText;
    private Button appInfoButton;

    private ContactPerson contactPerson;

    public ContactPersonLayoutComponent(AppCompatActivity activity){
        displayIcon = (ImageView) activity.findViewById(R.id.contact_display_icon);
        displayName = (TextView) activity.findViewById(R.id.contact_display_name);
        displayPhoneNumber = (TextView) activity.findViewById(R.id.contact_display_phone_number);
        callButton = (IconButton) activity.findViewById(R.id.contact_call_button);
        textButton = (IconButton) activity.findViewById(R.id.contact_text_button);

        permissionText = (TextView) activity.findViewById(R.id.no_permission_text);
        appInfoButton = (Button) activity.findViewById(R.id.app_info_button);
        Timber.tag(TAG).d("...ContactPersonSupportLayoutComponent created");
    }

    public void setValues(ContactPerson contactPerson){

        Picasso.get()
                .load(contactPerson.getDisplayIconUrl())
                .into(displayIcon);


        displayName.setText(contactPerson.getDisplayName());
        displayPhoneNumber.setText(contactPerson.getDisplayPhoneNumber());

        this.contactPerson = contactPerson;
    }

    public ContactPerson getContactPerson(){
        return this.contactPerson;
    }

    public void setVisible(Boolean hasPermission){
        Timber.tag(TAG).d("setVisible START...");
        if (hasPermission) {
            Timber.tag(TAG).d("...hasPermission TRUE");
            if (contactPerson != null) {
                Timber.tag(TAG).d("   ...we have a contact person");
                displayIcon.setVisibility(View.VISIBLE);
                displayName.setVisibility(View.VISIBLE);
                displayPhoneNumber.setVisibility(View.VISIBLE);

                permissionText.setVisibility(View.INVISIBLE);
                appInfoButton.setVisibility(View.INVISIBLE);

                if (contactPerson.getCanVoice()) {
                    callButton.setVisibility(View.VISIBLE);
                } else {
                    callButton.setVisibility(View.INVISIBLE);
                }

                if (contactPerson.getCanSMS()) {
                    textButton.setVisibility(View.VISIBLE);
                } else {
                    textButton.setVisibility(View.INVISIBLE);
                }
            } else {
                Timber.tag(TAG).d("   ...can't set visible, contact person is null");
                //permission text is invisible
                permissionText.setVisibility(View.INVISIBLE);
                appInfoButton.setVisibility(View.INVISIBLE);

                //contact info is invisible
                displayIcon.setVisibility(View.INVISIBLE);
                displayName.setVisibility(View.INVISIBLE);
                displayPhoneNumber.setVisibility(View.INVISIBLE);
                callButton.setVisibility(View.INVISIBLE);
                textButton.setVisibility(View.INVISIBLE);
            }
        } else {
            Timber.tag(TAG).d("...hasPermission FALSE");
            //permission text is visible
            permissionText.setVisibility(View.VISIBLE);
            appInfoButton.setVisibility(View.VISIBLE);

            //contact info is invisible
            displayIcon.setVisibility(View.INVISIBLE);
            displayName.setVisibility(View.INVISIBLE);
            displayPhoneNumber.setVisibility(View.INVISIBLE);
            callButton.setVisibility(View.INVISIBLE);
            textButton.setVisibility(View.INVISIBLE);
        }
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        displayIcon.setVisibility(View.INVISIBLE);
        displayName.setVisibility(View.INVISIBLE);
        displayPhoneNumber.setVisibility(View.INVISIBLE);
        callButton.setVisibility(View.INVISIBLE);
        textButton.setVisibility(View.INVISIBLE);
        permissionText.setVisibility(View.INVISIBLE);
        appInfoButton.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        displayIcon.setVisibility(View.GONE);
        displayName.setVisibility(View.GONE);
        displayPhoneNumber.setVisibility(View.GONE);
        callButton.setVisibility(View.GONE);
        textButton.setVisibility(View.GONE);
        permissionText.setVisibility(View.GONE);
        appInfoButton.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        displayIcon = null;
        displayName = null;
        displayPhoneNumber = null;
        callButton = null;
        textButton = null;
        permissionText = null;
        appInfoButton = null;

        contactPerson = null;
        Timber.tag(TAG).d("components closed");
    }

}
