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
public class PhoneCallPermissionComponent {
    private static final String TAG = "PhoneCallPermissionComponent";


    private TextView permissionText;
    private Button settingsButton;

    public PhoneCallPermissionComponent(AppCompatActivity activity){
        permissionText = (TextView) activity.findViewById(R.id.no_permission_text);
        settingsButton = (Button) activity.findViewById(R.id.settings_button);
    }

    public void setVisible(){
        permissionText.setVisibility(View.VISIBLE);
        settingsButton.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        permissionText.setVisibility(View.INVISIBLE);
        settingsButton.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        permissionText.setVisibility(View.GONE);
        settingsButton.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        permissionText = null;
        settingsButton = null;
        Timber.tag(TAG).d("components closed");
    }
}
