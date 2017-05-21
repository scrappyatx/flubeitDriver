/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.network.toBeDeleted;

import android.util.Log;

import com.google.gson.Gson;
import com.rollbar.android.Rollbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created on 5/10/2017
 * Project : Driver
 */

public class HttpAblyTokenDELETE implements Callback {
    private static final String TAG = "HttpAblyTokenDELETE";
    private HttpAblyTokenCallbackDELETE mCallback;
    private OkHttpClient mClient;
    private static final MediaType mTypeJSON = MediaType.parse("application/json; charset=utf-8");

    public HttpAblyTokenDELETE(HttpAblyTokenCallbackDELETE callback) {
        mCallback = callback;

        //initialize the http client & add logging
        HttpLoggingInterceptor li = new HttpLoggingInterceptor();
        li.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient = new OkHttpClient().newBuilder().addInterceptor(li).build();
    }

    private Request buildAblyTokenRequest(String url, String clientId) {
        OkHttpClient client = new OkHttpClient();

        JSONObject mJSON = new JSONObject();

        try {
            mJSON.put("clientId", clientId);
        } catch (JSONException e) {
            Log.d(TAG, "JSON exception :" + e.getMessage());
            Rollbar.reportException(e, "warning", "JSON error when forming clientID packet -> " + e.getMessage());
            e.printStackTrace();
        }

        String jsonString = mJSON.toString();

        RequestBody body = RequestBody.create(mTypeJSON, jsonString);

        return new Request.Builder().url(url).post(body).build();
    }

    public void requestAblyTokenAsync(String url, String clientId) {
        Request request = buildAblyTokenRequest(url, clientId);
        mClient.newCall(request).enqueue(this);
        Log.d(TAG,"*** requestAblyTokenAsync SENT to remote server...");
    }

    public String requestAblyTokenBlocking(String url, String clientId) throws IOException {
        Request request =  buildAblyTokenRequest(url, clientId);
        Log.d(TAG,"*** requestAblyTokenBlocking SENT to remote server...");
        Response response = mClient.newCall(request).execute();
        Log.d(TAG,"*** requestAblyTokenBlocking RECEIVED from remote server");
        return response.body().string();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.d(TAG,"onFailure()");
        String errorMessage = e.getMessage();
        Log.d(TAG, "*** onHttp call failed -->" + errorMessage);
        e.printStackTrace();
        mCallback.requestAblyTokenFailure(errorMessage);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.d(TAG,"onResponse()");
        String rB = response.body().string();
        Log.d(TAG, "response.body().string() --> " + rB);

        if (response.isSuccessful()) {
            // put result into driver
            Log.d(TAG, "Token --> " + rB);
            mCallback.requestAblyTokenSuccess(rB);
        } else {
            //report failure
            FailureJSON rJ = new Gson().fromJson(rB,FailureJSON.class);
            Log.d(TAG, "Error Message --> " + rJ.getErrorMessage());
            Log.d(TAG, "Error Code --> " + rJ.getErrorCode());
            Log.d(TAG, "Documentation -> " + rJ.getDocumentation());

            mCallback.requestAblyTokenFailure(rJ.getErrorCode() + " : " + rJ.getErrorMessage());
            throw new IOException("Unexpected code " + response);
        }
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
