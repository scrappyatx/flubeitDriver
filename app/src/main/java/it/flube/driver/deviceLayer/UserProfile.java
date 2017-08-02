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

    private OkHttpClient mClient;
    private UserProfileInterface.Response mResponse;

    public UserProfile() {}

    public void getDriverRequest(@NonNull String profileUrl, @NonNull String username, @NonNull String password, @NonNull UserProfileInterface.Response response) {
        mResponse = response;
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
            mResponse.getDriverFailure(e.getMessage());
        }
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull final IOException e) {
        Timber.tag(TAG).e(e);
        mResponse.getDriverFailure(e.getMessage());
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) {
        try {
            String rB = response.body().string();
            Timber.tag(TAG).d(TAG, "response.body().string() --> " + rB);

            if (response.isSuccessful()) {
                // put result into driver
                SuccessJSON rJ = new Gson().fromJson(rB, SuccessJSON.class);
                userPropertiesJSON pJ = new Gson().fromJson(rJ.getProperties(), userPropertiesJSON.class);

                Timber.tag(TAG).d("Client ID --> " + rJ.getClientId());
                Timber.tag(TAG).d("First name -> " + rJ.getFirstName());
                Timber.tag(TAG).d("Last name --> " + rJ.getLastName());
                Timber.tag(TAG).d("Email ------> " + rJ.getEmail());
                Timber.tag(TAG).d("Role -------> " + rJ.getRole());
                Timber.tag(TAG).d("is Driver? -> " + pJ.isDriver());
                Timber.tag(TAG).d("imageUrl ---> " + rJ.getImageUrl());

                Driver driver = new Driver();
                driver.setClientId(rJ.getClientId());
                driver.setFirstName(rJ.getFirstName());
                driver.setLastName(rJ.getLastName());
                //TODO should get display name from profile request response instead of building myself
                driver.setDisplayName(rJ.getFirstName() + " " + rJ.getLastName());

                driver.setEmail(rJ.getEmail());
                driver.setRole(rJ.getRole());
                driver.setPhotoUrl(rJ.getImageUrl());

                if (pJ.isDriver()) {
                    mResponse.getDriverSuccess(driver);
                } else {
                    mResponse.getDriverUserNotADriverFailure();
                }
            } else {
                //report failure
                final FailureJSON rJ = new Gson().fromJson(rB, FailureJSON.class);
                Timber.tag(TAG).d("Error Message --> " + rJ.getErrorMessage());
                Timber.tag(TAG).d("Error Code --> " + rJ.getErrorCode());
                Timber.tag(TAG).d("Documentation -> " + rJ.getDocumentation());


                if (rJ.getErrorCode().equals("401")) {
                    mResponse.getDriverAuthFailure(rJ.getErrorCode() + " : " + rJ.getErrorMessage());
                } else {
                    mResponse.getDriverFailure(rJ.getErrorCode() + " : " + rJ.getErrorMessage());
                }
            }
        } catch (final Exception e) {
            //report failure
            Timber.tag(TAG).e(e);
            mResponse.getDriverFailure(e.getMessage());
        }
    }

    //helper classes to parse JSON responses from server
    private class SuccessJSON {
        private String firstName;
        private String lastName;
        private String clientId;
        private String email;
        private String role;
        private String imageUrl;
        private String properties;
        String getEmail() { return email;}
        String getRole() { return role; }
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
        Boolean isDriver(){ return driver; }
    }

}


