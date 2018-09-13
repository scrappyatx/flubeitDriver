/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.account;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import timber.log.Timber;

/**
 * Created on 9/12/2018
 * Project : Driver
 */
public class AccountActivityLayoutComponents implements
    View.OnClickListener {
    private static final String TAG="AccountActivityLayoutComponents";

    private TextView profileDetail;
    private Button logoutButton;
    private TextView softwareVersion;
    private TextView buildFlavor;

    private Response response;
    private Boolean hasDriver;

    public AccountActivityLayoutComponents(AppCompatActivity activity, Response response){
        this.response = response;

        hasDriver = false;

        profileDetail = (TextView) activity.findViewById(R.id.account_profile_details);
        softwareVersion = (TextView) activity.findViewById(R.id.account_software_version);
        buildFlavor = (TextView) activity.findViewById(R.id.account_build_flavor);
        logoutButton = (Button) activity.findViewById(R.id.account_logout_button);
        setInvisible();
        Timber.tag(TAG).d("created");
    }

    public void setValuesNoDriver(AppCompatActivity activity){
        hasDriver = false;

        softwareVersion.setText("version : " + BuildConfig.VERSION_NAME);
        buildFlavor.setText(BuildConfig.FLAVOR + " " + BuildConfig.BUILD_TYPE);
        Timber.tag(TAG).d("version -> %s", BuildConfig.VERSION_NAME);
        Timber.tag(TAG).d("flavor -> %s %s", BuildConfig.FLAVOR, BuildConfig.BUILD_TYPE);

        profileDetail.setText(activity.getResources().getString(R.string.account_profile_details_default));

    }

    public void setValuesDriver(Driver driver){
        hasDriver = true;

        softwareVersion.setText("version : " + BuildConfig.VERSION_NAME);
        buildFlavor.setText(BuildConfig.FLAVOR + " " + BuildConfig.BUILD_TYPE);
        Timber.tag(TAG).d("version -> %s", BuildConfig.VERSION_NAME);
        Timber.tag(TAG).d("flavor -> %s %s", BuildConfig.FLAVOR, BuildConfig.BUILD_TYPE);

        String details = "Name --> " + driver.getNameSettings().getDisplayName() + System.getProperty("line.separator")
                + "Email --> " + driver.getEmail() + System.getProperty("line.separator")+ System.getProperty("line.separator")
                + "Client ID --> "  + driver.getClientId() + System.getProperty("line.separator")+ System.getProperty("line.separator")
                + "Photo Url --> "  + driver.getPhotoUrl() + System.getProperty("line.separator")+ System.getProperty("line.separator")
                + "DisplayNumber --> " + driver.getPhoneSettings().getDisplayNumber() + System.getProperty("line.separator") + System.getProperty("line.separator")
                + "isDev --> " + driver.getUserRoles().getDev() + System.getProperty("line.separator")
                + "isQA --> " + driver.getUserRoles().getQa() + System.getProperty("line.separator")+ System.getProperty("line.separator")
                + "publicOffersNode --> " + driver.getCloudDatabaseSettings().getPublicOffersNode() + System.getProperty("line.separator")
                + "personalOffersNode --> " + driver.getCloudDatabaseSettings().getPersonalOffersNode() + System.getProperty("line.separator")
                + "demoOffersNode --> " + driver.getCloudDatabaseSettings().getDemoOffersNode() + System.getProperty("line.separator")
                + "scheduledBatchesNode --> " + driver.getCloudDatabaseSettings().getScheduledBatchesNode() + System.getProperty("line.separator")
                + "activeBatchesNode --> " + driver.getCloudDatabaseSettings().getActiveBatchNode() + System.getProperty("line.separator");

        Timber.tag(TAG).d("details -->" + details);

        profileDetail.setText(details);
    }

    public void setVisible(){
        Timber.tag(TAG).d("setVisible START...");
        profileDetail.setVisibility(View.VISIBLE);
        softwareVersion.setVisibility(View.VISIBLE);
        buildFlavor.setVisibility(View.VISIBLE);

        ///logout button is only visible if we HAVE a driver, and there is NO active batch
        if (hasDriver) {
            Timber.tag(TAG).d("...have driver");
            if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {
                Timber.tag(TAG).d("   ...and active batch, set button INVISIBLE");
                logoutButton.setVisibility(View.INVISIBLE);
            } else {
                Timber.tag(TAG).d("   ...and NO active batch, set button VISIBLE");
                logoutButton.setVisibility(View.VISIBLE);
            }
        } else {
            Timber.tag(TAG).d("...no driver, set button INVISIBLE");
            logoutButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setInvisible(){
        profileDetail.setVisibility(View.INVISIBLE);
        softwareVersion.setVisibility(View.INVISIBLE);
        buildFlavor.setVisibility(View.INVISIBLE);
        logoutButton.setVisibility(View.INVISIBLE);
    }

    public void setGone(){
        profileDetail.setVisibility(View.GONE);
        softwareVersion.setVisibility(View.GONE);
        buildFlavor.setVisibility(View.GONE);
        logoutButton.setVisibility(View.GONE);
    }

    public void close(){
        profileDetail = null;
        softwareVersion = null;
        buildFlavor = null;
        logoutButton = null;
        response = null;
        Timber.tag(TAG).d("close");
    }


    /// button response interface
    public void onClick(View v){
        Timber.tag(TAG).d("onClick");
        response.logoutButtonClicked();
    }

    public interface Response {
        void logoutButtonClicked();
    }
}
