/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.applicationLayer;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.picasso.Picasso;

import it.flube.driver.BuildConfig;
import timber.log.Timber;

/**
 * Created on 7/3/2017
 * Project : Driver
 */

public class AppInitialization {
    private final String TAG = "AppInitialization";
    private static final String instabug_auth_token = "66a3ed498b5c8845ee12ae07b3aa2553";

    private Context appContext;

    public AppInitialization(Context appContext){
        this.appContext = appContext;
    }

    void initializeLeakDetection(Application application, Context context) {
        if (BuildConfig.DEBUG) {
            //setup LeakCanary
            if (!LeakCanary.isInAnalyzerProcess(context)) {
                // only install LeakCanary if this process is NOT being used for heap analysis
                LeakCanary.install(application);
            }
        }
    }

    void setThreadPolicy(){
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
        }
    }

    void setVMPolicy(){
        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    //.penaltyDeath()
                    .build());
        }
    }

    void initializeAndCreateImageLoaderLogic(){
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.get().load(uri).placeholder(placeholder).into(imageView);
                //Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
                //Picasso.with(imageView.getContext()).load(object.get()).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.get().cancelRequest(imageView);
                //Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }
        });
    }

    void initializeBugReporting(Application application){
        new Instabug.Builder(application, instabug_auth_token)
                .setInvocationEvent(InstabugInvocationEvent.SHAKE)
                .build();
    }


}
