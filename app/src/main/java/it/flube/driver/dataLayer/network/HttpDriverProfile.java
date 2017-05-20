/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.network;

import android.util.Log;
import com.google.gson.Gson;
import java.io.IOException;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.callBacks.repositories.driver.DriverNetworkRepositoryCallback;
import it.flube.driver.modelLayer.interfaces.repositories.driverStorage.DriverNetworkRepository;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public class HttpDriverProfile implements DriverNetworkRepository, Callback {
    private static final String TAG = "HttpDriverProfile";

    private DriverNetworkRepositoryCallback mCallback;
    private DriverSingleton mDriver;
    private OkHttpClient mClient;

    public HttpDriverProfile(DriverNetworkRepositoryCallback callback) {
        mCallback = callback;

        //initialize the http client & add logging
        HttpLoggingInterceptor li = new HttpLoggingInterceptor();
        li.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient = new OkHttpClient().newBuilder().addInterceptor(li).build();
    }

    public void requestDriverProfile(DriverSingleton driver, String requestUrl, String username, String password) {
        //this is where the request result will be stored
        mDriver = driver;

        //build the request URL
        HttpUrl.Builder uB = HttpUrl.parse(requestUrl).newBuilder();
        uB.addQueryParameter("profileName", username);
        uB.addQueryParameter("password", password);

        String url = uB.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        //make the request to the remote server
        mClient.newCall(request).enqueue(this);
        Log.d(TAG,"*** requestDriverProfile SENT to remote server...");
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.d(TAG,"onFailure()");
        String errorMessage = e.getMessage();
        Log.d(TAG, "*** onHttp call failed -->" + errorMessage);
        e.printStackTrace();
        mCallback.requestDriverProfileFailure(errorMessage);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.d(TAG,"onResponse()");
        String rB = response.body().string();
        Log.d(TAG, "response.body().string() --> " + rB);

        if (response.isSuccessful()) {
            // put result into driver
            SuccessJSON rJ = new Gson().fromJson(rB, SuccessJSON.class);

            Log.d(TAG, "Client ID --> " + rJ.getClientId());
            Log.d(TAG, "First name --> " + rJ.getFirstName());
            Log.d(TAG, "Last name--> " + rJ.getLastName());

            mDriver.setClientId(rJ.getClientId());
            mDriver.setFirstName(rJ.getFirstName());
            mDriver.setLastName(rJ.getLastName());
            mDriver.setLoaded(true);
            mDriver.setOnDuty(false);

            mCallback.requestDriverProfileSuccess(mDriver);
        } else {
            //report failure
            FailureJSON rJ = new Gson().fromJson(rB,FailureJSON.class);
            Log.d(TAG, "Error Message --> " + rJ.getErrorMessage());
            Log.d(TAG, "Error Code --> " + rJ.getErrorCode());
            Log.d(TAG, "Documentation -> " + rJ.getDocumentation());

            if (rJ.getErrorCode().equals("401")) {
                mCallback.requestDriverProfileAuthenticationFailure(rJ.getErrorCode() + " : " + rJ.getErrorMessage());
            } else {
                mCallback.requestDriverProfileFailure(rJ.getErrorCode() + " : " + rJ.getErrorMessage());
            }

            throw new IOException("Unexpected code " + response);
        }
    }

    //helper classes to parse JSON responses from server
    private class SuccessJSON {
        private String firstName;
        private String lastName;
        private String clientId;
        String getClientId() { return clientId; }
        String getFirstName() { return firstName; }
        String getLastName() { return lastName; }
    }

    private class FailureJSON {
        private String errorMessage;
        private String errorCode;
        private String documentation;
        String getErrorMessage() { return errorMessage; }
        String getErrorCode() { return errorCode; }
        String getDocumentation() { return documentation; }
    }

}


