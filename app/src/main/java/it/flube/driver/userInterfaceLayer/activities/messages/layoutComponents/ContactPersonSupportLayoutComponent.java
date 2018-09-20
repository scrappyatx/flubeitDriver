/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages.layoutComponents;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconButton;
import com.squareup.picasso.Picasso;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.ContactPerson;
import timber.log.Timber;

/**
 * Created on 7/6/2018
 * Project : Driver
 */
public class ContactPersonSupportLayoutComponent implements
    View.OnClickListener {
    private final String TAG="ContactPersonSupportLayoutComponent";

    ///
    ///     wrapper class for the layout file:
    ///     service_provider_person_customer.xml
    ///
    private static final String CALL_BUTTON_TAG = "callButton";
    private static final String TEXT_BUTTON_TAG = "textButton";

    private ImageView displayIcon;
    private TextView displayName;
    private TextView displayPhoneNumber;
    private IconButton callButton;
    private IconButton textButton;

    private ContactPerson contactPerson;
    private Response response;

    public ContactPersonSupportLayoutComponent(AppCompatActivity activity, Response response){
        this.response = response;

        displayIcon = (ImageView) activity.findViewById(R.id.support_display_icon);
        displayName = (TextView) activity.findViewById(R.id.support_display_name);
        displayPhoneNumber = (TextView) activity.findViewById(R.id.support_display_phone_number);

        callButton = (IconButton) activity.findViewById(R.id.support_call_button);
        callButton.setOnClickListener(this);
        callButton.setTag(CALL_BUTTON_TAG);

        textButton = (IconButton) activity.findViewById(R.id.support_text_button);
        textButton.setOnClickListener(this);
        textButton.setTag(TEXT_BUTTON_TAG);

        Timber.tag(TAG).d("...ContactPersonSupportLayoutComponent created");
    }

    public void setValues(ContactPerson contactPerson){

        Picasso.get()
                .load(contactPerson.getDisplayIconUrl())
                .fit()
                .centerInside()
                .into(displayIcon);


        displayName.setText(contactPerson.getDisplayName());

        ///
        /// we will display a phone number for flube.it support whether proxy or not
        ///
        if (contactPerson.getHasProxyPhoneNumber()) {
            displayPhoneNumber.setText(contactPerson.getProxyDisplayPhoneNumber());
        } else {
            displayPhoneNumber.setText(contactPerson.getDisplayPhoneNumber());
        }

        this.contactPerson = contactPerson;
    }

    public ContactPerson getContactPerson(){
        return this.contactPerson;
    }

    public void setVisible(){
        if (contactPerson != null) {
            displayIcon.setVisibility(View.VISIBLE);
            displayName.setVisibility(View.VISIBLE);
            displayPhoneNumber.setVisibility(View.VISIBLE);

            if (contactPerson.getCanVoice()) {
                callButton.setVisibility(View.VISIBLE);
            }

            if (contactPerson.getCanSMS()) {
                textButton.setVisibility(View.VISIBLE);
            }
        } else {
            Timber.tag(TAG).d("   ...can't set visible, contact person is null");
        }
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        displayIcon.setVisibility(View.INVISIBLE);
        displayName.setVisibility(View.INVISIBLE);
        displayPhoneNumber.setVisibility(View.INVISIBLE);
        callButton.setVisibility(View.INVISIBLE);
        textButton.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        displayIcon.setVisibility(View.GONE);
        displayName.setVisibility(View.GONE);
        displayPhoneNumber.setVisibility(View.GONE);
        callButton.setVisibility(View.GONE);
        textButton.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        displayIcon = null;
        displayName = null;
        displayPhoneNumber = null;
        callButton = null;
        textButton = null;
        contactPerson = null;
        Timber.tag(TAG).d("components closed");
    }

    /// View.OnClick listener
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        switch (v.getTag().toString()){
            case CALL_BUTTON_TAG:
                if (contactPerson.getHasProxyPhoneNumber()) {
                    response.supportCallButtonClicked(contactPerson.getProxyDialPhoneNumber());
                } else {
                    response.supportCallButtonClicked(contactPerson.getDialPhoneNumber());
                }
                break;
            case TEXT_BUTTON_TAG:
                if (contactPerson.getHasProxyPhoneNumber()) {
                    response.supportTextButtonClicked(contactPerson.getProxyDialPhoneNumber());
                } else {
                    response.supportTextButtonClicked(contactPerson.getDialPhoneNumber());
                }
                break;
        }

    }

    public interface Response {
        void supportCallButtonClicked(String dialPhoneNumber);

        void supportTextButtonClicked(String dialPhoneNumber);
    }

}
