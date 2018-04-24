/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public class BatchIconGenerator {

    public static String getRandomDistanceIndicatorUrl(){
        ArrayList<String> urlList = new ArrayList<String>();
        Random random = new Random();
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/distanceIndicators%2Fdistance_long_ver2.png?alt=media&token=3e95c94d-9a53-4be3-acdb-7efc0ed83339");
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/distanceIndicators%2Fdistance_med_ver2.png?alt=media&token=dc311b19-d4f2-491d-af3b-f85fa5c0c019");
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/distanceIndicators%2Fdistance_short_ver2.png?alt=media&token=aad103c9-a6ca-42ef-b086-74547bafe92e");

        return urlList.get(random.nextInt(urlList.size()));
    }

    public static String getRandomIconUrl(){
        ArrayList<String> urlList = new ArrayList<String>();
        Random random = new Random();

        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Foil-change-icon.png?alt=media&token=b1599ce2-67ec-4bda-9a4b-d3a4ae8cdea3");
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Fkwikkar.PNG?alt=media&token=e60e8956-bb62-4758-abab-811cd1e6546b");
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Fjiffy-lube_ver2.png?alt=media&token=d4d79f18-1d4c-463f-98f3-11b3572bfd37");
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Fexpressoilchange.PNG?alt=media&token=4414e2f8-59ca-474d-95aa-4c7ee424481b");

        return urlList.get(random.nextInt(urlList.size()));
    }
}
