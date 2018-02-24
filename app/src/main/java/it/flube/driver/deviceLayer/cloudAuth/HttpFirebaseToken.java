/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudAuth;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created on 6/19/2017
 * Project : Driver
 */

public class HttpFirebaseToken implements Callback {
    private static final String TAG = "HttpFirebaseToken";
    private TokenResponse mResponse;

    public HttpFirebaseToken() {
    }


    public void tokenRequest(@NonNull String tokenUrl, @NonNull String clientId, @NonNull TokenResponse response) {
        mResponse = response;
        try {

            //build the request
            HttpUrl.Builder uB = HttpUrl.parse(tokenUrl).newBuilder();
            uB.addQueryParameter("clientId", clientId);
            String requestUrl = uB.build().toString();
            Request request = new Request.Builder().url(requestUrl).get().build();

            //initialize the http client & add logging
            HttpLoggingInterceptor li = new HttpLoggingInterceptor();
            li.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(li).build();

            //make the request ASYNC
            client.newCall(request).enqueue(this);
            Timber.tag(TAG).d("*** requestFirebaseTokenAsync SENT to remote server...");

        } catch (final Exception e) {
            Timber.tag(TAG).e(e);
            mResponse.firebaseTokenFailure(e.getMessage());
        }
    }


    ////
    ////  Callbacks for OkHttpClient
    ////
    @Override
    public void onFailure(@NonNull Call call, @NonNull final IOException e) {
        Timber.tag(TAG).e(e);
        mResponse.firebaseTokenFailure(e.getMessage());
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull okhttp3.Response response) throws IOException {
        try {
            String rB = response.body().string();
            Timber.tag(TAG).d("response.body().string() --> " + rB);

            if (response.isSuccessful()) {
                // put result into driver
                final HttpFirebaseToken.SuccessJSON rJ = new Gson().fromJson(rB, HttpFirebaseToken.SuccessJSON.class);

                Timber.tag(TAG).d("token --> " + rJ.getToken());
                mResponse.firebaseTokenSuccess(rJ.getToken());
            } else {
                //report failure
                final HttpFirebaseToken.FailureJSON rJ = new Gson().fromJson(rB, HttpFirebaseToken.FailureJSON.class);
                Timber.tag(TAG).d("Error Message --> " + rJ.getErrorMessage());
                Timber.tag(TAG).d("Error Code --> " + rJ.getErrorCode());
                Timber.tag(TAG).d("Documentation -> " + rJ.getDocumentation());
                mResponse.firebaseTokenFailure(rJ.getErrorMessage());
                           }
        } catch (final Exception e) {
            Timber.tag(TAG).e(e);
            mResponse.firebaseTokenFailure(e.getMessage());

        }
    }

    ///
    ///  helper classes to parse JSON responses from server
    ///
    private class SuccessJSON {
        private String token;
        String getToken() { return token;}
    }

    private class FailureJSON {
        private String errorMessage;
        private String errorCode;
        private String documentation;
        String getErrorMessage() { return errorMessage; }
        String getErrorCode() { return errorCode; }
        String getDocumentation() { return documentation; }
    }

    ///
    /// interface to return results to a calling class
    ///
    public interface TokenResponse {
        void firebaseTokenSuccess(String token);

        void firebaseTokenFailure(String message);
    }
}
