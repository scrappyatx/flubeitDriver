/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.offers;

import android.content.Context;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import it.flube.driver.userInterfaceLayer.layoutComponents.LayoutComponentUtilities;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 9/7/2018
 * Project : Driver
 */
public class OfferRowListBuilder {
    private static final String TAG = "OfferRowListBuilder";

    public static ArrayList<OfferRowHolder> getOfferRowList(Context context, ArrayList<Batch> offerList){
        ///
        ///  Takes in an ArrayList<Batch> and returns an ArrayList<OfferRowHolder>
        ///  with the following properties
        ///
        ///  ArrayList<OfferRowHolder> are:
        ///     1. sorted by start time ascending (earlier offers first, later offers late)
        ///     2. displayHeader populated ("Today", "Tomorrow", etc)
        ///     3. showHeader is set to true/false based on whether the header should be displayed for that entry

        Map<Long, Batch> batchMap = new TreeMap<Long, Batch>();
        ArrayList<OfferRowHolder> offerRowList = new ArrayList<OfferRowHolder>();

        //iterate over offerList, and build batchMap where key is equal to start time
        Timber.tag(TAG).d("building batchMap...");
        for (Batch batch : offerList){
            batchMap.put(batch.getExpectedStartTime(), batch);
            Timber.tag(TAG).d("    expected start time -> " + batch.getExpectedStartTime().toString());
        }

        //put results into arraylist, will be sorted by sequence asending in the treemap
        Timber.tag(TAG).d("putting results in arrayList sorted by start time...");
        for (Map.Entry<Long, Batch> entry : batchMap.entrySet()){

            // create the offerRowHolder object
            OfferRowHolder offerRowHolder = new OfferRowHolder();
            offerRowHolder.setBatch(entry.getValue());
            offerRowHolder.setStartTime(entry.getValue().getExpectedStartTime());
            offerRowHolder.setDisplayHeader(LayoutComponentUtilities.getDisplayDate(context, BuilderUtilities.convertMillisToDate(entry.getValue().getExpectedStartTime())));
            offerRowHolder.setShowHeader(false);

            Timber.tag(TAG).d("   expected start time -> " + offerRowHolder.getStartTime().toString());

            offerRowList.add(offerRowHolder);
        }

        //how iterate through the offerRowHolder array, and determine which items need to have header displayed
        Timber.tag(TAG).d("determining which headers need to be displayed...");
        String lastDisplayHeader = "";

        for (OfferRowHolder offerRowHolder : offerRowList){

            if (offerRowHolder.getDisplayHeader().equals(lastDisplayHeader)) {
                // the header is the same as the one prior, so we DON'T need to show it
                offerRowHolder.setShowHeader(false);

            } else {
                // the header is not the same as the one prior, so we need to show it
                offerRowHolder.setShowHeader(true);
            }
            Timber.tag(TAG).d("   expected start time -> " + offerRowHolder.getStartTime().toString() + " show header -> " + offerRowHolder.getShowHeader().toString());
            //set this as the lastDisplayHeader
            lastDisplayHeader = offerRowHolder.getDisplayHeader();
        }

        return offerRowList;

    }

}
