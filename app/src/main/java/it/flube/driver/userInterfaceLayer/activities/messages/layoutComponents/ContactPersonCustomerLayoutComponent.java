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
public class ContactPersonCustomerLayoutComponent implements
    View.OnClickListener {
    private final String TAG="ContactPersonCustomerLayoutComponent";

    ///
    ///     wrapper class for the layout file:
    ///     contact_person_customer.xml
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

    public ContactPersonCustomerLayoutComponent(AppCompatActivity activity, Response response){
        this.response = response;

        displayIcon = (ImageView) activity.findViewById(R.id.customer_display_icon);
        displayName = (TextView) activity.findViewById(R.id.customer_display_name);
        displayPhoneNumber = (TextView) activity.findViewById(R.id.customer_display_phone_number);

        callButton = (IconButton) activity.findViewById(R.id.customer_call_button);
        callButton.setOnClickListener(this);
        callButton.setTag(CALL_BUTTON_TAG);

        textButton = (IconButton) activity.findViewById(R.id.customer_text_button);
        textButton.setOnClickListener(this);
        textButton.setTag(TEXT_BUTTON_TAG);

        Timber.tag(TAG).d("...ContactPersonCustomerLayoutComponent created");
    }

    public void setValues(ContactPerson contactPerson){

        Picasso.get()
                .load(contactPerson.getDisplayIconUrl())
                .fit()
                .centerInside()
                .into(displayIcon);


        displayName.setText(contactPerson.getDisplayName());

        ////
        //// We will only display phone number for a customer IF there is a proxy phone number
        ////
        if (contactPerson.getHasProxyPhoneNumber()) {
            displayPhoneNumber.setText(contactPerson.getProxyDisplayPhoneNumber());
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

            if (contactPerson.getCanVoice() && contactPerson.getHasProxyPhoneNumber()) {
                callButton.setVisibility(View.VISIBLE);
            }

            if (contactPerson.getCanSMS() && contactPerson.getHasProxyPhoneNumber()) {
                textButton.setVisibility(View.VISIBLE);
            }
        } else {
            Timber.tag(TAG).d("   ...can't set visible, contact person is null");
            setInvisible();
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
        response = null;

        Timber.tag(TAG).d("components closed");
    }

    /// View.OnClick listener
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        switch (v.getTag().toString()){
            case CALL_BUTTON_TAG:
                response.customerCallButtonClicked(contactPerson.getProxyDialPhoneNumber());
                break;
            case TEXT_BUTTON_TAG:
                response.customerTextButtonClicked(contactPerson.getProxyDialPhoneNumber());
                break;
        }

    }

    public interface Response {
        void customerCallButtonClicked(String dialPhoneNumber);

        void customerTextButtonClicked(String dialPhoneNumber);
    }

}
