/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.network;

import android.util.Log;

import com.google.gson.Gson;
import com.rollbar.android.Rollbar;

import it.flube.driver.dataLayer.interfaces.eventBusEvents.EbEventClientId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Bryan on 4/16/2017.
 */

public class HttpMessagingDELETE {
    private static final String TAG = "HttpMessagingDELETE";
    private static final String mURL = "https://api.cloudconfidant.com/concierge-oil-service/ably/token";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String failedResponse = "{\"result\":\"failed\"}";
    private static String mResponse;

    public static String getAblyToken(String clientId) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject mJSON = new JSONObject();

        try {
            mJSON.put("clientId", clientId);
        } catch (JSONException e) {
            Log.d(TAG, "JSON exception :" + e.getMessage());
            e.printStackTrace();
            Rollbar.reportException(e, "warning", "JSON error when forming clientID packet -> " + e.getMessage());
        }

        String jsonString = mJSON.toString();

        RequestBody body = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder()
                .url(mURL)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private class clientIdResponse {
        private String firstName;
        private String lastName;
        private String clientId;
        //private String imageBase64;
        public String getClientId() { return clientId; }
    }

    public static void getClientID(String username, String password) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JSONObject mJSON = new JSONObject();

        try {
            mJSON.put("profileName", username);
            mJSON.put("password", password);
        } catch (JSONException e) {
            Log.d(TAG, "JSON exception :" + e.getMessage());
            e.printStackTrace();
            Rollbar.reportException(e, "warning", "JSON error when forming profileName / password packet -> " + e.getMessage());
        }

        String jsonString = mJSON.toString();
        String mURL = "https://api.cloudconfidant.com/concierge-oil-service/ably/clientId";

        HttpUrl.Builder urlBuilder = HttpUrl.parse(mURL).newBuilder();
        urlBuilder.addQueryParameter("profileName", "test");
        urlBuilder.addQueryParameter("password", "password");
        String url = urlBuilder.build().toString();

        //RequestBody body = RequestBody.create(JSON, jsonString);
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onHttp call failed :" + e.getMessage());
                mResponse = failedResponse;
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    mResponse = failedResponse;
                    Log.d(TAG, "onHttp unexpected response :" + response.toString());
                    throw new IOException("Unexpected code " + response);

                } else {
                    // do something wih the result
                    Log.d(TAG, "onHttp sucess :" + response.toString());
                    Log.d(TAG, "response.body().contentType().toString():" + response.body().contentType().toString());
                    Log.d(TAG, "response.body().toString() :" + response.body().toString());

                    String myResponseTotal = response.body().string();

                    Log.d(TAG, "response.body().string() :" + myResponseTotal);

                    //TODO convert response to Json object here, then extract client ID
                    Log.d(TAG,"about to convert response to object :" + myResponseTotal);
                    clientIdResponse myResponse = new Gson().fromJson(myResponseTotal, clientIdResponse.class);
                    Log.d(TAG,"finished converting response to object :" + myResponseTotal);

                    String thisClientId = myResponse.getClientId();

                    EventBus.getDefault().post(new EbEventClientId(thisClientId));
                }
            }

        });
    }




    /*    client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onHttp call failed :" + e.getMessage());
                mResponse = failedResponse;
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    mResponse = failedResponse;
                    Log.d(TAG, "onHttp unexpected response :" + response.toString());
                    throw new IOException("Unexpected code " + response);

                } else {
                    // do something wih the result
                    Log.d(TAG, "onHttp sucess :" + response.toString());
                    Log.d(TAG, "response.body().contentType().toString():" + response.body().contentType().toString());
                    Log.d(TAG, "response.body().toString() :" + response.body().toString());
                    Log.d(TAG, "response.body().string() :" + response.body().string());
                    mResponse = response.body().string();
                }
            }

        });
        return mResponse;
        }
    */

}
