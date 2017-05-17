/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.rollbar.android.Rollbar;

import it.flube.driver.R;
import it.flube.driver.dataLayer.network.ServerMessaging;
import it.flube.driver.userInterfaceLayer.activities.MainActivity;

import java.util.concurrent.TimeUnit;




/**
 * Created by Bryan on 4/2/2017.
 */

public class ServerCommService extends Service {
    private static final String TAG = "ServerCommService";

    private static final long UPDATE_INTERVAL_MS = TimeUnit.SECONDS.toMillis(5);
    private static final int SERVICE_ID = 101;

    private ServerMessaging mServerMessaging;

    private static final int FIVE_SECONDS = 1000 * 5;
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private static final int TEN_METERS = 0;

    private static final int REQUEST_ERROR = 0;

    private boolean mGoogleApiAvailable;
    private GoogleApiClient mGoogleApiClient;
    private MyLocationListener mLocationListener;
    private myConnectionCallback myConnectionCallback;
    private myConnectionFailure myConnectionFailureListener;


    @Override
    public void onCreate() {

        super.onCreate();

        Log.d(TAG, "onCreate() start");

        //establish communication to remote server
        mServerMessaging = new ServerMessaging("test");

        //connect to google play api for location services
        if (IsGoogleApiAvailable()) {
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }


        //start service in foreground
        startForeground(SERVICE_ID, getNotification());
        Log.d(TAG, "startForeground with notification");


        Log.d(TAG, "onCreate() complete");
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        // this gets called properly
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        //used only in case of bound services
        return null;
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, ServerCommService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"onStartCommand()");
        handleIntent(intent);
        return START_STICKY;
    }

    private Notification getNotification() {

        Resources resources = getResources();

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,i,0);

        RemoteViews notificationView = new RemoteViews(this.getPackageName(), R.layout.server_comm_service);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle("Test 1")
                .setTicker("ScrubApp7")
                .setContentText("Test 3")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                        Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContent(notificationView)
                .setContentIntent(pi)
                .setOngoing(true).build();

         return notification;
    }

    private void handleIntent(Intent i) {
        if (i.hasExtra("action")) {
            //no extras in this intent, do nothing
            String action = i.getStringExtra("action");

            Log.d(TAG,"handling intent:" + action);
            switch (action) {
                case "location":
                    //mServerMessaging.sendCurrentLocation();

                    break;

                case "onDuty":
                    //set duty status
                    mServerMessaging.sendDutyStatus(true);

                    //start location updates if google api client is avaialable
                    if (mGoogleApiAvailable) {
                        mGoogleApiClient.connect();
                    }

                    //schedule location updates
                    //setLocationUpdates(getApplicationContext(), true);
                    break;

                case "offDuty":
                    //cancel location updates
                    //setLocationUpdates(getApplicationContext(), false);
                    if (mGoogleApiAvailable) {
                        stopLocationUpdates();
                        mGoogleApiClient.disconnect();
                    }

                    //set duty status
                    mServerMessaging.sendDutyStatus(false);

                    //stop foreground
                    stopForeground(true);

                    //stop the service
                    stopSelf(SERVICE_ID);


                    break;
                default:
                    break;
            }
        } else {
            //no action specified in this intent, do nothing
            Log.d(TAG,"handling intent: no action");
        }
    }


    public static void setLocationUpdates(Context context, boolean isOn) {
        Intent i = ServerCommService.newIntent(context);
        i.putExtra("action","location");

        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),UPDATE_INTERVAL_MS, pi);
            Log.d(TAG,"starting scheduled location updates every " + Long.toString(UPDATE_INTERVAL_MS) + " ms");
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
            Log.d(TAG,"stopping scheduled location updates" );
        }
    }

    private boolean IsGoogleApiAvailable(){
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(getApplicationContext());

        if (errorCode == ConnectionResult.SUCCESS) {
            //we have api, return true
            mGoogleApiAvailable = true;
            Log.d(TAG,"Google API present");
            return true;
        } else {
            //we don't have google api, inform user via dialog

            //Dialog errorDialog = apiAvailability.getErrorDialog(mContext, errorCode, REQUEST_ERROR);
            //errorDialog.show();

            //TODO show dialog to user if google api not present
            mGoogleApiAvailable = false;
            Log.d(TAG,"Google API not present");
            return false;
        }
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        myConnectionCallback = new myConnectionCallback();
        myConnectionFailureListener = new myConnectionFailure();

        mGoogleApiClient.registerConnectionCallbacks(myConnectionCallback);
        mGoogleApiClient.registerConnectionFailedListener(myConnectionFailureListener);

    }

    private void startLocationUpdates() {
            //create pending intent
            //Intent i = ServerCommService.newIntent(getApplicationContext());
            //i.putExtra("action","location");
            //PendingIntent pi = PendingIntent.getService(getApplicationContext(), 0, i, 0);


            //create location request
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(FIVE_SECONDS);
            locationRequest.setSmallestDisplacement(TEN_METERS);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            mLocationListener = new MyLocationListener();

            //TODO check for location permissions, ask user to provide permission if we don't have them
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, mLocationListener);
                Log.d(TAG,"started location upates");
            } catch (SecurityException e) {
                Log.d(TAG, "security exception when starting location updates");
                Rollbar.reportException(e, "warning", "security exception when starting location updates");
            }
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,mLocationListener);
        Log.d(TAG,"stopped location updates");
    }

    public class MyLocationListener implements LocationListener
    {
        public void onLocationChanged(final Location myLoc)
        {
            mServerMessaging.sendCurrentLocation(myLoc.getLatitude(), myLoc.getLongitude());
            Log.d(TAG,"Got a fix: " + myLoc.toString());
        }
    }

    public class myConnectionCallback implements GoogleApiClient.ConnectionCallbacks {
        @Override
        public void onConnected(Bundle bundle) {
            Log.d(TAG,"connected to googleApi Location");
            startLocationUpdates();
        }

        @Override
        public void onConnectionSuspended(int i) {
            stopLocationUpdates();
            Log.d(TAG,"connection suspended");
        }

    }

    public class myConnectionFailure implements GoogleApiClient.OnConnectionFailedListener {
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            stopLocationUpdates();
            Log.d(TAG,"connection failed");
        }
    }
}


