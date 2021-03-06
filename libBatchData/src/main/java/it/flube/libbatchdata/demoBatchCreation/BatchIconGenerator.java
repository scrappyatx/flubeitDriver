/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import java.util.ArrayList;
import java.util.Random;

import it.flube.libbatchdata.constants.TargetEnvironmentConstants;

import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.EXPRESS_ICON_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.GENERIC_ICON_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.JIFFYLUBE_ICON_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.KWIKKAR_ICON_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.LONG_DISTANCE_ICON_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.MED_DISTANCE_ICON_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SERVICE_ONE_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SHORT_DISTANCE_ICON_URL_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.EXPRESS_ICON_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.GENERIC_ICON_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.JIFFYLUBE_ICON_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.KWIKKAR_ICON_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.LONG_DISTANCE_ICON_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.MED_DISTANCE_ICON_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SERVICE_ONE_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SHORT_DISTANCE_ICON_URL_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.EXPRESS_ICON_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.GENERIC_ICON_URL_PRODUCTON;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.JIFFYLUBE_ICON_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.KWIKKAR_ICON_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.LONG_DISTANCE_ICON_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.MED_DISTANCE_ICON_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SERVICE_ONE_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SHORT_DISTANCE_ICON_URL_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.EXPRESS_ICON_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.GENERIC_ICON_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.JIFFYLUBE_ICON_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.KWIKKAR_ICON_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.LONG_DISTANCE_ICON_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.MED_DISTANCE_ICON_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SERVICE_ONE_URL_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SHORT_DISTANCE_ICON_URL_STAGING;
import static it.flube.libbatchdata.constants.TargetEnvironmentConstants.DEFAULT_TARGET_ENVIRONMENT;


/**
 * Created on 4/23/2018
 * Project : Driver
 */
public class BatchIconGenerator {


    //// methods for getting a random distance indicator url
    ///public static String getRandomDistanceIndicatorUrl(){
    ///    return getRandomDistanceUrl(DEFAULT_TARGET_ENVIRONMENT);
    ///}

    public static String getRandomDistanceIndicatorUrl(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        return getRandomDistanceUrl(targetEnvironment);
    }

    /// methods for getting a random service provider icon url

    ///public static String getRandomIconUrl(){
    ///   return getRandomServiceProviderUrl(DEFAULT_TARGET_ENVIRONMENT);
    ///}

    public static String getRandomIconUrl(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        return getRandomServiceProviderUrl(targetEnvironment);
    }

    //// private methods that select constants based on target environment

    private static String getRandomDistanceUrl(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        String shortUrl;
        String medUrl;
        String longUrl;

        switch (targetEnvironment){
            case PRODUCTION:
                shortUrl = SHORT_DISTANCE_ICON_URL_PRODUCTION;
                medUrl = MED_DISTANCE_ICON_URL_PRODUCTION;
                longUrl = LONG_DISTANCE_ICON_URL_PRODUCTION;
                break;
            case DEMO:
                shortUrl = SHORT_DISTANCE_ICON_URL_DEMO;
                medUrl = MED_DISTANCE_ICON_URL_DEMO;
                longUrl = LONG_DISTANCE_ICON_URL_DEMO;
                break;
            case STAGING:
                shortUrl = SHORT_DISTANCE_ICON_URL_STAGING;
                medUrl = MED_DISTANCE_ICON_URL_STAGING;
                longUrl = LONG_DISTANCE_ICON_URL_STAGING;
                break;
            case DEVELOPMENT:
                shortUrl = SHORT_DISTANCE_ICON_URL_DEVELOPMENT;
                medUrl = MED_DISTANCE_ICON_URL_DEVELOPMENT;
                longUrl = LONG_DISTANCE_ICON_URL_DEVELOPMENT;
                break;
            default:
                shortUrl = SHORT_DISTANCE_ICON_URL_DEVELOPMENT;
                medUrl = MED_DISTANCE_ICON_URL_DEVELOPMENT;
                longUrl = LONG_DISTANCE_ICON_URL_DEVELOPMENT;
                break;
        }

        ArrayList<String> urlList = new ArrayList<String>();
        Random random = new Random();

        urlList.add(shortUrl);
        urlList.add(medUrl);
        urlList.add(longUrl);

        return urlList.get(random.nextInt(urlList.size()));
    }

    private static String getRandomServiceProviderUrl(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        String kwikkarUrl;
        String jiffyLubeUrl;
        String expressUrl;
        String serviceOneUrl;
        String genericUrl;

        switch (targetEnvironment){
            case PRODUCTION:
                kwikkarUrl = KWIKKAR_ICON_URL_PRODUCTION;
                jiffyLubeUrl = JIFFYLUBE_ICON_URL_PRODUCTION;
                expressUrl = EXPRESS_ICON_URL_PRODUCTION;
                serviceOneUrl = SERVICE_ONE_URL_PRODUCTION;
                genericUrl = GENERIC_ICON_URL_PRODUCTON;
                break;
            case DEMO:
                kwikkarUrl = KWIKKAR_ICON_URL_DEMO;
                jiffyLubeUrl = JIFFYLUBE_ICON_URL_DEMO;
                expressUrl = EXPRESS_ICON_URL_DEMO;
                serviceOneUrl = SERVICE_ONE_URL_DEMO;
                genericUrl = GENERIC_ICON_URL_DEMO;
                break;
            case STAGING:
                kwikkarUrl = KWIKKAR_ICON_URL_STAGING;
                jiffyLubeUrl = JIFFYLUBE_ICON_URL_STAGING;
                expressUrl = EXPRESS_ICON_URL_STAGING;
                serviceOneUrl = SERVICE_ONE_URL_STAGING;
                genericUrl = GENERIC_ICON_URL_STAGING;
                break;
            case DEVELOPMENT:
                kwikkarUrl = KWIKKAR_ICON_URL_DEVELOPMENT;
                jiffyLubeUrl = JIFFYLUBE_ICON_URL_DEVELOPMENT;
                expressUrl = EXPRESS_ICON_URL_DEVELOPMENT;
                serviceOneUrl = SERVICE_ONE_URL_DEVELOPMENT;
                genericUrl = GENERIC_ICON_URL_DEVELOPMENT;
                break;
            default:
                kwikkarUrl = KWIKKAR_ICON_URL_DEVELOPMENT;
                jiffyLubeUrl = JIFFYLUBE_ICON_URL_DEVELOPMENT;
                expressUrl = EXPRESS_ICON_URL_DEVELOPMENT;
                serviceOneUrl = SERVICE_ONE_URL_DEVELOPMENT;
                genericUrl = GENERIC_ICON_URL_DEVELOPMENT;
                break;
        }

        /// create a list of the service provider logo urls
        ArrayList<String> urlList = new ArrayList<String>();
        Random random = new Random();

        urlList.add(kwikkarUrl);
        urlList.add(jiffyLubeUrl);
        urlList.add(expressUrl);
        urlList.add(serviceOneUrl);
        urlList.add(genericUrl);

        /// randomly return one from the list
        return urlList.get(random.nextInt(urlList.size()));
    }

    public static String getJiffyLubeIconUrl(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        String jiffyLubeUrl;

        switch (targetEnvironment){
            case PRODUCTION:
                jiffyLubeUrl = JIFFYLUBE_ICON_URL_PRODUCTION;
                break;
            case DEMO:
                jiffyLubeUrl = JIFFYLUBE_ICON_URL_DEMO;
                break;
            case STAGING:
                jiffyLubeUrl = JIFFYLUBE_ICON_URL_STAGING;
                break;
            case DEVELOPMENT:
                jiffyLubeUrl = JIFFYLUBE_ICON_URL_DEVELOPMENT;
                break;
            default:
                jiffyLubeUrl = JIFFYLUBE_ICON_URL_DEVELOPMENT;
                break;
        }
        return jiffyLubeUrl;
    }


}
