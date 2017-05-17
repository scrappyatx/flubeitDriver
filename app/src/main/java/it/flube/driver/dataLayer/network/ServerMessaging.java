/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rollbar.android.Rollbar;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.EbEventOfferList;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.ably.lib.realtime.AblyRealtime;
import io.ably.lib.realtime.Channel;
import io.ably.lib.realtime.CompletionListener;
import io.ably.lib.rest.Auth;
import io.ably.lib.types.AblyException;
import io.ably.lib.types.ClientOptions;
import io.ably.lib.types.ErrorInfo;
import io.ably.lib.types.Message;

/**
 * Created by Bryan on 4/11/2017.
 */

public class ServerMessaging {
    private static final String TAG = "ServerMessaging";
    private static final String ABLY_ONDUTY_CHANNEL = "driverStatus";

    private static Channel mAblyOnDutyChannel = null;
    private static AblyRealtime mAblyRealTime = null;

    private boolean isConnected = false;


    public ServerMessaging(String clientId) {
        //setup Ably
        try {
            initAbly(clientId);
            isConnected = true;
            Log.d(TAG,"initAbly success");
        } catch (AblyException e) {
            isConnected = false;
            Log.d(TAG,"initAbly error");
            Rollbar.reportException(e, "warning", "intiAbly error -> " + e.getMessage());
            e.printStackTrace();
            //Rollbar.reportException(e, "critical", "initAbly() error");
        }
    }


    public boolean isConnected() {
        return isConnected;
    }

    private static void initAbly(String clientId) throws AblyException {
        ClientOptions clientOptions = new ClientOptions();
        clientOptions.authCallback = new Auth.TokenCallback() {


            @Override
            public Object getTokenRequest(Auth.TokenParams tokenParams) throws AblyException {
                try {
                    String httpAuthResponse = HttpMessagingDELETE.getAblyToken("58a87af8dd1eee33205d93b7");
                    Auth.TokenRequest tokenRequest = new Gson().fromJson(httpAuthResponse, Auth.TokenRequest.class);
                    Log.d(TAG,"*** Ably token request received from remote server ***");
                    return tokenRequest;
                } catch (IOException e) {
                    Log.d(TAG,"*** IOException error on Ably token request to remote server :" + e.toString());
                    Rollbar.reportException(e, "warning", "Ably token request to remote server error -> " + e.getMessage());
                    return null;
                }
            }
        };

        mAblyRealTime = new AblyRealtime(clientOptions);

        //mAblyRealTime = new AblyRealtime(ABLY_API_KEY);





        //subscribe to location channel, and get notifed when message is received
        mAblyOnDutyChannel = mAblyRealTime.channels.get(ABLY_ONDUTY_CHANNEL);
        mAblyOnDutyChannel.subscribe(new Channel.MessageListener() {
            @Override
            public void onMessage(Message message) {

                switch (message.name) {
                    case "offerlist":
                        Log.d(TAG,"*** CURRENT OFFERS HAVE ARRIVED!!! ***");
                        Log.d(TAG,"message arrived in ably channel: name->" + message.name + "  data ->" + message.data);
                        Gson mGson = new Gson();
                        ArrayList<Offer> mOfferList = mGson.fromJson(message.data.toString(),new TypeToken<List<Offer>>(){}.getType());

                        Log.d(TAG,"mOfferList has " + mOfferList.size() + " elements");


                        EventBus.getDefault().post(new EbEventOfferList(mOfferList));
                        Log.d(TAG,"mOfferList has been POSTED on the EventBus");

                        break;

                    default:
                        Log.d(TAG,"message arrived in ably channel: name->" + message.name + "  data ->" + message.data);
                        break;
                }
            }
        });
    }

    public static void sendDutyStatus(boolean dutyFlag) {
        // dutyFlag true = ON DUTY
        // dutyFlag false = OFF DUTY

        JSONObject mJSON = new JSONObject();

        try {
            mJSON.put("flube.it.flube.it.driver", "Fizzle Sparkcrank");
            mJSON.put("zone", "Austin");
            mJSON.put("onDuty", dutyFlag);
        } catch (JSONException e) {
            Log.d(TAG,"JSON exception while publishing message: " + e.getMessage());
            e.printStackTrace();
            Rollbar.reportException(e, "critical", "JSON exception in sendDutyStatus()");
        }

        try {
            //
            mAblyOnDutyChannel.publish("onDuty", mJSON, new CompletionListener() {
                @Override
                public void onError(ErrorInfo reason) {
                    Log.d(TAG,"Unable to publish message; err = " + reason.message);
                }

                @Override
                public void onSuccess() {
                    Log.d(TAG,"Message successfully sent");
                }
            });
        }
        catch (AblyException e) {
            Log.d(TAG,"initAbly publish error: " + e.getMessage());
            e.printStackTrace();
            Rollbar.reportException(e, "critical", "Ably publish error in sendDutyStatus()");
        }
    }

    public static void sendCurrentLocation(double myLat,double myLon) {
        JSONObject mJSON = new JSONObject();


        try {
            mJSON.put("flube.it.flube.it.driver", "Fizzle Sparkcrank");
            mJSON.put("zone", "Austin");
            mJSON.put("latitude", myLat);
            mJSON.put("longitude", myLon);

        } catch (JSONException e) {
            Log.d(TAG,"JSON exception while publishing message: " + e.getMessage());
            Rollbar.reportException(e, "warning", "JSON error when forming location packet -> " + e.getMessage());
            e.printStackTrace();
        }

        try {
            mAblyOnDutyChannel.publish("location", mJSON, new CompletionListener() {
                @Override
                public void onError(ErrorInfo reason) {
                    Log.d(TAG,"Unable to publish message; err = " + reason.message);
                }

                @Override
                public void onSuccess() {
                    Log.d(TAG,"Message successfully sent");
                }
            });
        }
        catch (AblyException e) {
            Log.d(TAG,"initAbly publish error: " + e.getMessage());
            Rollbar.reportException(e, "warning", "initAbly publish error -> " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void sendClaimOffer() {
        JSONObject mJSON = new JSONObject();

        try {
            mJSON.put("offerOID", "Fizzle Sparkcrank");
            mJSON.put("flube.it.flube.it.driver", "Fizzle Sparkcrank");
        } catch (JSONException e) {
            Log.d(TAG,"JSON exception while publishing message: " + e.getMessage());
            Rollbar.reportException(e, "warning", "JSON error when forming claimOffer packet -> " + e.getMessage());
            e.printStackTrace();
        }

        try {
            mAblyOnDutyChannel.publish("claimOffer", mJSON, new CompletionListener() {
                @Override
                public void onError(ErrorInfo reason) {
                    Log.d(TAG,"Unable to publish message; err = " + reason.message);
                }

                @Override
                public void onSuccess() {
                    Log.d(TAG,"Message successfully sent");
                }
            });
        }
        catch (AblyException e) {
            Log.d(TAG,"initAbly publish error: " + e.getMessage());
            Rollbar.reportException(e, "warning", "initAbly publish error -> " + e.getMessage());
            e.printStackTrace();
        }

    }

}
