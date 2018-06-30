/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoList;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 1/21/2018
 * Project : Driver
 */

public class PhotoRequestListLayoutComponents {

        public final static String TAG = "PhotoRequestListLayoutComponents";
        ///
        ///     wrapper class for the layout file:
        ///     batch_tab_details_viewgroup.xml
        ///
        private RecyclerView listView;
        private PhotoRequestListAdapter adapter;
        private Boolean okToFinish;


    public PhotoRequestListLayoutComponents(AppCompatActivity activity, PhotoRequestListAdapter.Response response){
        listView = (RecyclerView) activity.findViewById(R.id.photo_request_list_view);
        listView.setVisibility(View.INVISIBLE);

        adapter = new PhotoRequestListAdapter(activity, response);
        listView.setLayoutManager(new LinearLayoutManager(activity));
        listView.setAdapter(adapter);

        okToFinish = false;
        setInvisible();
        Timber.tag(TAG).d("...components created");
    }

    public Boolean readyToFinishStep(){
        return okToFinish;
    }

    public void setValues(HashMap<String, PhotoRequest> photoRequestList){
        Timber.tag(TAG).d("   setValues --> photo request list has " + photoRequestList.size() + " items");

        ///TODO maybe update apdater to take the native hashmap list instead of an array list, for now convert to an array list ordered by sequence
        Map<Integer, PhotoRequest> photoMap = new TreeMap<Integer, PhotoRequest>();
        ArrayList<PhotoRequest> photoList = new ArrayList<PhotoRequest>();

        //make a hashmap where key is equal to the sequence
        for (Map.Entry<String, PhotoRequest> entry : photoRequestList.entrySet()){
            photoMap.put(entry.getValue().getSequence(), entry.getValue());
        }

        //put results into arraylist, will be sorted by sequence asending in the treemap
        for (Map.Entry<Integer, PhotoRequest> entry : photoMap.entrySet()){
            photoList.add(entry.getValue());
        }

        adapter.updateList(photoList);

        //// loop through photoList, and see if every photo as at least an attempt
        //// if so, then set OK to finish
        okToFinish = true;
        for (PhotoRequest thisRequest : photoList) {
            if (thisRequest.getStatus().equals(PhotoRequest.PhotoStatus.NO_ATTEMPTS)) {
                /// a single photoRequest with a status of "NO ATTEMPTS" means we are not ready to finish
                okToFinish = false;
            }
        }
    }

    public ArrayList<PhotoRequest> getPhotoRequestList(){
        Timber.tag(TAG).d("getPhotoRequestList");
       return adapter.getPhotoRequestList();
    }

    public void setVisible(){
        listView.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("...set VISIBLE");
    }

    public void setInvisible(){
        listView.setVisibility(View.INVISIBLE);
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        listView.setVisibility(View.GONE);
        Timber.tag(TAG).d("...set GONE");
    }

    public void close(){
        listView = null;
        adapter = null;
    }

}
