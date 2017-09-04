/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.interfaces.UserProfileInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public class UserProfile implements UserProfileInterface, Callback {
    private static final String TAG = "UserProfile";

    private static final String PUBLIC_OFFERS_NODE = "offers";
    private static final String PERSONAL_OFFERS_NODE = "personalOffers";
    private static final String DEMO_OFFERS_NODE = "demoOffers";
    private static final String SCHEDULED_BATCHES_NODE = "assignedBatches";
    private static final String ACTIVE_BATCH_NODE = "activeBatch";

    private UserProfileInterface.Response response;

    public UserProfile() {}

    public void getDriverRequest(@NonNull String profileUrl, @NonNull String username, @NonNull String password, @NonNull UserProfileInterface.Response response) {
        this.response = response;
        try {
            //build the request
            HttpUrl.Builder uB = HttpUrl.parse(profileUrl).newBuilder();
            uB.addQueryParameter("profileName", username);
            uB.addQueryParameter("password", password);
            String requestUrl = uB.build().toString();
            Timber.tag(TAG).d("requestUrl = " + requestUrl);
            Request request = new Request.Builder().url(requestUrl).build();

            //initialize the http client & add logging
            HttpLoggingInterceptor li = new HttpLoggingInterceptor();
            li.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(li).build();

            //make the request ASYNC
            client.newCall(request).enqueue(this);
            Timber.tag(TAG).d("getDriverRequest SENT to remote server...");

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
            this.response.getDriverFailure(e.getMessage());
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull final IOException e) {
        Timber.tag(TAG).e(e);
        this.response.getDriverFailure(e.getMessage());
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull okhttp3.Response httpResponse) {
        try {
            String responseBody = httpResponse.body().string();
            Timber.tag(TAG).d(TAG, "httpResponse.body().string() --> " + responseBody);

            if (httpResponse.isSuccessful()) {
                // put result into driver
                SuccessJSON profileData = new Gson().fromJson(responseBody, SuccessJSON.class);
                userPropertiesJSON propertyData = new Gson().fromJson(profileData.getProperties(), userPropertiesJSON.class);

                Timber.tag(TAG).d("Client ID -------------> " + profileData.getClientId());
                Timber.tag(TAG).d("First name ------------> " + profileData.getFirstName());
                Timber.tag(TAG).d("Last name -------------> " + profileData.getLastName());
                Timber.tag(TAG).d("Email -----------------> " + profileData.getEmail());
                Timber.tag(TAG).d("imageUrl --------------> " + profileData.getImageUrl());

                Timber.tag(TAG).d("is Driver? ------------> " + propertyData.isDriver());
                Timber.tag(TAG).d("is Dev? ---------------> " + propertyData.isDev());
                Timber.tag(TAG).d("is QA? ----------------> " + propertyData.isQA());
                Timber.tag(TAG).d("publicOffersNode ------> " + propertyData.getPublicOffersNode());
                Timber.tag(TAG).d("personalOffersNode ----> " + propertyData.getPersonalOffersNode());
                Timber.tag(TAG).d("demoOffersNode --------> " + propertyData.getDemoOffersNode());
                Timber.tag(TAG).d("scheduledBatchesNode --> " + propertyData.getScheduledBatchesNode());
                Timber.tag(TAG).d("activeBatchNode -------> " + propertyData.getActiveBatchNode());

                if (propertyData.isDriver()) {
                    Driver driver = new Driver();

                    driver.setClientId(profileData.getClientId());
                    driver.setFirstName(profileData.getFirstName());
                    driver.setLastName(profileData.getLastName());
                    //TODO should get display name from profile request response instead of building myself
                    driver.setDisplayName(profileData.getFirstName() + " " + profileData.getLastName());
                    driver.setEmail(profileData.getEmail());
                    driver.setPhotoUrl(profileData.getImageUrl());

                    driver.setIsDev(propertyData.isDev());
                    driver.setIsQA(propertyData.isQA());
                    driver.setPublicOffersNode(propertyData.getPublicOffersNode());
                    driver.setPersonalOffersNode(propertyData.getPersonalOffersNode());
                    driver.setDemoOffersNode(propertyData.getDemoOffersNode());
                    driver.setScheduledBatchesNode(propertyData.getScheduledBatchesNode());
                    driver.setActiveBatchNode(propertyData.getActiveBatchNode());

                    response.getDriverSuccess(driver);
                } else {
                    response.getDriverUserNotADriverFailure();
                }
            } else {
                //report failure
                final FailureJSON failureData = new Gson().fromJson(responseBody, FailureJSON.class);
                Timber.tag(TAG).d("Error Message --> " + failureData.getErrorMessage());
                Timber.tag(TAG).d("Error Code --> " + failureData.getErrorCode());
                Timber.tag(TAG).d("Documentation -> " + failureData.getDocumentation());

                if (failureData.getErrorCode().equals("401")) {
                    response.getDriverAuthFailure(failureData.getErrorCode() + " : " + failureData.getErrorMessage());
                } else {
                    response.getDriverFailure(failureData.getErrorCode() + " : " + failureData.getErrorMessage());
                }
            }
        } catch (final Exception e) {
            //report failure
            Timber.tag(TAG).e(e);
            response.getDriverFailure(e.getMessage());
        }
    }

    //helper classes to parse JSON responses from server
    private class SuccessJSON {
        private String firstName;
        private String lastName;
        private String clientId;
        private String email;
        private String imageUrl;
        private String properties;
        String getEmail() { return email;}
        String getClientId() { return clientId; }
        String getFirstName() { return firstName; }
        String getLastName() { return lastName; }
        String getImageUrl() { return imageUrl; }
        String getProperties() { return properties; }
    }

    private class FailureJSON {
        private String errorMessage;
        private String errorCode;
        private String documentation;
        String getErrorMessage() { return errorMessage; }
        String getErrorCode() { return errorCode; }
        String getDocumentation() { return documentation; }
    }

    private class userPropertiesJSON {
        private Boolean driver;
        private Boolean dev;
        private Boolean qa;
        private String publicOffersNode;
        private String personalOffersNode;
        private String demoOffersNode;
        private String scheduledBatchesNode;
        private String activeBatchNode;

        Boolean isDriver(){ return (driver != null) ? driver : false; }
        Boolean isDev() { return (dev != null) ? dev : false;}
        Boolean isQA() { return (qa != null) ? qa : false; }
        String getPublicOffersNode() { return (publicOffersNode != null) ? publicOffersNode : PUBLIC_OFFERS_NODE;}
        String getPersonalOffersNode() {return (personalOffersNode != null) ? personalOffersNode : PERSONAL_OFFERS_NODE;}
        String getScheduledBatchesNode() {return (scheduledBatchesNode != null) ? scheduledBatchesNode : SCHEDULED_BATCHES_NODE;}
        String getActiveBatchNode() { return (activeBatchNode != null) ? activeBatchNode : ACTIVE_BATCH_NODE; }
        String getDemoOffersNode() { return (demoOffersNode != null) ? demoOffersNode : DEMO_OFFERS_NODE; }
    }

}


