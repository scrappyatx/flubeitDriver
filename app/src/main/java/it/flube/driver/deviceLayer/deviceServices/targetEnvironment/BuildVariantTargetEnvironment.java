/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.targetEnvironment;

import it.flube.driver.BuildConfig;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import timber.log.Timber;

/**
 * Created on 8/24/2018
 * Project : Driver
 */
public class BuildVariantTargetEnvironment {
    private static final String TAG = "BuildVariantTargetEnvironment";

    private static final String PRODUCTION_TAG = "production";
    private static final String DEMO_TAG = "demo";
    private static final String STAGING_TAG = "staging";
    private static final String DEVELOPMENT_TAG = "develop";

    public static TargetEnvironmentConstants.TargetEnvironment getTargetEnvironment(){
        Timber.tag(TAG).d("getTargetEnvironment");
        Timber.tag(TAG).d("    BuildConfig.BUILD_TYPE -> " + BuildConfig.BUILD_TYPE);
        Timber.tag(TAG).d("    BuildConfig.FLAVOR     -> " + BuildConfig.FLAVOR);

        switch (BuildConfig.FLAVOR){
            case PRODUCTION_TAG:
                return TargetEnvironmentConstants.TargetEnvironment.PRODUCTION;
            case DEMO_TAG:
                return TargetEnvironmentConstants.TargetEnvironment.DEMO;
            case STAGING_TAG:
                return TargetEnvironmentConstants.TargetEnvironment.STAGING;
            case DEVELOPMENT_TAG:
                return TargetEnvironmentConstants.TargetEnvironment.DEVELOPMENT;
            default:
                /// this should never happen
                Timber.tag(TAG).w("   ...unrecognized flavor, BuildConfig.FLAVOR -> " + BuildConfig.FLAVOR);
                return TargetEnvironmentConstants.TargetEnvironment.DEVELOPMENT;
        }
    }

}
