/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activityNavigator;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoDetailActivity;
import it.flube.driver.userInterfaceLayer.activities.offers.demoOffers.DemoOffersActivity;
import timber.log.Timber;

/**
 * Created on 2/24/2018
 * Project : Driver
 */

public class PhotoStepNavigator {
    private static final String TAG = "PhotoStepNavigator";

    public static void gotoActivityPhotoDetail(Context context, String photoRequestGuid){
        Intent i = new Intent(context, PhotoDetailActivity.class);
        i.putExtra(PhotoDetailActivity.PHOTO_REQUEST_GUID_KEY, photoRequestGuid);
        context.startActivity(i);
        Timber.tag(TAG).d("starting PhotoDetailActivity, photoRequestGuid -> " + photoRequestGuid);
    }
}
