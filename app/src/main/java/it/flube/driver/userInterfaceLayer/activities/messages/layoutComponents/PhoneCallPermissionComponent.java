/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages.layoutComponents;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.flube.driver.R;
import timber.log.Timber;

/**
 * Created on 7/23/2018
 * Project : Driver
 */
public class PhoneCallPermissionComponent implements
    View.OnClickListener {
    private static final String TAG = "PhoneCallPermissionComponent";


    private TextView permissionText;
    private Button appInfoButton;
    private Response response;

    public PhoneCallPermissionComponent(AppCompatActivity activity, Response response){
        this.response = response;

        permissionText = (TextView) activity.findViewById(R.id.no_permission_text);

        appInfoButton = (Button) activity.findViewById(R.id.settings_button);
        appInfoButton.setOnClickListener(this);
    }

    public void setVisible(){
        permissionText.setVisibility(View.VISIBLE);
        appInfoButton.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        permissionText.setVisibility(View.INVISIBLE);
        appInfoButton.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        permissionText.setVisibility(View.GONE);
        appInfoButton.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        permissionText = null;
        appInfoButton = null;
        Timber.tag(TAG).d("components closed");
    }

    ///View.OnClick interface
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        response.appInfoButtonClicked();

    }

    public interface Response {
        void appInfoButtonClicked();
    }
}
